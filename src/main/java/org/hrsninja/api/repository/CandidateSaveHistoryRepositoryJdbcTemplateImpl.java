package org.hrsninja.api.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hrsninja.api.model.CandidateSaveHistoryEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CandidateSaveHistoryRepositoryJdbcTemplateImpl implements CandidateSaveHistoryRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<CandidateSaveHistoryEntity> ROW_MAPPER = (rs, rowNum) -> {
        CandidateSaveHistoryEntity entity = new CandidateSaveHistoryEntity();
        entity.setId(rs.getObject("id", UUID.class));
        entity.setCandidateId(rs.getObject("candidate_id", UUID.class));
        entity.setCreatedDatetime(rs.getObject("created_datetime", Timestamp.class).toLocalDateTime());
        return entity;
    };

    @Override
    public CandidateSaveHistoryEntity save(CandidateSaveHistoryEntity entity) {
        String sql = """
                INSERT INTO candidates_save_history (id, candidate_id, created_datetime)
                VALUES (?, ?, ?)
                RETURNING id, candidate_id, created_datetime
                """;
        log.info("Save candidate save history by JDBC Template");
        return jdbcTemplate.queryForObject(sql, ROW_MAPPER,
                entity.getId(),
                entity.getCandidateId(),
                entity.getCreatedDatetime()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CandidateSaveHistoryEntity> findById(UUID id) {
        String sql = """
                SELECT id, candidate_id, created_datetime
                FROM candidates_save_history
                WHERE id = ?
                """;
        log.info("Find candidate save history by id by JDBC Template");
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, ROW_MAPPER, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CandidateSaveHistoryEntity> findAll() {
        String sql = """
                SELECT id, candidate_id, created_datetime
                FROM candidates_save_history
                ORDER BY created_datetime DESC
                """;
        log.info("Find all candidate save history by JDBC Template");
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }
} 