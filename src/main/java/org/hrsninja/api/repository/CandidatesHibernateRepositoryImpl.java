package org.hrsninja.api.repository;

import  org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hrsninja.api.model.Candidate;
import org.hrsninja.api.model.CandidateStatus;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
@Primary
@Slf4j
@RequiredArgsConstructor
public class CandidatesHibernateRepositoryImpl implements CandidateRepository {
    private final SessionFactory sessionFactory;

    @Override
    @Transactional
    public Candidate save(Candidate candidate) {
        Session session = sessionFactory.getCurrentSession();
        log.info("Save candidate via Hibernate");

        session.persist(candidate);

        return candidate;
    }

    @Override
    public Candidate update(Candidate candidate) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            log.info("Update candidate via Hibernate");

            transaction = session.beginTransaction();

            Candidate merged = session.merge(candidate);

            transaction.commit();

            return merged;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }

            log.error("Error", e);

            throw e;
        }
    }

    public Optional<Candidate> findById(UUID id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            log.info("Find candidate by ID via Hibernate");
            transaction = session.beginTransaction();

            Query<Candidate> query = session.createQuery(
                    "SELECT DISTINCT c FROM Candidate c " +
                            "LEFT JOIN FETCH c.positions " +
                            "LEFT JOIN FETCH c.comments " +
                            "WHERE c.id = :id", Candidate.class);
            query.setParameter("id", id);
            Optional<Candidate> candidate = query.uniqueResultOptional();

            transaction.commit();
            return candidate;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            log.error("Error", e);
            throw e;
        }
    }

    @Override
    public List<Candidate> findAll() {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            log.info("Find all candidates via Hibernate");

            transaction = session.beginTransaction();

            Query<Candidate> query = session.createQuery("FROM Candidate", Candidate.class);
            List<Candidate> candidates = query.list();

            transaction.commit();

            return candidates;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }

            log.error("Error", e);

            throw e;
        }
    }

    @Override
    public List<Candidate> search(String fio, Set<CandidateStatus> statuses) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            log.info("Search candidates with given criteria via Hibernate");

            transaction = session.beginTransaction();

            StringBuilder hql = buildHql(fio, statuses);

            Query<Candidate> query = session.createQuery(hql.toString(), Candidate.class);

            if (fio != null && !fio.trim().isEmpty()) {
                query.setParameter("fio", "%" + fio.trim() + "%");
            }

            if (statuses != null && !statuses.isEmpty()) {
                query.setParameter("statuses", statuses);
            }

            List<Candidate> candidates = query.list();

            transaction.commit();

            return candidates;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }

            log.error("Error", e);

            throw e;
        }
    }

    @Override
    public void deleteById(UUID id) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            log.info("Delete candidate by ID via Hibernate");

            transaction = session.beginTransaction();

            Candidate candidate = session.get(Candidate.class, id);
            if (candidate != null) {
                session.remove(candidate);
            }

            transaction.commit();
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }

            log.error("Error", e);

            throw e;
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
