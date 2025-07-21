package org.hrsninja.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ExtendedCandidateDTO extends CandidateDTO {
    private Set<PositionDto> positions;
    private Set<CommentDto> comments;
} 