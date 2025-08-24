package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	// 単一ユーザーの投稿（論理削除除外）
    List<Post> findByUser_IdAndDeletedAtIsNullOrderByCreatedAtDesc(Long userId);

    // 複数ユーザーの投稿（論理削除除外）※ List 受け取り
    List<Post> findByUser_IdInAndDeletedAtIsNullOrderByCreatedAtDesc(List<Long> userIds);

    // 最新1件（FollowListControllerで使用中）
    Optional<Post> findFirstByUser_IdAndDeletedAtIsNullOrderByCreatedAtDesc(Long userId);
}

