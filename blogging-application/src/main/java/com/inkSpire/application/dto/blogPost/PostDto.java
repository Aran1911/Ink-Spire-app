package com.inkSpire.application.dto.blogPost;

import com.inkSpire.application.dto.comment.CommentDto;
import com.inkSpire.application.dto.user.UserDto;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Data Transfer Object (DTO) representing a blog post.
 *
 * @author Maran.C
 */
public class PostDto {

    private Long blogPostId; // Unique identifier for the blog post.
    private String title; // Title of the blog post.
    private String content; // Content of the blog post.
    private Date creationDate; // Date when the blog post was created.
    private Date lastModifiedDate; // Date when the blog post was last modified.
    private UserDto author; // Author of the blog post.
    private Set<String> categories; // Categories associated with the blog post.
    private List<CommentDto> comments; // Comments on the blog post.

    /**
     * Get the unique identifier of the blog post.
     *
     * @return The unique identifier of the blog post.
     */
    public Long getBlogPostId() {
        return blogPostId;
    }

    /**
     * Set the unique identifier of the blog post.
     *
     * @param blogPostId The unique identifier to set.
     */
    public void setBlogPostId(Long blogPostId) {
        this.blogPostId = blogPostId;
    }

    /**
     * Get the title of the blog post.
     *
     * @return The title of the blog post.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the blog post.
     *
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the content of the blog post.
     *
     * @return The content of the blog post.
     */
    public String getContent() {
        return content;
    }

    /**
     * Set the content of the blog post.
     *
     * @param content The content to set.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get the date when the blog post was created.
     *
     * @return The creation date of the blog post.
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Set the date when the blog post was created.
     *
     * @param creationDate The creation date to set.
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Get the date when the blog post was last modified.
     *
     * @return The last modification date of the blog post.
     */
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Set the date when the blog post was last modified.
     *
     * @param lastModifiedDate The last modification date to set.
     */
    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    /**
     * Get the author of the blog post.
     *
     * @return The author of the blog post. This is an instance of {@link UserDto}.
     */
    public UserDto getAuthor() {
        return author;
    }

    /**
     * Set the author of the blog post.
     *
     * @param author The author to set.
     */
    public void setAuthor(UserDto author) {
        this.author = author;
    }

    /**
     * Get the categories associated with the blog post.
     *
     * @return The categories associated with the blog post.
     */
    public Set<String> getCategories() {
        return categories;
    }

    /**
     * Set the categories associated with the blog post.
     *
     * @param categories The categories to set.
     */
    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    /**
     * Get the comments on the blog post.
     *
     * @return The comments on the blog post. These are instances of {@link CommentDto}.
     */
    public List<CommentDto> getComments() {
        return comments;
    }

    /**
     * Set the comments on the blog post.
     *
     * @param comments The comments to set.
     */
    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    /**
     * Default constructor for PostDto.
     */
    public PostDto() {
    }

    /**
     * Constructor to initialize all attributes of the PostDto.
     *
     * @param blogPostId      Unique identifier of the blog post.
     * @param title           Title of the blog post.
     * @param content         Content of the blog post.
     * @param creationDate    Date when the blog post was created.
     * @param lastModifiedDate Date when the blog post was last modified.
     * @param author          Author of the blog post. An instance of {@link UserDto}.
     * @param categories      Categories associated with the blog post.
     * @param comments        Comments on the blog post. A list of {@link CommentDto}.
     */
    public PostDto(Long blogPostId,
                   String title,
                   String content,
                   Date creationDate,
                   Date lastModifiedDate,
                   UserDto author,
                   Set<String> categories,
                   List<CommentDto> comments) {
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
