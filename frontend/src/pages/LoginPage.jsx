import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import FormInput from "../components/forms/FormInput";
import "./LoginPage.css";
import { loginUser } from "../services/authService";
import { useAuth } from "../context/AuthContext";

/**
 * Login page for WorkFlow.
 *
 * AuthLayout provides the common authentication layout.
 * FormInput provides reusable input fields.
 */
function LoginPage() {

    /*
     * Provides programmatic navigation after successful login.
     */
    const navigate = useNavigate();

    /*
    * Access authentication actions from AuthContext.
    */
    const { login } = useAuth();

    /*
     * Stores the email entered by the user.
     */
    const [email, setEmail] = useState("");

    /*
     * Stores the password entered by the user.
     */
    const [password, setPassword] = useState("");

    /*
    * Stores the success message after successful login.
    */
    const [successMessage, setSuccessMessage] = useState("");

    /*
    * Stores the error message returned by the backend.
    */
    const [loginError, setLoginError] = useState("");

    /*
    * Tracks whether the login API request is in progress.
    */
    const [isLoading, setIsLoading] = useState(false);

    /*
    * Handles the login form submission.
    *
    * Sends the user's credentials to the backend
    * and handles the login API response.
    */
    const handleSubmit = async (event) => {
        event.preventDefault();

        /*
        * Clear messages from the previous login attempt.
        */
        setSuccessMessage("");
        setLoginError("");

        /*
        * Prepare the login data expected by the backend.
        */
        const loginData = {
            email,
            password
        };

        try {
            /*
            * Show loading state while the API request is running.
            */
            setIsLoading(true);

            /*
            * Send login request to the backend.
            */
            const response = await loginUser(loginData);

            /*
            * Store the JWT token returned by the backend.
            *
            * The token will be used later when calling
            * protected backend APIs.
            */
            const token = response.data?.token;

            if (token) {
                /*
                * Store the JWT returned by the backend.
                */
                localStorage.setItem("workflow_token", token);

                /*
                * Update the global authentication state
                * only after a valid token is available.
                */
                login();
            }


            /*
            * Temporarily store the success message.
            *
            * The response message will be displayed in
            * the next part of Step 25C.
            */
            setSuccessMessage(
                response.data?.message || "Login successful."
            );

            /*
            * Temporary console log for verification.
            */
            console.log("Login successful:", response.data);

            /*
            * Redirect the user to the dashboard
            * after successful authentication.
            */
            navigate("/dashboard");

        } catch (error) {
            /*
            * Read the backend error message when available.
            *
            * If the backend does not provide a message,
            * use a safe fallback message.
            */
            const message =
                error.response?.data?.message ||
                "Login failed. Please check your credentials.";

            setLoginError(message);

            /*
            * Temporary console log for debugging.
            */
            console.error("Login failed:", error.response?.data || error.message);

        } finally {
            /*
            * Stop the loading state regardless of success or failure.
            */
            setIsLoading(false);
        }
    };

    return (
        <>
            {/* Login heading */}
            <div className="auth-title">
                <h2>Welcome Back</h2>
                <p>Login to your account</p>
            </div>

            {/* Login form */}
            <form onSubmit={handleSubmit}>

                {/* Reusable email input */}
                <FormInput
                    id="email"
                    label="Email"
                    type="email"
                    placeholder="Enter your email"
                    value={email}
                    onChange={(event) => {
                        setEmail(event.target.value);
                        setLoginError("");
                    }}
                    required
                />

                {/* Reusable password input */}
                <FormInput
                    id="password"
                    label="Password"
                    type="password"
                    placeholder="Enter your password"
                    value={password}
                    onChange={(event) => {
                        setPassword(event.target.value);
                        setLoginError("");
                    }}
                    required
                />

                {/* Login success message */}
                {successMessage && (
                    <p className="form-success">
                        {successMessage}
                    </p>
                )}

                {/* Login error message */}
                {loginError && (
                    <p className="form-error">
                        {loginError}
                    </p>
                )}

                {/* Login button */}
                <button
                    type="submit"
                    className="auth-button"
                    disabled={isLoading}
                >
                    {isLoading ? "Logging in..." : "Login"}
                </button>

            </form>

            {/* Register navigation */}
            <div className="auth-footer">
                <p>
                    Don't have an account?{" "}
                    <Link to="/register">
                        Register
                    </Link>
                </p>
            </div>
        </>
    );
}

export default LoginPage;