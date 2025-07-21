package org.hrsninja.api.service;

import org.hrsninja.api.dto.*;
import org.hrsninja.api.model.CandidateStatus;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CandidateService {
    CandidateDTO create(CreateCandidateRequest request);
    CandidateDTO update(UUID id, UpdateCandidateRequest request);
    CandidateDTO changeStatus(UUID id, ChangeStatusRequest request);
    List<CandidateDTO> findAll();
    ExtendedCandidateDTO findById(UUID id);
    List<CandidateDTO> search(String fio, Set<CandidateStatus> statuses);
    void deleteById(UUID id);
}
