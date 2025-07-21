package org.hrsninja.api.mapper;

import org.hrsninja.api.dto.PositionDto;
import org.hrsninja.api.model.Position;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PositionMapper {
    @Mapping(target = "candidateIds", source = "candidates", qualifiedByName = "candidatesToIds")
    PositionDto toDto(Position position);

    @Named("candidatesToIds")
    default Set<UUID> mapCandidatesToIds(Set<org.hrsninja.api.model.Candidate> candidates) {
        if (candidates == null) return null;
        return candidates.stream().map(org.hrsninja.api.model.Candidate::getId).collect(Collectors.toSet());
    }
} 