// src/main/java/com/example/demo/Controller/MyPageController.java
package com.example.demo.Controller;

import static java.nio.file.StandardCopyOption.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.form.ProfileForm;

@Controller
public class MyPageController {

    private final UserRepository userRepository;

    @Value("${app.user-icon-dir:./user-icons}")
    private String userIconDir; // 相対でもOK。絶対に正規化して使う

    public MyPageController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model) {
        Long me = getSessionUserId(session);
        if (me == null) return "redirect:/login";

        User user = userRepository.findById(me).orElseThrow();

        if (".".equals(user.getIconImage())) user.setIconImage(null);

        ProfileForm form = new ProfileForm();
        form.setName(user.getName());
        form.setEmail(user.getEmail());
        form.setBio(user.getBio());

        model.addAttribute("me", user);
        model.addAttribute("form", form);
        return "mypage";
    }

    @PostMapping("/mypage/update")
    public String update(@Valid ProfileForm form, BindingResult binding,
                         HttpSession session, Model model) throws IOException {
        Long me = getSessionUserId(session);
        if (me == null) return "redirect:/login";

        User user = userRepository.findById(me).orElseThrow();

        // 入力エラー時は戻す
        if (binding.hasErrors()) {
            model.addAttribute("me", user);
            return "mypage";
        }

        // パスワード（任意・エンコーダーなしのまま）
        boolean hasPw = StringUtils.hasText(form.getNewPassword()) || StringUtils.hasText(form.getNewPasswordConfirm());
        if (hasPw) {
            if (!StringUtils.hasText(form.getNewPassword()) || !StringUtils.hasText(form.getNewPasswordConfirm())) {
                binding.rejectValue("newPassword", "blank", "新しいパスワードと確認を両方入力してください。");
                model.addAttribute("me", user);
                return "mypage";
            }
            if (form.getNewPassword().length() < 6) {
                binding.rejectValue("newPassword", "short", "パスワードは6文字以上にしてください。");
                model.addAttribute("me", user);
                return "mypage";
            }
            if (!form.getNewPassword().equals(form.getNewPasswordConfirm())) {
                binding.rejectValue("newPasswordConfirm", "mismatch", "確認用パスワードが一致しません。");
                model.addAttribute("me", user);
                return "mypage";
            }
            user.setPassword(form.getNewPassword()); // 開発用：平文保存
        }

        // 基本項目
        user.setName(form.getName());
        user.setEmail(form.getEmail());
        user.setBio(form.getBio());

        // ここが重要：保存先を絶対パスに正規化してから作成＆保存
        if (form.getIcon() != null && !form.getIcon().isEmpty()) {
            String ext = getExt(form.getIcon().getOriginalFilename());
            String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String filename = "user-" + me + "-" + ts + ext;

            Path baseDir = Paths.get(userIconDir).toAbsolutePath().normalize();
            Files.createDirectories(baseDir); // 無ければ作る（親もまとめて）

            Path saveTo = baseDir.resolve(filename);

            try (InputStream in = form.getIcon().getInputStream()) {
                Files.copy(in, saveTo, REPLACE_EXISTING); // 自前で書く：確実にこの絶対パスに保存
            }

            // 公開URL（Webから見えるパス）
            user.setIconImage("/user-icons/" + filename);
        } else if (!StringUtils.hasText(user.getIconImage()) || ".".equals(user.getIconImage())) {
            // 空や「.」ならデフォルトへ矯正（任意）
            user.setIconImage("/images/icon1.png");
        }

        userRepository.save(user);

        // ヘッダー表示用のセッション値を更新
        session.setAttribute("userName", user.getName());
        session.setAttribute("userIconImage", user.getIconImage());

        // 要望どおり：保存後はトップへ
        return "redirect:/topPage";
    }

    private String getExt(String name) {
        if (!StringUtils.hasText(name) || !name.contains(".")) return ".png";
        return name.substring(name.lastIndexOf("."));
    }

    private Long getSessionUserId(HttpSession session) {
        Object id = session.getAttribute("userId");
        if (id instanceof Long l) return l;
        if (id instanceof Integer i) return i.longValue();
        if (id instanceof String s) try { return Long.valueOf(s); } catch (NumberFormatException ignore) {}
        return null;
    }
}