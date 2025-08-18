package com.example.demo.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Dto.CreateAccountDto;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User authenticate(String email, String password) {
        if (email == null || password == null) return null;
        User user = userRepository.findByEmail(email.trim());
        return (user != null && password.equals(user.getPassword())) ? user : null;
    }

    @Override
    public boolean emailExists(String email) {
        return email != null && userRepository.findByEmail(email.trim()) != null;
    }

    @Override
    @Transactional
    public User registerUser(CreateAccountDto dto) {
        if (emailExists(dto.getEmail())) {
            throw new IllegalStateException("このメールアドレスは既に登録されています。");
        }
        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            throw new IllegalStateException("パスワードと確認用パスワードが一致しません。");
        }
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail().trim());
        user.setPassword(dto.getPassword());
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsersExcept(Long id) {
        return userRepository.findByIdNot(id);
    }

    @Override
    public List<User> searchUsers(String keyword, Long excludeId) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllUsersExcept(excludeId);
        }
        return userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                keyword.trim(), keyword.trim()
        ).stream().filter(u -> !u.getId().equals(excludeId)).collect(Collectors.toList());
    }

    @Override
    public Set<Long> getFollowingIds(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || user.getFollows() == null) return Collections.emptySet();
        return user.getFollows().stream().map(User::getId).collect(Collectors.toSet());
    }

    @Override
    public List<User> getFollowings(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return (user != null) ? user.getFollowing().stream()
                .map(f -> f.getFollowed())
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    @Override
    public List<User> getFollowers(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return (user != null) ? user.getFollowers().stream()
                .map(f -> f.getFollowing())
                .collect(Collectors.toList())
                : Collections.emptyList();
    }
}