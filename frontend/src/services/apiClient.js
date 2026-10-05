import axios from "axios";

/**
 * Central Axios client for the WorkFlow frontend.
 *
 * Keeping Axios configuration in one place means
 * all API calls can use the same backend configuration.
 */
const apiClient = axios.create({

    /*
     * Backend URL comes from the Vite environment variable.
     *
     * This avoids hardcoding the backend URL directly
     * inside our JavaScript code.
     */
    baseURL: import.meta.env.VITE_API_BASE_URL,

    /*
     * Maximum time Axios will wait for a response.
     *
     * 10 seconds = 10000 milliseconds.
     */
    timeout: 10000,

    /*
     * Default headers sent with API requests.
     *
     * Most WorkFlow APIs will exchange JSON data.
     */
    headers: {
        "Content-Type": "application/json"
    }
});

/*
 * Axios request interceptor.
 *
 * Runs before every API request and checks whether
 * a JWT token is available in localStorage.
 */
apiClient.interceptors.request.use(
    (config) => {
        /*
         * Retrieve the JWT saved during login.
         */
        const token = localStorage.getItem("workflow_token");

        /*
         * Add the JWT to the Authorization header
         * when a token is available.
         */
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }

        return config;
    },
    (error) => {
        /*
         * Pass request errors to Axios.
         */
        return Promise.reject(error);
    }
);

/*
 * Export the configured Axios client.
 *
 * Other services can import this instead of
 * importing axios directly.
 */
export default apiClient;