import apiClient from "./apiClient";

/**
 * Authentication service for WorkFlow.
 *
 * This file contains all API calls related to
 * user authentication.
 *
 * LoginPage and RegisterPage will use these methods
 * instead of calling Axios directly.
 */

/**
 * Register a new WorkFlow user.
 *
 * @param {Object} registerData - User registration data.
 * @returns {Promise} Axios response from the backend.
 */
export const registerUser = async (registerData) => {

    /*
     * Send registration data to the Spring Boot
     * authentication endpoint.
     */
    const response = await apiClient.post(
        "/api/auth/register",
        registerData
    );

    /*
     * Return the backend response to the caller.
     */
    return response;
};


/**
 * Login an existing WorkFlow user.
 *
 * @param {Object} loginData - User login credentials.
 * @returns {Promise} Axios response containing the JWT.
 */
export const loginUser = async (loginData) => {

    /*
     * Send login credentials to the Spring Boot
     * authentication endpoint.
     */
    const response = await apiClient.post(
        "/api/auth/login",
        loginData
    );

    /*
     * Return the backend response to the caller.
     */
    return response;
};