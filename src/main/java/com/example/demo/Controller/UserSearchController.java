package com.example.demo.Controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.FollowService;

@Controller
public class UserSearchController {

    private final UserRepository userRepository;
    private final FollowService followService;

    public UserSearchController(UserRepository userRepository,
                                FollowService followService) {
        this.userRepository = userRepository;
        this.followService = followService;
    }

    @GetMapping("/userSearch")
    public String searchUsers(@RequestParam(required = false) String keyword,
                              HttpSession session,
                              Model model) {

        Long me = (Long) session.getAttribute("userId");
        List<User> users;

        if (keyword == null || keyword.trim().isEmpty()) {
            // 初回アクセス/空入力 → 全件表示（自分は除外）
            users = userRepository.findAll();
        } else {
            // 部分一致（大文字小文字無視）
            users = userRepository.findByNameContainingIgnoreCase(keyword);
        }

        // 自分自身を結果から除外
        if (me != null) {
            users.removeIf(u -> u.getId().equals(me));
        }

        model.addAttribute("userList", users);
        model.addAttribute("keyword", keyword);

        if (me != null) {
            model.addAttribute("followers", followService.getFollowers(me));
            model.addAttribute("followings", followService.getFollowings(me));
            model.addAttribute("followingIds", followService.getFollowingIds(me));
        } else {
            model.addAttribute("followingIds", java.util.Collections.emptySet());
        }

        if (users.isEmpty()) {
            model.addAttribute("message", "該当ユーザーがいません。");
        }

        return "userSearch";
    }
}