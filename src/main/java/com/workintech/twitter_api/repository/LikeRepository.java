package com.workintech.twitter_api.repository;

import com.workintech.twitter_api.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {


    @Query("SELECT lk FROM Like lk WHERE lk.user.id = :userId AND lk.tweet.id = :tweetId")
    Optional<Like> findByUserIdAndTweetId(Long userId, Long tweetId);


    @Query("SELECT lk FROM Like lk WHERE lk.id = :likeId AND lk.user.id = :userId")
    Optional<Like> findByIdAndUserId(Long likeId, Long userId);
}