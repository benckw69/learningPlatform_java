package com.benckw69.learningPlatform_java.Search;

import com.benckw69.learningPlatform_java.Course.Category;

public class SearchCourseRequest {
    private SearchCourseMethod searchCourseMethod;
    private String searchWords;
    private Category category;
    
    public SearchCourseMethod getSearchCourseMethod() {
        return searchCourseMethod;
    }
    public void setSearchCourseMethod(SearchCourseMethod searchCourseMethod) {
        this.searchCourseMethod = searchCourseMethod;
    }
    public String getSearchWords() {
        return searchWords;
    }
    public void setSearchWords(String searchWords) {
        this.searchWords = searchWords;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

}
