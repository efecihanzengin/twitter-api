package com.workintech.twitter_api.controller;


import com.workintech.twitter_api.entity.Tweet;
import com.workintech.twitter_api.service.TweetService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/tweet")
public class TweetController {

    private final TweetService tweetService;

    @GetMapping //tum tweetleri getirme
    public Iterable<Tweet> getAllTweets() {
        return tweetService.findAll(); //TweetServicedeki tum tweetleri alip dondur.
    }

    @PostMapping // yeni bir tweet olusturan
    public Tweet createTweet(@RequestBody Tweet tweet) {
        return tweetService.save(tweet); //tweetservicedeki tweeti kaydedip dondur.
    }

    @GetMapping("/{id}") //belirli id ye sahip twiti getiren
    public Tweet getTweetById(@PathVariable Long id) {
        return tweetService.findById(id); //tweetserviceden id ye gore tweet bulup dodur
    }

    @PutMapping("/{id}") //guncelleme
    public Tweet updateTweet(@PathVariable Long id, @RequestBody Tweet tweet) {
        tweet.setId(id);
        return tweetService.save(tweet); // RequestBody ile json formatinda nesneyi aldik, id yi set edip guncelleyip dondurduk
    }

    @DeleteMapping("/{id}") //silme
    public void deleteTweet(@PathVariable Long id) {
        tweetService.delete(id);
    }
}
