package com.projectanalyzer.analyzer;

import java.io.File;
import java.util.*;

public class LanguageDetector {

    private static final Map<String, String> EXTENSION_MAP = new HashMap<>();

    static {
        EXTENSION_MAP.put("java", "Java");
        EXTENSION_MAP.put("js", "JavaScript");
        EXTENSION_MAP.put("jsx", "JavaScript");
        EXTENSION_MAP.put("ts", "TypeScript");
        EXTENSION_MAP.put("tsx", "TypeScript");
        EXTENSION_MAP.put("py", "Python");
        EXTENSION_MAP.put("c", "C");
        EXTENSION_MAP.put("h", "C/C++ Header");
        EXTENSION_MAP.put("cpp", "C++");
        EXTENSION_MAP.put("hpp", "C++");
        EXTENSION_MAP.put("html", "HTML");
        EXTENSION_MAP.put("htm", "HTML");
        EXTENSION_MAP.put("css", "CSS");
        EXTENSION_MAP.put("scss", "CSS");
        EXTENSION_MAP.put("sql", "SQL");
        EXTENSION_MAP.put("xml", "XML");
        EXTENSION_MAP.put("yml", "YAML");
        EXTENSION_MAP.put("yaml", "YAML");
        EXTENSION_MAP.put("json", "JSON");
        EXTENSION_MAP.put("kt", "Kotlin");
        EXTENSION_MAP.put("go", "Go");
        EXTENSION_MAP.put("cs", "C#");
    }

    public static String detectLanguage(File file) {
        String name = file.getName();
        int lastIndex = name.lastIndexOf('.');
        if (lastIndex > 0 && lastIndex < name.length() - 1) {
            String ext = name.substring(lastIndex + 1).toLowerCase();
            return EXTENSION_MAP.getOrDefault(ext, "Unknown");
        }
        return "Unknown";
    }

    public static String detectBuildTool(File file) {
        String name = file.getName().toLowerCase();
        if ("pom.xml".equals(name)) return "Maven";
        if (name.contains("build.gradle")) return "Gradle";
        if ("package.json".equals(name)) return "npm";
        if ("yarn.lock".equals(name)) return "yarn";
        if ("requirements.txt".equals(name) || "setup.py".equals(name)) return "pip";
        return null;
    }
}
