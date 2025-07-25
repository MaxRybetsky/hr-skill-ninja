package org.hrsninja.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.hrsninja.api.dto.*;
import org.hrsninja.api.exception.CandidateNotFoundException;
import org.hrsninja.api.exception.IllegalStatusTransitionException;
import org.hrsninja.api.model.Candidate;
import org.hrsninja.api.model.CandidateStatus;
import org.hrsninja.api.repository.CandidateRepository;
import org.hrsninja.api.mapper.CandidateMapper;
import org.hrsninja.api.service.CandidateService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import org.hrsninja.api.dto.ExtendedCandidateDTO;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository repository;
    private final CandidateMapper mapper;

    private static final Map<CandidateStatus, Set<CandidateStatus>> ALLOWED_TRANSITIONS = Map.of(
        CandidateStatus.NEW, Set.of(CandidateStatus.CV_REVIEW, CandidateStatus.DECLINED),
        CandidateStatus.CV_REVIEW, Set.of(CandidateStatus.SCHEDULED_FOR_INTERVIEW, CandidateStatus.DECLINED),
        CandidateStatus.SCHEDULED_FOR_INTERVIEW, Set.of(CandidateStatus.INTERVIEW, CandidateStatus.DECLINED),
        CandidateStatus.INTERVIEW, Set.of(CandidateStatus.OFFER, CandidateStatus.DECLINED),
        CandidateStatus.OFFER, Set.of(CandidateStatus.ACCEPTED, CandidateStatus.DECLINED),
        CandidateStatus.ACCEPTED, Set.of(CandidateStatus.STARTED_WORKING, CandidateStatus.DECLINED),
        CandidateStatus.STARTED_WORKING, Set.of(),
        CandidateStatus.DECLINED, Set.of()
    );

    @Override
    public CandidateDTO create(CreateCandidateRequest request) {
        Candidate candidate = new Candidate();

        candidate.setFio(request.getFio());
        candidate.setAge(request.getAge());
        candidate.setCvInfo(request.getCvInfo());
        candidate.setStatus(CandidateStatus.NEW);

        return mapper.toDTO(repository.save(candidate));
    }

    @Override
    public CandidateDTO update(UUID id, UpdateCandidateRequest request) {
        Candidate candidate = getCandidateOrThrow(id);
        
        candidate.setFio(request.getFio());
        candidate.setAge(request.getAge());
        candidate.setCvInfo(request.getCvInfo());

        return mapper.toDTO(repository.update(candidate));
    }

    @Override
    public CandidateDTO changeStatus(UUID id, ChangeStatusRequest request) {
        Candidate candidate = getCandidateOrThrow(id);
        
        if (!isValidTransition(candidate.getStatus(), request.getStatus())) {
            throw new IllegalStatusTransitionException(
                String.format("Cannot transition from %s to %s", 
                    candidate.getStatus(), request.getStatus()));
        }

        candidate.setStatus(request.getStatus());
        return mapper.toDTO(repository.update(candidate));
    }

    @Override
    public List<CandidateDTO> findAll() {
        return repository.findAll().stream()
            .map(mapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    // @Transactional(readOnly=true) - как один из вариантов фикс LIE
    public ExtendedCandidateDTO findExtendedById(UUID id) {
        return repository.findExtendedById(id)
                .map(mapper::toExtendedDTO)
                .orElseThrow(() -> new CandidateNotFoundException(id));
    }

    @Override
    public List<CandidateDTO> search(String fio, Set<CandidateStatus> statuses) {
        return repository.search(fio, statuses).stream()
            .map(mapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    private Candidate getCandidateOrThrow(UUID id) {
        return repository.findById(id)
            .orElseThrow(() -> new CandidateNotFoundException(id));
    }

    private boolean isValidTransition(CandidateStatus from, CandidateStatus to) {
        if (to == CandidateStatus.DECLINED) {
            return true;
        }
        Set<CandidateStatus> allowedTargets = ALLOWED_TRANSITIONS.get(from);
        return allowedTargets != null && allowedTargets.contains(to);
    }
}
