package org.hrsninja.api.repository;

import org.hrsninja.api.model.CandidateSaveHistoryEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CandidateSaveHistoryRepository {
    CandidateSaveHistoryEntity save(CandidateSaveHistoryEntity entity);
    Optional<CandidateSaveHistoryEntity> findById(UUID id);
    List<CandidateSaveHistoryEntity> findAll();
} 