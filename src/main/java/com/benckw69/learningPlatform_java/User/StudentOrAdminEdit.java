package com.benckw69.learningPlatform_java.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class StudentOrAdminEdit {
    @NotEmpty(message = "電郵地址不能為空")
    @Email(message = "電郵地址格式不正確")
    private String email;
    @NotEmpty(message = "用戶名稱不能為空")
    @Size(min = 4, max = 20, message = "用戶名稱長度需要在 4 至 20 個字元內")
    private String username;
    @NotEmpty(message = "密碼不能為空")
    @Size(min = 8, max = 20, message = "密碼長度需要在 8 至 20 個字元內")
    private String password;

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
}
