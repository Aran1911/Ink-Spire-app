package com.inkSpire.application.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Represents a blog post in the application.
 * This entity class is used to store information about blog posts, including their title, content, creation date,
 * last modification date, author, categories, and comments.
 */
@Entity
public class BlogPost implements Serializable {

    /**
     * The unique identifier for the blog post.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_post_id")
    private Long blogPostId;

    /**
     * The title of the blog post.
     */
    @NotBlank(message = "Title is required.")
    private String title;

    /**
     * The content of the blog post.
     */
    @NotBlank(message = "Content is required.")
    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * The date when the blog post was created.
     */
    @PastOrPresent(message = "Creation date must be in the past or present.")
    @NotNull(message = "Creation date is required.")
    private Date creationDate;

    /**
     * The date when the blog post was last modified.
     */
    @PastOrPresent(message = "Last modified date must be in the past or present.")
    @NotNull(message = "Last modified date is required.")
    private Date lastModifiedDate;

    /**
     * The user who authored the blog post.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    /**
     * The categories associated with the blog post.
     */
    @ElementCollection
    private Set<String> categories;

    /**
     * The list of comments on the blog post.
     */
    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL)
    private List<Comment> comments;

    // Getters and setters for the class properties...
    public Long getBlogPostId() {
        return blogPostId;
    }

    public void setBlogPostId(Long blogPostId) {
        this.blogPostId = blogPostId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setAuthor(User author) {
        this.author = author;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * Default constructor for the BlogPost class.
     */
    public BlogPost() {
    }

    /**
     * Parameterized constructor for the BlogPost class.
     *
     * @param blogPostId      The unique identifier for the blog post.
     * @param title           The title of the blog post.
     * @param content         The content of the blog post.
     * @param creationDate    The date when the blog post was created.
     * @param lastModifiedDate The date when the blog post was last modified.
     * @param author          The user who authored the blog post.
     * @param categories      The categories associated with the blog post.
     * @param comments        The list of comments on the blog post.
     */
    public BlogPost(Long blogPostId,
                    String title,
                    String content,
                    Date creationDate,
                    Date lastModifiedDate,
                    User author,
                    Set<String> categories,
                    List<Comment> comments) {
        this.blogPostId = blogPostId;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.lastModifiedDate = lastModifiedDate;
        this.author = author;
        this.categories = categories;
        this.comments = comments;
    }
}
