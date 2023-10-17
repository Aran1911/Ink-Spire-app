package com.inkSpire.application.dto.comment;

import com.inkSpire.application.dto.user.UserDtoMapper;
import com.inkSpire.application.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CommentDtoMapper implements Function<Comment, CommentDto> {

    private final UserDtoMapper userDtoMapper;

    @Autowired
    public CommentDtoMapper(UserDtoMapper userDtoMapper) {
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public CommentDto apply(Comment comment) {
        return new CommentDto(
                comment.getCommentId(),
                comment.getContent(),
                comment.getCreationDate(),
                comment.getLastModifiedDate(),
                userDtoMapper.apply(comment.getAuthor())
        );
    }
}
