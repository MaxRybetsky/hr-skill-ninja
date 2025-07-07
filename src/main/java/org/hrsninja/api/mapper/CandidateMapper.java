package org.hrsninja.api.mapper;

import org.hrsninja.api.dto.CandidateDTO;
import org.hrsninja.api.dto.CreateCandidateRequest;
import org.hrsninja.api.model.Candidate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CandidateMapper {
    CandidateDTO toDTO(Candidate candidate);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "comment", ignore = true)
    Candidate toEntity(CreateCandidateRequest request);
} 