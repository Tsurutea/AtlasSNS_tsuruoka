package com.example.demo.Controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Service.FollowService;

@Controller
public class FollowController {

    private final FollowService followService;

    // ★ 手書きコンストラクタでDI（Lombokなし）
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/follow")
    public String follow(@RequestParam("targetId") Long targetId, HttpSession session) {
        Long me = (Long) session.getAttribute("userId");
        if (me != null && !me.equals(targetId)) {
            followService.follow(me, targetId);
        }
        return "redirect:/userSearch"; // ← 検索ページに戻す
    }

    @PostMapping("/unfollow")
    public String unfollow(@RequestParam("targetId") Long targetId, HttpSession session) {
        Long me = (Long) session.getAttribute("userId");
        if (me != null && !me.equals(targetId)) {
            followService.unfollow(me, targetId);
        }
        return "redirect:/userSearch"; // ← 検索ページに戻す
    }
}