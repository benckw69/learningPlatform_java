package com.benckw69.learningPlatform_java.Search;

public class SearchUserRequest {
    

    private SearchUserMethod searchUserMethod;
    private String searchWords;

    public SearchUserMethod getSearchUserMethod() {
        return searchUserMethod;
    }
    public void setSearchUserMethod(SearchUserMethod searchUserMethod) {
        this.searchUserMethod = searchUserMethod;
    }
    public String getSearchWords() {
        return searchWords;
    }
    public void setSearchWords(String searchWords) {
        this.searchWords = searchWords;
    }
}
