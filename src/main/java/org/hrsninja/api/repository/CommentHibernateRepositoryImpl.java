package org.hrsninja.api.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hrsninja.api.model.Candidate;
import org.hrsninja.api.model.Comment;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
@Slf4j
@RequiredArgsConstructor
public class CommentHibernateRepositoryImpl implements CommentRepository {
    private final SessionFactory sessionFactory;

    @Override
    public Comment save(Comment comment) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            log.info("Save comment via Hibernate");

            transaction = session.beginTransaction();

            session.merge(comment);

            transaction.commit();

            return comment;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            log.error("Error", e);
            throw e;
        }
    }

    @Override
    public Optional<Comment> findById(UUID id) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            log.info("Find comment by ID via Hibernate");

            transaction = session.beginTransaction();

            Comment comment = session.get(Comment.class, id);

            transaction.commit();

            return Optional.ofNullable(comment);
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            log.error("Error", e);
            throw e;
        }
    }

    @Override
    public List<Comment> findAllByCandidate(Candidate candidate) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            log.info("Find comments by Candidate ID via Hibernate");

            transaction = session.beginTransaction();

            String hql = "FROM Comment c WHERE c.candidate = :candidate";

            List<Comment> commentQueries = session.createQuery(hql, Comment.class)
                    .setParameter("candidate", candidate)
                    .getResultList();

            transaction.commit();

            return commentQueries;
        } catch (Exception e) {
            log.error("Error", e);

            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }

            throw e;
        }
    }
} 