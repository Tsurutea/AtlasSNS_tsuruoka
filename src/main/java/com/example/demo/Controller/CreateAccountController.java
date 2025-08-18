package com.example.demo.Controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Dto.CreateAccountDto;
import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;

@Controller
public class CreateAccountController {

    private final UserService userService;

    @Autowired
    public CreateAccountController(UserService userService) {
        this.userService = userService;
    }

    // アカウント作成フォーム表示
    @GetMapping("/createAccount")
    public String showCreateAccountForm(Model model) {
        model.addAttribute("user", new CreateAccountDto());
        return "createAccount";
    }

    // 登録処理 → gotAccountへ
    @PostMapping("/createAccount")
    public String processCreateAccount(
            @Valid @ModelAttribute("user") CreateAccountDto dto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "createAccount";
        }

        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            model.addAttribute("passwordMismatch", "パスワードが一致しません");
            return "createAccount";
        }

        try {
            // ユーザー登録処理（DB保存）
            User savedUser = userService.registerUser(dto);
            // gotAccount画面にユーザー名を渡す
            model.addAttribute("userName", savedUser.getName());
            return "gotAccount";
        } catch (IllegalStateException e) {
            model.addAttribute("emailExistsError", e.getMessage());
            return "createAccount";
        }
    }

    // gotAccount → login 遷移ボタン処理
    @PostMapping("/goToLogin")
    public String goToLogin() {
        return "redirect:/login";
    }
}