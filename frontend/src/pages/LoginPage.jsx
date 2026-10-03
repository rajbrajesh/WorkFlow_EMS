import { useState } from "react";
import { Link } from "react-router-dom";

import FormInput from "../components/forms/FormInput";
import "./LoginPage.css";

/**
 * Login page for WorkFlow.
 *
 * AuthLayout provides the common authentication layout.
 * FormInput provides reusable input fields.
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
     * Backend API integration will be added later.
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

                {/* Reusable email input */}
                <FormInput
                    id="email"
                    label="Email"
                    type="email"
                    placeholder="Enter your email"
                    value={email}
                    onChange={(event) =>
                        setEmail(event.target.value)
                    }
                    required
                />

                {/* Reusable password input */}
                <FormInput
                    id="password"
                    label="Password"
                    type="password"
                    placeholder="Enter your password"
                    value={password}
                    onChange={(event) =>
                        setPassword(event.target.value)
                    }
                    required
                />

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