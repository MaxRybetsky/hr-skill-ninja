package org.hrsninja.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.hrsninja.api.model.Candidate;
import org.hrsninja.api.model.Position;
import org.hrsninja.api.model.PositionStatus;
import org.hrsninja.api.repository.CandidateRepository;
import org.hrsninja.api.repository.PositionRepository;
import org.hrsninja.api.dto.PositionDto;
import org.hrsninja.api.mapper.PositionMapper;
import org.hrsninja.api.service.PositionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hrsninja.api.util.TimeUtil.FIXED_DATE;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {
    private final PositionRepository positionRepository;
    private final CandidateRepository candidateRepository;
    private final PositionMapper positionMapper;

    @Override
    @Transactional
    public PositionDto addPosition(String name, String description) {
        Position position = new Position();
        position.setId(UUID.randomUUID());
        position.setName(name);
        position.setDescription(description);
        position.setStatus(PositionStatus.ACTIVE);
        position.setCreatedDate(FIXED_DATE);
        return positionMapper.toDto(positionRepository.save(position));
    }

    @Override
    @Transactional
    public void addCandidateToPosition(UUID positionId, UUID candidateId) {
        Position position = positionRepository.findById(positionId).orElseThrow();
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow();

        position.getCandidates().add(candidate);

        positionRepository.save(position);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PositionDto> getPositionWithCandidates(UUID positionId) {
        return positionRepository.findById(positionId).map(positionMapper::toDto);
    }

    @Override
    @Transactional
    public void archivePosition(UUID positionId) {
        Position position = positionRepository.findById(positionId).orElseThrow();

        position.setStatus(PositionStatus.ARCHIVE);

        positionRepository.save(position);
    }

    @Override
    @Transactional
    public List<PositionDto> findAll() {
        return positionRepository.findAll().stream().map(positionMapper::toDto).collect(Collectors.toList());
    }
} 