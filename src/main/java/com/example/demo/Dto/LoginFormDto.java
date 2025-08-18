package com.example.demo.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginFormDto {

    @NotBlank(message = "・メールアドレスを入力してください")
@Size(min = 5, max = 40, message = "・メールアドレスは5文字以上40文字以内で入力してください")
@Email(message = "・有効なメールアドレスを入力してください")
private String email;

@NotBlank(message = "・パスワードを入力してください")
@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "・パスワードは英数字のみで入力してください")
@Size(min = 8, max = 20, message = "・パスワードは8文字以上20文字以内で入力してください")
private String password;

    // getter・setter
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}