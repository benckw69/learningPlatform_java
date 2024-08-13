package com.benckw69.learningPlatform_java.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    
    @NotNull(message = "用戶類型不能為空")
    private Type type;
    @NotEmpty(message = "電郵地址不能為空")
    @Email(message = "電郵地址格式不正確")
    private String email;
    @NotEmpty(message = "用戶名稱不能為空")
    @Size(min = 4, max = 20, message = "用戶名稱長度需要在 4 至 20 個字元內")
    private String username;
    @NotEmpty(message = "密碼不能為空")
    @Size(min = 8, max = 20, message = "密碼長度需要在 8 至 20 個字元內")
    private String password;
    @NotEmpty(message = "確認密碼不能為空")
    private String password_repeat;
    private Integer referral;

    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword_repeat() {
        return password_repeat;
    }
    public void setPassword_repeat(String password_repeat) {
        this.password_repeat = password_repeat;
    }
    public Integer getReferral() {
        return referral;
    }
    public void setReferral(Integer referral) {
        this.referral = referral;
    }
}
