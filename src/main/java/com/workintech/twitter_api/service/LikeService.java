package com.workintech.twitter_api.service;


import com.workintech.twitter_api.entity.Like;
import com.workintech.twitter_api.entity.Tweet;
import com.workintech.twitter_api.entity.User;
import com.workintech.twitter_api.repository.LikeRepository;
import com.workintech.twitter_api.repository.TweetRepository;
import com.workintech.twitter_api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    public Like addLike(Long userId, Long tweetId) {

        // kullanici ve tweeti bulmaya calisiyoruz
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Tweet> tweetOptional = tweetRepository.findById(tweetId);

        if(userOptional.isEmpty() || tweetOptional.isEmpty()) {
            throw new RuntimeException("User or Tweet not found.");
        }

        User user = userOptional.get();
        Tweet tweet = tweetOptional.get();
        // --

        // Kullanicinin tweete begeni atip atmadigini kontrol et
        Optional<Like> existingLike = likeRepository.findByUserAndTweetId(userId, tweetId);
        if (existingLike.isPresent()){
            throw new RuntimeException("User has already liked this tweet.");
        }

        //like objesi olustur kaydet
        Like like = new Like();
        like.setUser(user);
        like.setTweet(tweet);

        user.getLikes().add(like);
        tweet.getLikes().add(like);
        return likeRepository.save(like);
    }
    //like silme islemi
    public void removeLike(Long likeId, Long userId) {

        Optional<Like> likeOptional = likeRepository.findById(likeId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (likeOptional.isEmpty() || userOptional.isEmpty()) {
            throw new RuntimeException("Like or User not found."); // Daha sonra Custom Exception kullanabiliriz
        }

        Like like = likeOptional.get();
        User user = userOptional.get();



        if (!like.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not authorized to remove this like. Only the user who liked it can remove it."); // Daha sonra Custom Exception kullanabiliriz
        }

        user.getLikes().remove(like);
        like.getTweet().getLikes().remove(like);

        likeRepository.delete(like);
    }
}
