package com.workintech.twitter_api.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "comments", schema = "public")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", length = 500, nullable = false)
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    //yorum sahibi, many to one
    @JsonBackReference("userComments")
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonBackReference("tweetComments")
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "tweet_id", nullable = false)
    private Tweet tweet; // yorumun yapildigi tweet
}
