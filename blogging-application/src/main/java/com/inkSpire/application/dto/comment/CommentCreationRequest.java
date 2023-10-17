package com.inkSpire.application.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CommentCreationRequest {

    @NotBlank(message = "Content is required.")
    private String content;

    @NotNull(message = "Post id is required.")
    private Long postId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public CommentCreationRequest() {
    }

    public CommentCreationRequest(String content, Long postId) {
        this.content = content;
        this.postId = postId;
    }
}
