import { useState } from "react";
import { Link } from "react-router-dom";

import FormInput from "../components/forms/FormInput";
import { registerUser } from "../services/authService";

/**
 * Register page for WorkFlow.
 *
 * AuthLayout provides the common authentication layout.
 * FormInput provides reusable text/password input fields.
 */
function RegisterPage() {

    /*
     * Stores the user's name.
     */
    const [name, setName] = useState("");

    /*
     * Stores the user's email.
     */
    const [email, setEmail] = useState("");

    /*
     * Stores the user's password.
     */
    const [password, setPassword] = useState("");

    /*
     * Stores the confirmation password.
     */
    const [confirmPassword, setConfirmPassword] = useState("");

    /*
    * Stores the password mismatch error message.
    *
    * The error is displayed directly below the
    * Confirm Password field instead of using alert().
    */
    const [passwordError, setPasswordError] = useState("");

    /*
    * Stores the registration success message.
    */
    const [successMessage, setSuccessMessage] = useState("");

    /*
    * Stores the registration API error message.
    */
    const [registrationError, setRegistrationError] = useState("");

    /*
    * Tracks whether the registration API request
    * is currently in progress.
    */
    const [isLoading, setIsLoading] = useState(false);


    /*
     * Handles registration form submission.
     *
     * Backend API integration will be added later.
     */
    const handleSubmit = async (event) => {
        event.preventDefault();
        /*
        * Clear previous API messages before starting
        * a new registration attempt.
        */
        setSuccessMessage("");
        setRegistrationError("");


        /*
         * Temporary frontend validation.
         */
        if (password !== confirmPassword) {
            setPasswordError("Passwords do not match.");
            return;
        }

        /*
        * Prepare only the data required by the backend.
        *
        * confirmPassword is a frontend-only field and
        * should not be sent to the backend.
        *
        * Role is intentionally not sent because new users
        * are assigned USER role by the backend.
        */
        const registerData = {
            name,
            email,
            password
        };

        try {

            /*
            * Show loading state while the backend
            * processes the registration request.
            */
            setIsLoading(true);

            /*
            * Send registration request to the backend.
            */
            const response = await registerUser(registerData);

            /*
            * Show successful registration message
            * directly to the user.
            */
            setSuccessMessage(
                response.data?.message || "Registration successful."
            );

        } catch (error) {

            /*
            * Try to use the backend's error message.
            *
            * If the backend does not provide one,
            * show a safe generic message.
            */
            const message =
                error.response?.data?.message ||
                "Registration failed. Please try again.";

            setRegistrationError(message);
        } finally {

            /*
            * Stop the loading state whether the request
            * succeeds or fails.
            */
            setIsLoading(false);
        }
    };

    return (
        <>
            {/* Registration heading */}
            <div className="auth-title">
                <h2>Create Account</h2>
                <p>Register for a WorkFlow account</p>
            </div>

            {/* Registration form */}
            <form onSubmit={handleSubmit}>

                {/* Reusable name input */}
                <FormInput
                    id="name"
                    label="Name"
                    type="text"
                    placeholder="Enter your name"
                    value={name}
                    onChange={(event) =>
                        setName(event.target.value)
                    }
                    required
                />

                {/* Reusable email input */}
                <FormInput
                    id="email"
                    label="Email"
                    type="email"
                    placeholder="Enter your email"
                    value={email}
                    onChange={(event) =>{
                        setEmail(event.target.value);

                        // Clear previous backend registration error
                        // when the user starts correcting the email.
                        setRegistrationError("");
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
                        const value = event.target.value;

                        setPassword(value);

                        /*
                        * Re-check the confirm password whenever
                        * the original password changes.
                        */
                        if (confirmPassword && value !== confirmPassword) {
                            setPasswordError("Passwords do not match.");
                        } else {
                            setPasswordError("");
                        }
                    }}
                    required
                />

                {/* Reusable confirm-password input */}
                <FormInput
                    id="confirmPassword"
                    label="Confirm Password"
                    type="password"
                    placeholder="Confirm your password"
                    value={confirmPassword}
                    onChange={(event) => {
                        const value = event.target.value;

                        setConfirmPassword(value);

                        /*
                        * Live password matching.
                        *
                        * We only show the error when the user has
                        * entered something and the passwords differ.
                        */
                        if (value && value !== password) {
                            setPasswordError("Passwords do not match.");
                        } else {
                            setPasswordError("");
                        }
                    }}
                    required
                />

                {/* Password mismatch error */}
                {passwordError && (
                    <p className="form-error">
                        {passwordError}
                    </p>
                )}

                {/* Registration success message */}
                {successMessage && (
                    <p className="form-success">
                        {successMessage}
                    </p>
                )}

                {/* Registration error message */}
                {registrationError && (
                    <p className="form-error">
                        {registrationError}
                    </p>
                )}


                {/* Register button */}
                <button
                    type="submit"
                    className="auth-button"
                    disabled={isLoading}
                >
                    {isLoading ? "Registering..." : "Register"}
                </button>

            </form>

            {/* Login navigation */}
            <div className="auth-footer">
                <p>
                    Already have an account?{" "}
                    <Link to="/login">
                        Login
                    </Link>
                </p>
            </div>
        </>
    );
}

export default RegisterPage;