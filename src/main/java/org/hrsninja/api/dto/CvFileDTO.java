package org.hrsninja.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CvFileDTO {
    private String fileName;
    private String contentType;
    private byte[] cvFile;
} 