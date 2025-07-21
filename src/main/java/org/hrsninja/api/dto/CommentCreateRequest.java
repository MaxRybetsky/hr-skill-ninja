package org.hrsninja.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateRequest {
    private String author;
    private String comment;
} 