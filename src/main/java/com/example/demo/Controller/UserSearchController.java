package com.example.demo.Controller;

import java.util.List;
import java.util.Set;

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

	// -------------------------
	// ユーザー検索画面
	// -------------------------
	@GetMapping("/userSearch")
	public String searchUsers(@RequestParam(required = false) String keyword,
	                          HttpSession session,
	                          Model model) {

	    Long me = (Long) session.getAttribute("userId");
	    List<User> userList;

	    if (keyword == null || keyword.trim().isEmpty()) {
	        userList = userRepository.findAll();
	    } else {
	        userList = userRepository.findByNameContainingIgnoreCase(keyword);
	    }

	    // ★ 自分を除外
	    if (me != null) {
	        userList = userList.stream()
	                           .filter(u -> !u.getId().equals(me))
	                           .toList();
	    }

	    model.addAttribute("userList", userList);
	    model.addAttribute("keyword", keyword);

	    if (me != null) {
	        model.addAttribute("followers", followService.getFollowers(me));
	        model.addAttribute("followings", followService.getFollowings(me));
	        model.addAttribute("followingIds", followService.getFollowingIds(me));
	    } else {
	        model.addAttribute("followers", List.of());
	        model.addAttribute("followings", List.of());
	        model.addAttribute("followingIds", Set.of());
	    }

	    if (userList.isEmpty()) {
	        model.addAttribute("message", "該当ユーザーがいません。");
	    }

	    return "userSearch";
	}
}