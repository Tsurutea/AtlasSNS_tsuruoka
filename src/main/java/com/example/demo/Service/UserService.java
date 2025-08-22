// com.example.demo.Service.UserService
package com.example.demo.Service;

import java.util.List;
import java.util.Set;

import com.example.demo.Dto.CreateAccountDto;
import com.example.demo.Entity.User;

public interface UserService {
    User authenticate(String email, String password);
    boolean emailExists(String email);
    User registerUser(CreateAccountDto dto);
    List<User> getAllUsersExcept(Long id);
    List<User> searchUsers(String keyword, Long excludeId);
    Set<Long> getFollowingIds(Long userId);

    // 追加
    User findById(Long id);

    // フォロー・フォロワー用
    List<User> getFollowings(Long userId);
    List<User> getFollowers(Long userId);
}