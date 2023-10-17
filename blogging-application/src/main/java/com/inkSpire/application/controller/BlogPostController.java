package com.inkSpire.application.controller;

import com.inkSpire.application.common.ApiResponse;
import com.inkSpire.application.common.CommonServiceUtils;
import com.inkSpire.application.dto.blogPost.CategoryFilterRequest;
import com.inkSpire.application.dto.blogPost.CreationRequest;
import com.inkSpire.application.dto.blogPost.PostDto;
import com.inkSpire.application.dto.blogPost.UpdateRequest;
import com.inkSpire.application.service.impl.BlogPostServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class defines the REST API endpoints for managing blog posts.
 *
 * @author Maran.C
 */
@RestController
@RequestMapping("/blog-post")
@CrossOrigin("/*/**")

public class BlogPostController {
    private final BlogPostServiceImpl blogPostService;
    private final CommonServiceUtils commonServiceUtils;

    @Autowired
    public BlogPostController(BlogPostServiceImpl blogPostService, CommonServiceUtils commonServiceUtils) {
        this.blogPostService = blogPostService;
        this.commonServiceUtils = commonServiceUtils;
    }

    /**
     * Endpoint for creating a new blog post.
     * @param blogPost The request body containing the blog post details.
     * @return A response entity with the result of the post creation operation.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<PostDto>> postNewBlogPost(@RequestBody @NotNull @Valid CreationRequest blogPost) {
        if (commonServiceUtils.isUserAuthenticated()) {
            PostDto post = blogPostService.saveBlogPost(blogPost);
            return new ResponseEntity<>(
                    commonServiceUtils.generateResponse(
                            true,
                            "Post created successfully.",
                            post
                    ),
                    HttpStatus.CREATED
            );
        } else {
            return new ResponseEntity<>(
                    commonServiceUtils.generateResponse(
                            false,
                            "You are not authorized to create a post."
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

    /**
     * Endpoint for retrieving blog posts for specified categories.
     * @param request The request body containing category filter criteria.
     * @return A response entity with the list of retrieved posts.
     */
    @GetMapping("/category")
    public ResponseEntity<ApiResponse<List<PostDto>>> getAllPostForCategories(@RequestBody @NotNull @Valid CategoryFilterRequest request) {
        if (commonServiceUtils.isUserAuthenticated()) {
            List<PostDto> posts = blogPostService.getAllPosts(request);
            return new ResponseEntity<>(
                    commonServiceUtils.generateResponse(
                            true,
                            "Posts are retrieved.",
                            posts
                    ),
                    HttpStatus.CREATED
            );
        } else {
            return new ResponseEntity<>(
                    commonServiceUtils.generateResponse(
                            false,
                            "Sign in or Sign up to see the post."
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

    /**
     * Endpoint for retrieving all blog posts.
     * @return A response entity with a list of all available blog posts.
     */
    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return new ResponseEntity<>(blogPostService.getAllPosts(), HttpStatus.OK);
    }

    /**
     * Endpoint for retrieving blog posts for the currently authenticated user.
     * @return A response entity with the list of posts belonging to the user.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<PostDto>>> getAllPostsForUser() {
        if (commonServiceUtils.isUserAuthenticated()) {
            List<PostDto> posts = blogPostService.getAllPostForUser();
            return new ResponseEntity<>(
                    commonServiceUtils.generateResponse(
                            true,
                            "Post created successfully.",
                            posts
                    ),
                    HttpStatus.CREATED
            );
        } else {
            return new ResponseEntity<>(
                    commonServiceUtils.generateResponse(
                            false,
                            "Sign in or Sign up to see the post."
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

    /**
     * Endpoint for updating an existing blog post.
     * @param pid The ID of the post to be updated.
     * @param updateRequest The request body containing the update details.
     * @return A response entity with the result of the update operation.
     */
    @PutMapping
    public ResponseEntity<ApiResponse<PostDto>> update(@RequestParam(name = "pid") Long pid,
                                                       @NotNull @Valid @RequestBody UpdateRequest updateRequest) {
        if (commonServiceUtils.isUserAuthenticated()) {
            PostDto post = blogPostService.updateBlogPost(pid, updateRequest);
            return new ResponseEntity<>(
                    commonServiceUtils.generateResponse(
                            true,
                            "Blog post updated.",
                            post
                    ),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    commonServiceUtils.generateResponse(
                            false,
                            "Sign in or Sign up to update the post."
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

    /**
     * Endpoint for deleting a specific blog post.
     * @param pId The ID of the post to be deleted.
     * @return A response entity indicating the result of the delete operation.
     */
    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> deleteBlogPost(@RequestParam(name = "pId") Long pId) {
        if (commonServiceUtils.isUserAuthenticated()) {
            return new ResponseEntity<>(commonServiceUtils.generateResponse(
                    true,
                    blogPostService.deleteBlogPost(pId)
            ), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    commonServiceUtils.generateResponse(
                            false,
                            "Sign in or Sign up to delete the post."
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

    /**
     * Endpoint for deleting all blog posts.
     * @return A response entity indicating the result of the delete operation.
     */
    @DeleteMapping("/all")
    public ResponseEntity<ApiResponse<String>> deleteAllPost() {
        if (commonServiceUtils.isUserAuthenticated()) {
            return new ResponseEntity<>(commonServiceUtils.generateResponse(
                    true,
                    blogPostService.deleteAllBlogPosts()
            ), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    commonServiceUtils.generateResponse(
                            false,
                            "Sign in or Sign up to delete the post."
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }
}
