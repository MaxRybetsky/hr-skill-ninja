package org.hrsninja.api.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hrsninja.api.model.CvFile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CvRepositoryJdbcTemplateImpl implements CvRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<CvFile> ROW_MAPPER = (rs, rowNum) -> {
        CvFile cvFile = new CvFile();
        cvFile.setId(rs.getObject("id", UUID.class));
        cvFile.setCandidateId(rs.getObject("candidate_id", UUID.class));
        cvFile.setFileName(rs.getString("file_name"));
        cvFile.setContentType(rs.getString("content_type"));
        cvFile.setCvFile(rs.getBytes("cv_file"));
        return cvFile;
    };

    @Override
    public void save(CvFile cvFile) {
        log.info("Upload CV file to DB via JDBC Template");

        String sql = """
                INSERT INTO cvs (id, candidate_id, file_name, content_type, cv_file)
                VALUES (?, ?, ?, ?, ?)
                ON CONFLICT (candidate_id) DO UPDATE SET
                    file_name = EXCLUDED.file_name,
                    content_type = EXCLUDED.content_type,
                    cv_file = EXCLUDED.cv_file
                RETURNING id, candidate_id, file_name, content_type, cv_file
                """;

        jdbcTemplate.queryForObject(sql, ROW_MAPPER,
                cvFile.getId(),
                cvFile.getCandidateId(),
                cvFile.getFileName(),
                cvFile.getContentType(),
                cvFile.getCvFile());
    }

    @Override
    public Optional<CvFile> findByCandidateId(UUID candidateId) {
        log.info("Get CV file to DB via JDBC Template");

        String sql = """
                SELECT id, candidate_id, file_name, content_type, cv_file
                FROM cvs
                WHERE candidate_id = ?
                """;
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, ROW_MAPPER, candidateId));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
} 