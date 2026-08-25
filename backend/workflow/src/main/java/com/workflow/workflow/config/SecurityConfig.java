package com.workflow.workflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuration related to application security.
 *
 * For now, we are only registering BCryptPasswordEncoder
 * as a Spring Bean.
 *
 * Full Spring Security configuration and JWT authentication
 * will be introduced in the next security phase.
 */
@Configuration
public class SecurityConfig {

    /**
     * Creates a BCrypt password encoder managed by Spring.
     *
     * Spring will create this object and inject it wherever
     * BCryptPasswordEncoder is required.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}