// src/main/java/com/example/demo/Repository/FollowRepository.java
package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Entity.Follow;
import com.example.demo.Entity.User;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // 自分がフォローしているユーザー一覧（following -> followed）
    @Query("select f.followed from Follow f where f.following.id = :userId")
    List<User> findFollowingsByUserId(@Param("userId") Long userId);

    // 自分をフォローしているユーザー一覧（followed <- following）
    @Query("select f.following from Follow f where f.followed.id = :userId")
    List<User> findFollowersByUserId(@Param("userId") Long userId);

    @Query("select count(f) from Follow f where f.following.id = :userId")
    long countFollowings(@Param("userId") Long userId);

    @Query("select count(f) from Follow f where f.followed.id = :userId")
    long countFollowers(@Param("userId") Long userId);

    boolean existsByFollowing_IdAndFollowed_Id(Long followingId, Long followedId);

    void deleteByFollowing_IdAndFollowed_Id(Long followingId, Long followedId);
}