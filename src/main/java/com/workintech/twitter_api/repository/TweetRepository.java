package com.workintech.twitter_api.repository;

import com.workintech.twitter_api.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
