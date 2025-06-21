package org.hrsninja.api.repository;

import org.hrsninja.api.model.CvFile;

import java.util.Optional;
import java.util.UUID;

public interface CvRepository {
    void save(CvFile cvFile);
    Optional<CvFile> findByCandidateId(UUID candidateId);
} 