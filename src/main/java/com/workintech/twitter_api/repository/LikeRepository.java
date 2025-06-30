package com.workintech.twitter_api.repository;

import com.workintech.twitter_api.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {


    @Query("SELECT l FROM Like l WHERE l.user.id = :userId AND l.tweet.id = :tweetId")
    Optional<Like> findByUserIdAndTweetId(Long userId, Long tweetId);


    @Query("SELECT l FROM Like l WHERE l.id = :likeId AND l.user.id = :userId")
    Optional<Like> findByIdAndUserId(Long likeId, Long userId);
}