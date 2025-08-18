package com.example.demo.Controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.Entity.User;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.FollowService;

@Controller
public class TopPageController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final FollowService followService;

    public TopPageController(UserRepository userRepository,
                             PostRepository postRepository,
                             FollowService followService) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.followService = followService;
    }

    // -------------------------
    // タイムライン画面
    // -------------------------
    @GetMapping("/topPage")
    public String timeline(HttpSession session, Model model) {
        model.addAttribute("posts",
                postRepository.findAllByDeletedAtIsNullOrderByCreatedAtDesc());

        Long me = (Long) session.getAttribute("userId");
        if (me != null) {
            model.addAttribute("followers", followService.getFollowers(me));
            model.addAttribute("followings", followService.getFollowings(me));
        }
        return "topPage";
    }

    // -------------------------
    // 他ユーザーのプロフィール画面
    // -------------------------
    @GetMapping("/users/{id}")
    public String userProfile(@PathVariable Long id, HttpSession session, Model model) {
        User target = userRepository.findById(id).orElseThrow();
        model.addAttribute("profileUser", target);

        model.addAttribute("profilePosts",
                postRepository.findByUser_IdAndDeletedAtIsNullOrderByCreatedAtDesc(id));

        Long me = (Long) session.getAttribute("userId");
        boolean following = (me != null && !me.equals(id)) && followService.isFollowing(me, id);
        model.addAttribute("isFollowing", following);

        if (me != null) {
            model.addAttribute("followers", followService.getFollowers(me));
            model.addAttribute("followings", followService.getFollowings(me));
        }
        return "otherAccountProfile";
    }
}