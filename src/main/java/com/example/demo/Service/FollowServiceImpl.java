package com.example.demo.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // ✅ 追加

import com.example.demo.Entity.Follow;
import com.example.demo.Entity.User;
import com.example.demo.Repository.FollowRepository;

@Service
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;

    public FollowServiceImpl(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    @Override
    @Transactional(readOnly = true) // ✅ 参照系は readOnly
    public List<User> getFollowers(Long userId) {
        return followRepository.findByFollowedId(userId).stream()
                .map(Follow::getFollower)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getFollowings(Long userId) {
        return followRepository.findByFollowerId(userId).stream()
                .map(Follow::getFollowed)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Long> getFollowingIds(Long userId) {
        return followRepository.findByFollowerId(userId).stream()
                .map(f -> f.getFollowed().getId())
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isFollowing(Long followerId, Long followedId) {
        return followRepository.existsByFollowerIdAndFollowedId(followerId, followedId);
    }

    @Override
    @Transactional // ✅ 追加・更新系はトランザクション必要
    public void follow(Long followerId, Long followedId) {
        if (!isFollowing(followerId, followedId)) {
            Follow follow = new Follow();

            User follower = new User();
            follower.setId(followerId);
            follow.setFollower(follower);

            User followed = new User();
            followed.setId(followedId);
            follow.setFollowed(followed);

            followRepository.save(follow);
        }
    }

    @Override
    @Transactional // ✅ 削除系はトランザクション必要
    public void unfollow(Long followerId, Long followedId) {
        followRepository.deleteByFollowerIdAndFollowedId(followerId, followedId);
    }
}