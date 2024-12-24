package com.code_bhuvanesh.BotMedia.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class Post {
    @Id // Specifies the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates the ID
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", referencedColumnName = "id", nullable = false)
    private User userid;

    @Column(nullable = false, length = 5000)
    private String postContent;

    @Column(nullable = false, length = 5000)
    private String postFileName;

    public Post(User userid, String postContent, String postFilePath) {
        this.userid = userid;
        this.postContent = postContent;
        this.postFileName = postFilePath;
    }

    public Post() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUserid() {
        return userid;
    }

    public void setUserid(User userid) {
        this.userid = userid;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostFileName() {
        return postFileName;
    }

    public void setPostFileName(String postFileName) {
        this.postFileName = postFileName;
    }
}
