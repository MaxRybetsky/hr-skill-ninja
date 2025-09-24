package org.hrsninja.api.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hrsninja.api.model.Candidate;
import org.hrsninja.api.model.CandidateStatus;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
@Primary
@Slf4j
@RequiredArgsConstructor
public class CandidatesHibernateRepositoryImpl implements CandidateRepository {
    private final EntityManager em;

    @Override
    @Transactional
    public Candidate save(Candidate candidate) {
        em.persist(candidate);
        return candidate;
    }

    @Override
    @Transactional
    public Candidate update(Candidate candidate) {
        return em.merge(candidate);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Candidate> findById(UUID id) {
        Candidate candidate = em.find(Candidate.class, id);
        return Optional.ofNullable(candidate);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Candidate> findExtendedById(UUID id) {
        // Используем JOIN FETCH
        String jql = "SELECT c " +
                "FROM Candidate c " +
                "JOIN FETCH c.positions p " +
                "JOIN FETCH c.comments " +
                "JOIN FETCH p.candidates " +
                "WHERE c.id=:id";

        Candidate candidate = em.createQuery(jql, Candidate.class)
                .setParameter("id", id)
                .getSingleResult();

        return Optional.of(candidate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Candidate> findAll() {
        TypedQuery<Candidate> query = em.createQuery("FROM Candidate", Candidate.class);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Candidate> search(String fio, Set<CandidateStatus> statuses) {
        StringBuilder hql = buildHql(fio, statuses);
        TypedQuery<Candidate> query = em.createQuery(hql.toString(), Candidate.class);
        if (fio != null && !fio.trim().isEmpty()) {
            query.setParameter("fio", "%" + fio.trim() + "%");
        }
        if (statuses != null && !statuses.isEmpty()) {
            query.setParameter("statuses", statuses);
        }
        return query.getResultList();
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        Candidate candidate = em.find(Candidate.class, id);
        if (candidate != null) {
            em.remove(candidate);
        }
    }

    private StringBuilder buildHql(String fio, Set<CandidateStatus> statuses) {
        StringBuilder hql = new StringBuilder("FROM Candidate WHERE 1=1");
        if (fio != null && !fio.trim().isEmpty()) {
            hql.append(" AND LOWER(fio) LIKE LOWER(:fio)");
        }
        if (statuses != null && !statuses.isEmpty()) {
            hql.append(" AND status IN (:statuses)");
        }
        return hql;
    }
}
