package com.workintech.twitter_api.controller;


import com.workintech.twitter_api.entity.Tweet;
import com.workintech.twitter_api.service.TweetService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class TweetController {

    private final TweetService tweetService;

    @PostMapping("/{userId}/tweet")
    public Tweet createTweet(@PathVariable Long userId, @RequestBody Tweet tweet){
        return tweetService.save(tweet, userId);
    }

    @GetMapping("/tweet")
    public Iterable<Tweet> getAllTweets() {
        return tweetService.findAll();
    }

    @GetMapping("/tweet/{id}")
    public Tweet getTweetById(@PathVariable Long id) {
        return tweetService.findById(id);
    }

    @PutMapping("/tweet/{id}")
    public Tweet updateTweet(@PathVariable Long id, @RequestBody Tweet tweet) {
        return tweetService.save(tweet, tweet.getUser().getId());
    }

    @DeleteMapping("/{userId}/tweet/{tweetId}")
    public void deleteTweet(@PathVariable Long userId, @PathVariable Long tweetId) {
        tweetService.delete(tweetId, userId);
    }

    @GetMapping("/{userId}/tweets")
    public List<Tweet> getTweetsByUserId(@PathVariable Long userId) {
        return tweetService.findTweetsByUserId(userId);
    }
}
