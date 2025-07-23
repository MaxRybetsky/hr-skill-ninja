package org.hrsninja.api.controller;

import lombok.RequiredArgsConstructor;
import org.hrsninja.api.dto.CommentCreateRequest;
import org.hrsninja.api.dto.CommentDto;
import org.hrsninja.api.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/candidates/{candidateId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addCommentToCandidate(@PathVariable UUID candidateId, @RequestBody CommentCreateRequest request) {
        return commentService.addCommentToCandidate(candidateId, request.getAuthor(), request.getComment());
    }

    @GetMapping
    public List<CommentDto> getCommentsOfCandidate(@PathVariable UUID candidateId) {
        return commentService.findAllByCandidateId(candidateId);
    }
} 