package com.inkSpire.application.dto.blogPost;

import java.util.Set;

/**
 * Represents a request object for filtering blog posts by categories.
 *
 * @author Maran.C
 */
public class CategoryFilterRequest {

    private Set<String> categories;

    /**
     * Get the set of categories to filter by.
     *
     * @return The set of categories.
     */

    public Set<String> getCategories() {
        return categories;
    }

    /**
     * Set the categories to filter by.
     *
     * @param categories The set of categories to filter by.
     */
    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    /**
     * Default constructor for CategoryFilterRequest.
     */
    public CategoryFilterRequest() {
    }

    /**
     * Constructor for CategoryFilterRequest with an initial set of categories.
     *
     * @param categories The set of categories to filter by.
     */
    public CategoryFilterRequest(Set<String> categories) {
        this.categories = categories;
    }
}
