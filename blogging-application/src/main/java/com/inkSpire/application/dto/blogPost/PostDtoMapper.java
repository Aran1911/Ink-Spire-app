package com.inkSpire.application.dto.blogPost;

import com.inkSpire.application.dto.comment.CommentDtoMapper;
import com.inkSpire.application.dto.user.UserDtoMapper;
import com.inkSpire.application.entity.BlogPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * Mapper class for converting BlogPost entities to PostDto objects.
 *
 * @author Maran.C
 */
@Service
public class PostDtoMapper implements Function<BlogPost, PostDto> {

    private final UserDtoMapper userDtoMapper;
    private final CommentDtoMapper commentDtoMapper;


    /**
     * Constructor for PostDtoMapper.
     *
     * @param userDtoMapper    Mapper for UserDto.
     * @param commentDtoMapper Mapper for CommentDto.
     */
    @Autowired
    public PostDtoMapper(UserDtoMapper userDtoMapper, CommentDtoMapper commentDtoMapper) {
        this.userDtoMapper = userDtoMapper;
        this.commentDtoMapper = commentDtoMapper;
    }


    /**
     * Maps a BlogPost entity to a PostDto object.
     *
     * @param blogPost The BlogPost entity to be mapped.
     * @return A PostDto object representing the mapped BlogPost.
     */
    @Override
    public PostDto apply(BlogPost blogPost) {
        return new PostDto(
                blogPost.getBlogPostId(),
                blogPost.getTitle(),
                blogPost.getContent(),
                blogPost.getCreationDate(),
                blogPost.getLastModifiedDate(),
                userDtoMapper.apply(blogPost.getAuthor()),
                blogPost.getCategories(),
                blogPost.getComments().stream()
                        .map(commentDtoMapper).toList()
        );
    }
}
