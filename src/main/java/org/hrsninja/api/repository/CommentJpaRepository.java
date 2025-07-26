package org.hrsninja.api.repository;

import org.hrsninja.api.model.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * Применение EntityGraph в JPA Repository
 */
public interface CommentJpaRepository extends JpaRepository<Comment, UUID> {
    @Query("FROM Comment")
    @EntityGraph(attributePaths = {"candidate"})
    List<Comment> findAllExtended();
}