BEGIN; -- открытие транзакции

-- действие в рамках транзакции
INSERT INTO public.candidates
(id, fio, age, "position", cv_info, "comment", status)
VALUES('ad6bb218-a582-4872-b5fd-837493b68a29',
            'Ivanov Fedor Petrovich', 32, 'Lead Java Developer',
            'SkillBox', 'Ready for relocation', 'NEW'
            );

COMMIT; -- завершение транзакции

-- ИЛИ

BEGIN; -- открытие транзакции

-- действие в рамках транзакции
INSERT INTO public.candidates
(id, fio, age, "position", cv_info, "comment", status)
VALUES('ad6bb218-a582-4872-b5fd-837493b68a29',
            'Ivanov Fedor Petrovich', 32, 'Lead Java Developer',
            'SkillBox', 'Ready for relocation', 'NEW'
            );

ROLLBACK; -- откат транзакции - изменения не применятся