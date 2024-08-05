package com.benckw69.learningPlatform_java.User;

import jakarta.validation.constraints.NotEmpty;

public class TeacherEdit extends StudentOrAdminEdit {
    @NotEmpty(message = "用戶介紹不能為空")
    private String introduction;

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
