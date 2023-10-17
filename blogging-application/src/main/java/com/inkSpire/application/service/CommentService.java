package com.inkSpire.application.service;

import com.inkSpire.application.dto.comment.CommentCreationRequest;
import com.inkSpire.application.dto.comment.CommentDto;
import com.inkSpire.application.dto.comment.CommentUpdateRequest;

import java.util.List;

public interface CommentService {

    CommentDto saveComment(CommentCreationRequest creationRequest);

    CommentDto updateComment(CommentUpdateRequest updateRequest);

//    List<CommentDto> getAllComments(Long postId);

    String deleteComment(Long commentId);

    String deleteAllComments(Long postId);
}
