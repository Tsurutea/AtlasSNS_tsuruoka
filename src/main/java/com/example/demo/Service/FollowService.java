package com.example.demo.Service;

import java.util.List;
import java.util.Set;

import com.example.demo.Entity.User;

public interface FollowService {

    // フォロワー一覧を取得
    List<User> getFollowers(Long userId);

    // フォロー中一覧を取得
    List<User> getFollowings(Long userId);

    // ★ フォロー中ユーザーの ID だけを返す（TopPage用）
    Set<Long> getFollowingIds(Long userId);

    // フォローしているか判定
    boolean isFollowing(Long followerId, Long followingId);

    // フォロー処理
    void follow(Long followerId, Long followingId);

    // アンフォロー処理
    void unfollow(Long followerId, Long followingId);
}