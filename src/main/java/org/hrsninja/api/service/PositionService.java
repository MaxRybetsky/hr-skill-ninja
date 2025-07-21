package org.hrsninja.api.service;

import org.hrsninja.api.dto.PositionDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PositionService {
    PositionDto addPosition(String name, String description);
    void addCandidateToPosition(UUID positionId, UUID candidateId);
    Optional<PositionDto> getPositionWithCandidates(UUID positionId);
    void archivePosition(UUID positionId);
    List<PositionDto> findAll();
} 