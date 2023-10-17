package com.inkSpire.application.dto.comment;

import com.inkSpire.application.dto.user.UserDto;

import java.util.Date;

public class CommentDto {
    private Long commentId;
    private String content;
    private Date creationDate;
    private Date lastModifiedDate;
    private UserDto author;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }

    public CommentDto() {
    }

    public CommentDto(Long commentId,
                      String content,
                      Date creationDate,
                      Date lastModifiedDate,
                      UserDto author) {
        this.commentId = commentId;
        this.content = content;
        this.creationDate = creationDate;
        this.lastModifiedDate = lastModifiedDate;
        this.author = author;
    }
}
