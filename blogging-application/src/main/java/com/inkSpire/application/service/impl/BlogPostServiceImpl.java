package com.inkSpire.application.service.impl;

import com.inkSpire.application.common.CommonServiceUtils;
import com.inkSpire.application.dto.blogPost.*;
import com.inkSpire.application.entity.BlogPost;
import com.inkSpire.application.entity.Comment;
import com.inkSpire.application.entity.User;
import com.inkSpire.application.exception.BlogPostNotFoundException;
import com.inkSpire.application.exception.UnauthorizedException;
import com.inkSpire.application.repository.BlogPostRepository;
import com.inkSpire.application.service.BlogPostService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

/**
 * BlogPostController is a REST API controller responsible for managing blog posts within the application.
 * It defines endpoints for creating, retrieving, updating, and deleting blog posts, as well as for retrieving posts based on various criteria.
 * This controller serves as the interface between the client application and the underlying business logic encapsulated in the BlogPostServiceImpl class.
 * It also handles user authentication and authorization to ensure secure operations on blog posts.
 * <p>
 * BlogPostController collaborates with the BlogPostServiceImpl for executing blog post operations, CommonServiceUtils for common service functionalities,
 * and other dependencies for handling request and response data.
 * <p>
 * This class is a part of the application's RESTful API and is designed to be accessed by clients such as web or mobile applications.
 *
 * @author Maran.C
 * @see BlogPostServiceImpl
 * @see CommonServiceUtils
 * @see CreationRequest
 * @see UpdateRequest
 * @see CategoryFilterRequest
 */

@Service
public class BlogPostServiceImpl implements BlogPostService {

    private final PostDtoMapper dtoMapper;
    private final BlogPostRepository blogPostRepository;
    private final CommonServiceUtils commonServiceUtils;
    private final UserServiceImpl userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(BlogPostServiceImpl.class);


    @Autowired
    public BlogPostServiceImpl(PostDtoMapper dtoMapper,
                               BlogPostRepository blogPostRepository,
                               CommonServiceUtils commonServiceUtils,
                               UserServiceImpl userService) {
        this.dtoMapper = dtoMapper;
        this.blogPostRepository = blogPostRepository;
        this.commonServiceUtils = commonServiceUtils;
        this.userService = userService;
    }

    /**
     * Saves a new blog post.
     *
     * @param request The request object containing blog post details.
     * @return The DTO representing the saved blog post.
     */
    @Override
    public PostDto saveBlogPost(@NotNull @Valid CreationRequest request) {
        // Check if the user is authenticated before proceeding.
        if (commonServiceUtils.isUserAuthenticated()) {
            List<Comment> comments = new ArrayList<>();

            // Create a new blog post entity from the request and comments.
            BlogPost post = getBlogPost(request, comments);

            // Save the blog post and return its DTO.
            return dtoMapper.apply(blogPostRepository.save(post));
        } else {
            LOGGER.error("Unable to create a new post.");
            throw new UsernameNotFoundException("There is no user with this username.");
        }
    }

    /**
     * Updates an existing blog post.
     *
     * @param postId        The ID of the post to update.
     * @param updateRequest The request object containing the updated post details.
     * @return The DTO representing the updated blog post.
     */
    @Override
    public PostDto updateBlogPost(@Positive(message = "Invalid post id") Long postId, UpdateRequest updateRequest) {
        // Print a debug message to the console.
        System.out.println("From service layer: \n" + updateRequest);

        if (updateRequest == null) {
            LOGGER.error("Provided blogPost is null.");
            throw new IllegalArgumentException("Provided blogPost is null.");
        }

        // Validate the provided post ID.
        commonServiceUtils.validatePostId(postId, LOGGER, "post");

        // Find the blog post by ID or throw an exception if not found.
        BlogPost post = blogPostRepository.findById(postId).orElseThrow(
                () -> {
                    LOGGER.error("An error occurred while fetching blog post details.");
                    return new BlogPostNotFoundException("There is no blog post associated with this ID: " + id);
                }
        );

        // Update the blog post properties.
        post.setTitle(updateRequest.getTitle());
        post.setContent(updateRequest.getContent());
        post.setCategories(updateRequest.getCategories());

        // Save the updated post and return its DTO.
        return dtoMapper.apply(blogPostRepository.save(post));
    }

    /**
     * Retrieves a blog post by its ID.
     *
     * @param postId The ID of the post to retrieve.
     * @return The blog post entity.
     */
    @Override
    public BlogPost getBlogPostById(@Positive(message = "Invalid post id") Long postId) {
        // Find the blog post by ID or throw an exception if not found.
        return blogPostRepository.findById(postId).orElseThrow(
                () -> {
                    LOGGER.error("An error occurred while fetching blog post details.");
                    return new BlogPostNotFoundException("There is no blog post associated with this ID: " + id);
                }
        );
    }

