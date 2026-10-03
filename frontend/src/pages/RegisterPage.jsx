import { useState } from "react";
import { Link } from "react-router-dom";

/**
 * Register page for WorkFlow.
 *
 * AuthLayout already provides:
 * - Authentication page background
 * - Authentication card
 * - WorkFlow branding
 *
 * Therefore, this component contains only
 * registration-specific content.
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
     * Stores the selected user role.
     *
     * USER is the default role.
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
            alert("Passwords do not match.");
            return;
        }

        /*
         * Temporary console output.
         *
         * Later this data will be sent to the
         * Spring Boot registration API.
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

                {/* Name field */}
                <div className="form-group">
                    <label htmlFor="name">
                        Name
                    </label>

                    <input
                        id="name"
                        type="text"
                        placeholder="Enter your name"
                        value={name}
                        onChange={(event) =>
                            setName(event.target.value)
                        }
                        required
                    />
                </div>

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

                {/* Confirm password field */}
                <div className="form-group">
                    <label htmlFor="confirmPassword">
                        Confirm Password
                    </label>

                    <input
                        id="confirmPassword"
                        type="password"
                        placeholder="Confirm your password"
                        value={confirmPassword}
                        onChange={(event) =>
                            setConfirmPassword(event.target.value)
                        }
                        required
                    />
                </div>

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