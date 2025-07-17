package org.hrsninja.api.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "candidates")
@Getter
@Setter
public class Candidate {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;
    
    @Column(name = "fio", nullable = false)
    private String fio;
    
    @Column(name = "age", nullable = false)
    private short age;
    
    @Column(name = "position", nullable = false)
    private String position;
    
    @Column(name = "cv_info")
    private String cvInfo;
    
    @Column(name = "comment")
    private String comment;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CandidateStatus status;
} 