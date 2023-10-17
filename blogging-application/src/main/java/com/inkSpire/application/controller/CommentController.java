package com.inkSpire.application.controller;

import com.inkSpire.application.common.ApiResponse;
import com.inkSpire.application.common.CommonServiceUtils;
import com.inkSpire.application.dto.comment.CommentCreationRequest;
import com.inkSpire.application.dto.comment.CommentDto;
import com.inkSpire.application.dto.comment.CommentUpdateRequest;
import com.inkSpire.application.service.impl.CommentServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentServiceImpl commentService;
    private final CommonServiceUtils commonServiceUtils;

    public CommentController(CommentServiceImpl commentService, CommonServiceUtils commonServiceUtils) {
        this.commentService = commentService;
        this.commonServiceUtils = commonServiceUtils;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CommentDto>> createNewComment(@RequestBody @Valid @NotNull CommentCreationRequest creationRequest) {
        if (commonServiceUtils.isUserAuthenticated()) {
            CommentDto commentDto = commentService.saveComment(creationRequest);
            return new ResponseEntity<>(commonServiceUtils.generateResponse(
                    true,
                    "Comment created successfully.",
                    commentDto
            )
                    , HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(
                    commonServiceUtils.generateResponse(
                            false,
                            "You are not authorized to do commenting."
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<CommentDto>> updateComment(@RequestBody @NotNull @Valid CommentUpdateRequest updateRequest) {
        if (commonServiceUtils.isUserAuthenticated()) {
            CommentDto comment = commentService.updateComment(updateRequest);
            return new ResponseEntity<>(commonServiceUtils.generateResponse(
                    true,
                    "Comment successfully updated.",
                    comment
            )
                    , HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    commonServiceUtils.generateResponse(
                            false,
                            "You are not authorized to update a comment."
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> deleteComment(@RequestParam(name = "cId") @Positive(message = "Invalid comment ID.") Long cId) {
        if (commonServiceUtils.isUserAuthenticated()) {
            String message = commentService.deleteComment(cId);
            return new ResponseEntity<>(commonServiceUtils.generateResponse(
                    true,
                    "Comment successfully deleted.",
                    message
            )
                    , HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    commonServiceUtils.generateResponse(
                            false,
                            "You are not authorized to delete a comment."
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

}
