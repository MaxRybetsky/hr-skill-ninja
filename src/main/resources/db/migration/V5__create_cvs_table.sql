CREATE TABLE cvs (
    id UUID PRIMARY KEY,
    candidate_id UUID NOT NULL UNIQUE,
    file_name VARCHAR(255) NOT NULL,
    content_type VARCHAR(255) NOT NULL,
    cv_file BYTEA NOT NULL
);

COMMENT ON TABLE cvs IS 'Таблица для хранения CV кандидатов';
COMMENT ON COLUMN cvs.id IS 'Уникальный идентификатор записи CV';
COMMENT ON COLUMN cvs.candidate_id IS 'Идентификатор кандидата (внешний ключ)';
COMMENT ON COLUMN cvs.file_name IS 'Имя файла';
COMMENT ON COLUMN cvs.content_type IS 'MIME-тип файла';
COMMENT ON COLUMN cvs.cv_file IS 'Содержимое файла CV'; 