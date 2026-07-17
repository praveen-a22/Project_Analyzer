package com.projectanalyzer.analyzer;

import com.projectanalyzer.entity.Issue;
import com.projectanalyzer.entity.IssueCategory;
import com.projectanalyzer.entity.IssueStatus;
import com.projectanalyzer.entity.Severity;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SecurityAnalyzer {

    public static class SecurityResult {
        private double score;
        private List<Issue> issues;

        public SecurityResult() {}

        public SecurityResult(double score, List<Issue> issues) {
            this.score = score;
            this.issues = issues;
        }

        public double getScore() { return score; }
        public List<Issue> getIssues() { return issues; }

        public static Builder builder() { return new Builder(); }

        public static class Builder {
            private double score;
            private List<Issue> issues;

            public Builder score(double score) { this.score = score; return this; }
            public Builder issues(List<Issue> issues) { this.issues = issues; return this; }

            public SecurityResult build() {
                return new SecurityResult(score, issues);
            }
        }
    }

    private static final Pattern HARDCODED_PASS_PATTERN = Pattern.compile(
            "(?i)(password|passwd|pwd|secret)\\s*=\\s*\"[^\"]{4,}\"", Pattern.CASE_INSENSITIVE
    );

    private static final Pattern API_KEY_PATTERN = Pattern.compile(
            "(?i)(api[_-]?key|aws[_-]?secret|jwt[_-]?secret|private[_-]?key)\\s*=\\s*\"[^\"]{8,}\"", Pattern.CASE_INSENSITIVE
    );

    private static final Pattern SQL_CONCAT_PATTERN = Pattern.compile(
            "(?i)(SELECT|INSERT|UPDATE|DELETE|FROM|WHERE)\\s+.*\\+\\s*[a-zA-Z0-9_]+", Pattern.CASE_INSENSITIVE
    );

    private static final Pattern UNSAFE_EXEC_PATTERN = Pattern.compile(
            "Runtime\\.getRuntime\\(\\)\\.exec|ProcessBuilder", Pattern.CASE_INSENSITIVE
    );

    private static final Pattern WEAK_HASH_PATTERN = Pattern.compile(
            "(?i)MessageDigest\\.getInstance\\(\"(MD5|SHA-1|DES)\"\\)", Pattern.CASE_INSENSITIVE
    );

    public SecurityResult analyze(List<File> files) {
        List<Issue> issues = new ArrayList<>();
        int criticalCount = 0;
        int highCount = 0;

        for (File file : files) {
            try {
                List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
                String fileName = file.getName();

                for (int i = 0; i < lines.size(); i++) {
                    String line = lines.get(i);
                    int lineNum = i + 1;

                    // 1. Hardcoded Passwords
                    Matcher passMatcher = HARDCODED_PASS_PATTERN.matcher(line);
                    if (passMatcher.find() && !line.contains("getProperty") && !line.contains("${")) {
                        criticalCount++;
                        issues.add(Issue.builder()
                                .severity(Severity.CRITICAL)
                                .category(IssueCategory.SECURITY)
                                .title("Hardcoded Password Detected")
                                .description("Potential hardcoded credential string literal found in source code.")
                                .filePath(fileName)
                                .lineNumber(lineNum)
                                .status(IssueStatus.OPEN)
                                .recommendation("Extract secret credentials into application.yml properties or system environment variables.")
                                .build());
                    }

                    // 2. Hardcoded API Keys / Secrets
                    Matcher keyMatcher = API_KEY_PATTERN.matcher(line);
                    if (keyMatcher.find() && !line.contains("getProperty") && !line.contains("${")) {
                        criticalCount++;
                        issues.add(Issue.builder()
                                .severity(Severity.CRITICAL)
                                .category(IssueCategory.SECURITY)
                                .title("Hardcoded Secret / API Key")
                                .description("Potential plaintext API Key or Private Secret embedded in code.")
                                .filePath(fileName)
                                .lineNumber(lineNum)
                                .status(IssueStatus.OPEN)
                                .recommendation("Store API keys in environment variables or external secret vaults.")
                                .build());
                    }

                    // 3. SQL Concatenation Injection Risk
                    Matcher sqlMatcher = SQL_CONCAT_PATTERN.matcher(line);
                    if (sqlMatcher.find()) {
                        highCount++;
                        issues.add(Issue.builder()
                                .severity(Severity.HIGH)
                                .category(IssueCategory.SECURITY)
                                .title("Potential SQL Injection Risk")
                                .description("Raw SQL query string concatenation detected without parameterized binding.")
                                .filePath(fileName)
                                .lineNumber(lineNum)
                                .status(IssueStatus.OPEN)
                                .recommendation("Use JPA Named Parameters (:param) or CriteriaBuilder instead of String concatenation.")
                                .build());
                    }

                    // 4. Unsafe Execution
                    Matcher execMatcher = UNSAFE_EXEC_PATTERN.matcher(line);
                    if (execMatcher.find()) {
                        highCount++;
                        issues.add(Issue.builder()
                                .severity(Severity.HIGH)
                                .category(IssueCategory.SECURITY)
                                .title("Unsafe System Execution")
                                .description("System command execution via Runtime.exec or ProcessBuilder detected.")
                                .filePath(fileName)
                                .lineNumber(lineNum)
                                .status(IssueStatus.OPEN)
                                .recommendation("Avoid OS command invocation or thoroughly sanitize and validate input arguments.")
                                .build());
                    }

                    // 5. Weak Hashing Algorithms
                    Matcher hashMatcher = WEAK_HASH_PATTERN.matcher(line);
                    if (hashMatcher.find()) {
                        issues.add(Issue.builder()
                                .severity(Severity.MEDIUM)
                                .category(IssueCategory.SECURITY)
                                .title("Weak Cryptographic Hash Algorithm")
                                .description("Usage of weak or deprecated hash algorithm (MD5/SHA-1/DES).")
                                .filePath(fileName)
                                .lineNumber(lineNum)
                                .status(IssueStatus.OPEN)
                                .recommendation("Upgrade hashing algorithm to SHA-256, SHA-512, or BCrypt.")
                                .build());
                    }
                }
            } catch (Exception ignored) {
            }
        }

        double score = 100.0 - (criticalCount * 12.0) - (highCount * 6.0);
        if (score < 30.0) score = 30.0;
        if (score > 100.0) score = 100.0;

        return SecurityResult.builder()
                .score(Math.round(score * 10.0) / 10.0)
                .issues(issues)
                .build();
    }
}
