package org.hrsninja.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionCreateRequest {
    private String name;
    private String description;
} 