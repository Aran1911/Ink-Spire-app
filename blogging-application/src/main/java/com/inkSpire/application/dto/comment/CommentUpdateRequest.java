package com.inkSpire.application.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CommentUpdateRequest {
    @NotBlank(message = "Content is required.")
    private String content;

    @NotNull(message = "Comment id is required.")
    private Long commentID;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCommentID() {
        return commentID;
    }

    public void setCommentID(Long commentID) {
        this.commentID = commentID;
    }

    public CommentUpdateRequest() {
    }

    public CommentUpdateRequest(String content, Long comment) {
        this.content = content;
        this.commentID = comment;
    }
}
