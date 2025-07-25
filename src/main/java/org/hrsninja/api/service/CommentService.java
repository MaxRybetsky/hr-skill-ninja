package org.hrsninja.api.service;

import org.hrsninja.api.dto.CommentDto;
import org.hrsninja.api.dto.ExtendedCommentDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentService {
    CommentDto addCommentToCandidate(UUID candidateId, String author, String commentText);
    Optional<CommentDto> findById(UUID id);
    List<CommentDto> findAllByCandidateId(UUID candidateId);
    List<ExtendedCommentDto> findAll();
}