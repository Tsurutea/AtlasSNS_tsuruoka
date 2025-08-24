// src/main/java/com/example/demo/Controller/PostController.java
package com.example.demo.Controller;

import java.time.LocalDateTime;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Entity.Post;
import com.example.demo.Entity.User;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.UserRepository;

@Controller
public class PostController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostController(PostRepository postRepository,
                          UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // 投稿作成（topPage.html の form と一致させる）
    @PostMapping("/newPost")
    public String createPost(@RequestParam("content") String content,
                             HttpSession session,
                             RedirectAttributes ra) {
        Long me = (Long) session.getAttribute("userId");
        if (me == null) {
            return "redirect:/login";
        }
        if (content == null || content.trim().isEmpty()) {
            ra.addFlashAttribute("postError", "投稿内容を入力してください。");
            return "redirect:/topPage";
        }

        User userRef = new User();
        userRef.setId(me);

        Post p = new Post();
        p.setUser(userRef);
        p.setPost(content.trim());           // ← フィールド名は post
        p.setCreatedAt(LocalDateTime.now());
        p.setUpdatedAt(LocalDateTime.now());

        postRepository.save(p);
        return "redirect:/topPage";
    }

    // 投稿編集
    @PostMapping("/editPost")
    public String editPost(@RequestParam("postId") Long postId,
                           @RequestParam("content") String content,
                           HttpSession session,
                           RedirectAttributes ra) {
        Long me = (Long) session.getAttribute("userId");
        if (me == null) return "redirect:/login";

        Post p = postRepository.findById(postId).orElse(null);
        if (p == null || p.getDeletedAt() != null) {
            ra.addFlashAttribute("postError", "対象の投稿が見つかりません。");
            return "redirect:/topPage";
        }
        if (!p.getUser().getId().equals(me)) {
            ra.addFlashAttribute("postError", "自分の投稿のみ編集できます。");
            return "redirect:/topPage";
        }
        if (content == null || content.trim().isEmpty()) {
            ra.addFlashAttribute("postError", "内容を入力してください。");
            return "redirect:/topPage";
        }

        p.setPost(content.trim());
        p.setUpdatedAt(LocalDateTime.now());
        postRepository.save(p);

        return "redirect:/topPage";
    }

    // 投稿削除（ソフトデリート）
    @PostMapping("/deletePost")
    public String deletePost(@RequestParam("postId") Long postId,
                             HttpSession session,
                             RedirectAttributes ra) {
        Long me = (Long) session.getAttribute("userId");
        if (me == null) return "redirect:/login";

        Post p = postRepository.findById(postId).orElse(null);
        if (p == null || p.getDeletedAt() != null) {
            ra.addFlashAttribute("postError", "対象の投稿が見つかりません。");
            return "redirect:/topPage";
        }
        if (!p.getUser().getId().equals(me)) {
            ra.addFlashAttribute("postError", "自分の投稿のみ削除できます。");
            return "redirect:/topPage";
        }

        p.setDeletedAt(LocalDateTime.now());
        p.setUpdatedAt(LocalDateTime.now());
        postRepository.save(p);

        return "redirect:/topPage";
    }
}