package com.workintech.twitter_api.controller;

import com.workintech.twitter_api.entity.Comment;
import com.workintech.twitter_api.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/user/{userId}/tweet/{tweetId}")
    public Comment createComment(@PathVariable Long userId, @PathVariable Long tweetId, @RequestBody Comment comment) {
        return commentService.save(comment, userId, tweetId);
    }


    @PutMapping("/{commentId}/user/{userId}")
    public Comment updateComment(@PathVariable Long commentId, @RequestBody Comment comment, @PathVariable Long userId) {
        return commentService.update(commentId, comment, userId);
    }


    @DeleteMapping("/{commentId}/user/{userId}")
    public void deleteComment(@PathVariable Long commentId, @PathVariable Long userId) {
        commentService.delete(commentId, userId);
    }


}