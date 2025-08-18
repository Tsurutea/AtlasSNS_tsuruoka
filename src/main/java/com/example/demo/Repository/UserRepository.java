package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    List<User> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String nameKeyword, String emailKeyword);

    List<User> findByIdNot(Long id);

    // 自分がフォローしているユーザー
    @Query("SELECT f.followed FROM Follow f WHERE f.following.id = :userId")
    List<User> findFollowingsByUserId(@Param("userId") Long userId);

    // 自分をフォローしているユーザー
    @Query("SELECT f.following FROM Follow f WHERE f.followed.id = :userId")
    List<User> findFollowersByUserId(@Param("userId") Long userId);
}