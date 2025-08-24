package com.example.demo.Service;

import java.util.List;
import java.util.Set;

import com.example.demo.Dto.CreateAccountDto;
import com.example.demo.Dto.UserUpdateDto;
import com.example.demo.Entity.User;

public interface UserService {
    User authenticate(String email, String password);
    boolean emailExists(String email);
    User registerUser(CreateAccountDto dto);
    List<User> getAllUsersExcept(Long id);
    List<User> searchUsers(String keyword, Long excludeId);
    Set<Long> getFollowingIds(Long userId);

    User findById(Long id);
    List<User> getFollowings(Long userId);
    List<User> getFollowers(Long userId);

    // 追加: プロフィール更新用
    User updateUserProfile(Long userId, UserUpdateDto dto);
}