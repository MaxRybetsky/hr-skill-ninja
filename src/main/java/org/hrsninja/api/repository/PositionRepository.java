package org.hrsninja.api.repository;

import org.hrsninja.api.model.Position;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PositionRepository {
    Position save(Position position);
    Optional<Position> findById(UUID id);
    List<Position> findAll();
    void addCandidate(UUID positionId, UUID candidateId);
    void archive(UUID positionId);
} 