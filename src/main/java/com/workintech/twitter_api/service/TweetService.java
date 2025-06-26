package com.workintech.twitter_api.service;

import com.workintech.twitter_api.entity.Tweet;
import com.workintech.twitter_api.repository.TweetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class TweetService {

    private final TweetRepository tweetRepository;

    public Tweet save(Tweet tweet) {
        if(tweet.getCreatedAt() == null) {
            tweet.setCreatedAt(LocalDateTime.now());// tweet olusturma saatini ayarladim
        }
        return tweetRepository.save(tweet); //veritabanina kaydettim
    }

    public Iterable<Tweet> findAll(){
        return tweetRepository.findAll(); //tum tweetleri getirdim
    }

    public Tweet findById(Long id) {
        return tweetRepository.findById(id).orElse(null); //belirli id ye gore tweet dondurme
    }

    public void delete(Long id) {
        tweetRepository.deleteById(id); //secili id ye gore Tweet silme
    }
}
