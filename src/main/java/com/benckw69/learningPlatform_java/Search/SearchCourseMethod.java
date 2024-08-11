package com.benckw69.learningPlatform_java.Search;

public enum SearchCourseMethod {
    NAME("課程名稱"), 
    CATEGORY("課程類別"), 
    TEACHER("課程導師");

    private final String name;

    private SearchCourseMethod(String name){
        this.name = name;
    }

    public String getChineseName(){
        return name;
    }
}
