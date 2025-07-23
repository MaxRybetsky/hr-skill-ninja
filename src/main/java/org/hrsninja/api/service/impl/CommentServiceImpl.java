package org.hrsninja.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.hrsninja.api.model.Candidate;
import org.hrsninja.api.model.Comment;
import org.hrsninja.api.repository.CandidateRepository;
import org.hrsninja.api.repository.CommentRepository;
import org.hrsninja.api.dto.CommentDto;
import org.hrsninja.api.mapper.CommentMapper;
import org.hrsninja.api.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hrsninja.api.util.TimeUtil.FIXED_DATETIME;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CandidateRepository candidateRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto addCommentToCandidate(UUID candidateId, String author, String commentText) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new IllegalArgumentException("Candidate not found"));
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID());
        comment.setAuthor(author);
        comment.setComment(commentText);
        comment.setCreatedDatetime(FIXED_DATETIME);
        comment.setCandidate(candidate);
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public Optional<CommentDto> findById(UUID id) {
        return commentRepository.findById(id).map(commentMapper::toDto);
    }

    @Override
    public List<CommentDto> findAllByCandidateId(UUID candidateId) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new IllegalArgumentException("Candidate not found"));

        return commentRepository.findAllByCandidate(candidate)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }
} 