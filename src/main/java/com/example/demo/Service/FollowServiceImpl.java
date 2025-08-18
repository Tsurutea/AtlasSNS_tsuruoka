// src/main/java/com/example/demo/Service/FollowServiceImpl.java
package com.example.demo.Service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Entity.Follow;
import com.example.demo.Entity.User;
import com.example.demo.Repository.FollowRepository;
import com.example.demo.Repository.UserRepository;

@Service
@Transactional
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowServiceImpl(FollowRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getFollowings(Long userId) {
        return followRepository.findFollowingsByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getFollowers(Long userId) {
        return followRepository.findFollowersByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countFollowings(Long userId) {
        return followRepository.countFollowings(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countFollowers(Long userId) {
        return followRepository.countFollowers(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isFollowing(Long meId, Long otherId) {
        return followRepository.existsByFollowing_IdAndFollowed_Id(meId, otherId);
    }

    @Override
    public boolean follow(Long meId, Long targetId) {
        if (Objects.equals(meId, targetId)) return false; // 自分自身は不可
        if (followRepository.existsByFollowing_IdAndFollowed_Id(meId, targetId)) return false;

        User me = userRepository.findById(meId).orElseThrow();
        User target = userRepository.findById(targetId).orElseThrow();

        // Follow エンティティに (User following, User followed) のコンストラクタがある場合
        Follow f = new Follow(me, target);

        // もし無ければ↓で代替
        // Follow f = new Follow();
        // f.setFollowing(me);
        // f.setFollowed(target);

        followRepository.save(f);
        return true;
    }

    @Override
    public boolean unfollow(Long meId, Long targetId) {
        if (!followRepository.existsByFollowing_IdAndFollowed_Id(meId, targetId)) return false;
        followRepository.deleteByFollowing_IdAndFollowed_Id(meId, targetId);
        return true;
    }
}