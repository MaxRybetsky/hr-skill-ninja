package org.hrsninja.api.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.hrsninja.api.model.Candidate;
import org.hrsninja.api.model.CandidateStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class CandidateSearchRepository {
    private final EntityManager em;

    public List<Candidate> search(String fio, Set<CandidateStatus> statuses) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Candidate> cq = cb.createQuery(Candidate.class);

        // По аналогии с "FROM Candidate"
        Root<Candidate> root = cq.from(Candidate.class);

        // Предикаты - условия для WHERE
        List<Predicate> predicates = new ArrayList<>();
        if (fio != null) {
            predicates.add(cb.like(cb.lower(root.get("fio")), "%" + fio.toLowerCase() + "%"));
        }
        if (statuses != null && !statuses.isEmpty()) {
            predicates.add(root.get("status").in(statuses));
        }

        // Сам оператор WHERE добавляется к root - получается FROM Candidate WHERE *предикаты*
        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }
}
