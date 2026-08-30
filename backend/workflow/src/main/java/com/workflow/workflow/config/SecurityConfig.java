package com.workflow.workflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Main Spring Security configuration.
 *
 * Currently we are setting up the security framework
 * without implementing JWT yet.
 */
@Configuration
@EnableConfigurationProperties(JwtConfig.class)
public class SecurityConfig {

    /**
     * BCrypt encoder used for password hashing.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the Spring Security filter chain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                /*
                 * Disable CSRF because this application is
                 * being designed as a REST API.
                 */
                .csrf(csrf -> csrf.disable())

                /*
                 * Configure authentication behavior.
                 *
                 * For an API, an unauthenticated request should
                 * receive HTTP 401 instead of being redirected
                 * to a login page.
                 */
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(
                                (request, response, authException) ->
                                        response.sendError(
                                                HttpStatus.UNAUTHORIZED.value(),
                                                "Unauthorized"
                                        )
                        )
                )

                /*
                 * Authorization rules.
                 */
                .authorizeHttpRequests(auth -> auth

                        /*
                         * Registration and login are public.
                         */
                        .requestMatchers(
                                "/api/auth/register",
                                "/api/auth/login"
                        ).permitAll()

                        /*
                         * Every other API requires authentication.
                         */
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}