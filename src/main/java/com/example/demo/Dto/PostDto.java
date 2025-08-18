package com.example.demo.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostDto {

    @NotBlank(message = "投稿内容は必須です")
    @Size(min = 1, max = 150, message = "1文字以上150文字以内で入力してください")
    private String content;

    // Getter & Setter
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}