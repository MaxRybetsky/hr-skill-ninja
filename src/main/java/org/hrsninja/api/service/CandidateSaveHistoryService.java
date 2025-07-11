package org.hrsninja.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hrsninja.api.model.CandidateSaveHistoryEntity;
import org.hrsninja.api.repository.CandidateSaveHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CandidateSaveHistoryService {
    private final CandidateSaveHistoryRepository saveHistoryRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW) // DEFAULT, REQUIRES_NEW, NEVER, MANDATORY
    public void createHistoryNote(UUID candidateId) {
        log.info("Add history note");
        CandidateSaveHistoryEntity entity = new CandidateSaveHistoryEntity();

        entity.setId(UUID.randomUUID());
        entity.setCandidateId(candidateId);
        entity.setCreatedDatetime(LocalDateTime.now());

        saveHistoryRepository.save(entity);
    }
}
