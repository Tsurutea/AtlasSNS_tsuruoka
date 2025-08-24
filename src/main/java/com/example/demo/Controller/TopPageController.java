// src/main/java/com/example/demo/Controller/TopPageController.java
package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.Entity.Post;
import com.example.demo.Entity.User;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Service.FollowService;

@Controller
public class TopPageController {

    private final PostRepository postRepository;
    private final FollowService followService;

    public TopPageController(PostRepository postRepository,
                             FollowService followService) {
        this.postRepository = postRepository;
        this.followService = followService;
    }

    @GetMapping("/topPage")
    public String showTopPage(HttpSession session, Model model) {
        Long me = (Long) session.getAttribute("userId");
        if (me == null) return "redirect:/login";

        // フォロー一覧・フォロワー一覧
        List<User> followings = followService.getFollowings(me);
        List<User> followers  = followService.getFollowers(me);

        // 🔹 件数を model に渡す
        model.addAttribute("followingsCount", followings.size());
        model.addAttribute("followersCount", followers.size());

        // 投稿一覧の取得（フォローしている人＋自分）
        List<Long> ids = new ArrayList<>();
        ids.add(me);
        ids.addAll(followings.stream().map(User::getId).toList());

        List<Post> posts = ids.isEmpty()
                ? List.of()
                : postRepository.findByUser_IdInAndDeletedAtIsNullOrderByCreatedAtDesc(ids);

        model.addAttribute("posts", posts);
        model.addAttribute("followings", followings);
        model.addAttribute("followers", followers);

        return "topPage";
    }
}