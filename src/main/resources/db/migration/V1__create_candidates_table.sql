-- Step 1 - Create table
CREATE TABLE candidates (
    id           UUID PRIMARY KEY,
    fio          VARCHAR(255) NOT NULL,
    age          SMALLINT NOT NULL CHECK (age BETWEEN 14 AND 99),
    position     VARCHAR(255) NOT NULL,
    cv_info      TEXT NOT NULL,
    comment      TEXT,
    status       VARCHAR(255) NOT NULL
);

COMMENT ON TABLE candidates IS 'Таблица для хранения информации о кандидатах';
COMMENT ON COLUMN candidates.id IS 'Уникальный идентификатор кандидата';
COMMENT ON COLUMN candidates.fio IS 'Фамилия, имя и отчество кандидата';
COMMENT ON COLUMN candidates.age IS 'Возраст кандидата';
COMMENT ON COLUMN candidates.position IS 'Позиция, на которую претендует кандидат';
COMMENT ON COLUMN candidates.cv_info IS 'Информация из резюме кандидата';
COMMENT ON COLUMN candidates.comment IS 'Комментарий для внутреннего пользования';
COMMENT ON COLUMN candidates.status IS 'Текущий статус кандидата в процессе найма';
