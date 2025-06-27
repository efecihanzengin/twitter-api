package com.workintech.twitter_api.service;

import com.workintech.twitter_api.entity.Comment;
import com.workintech.twitter_api.entity.Tweet;
import com.workintech.twitter_api.entity.User;
import com.workintech.twitter_api.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final TweetService tweetService;

    public Comment save(Comment comment, Long userId, Long tweetId) {
        User user = userService.findById(userId);
        Tweet tweet = tweetService.findById(tweetId);

        if (user == null) {
            throw new RuntimeException("Yorumu yapacak kullanici bulunamadi: " + userId);
        }

        if(tweet == null) {
            throw new RuntimeException("Yorumu yapilacak tweet bulunamadi: " + tweetId);
        }

        comment.setUser(user);
        comment.setTweet(tweet);

        comment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    } // yeni yorum olusturup kaydetme

    public Comment findById(Long id) {
        Optional<Comment> foundComment = commentRepository.findById(id);

        if(foundComment.isPresent()){
            return foundComment.get();
        }

        throw new RuntimeException("Yorum bulunamadi:" + id);
    }

    //yorumu guncellemek icin kullanilan method
    public Comment update(Long commentId, Comment updatedComment,Long userId) {
        Comment existingComment = findById(commentId);

        if(!existingComment.getUser().getId().equals(userId)){
            throw new RuntimeException("Bu yorumu guncelleme yetkiniz bulunmamaktadir.");
        }

        existingComment.setContent(updatedComment.getContent());

        return commentRepository.save(existingComment);
    }

    public void delete(Long commentId, Long userId) {
        Comment commentToDelete = findById(commentId);

        boolean isCommentOwner = commentToDelete.getUser().getId().equals(userId);
        boolean isTweetOwner = commentToDelete.getTweet().getUser().getId().equals(userId);

        if(!isCommentOwner && !isTweetOwner) {
            throw new RuntimeException("Bu yorumu silme yetkiniz bulunmamaktadir.");

        }
        commentRepository.deleteById(commentId);
    }

}
