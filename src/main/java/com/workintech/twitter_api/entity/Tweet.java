package com.workintech.twitter_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
}
