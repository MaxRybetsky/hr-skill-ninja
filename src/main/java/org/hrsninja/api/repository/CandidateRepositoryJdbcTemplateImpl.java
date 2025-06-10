package org.hrsninja.api.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hrsninja.api.model.Candidate;
import org.hrsninja.api.model.CandidateStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CandidateRepositoryJdbcTemplateImpl implements CandidateRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Candidate> ROW_MAPPER = (rs, rowNum) -> {
        Candidate candidate = new Candidate();
        candidate.setId(rs.getObject("id", UUID.class));
        candidate.setFio(rs.getString("fio"));
        candidate.setAge(rs.getShort("age"));
        candidate.setPosition(rs.getString("position"));
        candidate.setCvInfo(rs.getString("cv_info"));
        candidate.setComment(rs.getString("comment"));
        candidate.setStatus(CandidateStatus.valueOf(rs.getString("status")));
        return candidate;
    };

    @Override
    public Candidate save(Candidate candidate) {
        String sql = """
                INSERT INTO candidates (id, fio, age, position, cv_info, status)
                VALUES (?, ?, ?, ?, ?, ?)
                RETURNING id, fio, age, position, cv_info, comment, status
                """;

        log.info("Save candidate by JDBC Template");

        return jdbcTemplate.queryForObject(sql, ROW_MAPPER,
                candidate.getId(),
                candidate.getFio(),
                candidate.getAge(),
                candidate.getPosition(),
                candidate.getCvInfo(),
                candidate.getStatus().name());
    }

    @Override
    public Candidate update(Candidate candidate) {
        String sql = """
                UPDATE candidates
                SET fio = ?, age = ?, position = ?, cv_info = ?, status = ?, comment = ?
                WHERE id = ?
                RETURNING id, fio, age, position, cv_info, comment, status
                """;

        log.info("Update candidate by JDBC Template");

        return jdbcTemplate.queryForObject(sql, ROW_MAPPER,
                candidate.getFio(),
                candidate.getAge(),
                candidate.getPosition(),
                candidate.getCvInfo(),
                candidate.getStatus().name(),
                candidate.getComment(),
                candidate.getId());
    }

    @Override
    public Optional<Candidate> findById(UUID id) {
        String sql = """
                SELECT id, fio, age, position, cv_info, comment, status
                FROM candidates
                WHERE id = ?
                """;

        log.info("Find candidate by JDBC Template");

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, ROW_MAPPER, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Candidate> findAll() {
        String sql = """
                SELECT id, fio, age, position, cv_info, comment, status
                FROM candidates
                ORDER BY id
                """;

        log.info("Find all candidates by JDBC Template");

        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    @Override
    public List<Candidate> search(String fio, Set<CandidateStatus> statuses, String position) {
        StringBuilder sql = new StringBuilder("""
                SELECT id, fio, age, position, cv_info, comment, status
                FROM candidates
                WHERE 1=1
                """);

        log.info("Search candidates by JDBC Template");

        List<Object> params = new ArrayList<>();

        if (fio != null && !fio.isBlank()) {
            sql.append(" AND fio ILIKE ?");
            params.add("%" + fio + "%");
        }

        if (statuses != null && !statuses.isEmpty()) {
            sql.append(" AND status = ANY(?)");
            params.add(statuses.stream().map(Enum::name).toArray(String[]::new));
        }

        if (position != null && !position.isBlank()) {
            sql.append(" AND position ILIKE ?");
            params.add("%" + position + "%");
        }

        sql.append(" ORDER BY id");

        return jdbcTemplate.query(sql.toString(), ROW_MAPPER, params.toArray());
    }
}
