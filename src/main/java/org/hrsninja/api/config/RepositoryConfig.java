package org.hrsninja.api.config;

import org.hrsninja.api.repository.CandidateRepository;
import org.hrsninja.api.repository.CandidateRepositoryJdbcTemplateImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RepositoryConfig {
    @Bean
    @Primary
    public CandidateRepository candidateRepository(CandidateRepositoryJdbcTemplateImpl repository) {
        return repository;
    }
} 