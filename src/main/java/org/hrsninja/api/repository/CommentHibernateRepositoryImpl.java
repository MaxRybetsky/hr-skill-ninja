package org.hrsninja.api.repository;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hrsninja.api.model.Candidate;
import org.hrsninja.api.model.Comment;
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
public class CommentHibernateRepositoryImpl implements CommentRepository {
    private final EntityManager em;
    private final CommentJpaRepository commentJpaRepository;

    @Override
    @Transactional
    public Comment save(Comment comment) {
        em.merge(comment);

        return comment;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> findById(UUID id) {
        return Optional.ofNullable( em.find(Comment.class, id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAllByCandidate(Candidate candidate) {
        String hql = "FROM Comment c WHERE c.candidate = :candidate";

        return em.createQuery(hql, Comment.class)
                .setParameter("candidate", candidate)
                .getResultList();
    }

    @Override
    public List<Comment> findAllExtended() {
        log.info("Find All Comments with Extended Candidate info");

        // 1. Строим граф: что именно нужно подтянуть «сразу»
        EntityGraph<Comment> graph = em.createEntityGraph(Comment.class);
        graph.addAttributeNodes("candidate");     // lazy-поле, которое хотим вытащить

        // 2. Выполняем обычный select, передавая граф через hint
        return em.createQuery("SELECT c FROM Comment c", Comment.class)
                .setHint("jakarta.persistence.fetchgraph", graph)
                .getResultList();                // Hibernate сгенерирует JOIN автоматически


        // Через JpaRepository:
        // return commentJpaRepository.findAllExtended();
    }
} 