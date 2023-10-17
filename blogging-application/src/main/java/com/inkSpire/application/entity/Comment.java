package com.inkSpire.application.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.util.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @NotBlank(message = "Content is required.")
    @Column(columnDefinition = "TEXT")
    private String content;

    @PastOrPresent(message = "Creation date must be in the past or present.")
    @NotNull(message = "Creation date is required.")
    private Date creationDate;

    @PastOrPresent(message = "Last modified date must be in the past or present.")
    @NotNull(message = "Last modified date is required.")
    private Date lastModifiedDate;

    @ManyToOne
    @JoinColumn(name = "username")
    private User author;

    @ManyToOne
    @JoinColumn(name = "blog_post_id")
    private BlogPost blogPost;

    public Long getCommentId() {
        return commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User user) {
        this.author = user;
    }

    public BlogPost getBlogPost() {
        return blogPost;
    }

    public void setBlogPost(BlogPost blogPost) {
        this.blogPost = blogPost;
    }

    public Comment() {
    }

    public Comment(Long commentId,
                   String content,
                   Date creationDate,
                   Date lastModifiedDate,
                   User author,
                   BlogPost blogPost) {
        this.commentId = commentId;
        this.content = content;
        this.creationDate = creationDate;
        this.lastModifiedDate = lastModifiedDate;
        this.author = author;
        this.blogPost = blogPost;
    }
}
