package org.hrsninja.api.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.UUID;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "cv_info")
    private String cvInfo;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CandidateStatus status;

    @ManyToMany(mappedBy = "candidates", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Position> positions = new HashSet<>();

    @OneToMany(mappedBy = "candidate", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();
} 