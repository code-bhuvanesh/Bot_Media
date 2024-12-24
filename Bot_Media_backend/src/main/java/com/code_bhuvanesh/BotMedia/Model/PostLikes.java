package com.code_bhuvanesh.BotMedia.Model;


import jakarta.persistence.*;

@Entity
public class PostLikes {
    @Id // Specifies the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates the ID
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", referencedColumnName = "id", nullable = false)
    private User userid;
}

