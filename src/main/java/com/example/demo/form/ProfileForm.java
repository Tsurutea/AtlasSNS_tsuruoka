package com.example.demo.form;

import org.springframework.web.multipart.MultipartFile;

public class ProfileForm {

    private String name;
    private String email;
    private String bio;

    // 画像アップロード
    private MultipartFile icon;

    // 追加: パスワード変更（任意）
    private String newPassword;
    private String newPasswordConfirm;

    // --- getters / setters ---
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public MultipartFile getIcon() { return icon; }
    public void setIcon(MultipartFile icon) { this.icon = icon; }

    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }

    public String getNewPasswordConfirm() { return newPasswordConfirm; }
    public void setNewPasswordConfirm(String newPasswordConfirm) { this.newPasswordConfirm = newPasswordConfirm; }
}