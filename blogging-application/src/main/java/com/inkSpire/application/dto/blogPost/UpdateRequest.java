package com.inkSpire.application.dto.blogPost;

import jakarta.validation.constraints.NotBlank;
import java.util.Set;

/**
 * This class represents a request to update a blog post.
 *
 * @author Maran.C
 */
public class UpdateRequest {

    @NotBlank(message = "Title is required.")
    private String title;

    @NotBlank(message = "Content is required.")
    private String content;

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
     * @param categories The categories to associate with the blog post.
     */
    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    /**
     * Default constructor for UpdateRequest.
     */
    public UpdateRequest() {
    }

    /**
     * Constructor for UpdateRequest with parameters.
     *
     * @param title      The title of the blog post.
     * @param content    The content of the blog post.
     * @param categories The categories associated with the blog post.
     */
    public UpdateRequest(String title, String content, Set<String> categories) {
        this.title = title;
        this.content = content;
        this.categories = categories;
    }
}
