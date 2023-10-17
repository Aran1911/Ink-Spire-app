package com.inkSpire.application.service.impl;

import com.inkSpire.application.common.CommonServiceUtils;
import com.inkSpire.application.dto.comment.CommentCreationRequest;
import com.inkSpire.application.dto.comment.CommentDto;
import com.inkSpire.application.dto.comment.CommentDtoMapper;
import com.inkSpire.application.dto.comment.CommentUpdateRequest;
import com.inkSpire.application.entity.BlogPost;
import com.inkSpire.application.entity.Comment;
import com.inkSpire.application.entity.User;
import com.inkSpire.application.exception.CommentNotFoundException;
import com.inkSpire.application.exception.UnauthorizedException;
import com.inkSpire.application.repository.CommentRepository;
import com.inkSpire.application.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommonServiceUtils commonServiceUtils;
    private final BlogPostServiceImpl blogPostService;
    private final UserServiceImpl userService;
    private final CommentDtoMapper commentDtoMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);

    public CommentServiceImpl(CommentRepository commentRepository,
                              CommonServiceUtils commonServiceUtils,
                              BlogPostServiceImpl blogPostService,
                              UserServiceImpl userService, CommentDtoMapper commentDtoMapper) {
        this.commentRepository = commentRepository;
        this.commonServiceUtils = commonServiceUtils;
        this.blogPostService = blogPostService;
        this.userService = userService;
        this.commentDtoMapper = commentDtoMapper;
    }

    @Override
    public CommentDto saveComment(@NotNull @Valid CommentCreationRequest creationRequest) {
        if (commonServiceUtils.isUserAuthenticated()) {
            Long postId = creationRequest.getPostId();
            commonServiceUtils.validatePostId(postId, LOGGER, "comment");
            BlogPost post = blogPostService.getBlogPostById(postId);
            Comment comment = new Comment();
            comment.setContent(creationRequest.getContent());
            comment.setCreationDate(new Date());
            comment.setLastModifiedDate(new Date());
            comment.setBlogPost(post);
            comment.setAuthor(getLoggedInUser());
            return commentDtoMapper.apply(commentRepository.save(comment));
        } else {
            LOGGER.error("Unauthorized user.");
            throw new UnauthorizedException("Unauthorized user.");
        }
    }

    @Override
    public CommentDto updateComment(@NotNull @Valid CommentUpdateRequest updateRequest) {
        if (commonServiceUtils.isUserAuthenticated()) {
            Long commentID = updateRequest.getCommentID();
            commonServiceUtils.validatePostId(commentID, LOGGER, "comment");
            Comment existingComment = commentRepository.findById(commentID)
                    .orElseThrow(
                            () -> {
                                LOGGER.error("An error occurred while fetching  comment details.");
                                return new CommentNotFoundException("There is no comment associated with this id.");
                            }
                    );
            existingComment.setContent(updateRequest.getContent());
            existingComment.setLastModifiedDate(new Date());
            return commentDtoMapper.apply(commentRepository.save(existingComment));
        } else {
            LOGGER.error("Unauthorized user.");
            throw new UnauthorizedException("Unauthorized user.");
        }
    }

//    @Override
//    public List<CommentDto> getAllComments(Long postId) {
//        return null;
//    }

    @Override
    public String deleteComment(Long commentId) {
        if (commonServiceUtils.isUserAuthenticated()) {
            commonServiceUtils.validatePostId(commentId, LOGGER, "comment");
            Comment existingComment = commentRepository.findById(commentId)
                    .orElseThrow(
                            () -> {
                                LOGGER.error("An error occurred while fetching  comment details.");
                                return new CommentNotFoundException("There is no comment associated with this id.");
                            }
                    );
            try {
                commentRepository.delete(existingComment);
                return "Comment deleted successfully.";
            } catch (Exception ex) {
                LOGGER.error("Unable to delete comment.\nCause: {}", ex.getLocalizedMessage());
                throw new RuntimeException("Unable to delete comment. \nCause: " + ex.getLocalizedMessage());
            }
        } else {
            LOGGER.error("Unauthorized user.");
            throw new UnauthorizedException("Unauthorized user.");
        }
    }

    @Override
    public String deleteAllComments(Long postID) {
        if (commonServiceUtils.isUserAuthenticated()) {
            commonServiceUtils.validatePostId(postID, LOGGER, "comment");
            try {
                commentRepository.deleteAllByBlogPost_BlogPostId(postID);
                return "Comments are deleted successfully.";
            } catch (Exception ex) {
                LOGGER.error("Unable to delete comments.\nCause: {}", ex.getLocalizedMessage());
                throw new RuntimeException("Unable to delete comments. \nCause: " + ex.getLocalizedMessage());
            }
        } else {
            LOGGER.error("Unauthorized user.");
            throw new UnauthorizedException("Unauthorized user.");
        }
    }

    private User getLoggedInUser() {
        if (commonServiceUtils.isUserAuthenticated()) {
            return userService.getUserByUsername(commonServiceUtils.getLoggedInUsername());
        } else {
            throw new UnauthorizedException("Unable to authorize user.");
        }
    }
}
