package com.inkSpire.application.dto.blogPost;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

/**
 * Data Transfer Object (DTO) for creating a blog post.
 *
 * @author Maran.C
 */
public class CreationRequest {

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
     * The categories associated with the blog post.
     */
    private Set<String> categories;

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
     * @param title The title of the blog post.
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
     * @param content The content of the blog post.
     */
    public void setContent(String content) {
        this.content = content;
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
     * @param categories The categories associated with the blog post.
     */
    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    /**
     * Default constructor for CreationRequest.
     */
    public CreationRequest() {
    }

    /**
     * Constructor for CreationRequest with parameters.
     *
     * @param title      The title of the blog post.
     * @param content    The content of the blog post.
     * @param categories The categories associated with the blog post.
     */
    public CreationRequest(String title, String content, Set<String> categories) {
        this.title = title;
        this.content = content;
        this.categories = categories;
    }
}
