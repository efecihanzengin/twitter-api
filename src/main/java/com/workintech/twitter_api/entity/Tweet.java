package com.workintech.twitter_api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tweets", schema = "public")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // tweet kimligi

    @Column(name = "content", nullable = false, length = 280)
    private String content; // tweet metini

    @Column(name = "created_at")
    private LocalDateTime createdAt; // olusturulma zamani

    @JsonBackReference
    @ManyToOne // bir cok tweet bir kullaniciya aittir
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // tweet atan kullanici

    @JsonManagedReference("tweetComments")
    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();//tweete yapilan yorumlarin listesi

}
