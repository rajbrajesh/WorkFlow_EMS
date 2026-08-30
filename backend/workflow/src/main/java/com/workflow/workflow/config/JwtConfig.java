package com.workflow.workflow.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Holds JWT-related configuration values.
 *
 * Values are loaded from application.properties:
 *
 * jwt.secret
 * jwt.expiration
 */
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    /*
     * Secret key used to sign and validate JWT tokens.
     */
    private String secret;

    /*
     * Token lifetime in milliseconds.
     */
    private long expiration;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}