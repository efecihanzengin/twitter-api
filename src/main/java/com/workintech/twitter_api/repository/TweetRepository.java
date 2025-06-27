package com.workintech.twitter_api.repository;

import com.workintech.twitter_api.entity.Tweet;
import com.workintech.twitter_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

    List<Tweet> findByUser(User user);

}
