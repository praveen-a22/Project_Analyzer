package com.projectanalyzer.analyzer;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class ProjectScanner {

    private static final Set<String> IGNORED_FOLDERS = new HashSet<>(Arrays.asList(
            ".git", "node_modules", "target", "build", "dist", ".idea", ".vscode", ".mvn", "bin", "out", ".settings", ".gradle"
    ));

    public static class ScanResult {
        private int totalFiles;
        private int totalLines;
        private Map<String, Integer> languageCounts;
        private List<String> detectedLanguages;
        private List<String> buildSystems;
        private List<String> detectedFrameworks;
        private List<String> sourceDirectories;
        private List<String> testDirectories;
        private List<File> javaFiles;
        private List<File> configFiles;

        public ScanResult() {}

        public ScanResult(int totalFiles, int totalLines, Map<String, Integer> languageCounts, List<String> detectedLanguages, List<String> buildSystems, List<String> detectedFrameworks, List<String> sourceDirectories, List<String> testDirectories, List<File> javaFiles, List<File> configFiles) {
            this.totalFiles = totalFiles;
            this.totalLines = totalLines;
            this.languageCounts = languageCounts;
            this.detectedLanguages = detectedLanguages;
            this.buildSystems = buildSystems;
            this.detectedFrameworks = detectedFrameworks;
            this.sourceDirectories = sourceDirectories;
            this.testDirectories = testDirectories;
            this.javaFiles = javaFiles;
            this.configFiles = configFiles;
        }

        public int getTotalFiles() { return totalFiles; }
        public int getTotalLines() { return totalLines; }
        public Map<String, Integer> getLanguageCounts() { return languageCounts; }
        public List<String> getDetectedLanguages() { return detectedLanguages; }
        public List<String> getBuildSystems() { return buildSystems; }
        public List<String> getDetectedFrameworks() { return detectedFrameworks; }
        public List<String> getSourceDirectories() { return sourceDirectories; }
        public List<String> getTestDirectories() { return testDirectories; }
        public List<File> getJavaFiles() { return javaFiles; }
        public List<File> getConfigFiles() { return configFiles; }

        public static Builder builder() { return new Builder(); }

        public static class Builder {
            private int totalFiles;
            private int totalLines;
            private Map<String, Integer> languageCounts;
            private List<String> detectedLanguages;
            private List<String> buildSystems;
            private List<String> detectedFrameworks;
            private List<String> sourceDirectories;
            private List<String> testDirectories;
            private List<File> javaFiles;
            private List<File> configFiles;

            public Builder totalFiles(int totalFiles) { this.totalFiles = totalFiles; return this; }
            public Builder totalLines(int totalLines) { this.totalLines = totalLines; return this; }
            public Builder languageCounts(Map<String, Integer> languageCounts) { this.languageCounts = languageCounts; return this; }
            public Builder detectedLanguages(List<String> detectedLanguages) { this.detectedLanguages = detectedLanguages; return this; }
            public Builder buildSystems(List<String> buildSystems) { this.buildSystems = buildSystems; return this; }
            public Builder detectedFrameworks(List<String> detectedFrameworks) { this.detectedFrameworks = detectedFrameworks; return this; }
            public Builder sourceDirectories(List<String> sourceDirectories) { this.sourceDirectories = sourceDirectories; return this; }
            public Builder testDirectories(List<String> testDirectories) { this.testDirectories = testDirectories; return this; }
            public Builder javaFiles(List<File> javaFiles) { this.javaFiles = javaFiles; return this; }
            public Builder configFiles(List<File> configFiles) { this.configFiles = configFiles; return this; }

            public ScanResult build() {
                return new ScanResult(totalFiles, totalLines, languageCounts, detectedLanguages, buildSystems, detectedFrameworks, sourceDirectories, testDirectories, javaFiles, configFiles);
            }
        }
    }

    public ScanResult scanProject(File rootDir) {
        if (rootDir == null || !rootDir.exists() || !rootDir.isDirectory()) {
            return ScanResult.builder()
                    .totalFiles(0)
                    .totalLines(0)
                    .languageCounts(new HashMap<>())
                    .detectedLanguages(new ArrayList<>())
                    .buildSystems(new ArrayList<>())
                    .detectedFrameworks(new ArrayList<>())
                    .sourceDirectories(new ArrayList<>())
                    .testDirectories(new ArrayList<>())
                    .javaFiles(new ArrayList<>())
                    .configFiles(new ArrayList<>())
                    .build();
        }

        int totalFiles = 0;
        int totalLines = 0;
        Map<String, Integer> languageCounts = new HashMap<>();
        Set<String> buildSystems = new HashSet<>();
        Set<String> frameworks = new HashSet<>();
        Set<String> sourceDirs = new HashSet<>();
        Set<String> testDirs = new HashSet<>();
        List<File> javaFiles = new ArrayList<>();
        List<File> configFiles = new ArrayList<>();

        Queue<File> queue = new LinkedList<>();
        queue.add(rootDir);

        while (!queue.isEmpty()) {
            File current = queue.poll();
            File[] files = current.listFiles();
            if (files == null) continue;

            for (File file : files) {
                if (file.isDirectory()) {
                    if (!IGNORED_FOLDERS.contains(file.getName().toLowerCase())) {
                        String relPath = file.getAbsolutePath().replace(rootDir.getAbsolutePath(), "").replace("\\", "/");
                        if (relPath.contains("/test")) {
                            testDirs.add(relPath);
                        } else if (relPath.contains("/src") || relPath.contains("/main")) {
                            sourceDirs.add(relPath);
                        }
                        queue.add(file);
                    }
                } else {
                    totalFiles++;
                    String lang = LanguageDetector.detectLanguage(file);
                    if (!"Unknown".equals(lang)) {
                        languageCounts.put(lang, languageCounts.getOrDefault(lang, 0) + 1);
                    }

                    String buildTool = LanguageDetector.detectBuildTool(file);
                    if (buildTool != null) {
                        buildSystems.add(buildTool);
                        configFiles.add(file);
                    }

                    if ("Java".equals(lang)) {
                        javaFiles.add(file);
                        checkSpringFramework(file, frameworks);
                    }

                    totalLines += countLines(file);
                }
            }
        }

        if (javaFiles.size() > 0 && buildSystems.contains("Maven")) {
            frameworks.add("Spring Boot");
        }

        List<String> sortedLangs = new ArrayList<>(languageCounts.keySet());

        return ScanResult.builder()
                .totalFiles(totalFiles)
                .totalLines(totalLines)
                .languageCounts(languageCounts)
                .detectedLanguages(sortedLangs)
                .buildSystems(new ArrayList<>(buildSystems))
                .detectedFrameworks(new ArrayList<>(frameworks))
                .sourceDirectories(new ArrayList<>(sourceDirs))
                .testDirectories(new ArrayList<>(testDirs))
                .javaFiles(javaFiles)
                .configFiles(configFiles)
                .build();
    }

    private void checkSpringFramework(File file, Set<String> frameworks) {
        try {
            String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            if (content.contains("org.springframework.boot")) {
                frameworks.add("Spring Boot");
            }
            if (content.contains("org.springframework.web")) {
                frameworks.add("Spring MVC / Web");
            }
            if (content.contains("org.springframework.security")) {
                frameworks.add("Spring Security");
            }
        } catch (Exception ignored) {
        }
    }

    private int countLines(File file) {
        try {
            return FileUtils.readLines(file, StandardCharsets.UTF_8).size();
        } catch (Exception e) {
            return 0;
        }
    }
}
