package org.hrsninja.api.service;

import org.hrsninja.api.model.CvFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public interface CvService {
    void saveCv(UUID candidateId, MultipartFile file) throws IOException;
    Optional<CvFile> getCvByCandidateId(UUID candidateId);
} 