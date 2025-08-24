package com.example.demo.Controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.Entity.Post;
import com.example.demo.Entity.User;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.FollowService;

@Controller
public class FollowController {

    private final FollowService followService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public FollowController(FollowService followService,
                            UserRepository userRepository,
                            PostRepository postRepository) {
        this.followService = followService;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @PostMapping("/follow")
    public String follow(@RequestParam("targetId") Long targetId,
                         HttpSession session,
                         HttpServletRequest request) {
        Long me = (Long) session.getAttribute("userId");
        if (me != null && !me.equals(targetId)) {
            followService.follow(me, targetId);
        }
        String referer = request.getHeader("Referer");
        return (referer != null && !referer.isBlank())
                ? "redirect:" + referer
                : "redirect:/users/" + targetId;
    }

    @PostMapping("/unfollow")
    public String unfollow(@RequestParam("targetId") Long targetId,
                           HttpSession session,
                           HttpServletRequest request) {
        Long me = (Long) session.getAttribute("userId");
        if (me != null && !me.equals(targetId)) {
            followService.unfollow(me, targetId);
        }
        String referer = request.getHeader("Referer");
        return (referer != null && !referer.isBlank())
                ? "redirect:" + referer
                : "redirect:/users/" + targetId;
    }

    // プロフィール表示（otherAccountProfile.html）
    @GetMapping("/users/{id}")
    public String showUser(@PathVariable("id") Long id, HttpSession session, Model model) {
        Long me = (Long) session.getAttribute("userId");
        if (me == null) return "redirect:/login";

        // プロフィール対象のユーザー
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // プロフィールユーザーの投稿一覧
        List<Post> profilePosts = postRepository
                .findByUser_IdAndDeletedAtIsNullOrderByCreatedAtDesc(id);

        // 最新投稿1件（必要なら）
        var latestPost = profilePosts.isEmpty() ? null : profilePosts.get(0);

        // ログインユーザーのフォロー・フォロワー
        List<User> followings = followService.getFollowings(me);
        List<User> followers  = followService.getFollowers(me);

        // ログインユーザーがこのユーザーをフォローしているか
        boolean isFollowing = followService.isFollowing(me, id);

        // View に渡す値
        model.addAttribute("profileUser", user);
        model.addAttribute("profilePosts", profilePosts);
        model.addAttribute("latestPost", latestPost);
        model.addAttribute("followings", followings);
        model.addAttribute("followers", followers);
        model.addAttribute("isFollowing", isFollowing);

        return "otherAccountProfile";  // ← HTML テンプレート
    }
}