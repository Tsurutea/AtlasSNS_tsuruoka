package com.example.demo.Controller;

import java.util.List;
import java.util.Set;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;

@Controller
public class UserSearchController {

    private final UserService userService;

    public UserSearchController(UserService userService) {
        this.userService = userService;
    }

    /** ユーザー検索ページ表示（初期状態：全件表示） */
    @GetMapping("/userSearch")
    public String showUserSearch(Model model, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        List<User> allUsers = userService.getAllUsersExcept(loginUser.getId());
        Set<Long> followingIds = userService.getFollowingIds(loginUser.getId());

        // 追加：フォロー数とフォロワー数
        List<User> followings = userService.getFollowings(loginUser.getId());
        List<User> followers = userService.getFollowers(loginUser.getId());

        model.addAttribute("userList", allUsers);
        model.addAttribute("followingIds", followingIds);
        model.addAttribute("followings", followings);
        model.addAttribute("followers", followers);
        model.addAttribute("keyword", "");

        return "userSearch";
    }

    /** 検索結果の表示（部分一致） */
    @PostMapping("/userSearch")
    public String searchUsers(@RequestParam("keyword") String keyword, Model model, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        List<User> userList = userService.searchUsers(keyword, loginUser.getId());
        Set<Long> followingIds = userService.getFollowingIds(loginUser.getId());

        // 追加：フォロー数とフォロワー数
        List<User> followings = userService.getFollowings(loginUser.getId());
        List<User> followers = userService.getFollowers(loginUser.getId());

        model.addAttribute("userList", userList);
        model.addAttribute("followingIds", followingIds);
        model.addAttribute("followings", followings);
        model.addAttribute("followers", followers);
        model.addAttribute("keyword", keyword);

        return "userSearch";
    }
}