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

import org.springframework.http.HttpMethod;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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

    // Defines which frontend origins, HTTP methods and headers
// are allowed to communicate with the backend.
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        /*
         * Allow requests coming from our React development server.
         *
         * React/Vite currently runs on localhost:5173.
         */
        configuration.setAllowedOrigins(
                List.of("http://localhost:5173")
        );

        /*
         * Allow the HTTP methods used by our frontend.
         */
        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                )
        );

        /*
         * Allow headers required by our API requests.
         *
         * Authorization will be important later when React
         * starts sending the JWT.
         */
        configuration.setAllowedHeaders(
                List.of(
                        "Authorization",
                        "Content-Type"
                )
        );

        /*
         * Allow the browser to expose these response headers
         * to the frontend when needed.
         */
        configuration.setExposedHeaders(
                List.of("Authorization")
        );

        /*
         * Register this CORS configuration for all backend endpoints.
         */
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }


    // Defines Spring Security's request filtering and authorization rules.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Enable CORS so the React frontend can communicate
                // with the Spring Boot backend.
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

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
                                "/api/auth/login",
                                "/error"
                        ).permitAll()

                        // All authenticated users can view employees.
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/employees",
                                "/api/employees/**"
                        ).hasAnyRole("ADMIN", "HR", "USER")

                        // Only ADMIN and HR can create employees.
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/employees",
                                "/api/employees/**"
                        ).hasAnyRole("ADMIN", "HR")

                        // Only ADMIN and HR can update employees.
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/employees",
                                "/api/employees/**"
                        ).hasAnyRole("ADMIN", "HR")

                        // Only ADMIN can delete employees.
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/employees",
                                "/api/employees/**"
                        ).hasRole("ADMIN")


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