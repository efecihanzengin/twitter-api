package com.workintech.twitter_api.service;

import com.workintech.twitter_api.entity.Tweet;
import com.workintech.twitter_api.entity.User;
import com.workintech.twitter_api.repository.TweetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class TweetService {

    private final TweetRepository tweetRepository;
    private final UserService userService;

    public Tweet save(Tweet tweet, Long userId) {
        User user = userService.findById(userId);
        if(user == null) {
            return null;
        }
        tweet.setUser(user);
        tweet.setCreatedAt(LocalDateTime.now());
        return tweetRepository.save(tweet); //veritabanina kaydettim
    }

    public Iterable<Tweet> findAll(){
        return tweetRepository.findAll(); //tum tweetleri getirdim
    }

    public Tweet findById(Long id) {
        return tweetRepository.findById(id).orElse(null); //belirli id ye gore tweet dondurme
    }

    public void delete(Long tweetId, Long userId) {
        Tweet tweet = findById(tweetId);

        if(tweet == null) {
            throw new RuntimeException("Silinecek tweet bulunamadi: " + tweetId);
        }

        if(!tweet.getUser().getId().equals(userId)) {
            throw new RuntimeException("Bu tweeti silemezsiniz. Sadece tweet sahipleri silebilir");
        }

        tweetRepository.deleteById(tweetId);
    }

    public List<Tweet> findTweetsByUserId(Long userId) {
        User user = userService.findById(userId);

        if(user == null){
            throw new RuntimeException("Tweetleri bulunacak kullanici bulunamadi: " + userId);
        }

        return tweetRepository.findByUser(user);
    }
}
