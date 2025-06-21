package org.hrsninja.api.service;

import lombok.RequiredArgsConstructor;
import org.hrsninja.api.exception.CandidateNotFoundException;
import org.hrsninja.api.model.CvFile;
import org.hrsninja.api.repository.CandidateRepository;
import org.hrsninja.api.repository.CvRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CvServiceImpl implements CvService {

    private final CvRepository cvRepository;
    private final CandidateRepository candidateRepository;

    @Override
    public void saveCv(UUID candidateId, MultipartFile file) throws IOException {
        candidateRepository.findById(candidateId)
                .orElseThrow(() -> new CandidateNotFoundException(candidateId));

        CvFile cvFile = new CvFile();
        cvFile.setId(UUID.randomUUID());
        cvFile.setCandidateId(candidateId);
        cvFile.setFileName(StringUtils.cleanPath(file.getOriginalFilename()));
        cvFile.setContentType(file.getContentType());
        cvFile.setCvFile(file.getBytes());

        cvRepository.save(cvFile);
    }

    @Override
    public Optional<CvFile> getCvByCandidateId(UUID candidateId) {
        return cvRepository.findByCandidateId(candidateId);
    }
} 