package com.workflow.workflow.security;

import com.workflow.workflow.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Read the Authorization header from the incoming request.
        String authorizationHeader = request.getHeader("Authorization");

        // If the request does not contain a Bearer token,
        // continue with the normal Spring Security filter chain.
        if (authorizationHeader == null ||
                !authorizationHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        // Remove "Bearer " and keep only the actual JWT.
        String token = authorizationHeader.substring(7);

        try {
            // Extract the user's email from the JWT subject.
            String email = jwtService.extractEmail(token);

            // Only authenticate if there is an email and
            // no authentication has already been established.
            if (email != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                // Validate the JWT using the email and expiration.
                if (jwtService.isTokenValid(token, email)) {

                    // Create an authenticated Spring Security user.
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    email,
                                    null,
                                    Collections.singletonList(
                                            new SimpleGrantedAuthority("ROLE_USER")
                                    )
                            );

                    // Store authentication inside Spring Security's context.
                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                }
            }

        } catch (Exception exception) {

            // Invalid or expired JWT should not crash the application.
            // The request will continue without authentication.
        }

        // Continue to the next filter.
        filterChain.doFilter(request, response);
    }
}