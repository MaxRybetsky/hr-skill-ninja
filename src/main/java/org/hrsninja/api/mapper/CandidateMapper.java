package org.hrsninja.api.mapper;

import org.hrsninja.api.dto.CandidateDTO;
import org.hrsninja.api.dto.ExtendedCandidateDTO;
import org.hrsninja.api.model.Candidate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PositionMapper.class, CommentMapper.class})
public interface CandidateMapper {
    CandidateDTO toDTO(Candidate candidate);

    ExtendedCandidateDTO toExtendedDTO(Candidate candidate);
} 