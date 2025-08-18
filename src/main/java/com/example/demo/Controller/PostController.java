package com.example.demo.Controller;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Dto.PostDto;
import com.example.demo.Entity.Post;
import com.example.demo.Entity.User;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.FollowService;

@Controller
public class PostController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FollowService followService;

    public PostController(PostRepository postRepository,
                          UserRepository userRepository,
                          FollowService followService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.followService = followService;
    }

    // 投稿フォーム用 DTO
    @ModelAttribute("postDto")
    public PostDto postDto() {
        return new PostDto();
    }

    // ---------------------
    // 新規投稿
    // ---------------------
    @PostMapping("/newPost")
    public String createPost(
            @Valid @ModelAttribute("postDto") PostDto postDto,
            BindingResult result,
            HttpSession session,
            Model model) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return "redirect:/login";

        if (result.hasErrors()) {
            List<Post> posts = postRepository.findAllByDeletedAtIsNullOrderByCreatedAtDesc();
            model.addAttribute("posts", posts);

            model.addAttribute("followings", followService.getFollowings(userId));
            model.addAttribute("followers",  followService.getFollowers(userId));
            model.addAttribute("followingsCount", followService.countFollowings(userId));
            model.addAttribute("followersCount",  followService.countFollowers(userId));
            model.addAttribute("userName", session.getAttribute("userName"));

            return "topPage";
        }

        Post post = new Post();
        post.setUser(user);
        post.setContent(postDto.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post);

        return "redirect:/topPage";
    }

    // ---------------------
    // 編集フォーム表示
    // ---------------------
    @GetMapping("/editPost")
    public String showEditForm(@RequestParam("postId") Long postId,
                               HttpSession session,
                               Model model) {
        Post post = postRepository.findById(postId).orElse(null);
        Long userId = (Long) session.getAttribute("userId");

        if (post == null || !post.getUser().getId().equals(userId)) {
            return "redirect:/topPage"; // 不正アクセス防止
        }

        model.addAttribute("post", post);
        return "editPost";
    }

    // ---------------------
    // 投稿更新
    // ---------------------
    @PostMapping("/editPost")
    public String editPost(@RequestParam("postId") Long postId,
                           @RequestParam("content") String content,
                           HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        Post post = postRepository.findById(postId).orElse(null);

        if (post != null && post.getUser().getId().equals(userId)) {
            post.setContent(content);
            post.setUpdatedAt(LocalDateTime.now());
            postRepository.save(post);
        }

        return "redirect:/topPage";
    }

    // ---------------------
    // 投稿削除（論理削除のみ）
    // ---------------------
    @PostMapping("/deletePost")
    public String deletePost(@RequestParam("postId") Long postId,
                             @RequestParam(value = "redirectTo", required = false) String redirectTo,
                             HttpSession session) {
        Long me = (Long) session.getAttribute("userId");
        if (me == null) {
            return "redirect:/login";
        }

        postRepository.findById(postId).ifPresent(post -> {
            if (post.getUser().getId().equals(me)) {
                post.setDeletedAt(LocalDateTime.now()); // 論理削除
                postRepository.save(post);
            }
        });

        if ("profile".equals(redirectTo)) {
            return "redirect:/users/" + me;
        }
        return "redirect:/topPage";
    }
}