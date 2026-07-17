package com.projectanalyzer.analyzer;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.projectanalyzer.entity.Issue;
import com.projectanalyzer.entity.IssueCategory;
import com.projectanalyzer.entity.IssueStatus;
import com.projectanalyzer.entity.Severity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class CodeQualityAnalyzer {

    public static class QualityResult {
        private double score;
        private double maintainabilityScore;
        private int totalClasses;
        private int totalMethods;
        private double avgMethodLength;
        private List<Issue> issues;

        public QualityResult() {}

        public QualityResult(double score, double maintainabilityScore, int totalClasses, int totalMethods, double avgMethodLength, List<Issue> issues) {
            this.score = score;
            this.maintainabilityScore = maintainabilityScore;
            this.totalClasses = totalClasses;
            this.totalMethods = totalMethods;
            this.avgMethodLength = avgMethodLength;
            this.issues = issues;
        }

        public double getScore() { return score; }
        public double getMaintainabilityScore() { return maintainabilityScore; }
        public int getTotalClasses() { return totalClasses; }
        public int getTotalMethods() { return totalMethods; }
        public double getAvgMethodLength() { return avgMethodLength; }
        public List<Issue> getIssues() { return issues; }

        public static Builder builder() { return new Builder(); }

        public static class Builder {
            private double score;
            private double maintainabilityScore;
            private int totalClasses;
            private int totalMethods;
            private double avgMethodLength;
            private List<Issue> issues;

            public Builder score(double score) { this.score = score; return this; }
            public Builder maintainabilityScore(double maintainabilityScore) { this.maintainabilityScore = maintainabilityScore; return this; }
            public Builder totalClasses(int totalClasses) { this.totalClasses = totalClasses; return this; }
            public Builder totalMethods(int totalMethods) { this.totalMethods = totalMethods; return this; }
            public Builder avgMethodLength(double avgMethodLength) { this.avgMethodLength = avgMethodLength; return this; }
            public Builder issues(List<Issue> issues) { this.issues = issues; return this; }

            public QualityResult build() {
                return new QualityResult(score, maintainabilityScore, totalClasses, totalMethods, avgMethodLength, issues);
            }
        }
    }

    public QualityResult analyze(List<File> javaFiles) {
        List<Issue> issues = new ArrayList<>();
        int totalClasses = 0;
        int totalMethods = 0;
        int totalMethodLines = 0;
        int longMethodsCount = 0;
        int largeClassesCount = 0;

        for (File file : javaFiles) {
            try {
                CompilationUnit cu = StaticJavaParser.parse(file);
                String relativePath = file.getName();

                List<ClassOrInterfaceDeclaration> classes = cu.findAll(ClassOrInterfaceDeclaration.class);
                totalClasses += classes.size();

                for (ClassOrInterfaceDeclaration clazz : classes) {
                    int classLines = clazz.getEnd().map(p -> p.line).orElse(0) - clazz.getBegin().map(p -> p.line).orElse(0);
                    if (classLines > 300) {
                        largeClassesCount++;
                        issues.add(Issue.builder()
                                .severity(Severity.MEDIUM)
                                .category(IssueCategory.CODE_QUALITY)
                                .title("Large Class Detected")
                                .description("Class '" + clazz.getNameAsString() + "' exceeds 300 lines of code (" + classLines + " lines). violates SRP.")
                                .filePath(relativePath)
                                .lineNumber(clazz.getBegin().map(p -> p.line).orElse(1))
                                .status(IssueStatus.OPEN)
                                .recommendation("Refactor large class into smaller single-responsibility domain components.")
                                .build());
                    }
                }

                List<MethodDeclaration> methods = cu.findAll(MethodDeclaration.class);
                totalMethods += methods.size();

                for (MethodDeclaration method : methods) {
                    int methodBegin = method.getBegin().map(p -> p.line).orElse(0);
                    int methodEnd = method.getEnd().map(p -> p.line).orElse(0);
                    int methodLength = methodEnd - methodBegin;
                    totalMethodLines += methodLength;

                    if (methodLength > 50) {
                        longMethodsCount++;
                        issues.add(Issue.builder()
                                .severity(Severity.HIGH)
                                .category(IssueCategory.CODE_QUALITY)
                                .title("Long Method")
                                .description("Method '" + method.getNameAsString() + "' contains " + methodLength + " lines, exceeding threshold of 50 lines.")
                                .filePath(relativePath)
                                .lineNumber(methodBegin)
                                .status(IssueStatus.OPEN)
                                .recommendation("Extract long method logic into smaller private helper methods or dedicated service objects.")
                                .build());
                    }

                    if (method.getBody().isPresent() && method.getBody().get().getStatements().isEmpty()) {
                        issues.add(Issue.builder()
                                .severity(Severity.LOW)
                                .category(IssueCategory.MAINTAINABILITY)
                                .title("Empty Method Body")
                                .description("Method '" + method.getNameAsString() + "' has an empty body.")
                                .filePath(relativePath)
                                .lineNumber(methodBegin)
                                .status(IssueStatus.OPEN)
                                .recommendation("Add implementation logic or document why the method is intentionally left empty.")
                                .build());
                    }
                }

            } catch (Exception ignored) {
            }
        }

        double avgMethodLength = totalMethods > 0 ? (double) totalMethodLines / totalMethods : 0.0;

        double score = 100.0 - (longMethodsCount * 5.0) - (largeClassesCount * 8.0);
        if (score < 40.0) score = 40.0;
        if (score > 100.0) score = 100.0;

        double maintainabilityScore = Math.min(100.0, Math.max(50.0, score + 3.0));

        return QualityResult.builder()
                .score(Math.round(score * 10.0) / 10.0)
                .maintainabilityScore(Math.round(maintainabilityScore * 10.0) / 10.0)
                .totalClasses(totalClasses)
                .totalMethods(totalMethods)
                .avgMethodLength(Math.round(avgMethodLength * 10.0) / 10.0)
                .issues(issues)
                .build();
    }
}
