package com.example.demo.Controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.Entity.Post;
import com.example.demo.Entity.User;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.FollowService;

@Controller
public class FollowListController {

    @Autowired
    private FollowService followService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository; // ★ 追加

    // カード表示用 DTO（レコード）
    public record UserCard(User user, Post latestPost) {}

    // フォロー一覧 + タイムライン
    @GetMapping("/followList")
    public String followTimeline(HttpSession session, Model model) {
        Long me = getSessionUserId(session);
        if (me == null) return "redirect:/login";

        List<User> followings = followService.getFollowings(me);
        List<User> followers  = followService.getFollowers(me);

        // タイムライン（フォロー全員の全投稿）
        List<Long> ids = followings.stream().map(User::getId).toList();
        List<Post> posts = ids.isEmpty()
                ? List.of()
                : postRepository.findByUser_IdInAndDeletedAtIsNullOrderByCreatedAtDesc(ids);

        // 各ユーザーの最新1件
        List<UserCard> userCards = followings.stream()
            .map(u -> new UserCard(
                    u,
                    postRepository
                        .findFirstByUser_IdAndDeletedAtIsNullOrderByCreatedAtDesc(u.getId())
                        .orElse(null)
            ))
            .toList();

        model.addAttribute("listTitle", "フォロー中");
        model.addAttribute("users", followings);
        model.addAttribute("posts", posts);
        model.addAttribute("userCards", userCards);
        model.addAttribute("followings", followings); // 上部アイコン帯で使用可
        model.addAttribute("followers", followers);   // 上部アイコン帯で使用可

        return "followList";
    }

 // フォロワー一覧
    @GetMapping("/followerList")
    public String followerList(HttpSession session, Model model) {
        Long me = getSessionUserId(session);
        if (me == null) return "redirect:/login";

        List<User> followings = followService.getFollowings(me);
        List<User> followers  = followService.getFollowers(me);

        // ▼ 追加：タイムライン（フォロワー全員の全投稿）
        List<Long> followerIds = followers.stream().map(User::getId).toList();
        List<Post> posts = followerIds.isEmpty()
                ? List.of()
                : postRepository.findByUser_IdInAndDeletedAtIsNullOrderByCreatedAtDesc(followerIds);

        // （カードは要らない場合はこのブロックごと削除可）
        List<UserCard> userCards = followers.stream()
            .map(u -> new UserCard(
                    u,
                    postRepository
                        .findFirstByUser_IdAndDeletedAtIsNullOrderByCreatedAtDesc(u.getId())
                        .orElse(null)
            ))
            .toList();

        model.addAttribute("listTitle", "フォロワー");
        model.addAttribute("users", followers);
        model.addAttribute("posts", posts);            // ← 空じゃなく、ここに渡す
        model.addAttribute("followings", followings);
        model.addAttribute("followers", followers);
        model.addAttribute("userCards", userCards);    // カード不要なら外してOK

        return "followerList";
    }


    // セッションから userId を取り出す共通処理
    private Long getSessionUserId(HttpSession session) {
        Object id = session.getAttribute("userId");
        if (id instanceof Long l) return l;
        if (id instanceof Integer i) return i.longValue();
        if (id instanceof String s) try { return Long.valueOf(s); } catch (NumberFormatException ignore) {}
        return null;
    }
}