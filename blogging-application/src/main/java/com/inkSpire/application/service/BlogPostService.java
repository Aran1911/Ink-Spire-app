package com.inkSpire.application.service;

import com.inkSpire.application.dto.blogPost.CategoryFilterRequest;
import com.inkSpire.application.dto.blogPost.CreationRequest;
import com.inkSpire.application.dto.blogPost.PostDto;
import com.inkSpire.application.dto.blogPost.UpdateRequest;
import com.inkSpire.application.entity.BlogPost;

import java.util.List;

/**
 * This interface defines the service methods for managing blog posts.
 *
 * @author Maran.C
 */
public interface BlogPostService {

    /**
     * Save a new blog post.
     *
     * @param request The request object containing the data for creating a new post.
     * @return A PostDto representing the saved blog post.
     * @see CreationRequest
     */
    PostDto saveBlogPost(CreationRequest request);

    /**
     * Update an existing blog post.
     *
     * @param postId         The ID of the blog post to update.
     * @param updateRequest  The request object containing the data for updating the post.
     * @return A PostDto representing the updated blog post.
     * @see UpdateRequest
     */
    PostDto updateBlogPost(Long postId, UpdateRequest updateRequest);

    /**
     * Retrieve a blog post by its ID.
     *
     * @param postID The ID of the blog post to retrieve.
     * @return A BlogPost object representing the retrieved blog post.
     */
    BlogPost getBlogPostById(Long postID);

    /**
     * Retrieve a list of all blog posts.
     *
     * @return A list of PostDto objects representing all the blog posts.
     */
    List<PostDto> getAllPosts();

    /**
     * Retrieve a list of all blog posts for a user.
     *
     * @return A list of PostDto objects representing the blog posts for the user.
     */
    List<PostDto> getAllPostForUser();

    /**
     * Retrieve a list of blog posts based on category filters.
     *
     * @param request The request object containing category filter criteria.
     * @return A list of PostDto objects representing the filtered blog posts.
     * @see CategoryFilterRequest
     */
    List<PostDto> getAllPosts(CategoryFilterRequest request);

    /**
     * Delete a blog post by its ID.
     *
     * @param postId The ID of the blog post to delete.
     * @return A message indicating the result of the deletion.
     */
    String deleteBlogPost(Long postId);

    /**
     * Delete all blog posts.
     *
     * @return A message indicating the result of the deletion.
     */
    String deleteAllBlogPosts();
}
