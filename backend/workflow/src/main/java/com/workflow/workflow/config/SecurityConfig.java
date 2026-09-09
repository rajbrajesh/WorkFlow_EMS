package com.workflow.workflow.config;

import com.workflow.workflow.security.JwtAuthenticationFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableConfigurationProperties(JwtConfig.class)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Constructor injection for our JWT filter.
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // BCrypt is used for securely hashing user passwords.
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Defines Spring Security's request filtering and authorization rules.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Disable CSRF because this is a stateless REST API using JWT.
                .csrf(csrf -> csrf.disable())

                // Return HTTP 401 when an unauthenticated user accesses
                // a protected endpoint.
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(
                                (request, response, authException) ->
                                        response.sendError(
                                                HttpStatus.UNAUTHORIZED.value(),
                                                "Unauthorized"
                                        )
                        )
                )

                // JWT authentication is stateless.
                // We do not want Spring Security to create/use HTTP sessions.
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // Define which endpoints require authentication.
                .authorizeHttpRequests(auth -> auth
                        // Register and login are public endpoints.
                        .requestMatchers(
                                "/api/auth/register",
                                "/api/auth/login"
                        ).permitAll()

                        // Every other endpoint requires authentication.
                        .anyRequest().authenticated()
                )

                // Run our JWT filter before Spring Security's
                // UsernamePasswordAuthenticationFilter.
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}