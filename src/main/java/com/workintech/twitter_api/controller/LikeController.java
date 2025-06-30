package com.workintech.twitter_api.controller;

import com.workintech.twitter_api.entity.Like;
import com.workintech.twitter_api.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;


    @PostMapping("/user/{userId}/tweet/{tweetId}")
    public Like addLike(@PathVariable Long userId, @PathVariable Long tweetId) {
        return likeService.addLike(userId, tweetId);
    }

    @DeleteMapping("/{likeId}/user/{userId}")
    public void removeLike(@PathVariable Long likeId, @PathVariable Long userId) {
        likeService.removeLike(likeId, userId);
    }
}