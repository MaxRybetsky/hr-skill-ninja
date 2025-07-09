-- Step 5 - Create candidates_save_history table
CREATE TABLE candidates_save_history (
    id UUID PRIMARY KEY,
    candidate_id UUID,
    created_datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE candidates_save_history IS 'История сохранения кандидатов';
COMMENT ON COLUMN candidates_save_history.id IS 'Уникальный идентификатор записи';
COMMENT ON COLUMN candidates_save_history.candidate_id IS 'ID кандидата';
COMMENT ON COLUMN candidates_save_history.created_datetime IS 'Дата и время сохранения';
