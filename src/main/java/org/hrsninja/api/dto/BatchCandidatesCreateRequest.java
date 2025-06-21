package org.hrsninja.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BatchCandidatesCreateRequest {
    @Valid
    @NotEmpty
    private List<CreateCandidateRequest> candidates;
}
