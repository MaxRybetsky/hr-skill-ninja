package org.hrsninja.api.repository;

import org.hrsninja.api.model.Candidate;
import org.hrsninja.api.model.Comment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository {
    Comment save(Comment comment);
    Optional<Comment> findById(UUID id);
    List<Comment> findAllByCandidate(Candidate candidate);
    List<Comment> findAllExtended();
}