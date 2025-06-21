package org.hrsninja.api.controller;

import lombok.RequiredArgsConstructor;
import org.hrsninja.api.service.CvService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/candidates/{candidateId}/cv")
@RequiredArgsConstructor
public class CvController {

    private final CvService cvService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadCv(@PathVariable UUID candidateId, @RequestParam("file") MultipartFile file) throws IOException {
        cvService.saveCv(candidateId, file);
    }

    @GetMapping
    public ResponseEntity<byte[]> downloadCv(@PathVariable UUID candidateId) {
        return cvService.getCvByCandidateId(candidateId)
                .map(cv -> ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(cv.getContentType()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + cv.getFileName() + "\"")
                        .body(cv.getCvFile()))
                .orElse(ResponseEntity.notFound().build());
    }
} 