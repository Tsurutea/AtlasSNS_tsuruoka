package com.example.demo.Controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Dto.UserUpdateDto;
import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;

@Controller
public class MyPageController {

    private final UserService userService;

    public MyPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/mypage")
    public String showMyPage(HttpSession session, Model model) {
        Long myId = (Long) session.getAttribute("userId");
        if (myId == null) {
            return "redirect:/login";
        }

        User me = userService.findById(myId);
        model.addAttribute("user", me);
        model.addAttribute("me", me);

        model.addAttribute("followings", userService.getFollowings(myId));
        model.addAttribute("followers", userService.getFollowers(myId));

        // フォーム用DTOをセット
        UserUpdateDto form = new UserUpdateDto();
        form.setName(me.getName());
        form.setEmail(me.getEmail());
        form.setBio(me.getBio());

        model.addAttribute("form", form);

        return "mypage";
    }

    @PostMapping("/mypage/update")
    public String updateProfile(@ModelAttribute("form") UserUpdateDto form, HttpSession session) {
        Long myId = (Long) session.getAttribute("userId");
        if (myId == null) {
            return "redirect:/login";
        }

        // ★ 戻り値を受け取る
        User updatedUser = userService.updateUserProfile(myId, form);

        // セッションに反映
        session.setAttribute("userName", updatedUser.getName());
        session.setAttribute("userIconImage", updatedUser.getIconImage());

        return "redirect:/mypage";
    }
}