    /**
     * Retrieves all blog posts.
     *
     * @return A list of DTOs representing all blog posts.
     */
    @Override
    public List<PostDto> getAllPosts() {
        // Retrieve all blog posts from the repository and map them to DTOs.
        return blogPostRepository.findAll().stream()
                .map(dtoMapper).toList();
    }

    /**
     * Retrieves all blog posts for the currently logged-in user.
     *
     * @return A list of DTOs representing the user's blog posts.
     */
    @Override
    public List<PostDto> getAllPostForUser() {
        final String email = commonServiceUtils.getLoggedInUsername();
        return blogPostRepository.findByAuthor_Email(email).stream()
                .map(dtoMapper).toList();
    }

    /**
     * Retrieves all blog posts based on a category filter.
     *
     * @param request The filter criteria.
     * @return A list of DTOs representing the filtered blog posts.
     */
    @Override
    public List<PostDto> getAllPosts(CategoryFilterRequest request) {
        // Retrieve blog posts based on the provided category filter and map them to DTOs.
        return blogPostRepository.findByCategoriesIn(request.getCategories()).stream()
                .map(dtoMapper).toList();
    }

    /**
     * Deletes a blog post by its ID.
     *
     * @param postId The ID of the post to delete.
     * @return A message indicating the result of the deletion.
     */
    @Override
    public String deleteBlogPost(Long postId) {
        // Validate the provided post ID.
        commonServiceUtils.validatePostId(postId, LOGGER, "comment");

        if (commonServiceUtils.isUserAuthenticated()) {
            // Find the blog post by ID or throw an exception if not found.
            BlogPost post = blogPostRepository.findById(postId).orElseThrow(
                    () -> {
                        LOGGER.error("An error occurred while fetching blog post details.");
                        return new BlogPostNotFoundException("There is no blog post associated with this ID: " + id);
                    }
            );

            try {
                // Delete the blog post and return a success message.
                blogPostRepository.delete(post);
                return "Blog post deleted successfully.";
            } catch (Exception exception) {
                LOGGER.error("Unable to delete post. \ncause: {}", exception.getLocalizedMessage());
                throw new RuntimeException("Unable to delete the post. \nCause: " + exception.getLocalizedMessage());
            }
        } else {
            throw new UnauthorizedException("Authentication required to post.");
        }
    }

    /**
     * Deletes all blog posts for the currently logged-in user.
     *
     * @return A message indicating the result of the deletion.
     */
    @Override
    @Transactional
    public String deleteAllBlogPosts() {
        if (commonServiceUtils.isUserAuthenticated()) {
            // Get the email of the currently logged-in user.
            final String email = commonServiceUtils.getLoggedInUsername();
            try {
                // Delete all blog posts for the user and return a success message.
                blogPostRepository.deleteAllByAuthor_Email(email);
                return "Blog posts are deleted successfully.";
            } catch (Exception exception) {
                LOGGER.error("Unable to delete posts. \ncause: {}", exception.getLocalizedMessage());
                throw new RuntimeException("Unable to delete the posts. \nCause: " + exception.getLocalizedMessage());
            }
        } else {
            throw new UnauthorizedException("Authentication required to post.");
        }
    }

    /**
     * Creates a new BlogPost entity from the provided CreationRequest and comments.
     *
     * @param request  The CreationRequest containing details for the new blog post.
     * @param comments A list of Comment entities associated with the blog post.
     * @return A new BlogPost entity initialized with the provided data.
     */
    private BlogPost getBlogPost(CreationRequest request, List<Comment> comments) {
        // Create a new blog post entity and set its properties.
        BlogPost post = new BlogPost();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCreationDate(new Date());
        post.setLastModifiedDate(new Date());
        post.setAuthor(getLoggedInUser()); // Assign the author of the post.
        post.setComments(comments);
        post.setCategories(request.getCategories());
        return post;
    }

    /**
     * Retrieves the currently logged-in user.
     *
     * @return The User entity representing the logged-in user.
     * @throws UnauthorizedException if the user is not authenticated.
     */
    private User getLoggedInUser() {
        if (commonServiceUtils.isUserAuthenticated()) {
            // Get the currently logged-in user by their username.
            return userService.getUserByUsername(commonServiceUtils.getLoggedInUsername());
        } else {
            // If the user is not authenticated, throw an UnauthorizedException.
            throw new UnauthorizedException("Unable to authorize user.");
        }
    }
}

