package org.hrsninja.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CvFile {
    private UUID id;
    private UUID candidateId;
    private String fileName;
    private String contentType;
    private byte[] cvFile;
}
