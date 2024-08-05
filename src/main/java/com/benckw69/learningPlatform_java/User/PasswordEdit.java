package com.benckw69.learningPlatform_java.User;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PasswordEdit {
    private String oldPassword;
    @NotEmpty(message = "密碼不能為空")
    @Size(min = 8, max = 20, message = "密碼長度需要在 8 至 20 個字元內")
    private String newPassword;
    @NotEmpty(message = "確認密碼不能為空")
    private String newPasswordRepeat;
    
    public String getOldPassword() {
        return oldPassword;
    }
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    public String getNewPasswordRepeat() {
        return newPasswordRepeat;
    }
    public void setNewPasswordRepeat(String newPasswordRepeat) {
        this.newPasswordRepeat = newPasswordRepeat;
    }

    
}
