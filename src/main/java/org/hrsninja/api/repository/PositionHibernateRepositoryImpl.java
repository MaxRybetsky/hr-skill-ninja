package org.hrsninja.api.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hrsninja.api.model.Candidate;
import org.hrsninja.api.model.Position;
import org.hrsninja.api.model.PositionStatus;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
@Slf4j
@RequiredArgsConstructor
public class PositionHibernateRepositoryImpl implements PositionRepository {
    private final EntityManager em;

    @Override
    @Transactional
    public Position save(Position position) {
        em.merge(position);

        return position;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Position> findById(UUID id) {
        return Optional.ofNullable(em.find(Position.class, id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Position> findAll() {
        return em.createQuery("FROM Position", Position.class).getResultList();
    }

    @Override
    @Transactional
    public void addCandidate(UUID positionId, UUID candidateId) {
        Position position = em.find(Position.class, positionId);
        Candidate candidate = em.find(Candidate.class, candidateId);

        if (position != null && candidate != null) {
            position.getCandidates().add(candidate);
        }
    }

    @Override
    @Transactional
    public void archive(UUID positionId) {
        Position position = em.find(Position.class, positionId);

        if (position != null) {
            position.setStatus(PositionStatus.ARCHIVE);
        }
    }
} 