package com.example.demo.Dto;

import org.springframework.web.multipart.MultipartFile;

public class UserUpdateDto {

    private String name;
    private String email;
    private String bio;

    private String currentPassword;
    private String newPassword;
    private String confirmPassword;

    private MultipartFile icon; // ★ 追加

    // --- Getter & Setter ---
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public MultipartFile getIcon() {
        return icon;
    }
    public void setIcon(MultipartFile icon) {
        this.icon = icon;
    }
}