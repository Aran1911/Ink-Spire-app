package com.inkSpire.application.repository;

import com.inkSpire.application.entity.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Repository for managing {@link BlogPost} entities.
 *
 * @author Maran.C
 */
@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    /**
     * Retrieves a list of {@link BlogPost} objects that belong to any of the specified categories.
     *
     * @param categories A set of category names to filter by.
     * @return A list of {@link BlogPost} objects matching the specified categories.
     */
    List<BlogPost> findByCategoriesIn(Set<String> categories);

    /**
     * Retrieves a list of {@link BlogPost} objects written by an author with the specified email.
     *
     * @param email The email of the author to filter by.
     * @return A list of {@link BlogPost} objects written by the specified author.
     */
    List<BlogPost> findByAuthor_Email(String email);

    /**
     * Deletes all {@link BlogPost} objects written by an author with the specified email.
     *
     * @param email The email of the author whose posts should be deleted.
     */
    void deleteAllByAuthor_Email(String email);
}
