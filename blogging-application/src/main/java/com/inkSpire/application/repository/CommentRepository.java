package com.inkSpire.application.repository;

import com.inkSpire.application.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    void deleteAllByBlogPost_BlogPostId(Long id);
}
