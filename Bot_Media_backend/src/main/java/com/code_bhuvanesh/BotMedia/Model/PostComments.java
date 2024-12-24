package com.code_bhuvanesh.BotMedia.Model;

import jakarta.persistence.*;

@Entity
public class PostComments {
    @Id // Specifies the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates the ID
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", referencedColumnName = "id", nullable = false)
    private User userid;

    @Column(nullable = false, length = 5000)
    private String comment;
}
