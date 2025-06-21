package org.hrsninja.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Candidate {
    private UUID id;
    private String fio;
    private short age;
    private String position;
    private String cvInfo;
    private String comment;
    private CandidateStatus status;

    /**
     * Вариант исправления ошибки при получении значения статуса для последующего сохранения в БД.
     * @return Статус в строковом значении.
     */
    public String getStatusString() {
        return status.name();
    }
} 