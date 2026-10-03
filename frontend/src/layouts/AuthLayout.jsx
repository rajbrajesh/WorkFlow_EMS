import { Outlet } from "react-router-dom";

/**
 * Common layout for authentication pages.
 *
 * AuthLayout provides the shared structure for:
 * - Login page
 * - Register page
 *
 * Outlet renders whichever authentication page
 * matches the current route.
 */
function AuthLayout() {
    return (
        <div className="auth-page">

            {/* Common authentication card */}
            <div className="auth-card">

                {/* Application branding */}
                <div className="auth-header">
                    <h1>WorkFlow</h1>
                    <p>Employee Management System</p>
                </div>

                {/* 
                 * Outlet renders the child authentication page.
                 *
                 * Example:
                 * /login    → LoginPage
                 * /register → RegisterPage
                 */}
                <Outlet />

            </div>

        </div>
    );
}

export default AuthLayout;