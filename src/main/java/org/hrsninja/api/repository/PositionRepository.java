package org.hrsninja.api.repository;

import org.hrsninja.api.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PositionRepository extends JpaRepository<Position, UUID> {
} 