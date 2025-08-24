package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    // userId をフォローしているユーザー一覧を取る
    List<Follow> findByFollowedId(Long userId);

    // userId がフォローしているユーザー一覧を取る
    List<Follow> findByFollowerId(Long userId);

    // フォロー関係が存在するか確認する
    boolean existsByFollowerIdAndFollowedId(Long followerId, Long followedId);

    // フォロー解除
    void deleteByFollowerIdAndFollowedId(Long followerId, Long followedId);
}