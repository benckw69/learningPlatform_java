package com.benckw69.learningPlatform_java.SearchUser;

public class SearchUserRequest {
    

    public SearchMethod searchMethod;
    public String searchWords;

    public SearchMethod getSearchMethod() {
        return searchMethod;
    }
    public void setSearchMethod(SearchMethod searchMethod) {
        this.searchMethod = searchMethod;
    }
    public String getSearchWords() {
        return searchWords;
    }
    public void setSearchWords(String searchWords) {
        this.searchWords = searchWords;
    }
}
