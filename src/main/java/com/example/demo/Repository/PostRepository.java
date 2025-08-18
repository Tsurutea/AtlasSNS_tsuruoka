package com.example.demo.Repository;

import java.util.List;
import java.util.Optional; // ← これを忘れずに

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByDeletedAtIsNullOrderByCreatedAtDesc(); // 必要なら残す

    // タイムライン（複数ユーザーの全投稿）
    List<Post> findByUser_IdInAndDeletedAtIsNullOrderByCreatedAtDesc(List<Long> userIds);

    // プロフィール等（単一ユーザーの全投稿）
    List<Post> findByUser_IdAndDeletedAtIsNullOrderByCreatedAtDesc(Long userId);

    // ★ 各ユーザーの最新1件（Optionalで返す）
    Optional<Post> findFirstByUser_IdAndDeletedAtIsNullOrderByCreatedAtDesc(Long userId);
    // ※ findTopBy でも同じ意味
}