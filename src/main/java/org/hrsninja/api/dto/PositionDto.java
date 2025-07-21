package org.hrsninja.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class PositionDto {
    private UUID id;
    private String name;
    private String description;
    private String status;
    private LocalDate createdDate;
    private Set<UUID> candidateIds;
} 