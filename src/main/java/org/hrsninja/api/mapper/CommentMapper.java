package org.hrsninja.api.mapper;

import org.hrsninja.api.dto.CommentDto;
import org.hrsninja.api.dto.ExtendedCommentDto;
import org.hrsninja.api.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDto toDto(Comment comment);
    List<CommentDto> toDtoList(List<Comment> comments);
    @Mapping(target = "candidateFio", expression = "java(comment.getCandidate().getFio())")
    ExtendedCommentDto toExtDto(Comment comment);
} 