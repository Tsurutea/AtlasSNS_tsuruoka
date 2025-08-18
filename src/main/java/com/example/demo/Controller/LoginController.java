package com.example.demo.Controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Dto.LoginFormDto;
import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    // ログイン画面表示
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new LoginFormDto());
        return "login";
    }

    // ログイン処理
    @PostMapping("/login")
    public String loginForm(@Valid @ModelAttribute("user") LoginFormDto user,
                            BindingResult result,
                            HttpSession session) {

        if (result.hasErrors()) {
            return "login";
        }

        // メールとパスワードで認証
        User foundUser = userService.authenticate(user.getEmail(), user.getPassword());

        if (foundUser == null) {
            result.reject("loginError", "メールアドレスまたはパスワードが正しくありません");
            return "login";
        }

        // セッションにユーザー情報を保存
        session.setAttribute("loginUser", foundUser); // ← 追加（Userオブジェクト全体）
        session.setAttribute("userName", foundUser.getName());
        session.setAttribute("userId", foundUser.getId());
        session.setAttribute("userIconImage", foundUser.getIconImage());

        return "redirect:/topPage";
    }
}