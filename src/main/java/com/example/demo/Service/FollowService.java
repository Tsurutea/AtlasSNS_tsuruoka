// src/main/java/com/example/demo/Service/FollowService.java
package com.example.demo.Service;

import java.util.List;

import com.example.demo.Entity.User;

public interface FollowService {

    List<User> getFollowings(Long userId);   // 自分がフォローしている相手一覧
    List<User> getFollowers(Long userId);    // 自分をフォローしている相手一覧

    long countFollowings(Long userId);
    long countFollowers(Long userId);

    boolean isFollowing(Long meId, Long otherId);

    /** すでにフォロー済み or 自分自身 → false, 新規作成できたら true */
    boolean follow(Long meId, Long targetId);

    /** もともとフォローしていなければ false, 削除できたら true */
    boolean unfollow(Long meId, Long targetId);
}