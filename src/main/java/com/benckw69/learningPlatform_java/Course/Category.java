package com.benckw69.learningPlatform_java.Course;

public enum Category {
    PROGRAMMING("程式"), 
    MUSIC("音樂"), 
    INVESTMENT("投資"), 
    LANGUAGE("語言"), 
    ART("藝術"), 
    DESIGN("設計"), 
    MARKETING("市場");

    private final String name;
    
    private Category(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
