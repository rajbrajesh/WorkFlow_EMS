import { useState } from "react";
import { Link } from "react-router-dom";

import "./LoginPage.css";

/**
 * Login page for WorkFlow.
 *
 * AuthLayout already provides:
 * - Authentication page background
 * - Authentication card
 * - WorkFlow branding
 *
 * Therefore, this component only contains
 * Login-specific content.
 */
function LoginPage() {

    /*
     * Stores the email entered by the user.
     */
    const [email, setEmail] = useState("");

    /*
     * Stores the password entered by the user.
     */
    const [password, setPassword] = useState("");

    /*
     * Handles the login form submission.
     *
     * API integration will be added later.
     */
    const handleSubmit = (event) => {
        event.preventDefault();

        console.log("Login form submitted:", {
            email,
            password
        });
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

                {/* Email field */}
                <div className="form-group">
                    <label htmlFor="email">
                        Email
                    </label>

                    <input
                        id="email"
                        type="email"
                        placeholder="Enter your email"
                        value={email}
                        onChange={(event) =>
                            setEmail(event.target.value)
                        }
                        required
                    />
                </div>

                {/* Password field */}
                <div className="form-group">
                    <label htmlFor="password">
                        Password
                    </label>

                    <input
                        id="password"
                        type="password"
                        placeholder="Enter your password"
                        value={password}
                        onChange={(event) =>
                            setPassword(event.target.value)
                        }
                        required
                    />
                </div>

                {/* Login button */}
                <button
                    type="submit"
                    className="auth-button"
                >
                    Login
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