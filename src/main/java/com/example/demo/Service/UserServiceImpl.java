package com.example.demo.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Dto.CreateAccountDto;
import com.example.demo.Dto.UserUpdateDto;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public User save(User user) {
        return userRepository.save(user);
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
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
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

    // 🔽 フォロー関連メソッド修正版
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
                .map(f -> f.getFollowed())   // 自分がフォローしている相手
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    @Override
    public List<User> getFollowers(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return (user != null) ? user.getFollowers().stream()
                .map(f -> f.getFollower())   // 自分をフォローしてくれる人
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    @Override
    @Transactional
    public User updateUserProfile(Long userId, UserUpdateDto dto) {
        User user = userRepository.findById(userId).orElseThrow();

        if (dto.getName() != null && !dto.getName().isEmpty()) {
            user.setName(dto.getName());
        }
        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getBio() != null) {
            user.setBio(dto.getBio());
        }

        // パスワード更新
        if (dto.getNewPassword() != null && !dto.getNewPassword().isEmpty()) {
            if (dto.getNewPassword().equals(dto.getConfirmPassword())) {
                user.setPassword(dto.getNewPassword()); // ★エンコード推奨
            } else {
                throw new IllegalStateException("新しいパスワードと確認用パスワードが一致しません。");
            }
        }

        // アイコン画像更新
        if (dto.getIcon() != null && !dto.getIcon().isEmpty()) {
            try {
                Path uploadPath = Paths.get("user-icons").toAbsolutePath().normalize();
                File dir = uploadPath.toFile();
                if (!dir.exists()) {
                    dir.mkdirs(); // user-icons フォルダが無ければ作る
                }

                // ファイル名を安全なUUIDに
                String ext = "";
                String originalFilename = dto.getIcon().getOriginalFilename();
                if (originalFilename != null && originalFilename.contains(".")) {
                    ext = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                String safeFilename = UUID.randomUUID().toString() + ext;

                File destination = new File(dir, safeFilename);
                dto.getIcon().transferTo(destination);

                // DBには公開用のパスを保存
                user.setIconImage("/user-icons/" + safeFilename);

            } catch (Exception e) {
                throw new RuntimeException("アイコン画像の保存に失敗しました", e);
            }
        }

        return userRepository.save(user);
    }
}