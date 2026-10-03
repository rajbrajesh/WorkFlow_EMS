import { useState } from "react";
import { Link } from "react-router-dom";

import FormInput from "../components/forms/FormInput";

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
     * Stores the selected user role.
     */
    const [role, setRole] = useState("USER");

    /*
     * Handles registration form submission.
     *
     * Backend API integration will be added later.
     */
    const handleSubmit = (event) => {
        event.preventDefault();

        /*
         * Temporary frontend validation.
         */
        if (password !== confirmPassword) {
            setPasswordError("Passwords do not match.");
            return;
        }

        /*
         * Temporary console output.
         */
        console.log("Register form submitted:", {
            name,
            email,
            password,
            role
        });
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

                {/* Role field */}
                <div className="form-group">

                    <label htmlFor="role">
                        Role
                    </label>

                    <select
                        id="role"
                        value={role}
                        onChange={(event) =>
                            setRole(event.target.value)
                        }
                    >
                        <option value="USER">User</option>
                        <option value="HR">HR</option>
                        <option value="ADMIN">Admin</option>
                    </select>

                </div>

                {/* Register button */}
                <button
                    type="submit"
                    className="auth-button"
                >
                    Register
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