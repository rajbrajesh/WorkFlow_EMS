import { Routes, Route, Navigate } from "react-router-dom";

/**
 * Defines all application routes for WorkFlow.
 *
 * Currently we are creating placeholder routes.
 * Actual Login, Register, Dashboard and Employee pages
 * will be created in later phases.
 */
function AppRoutes() {
    return (
        <Routes>

            {/* Temporary home route. */}
            <Route
                path="/"
                element={<Navigate to="/login" replace />}
            />

            {/* Authentication routes. */}
            <Route
                path="/login"
                element={<h1>Login Page</h1>}
            />

            <Route
                path="/register"
                element={<h1>Register Page</h1>}
            />

            {/* Dashboard route. */}
            <Route
                path="/dashboard"
                element={<h1>Dashboard Page</h1>}
            />

            {/* Employee management routes. */}
            <Route
                path="/employees"
                element={<h1>Employees Page</h1>}
            />

            <Route
                path="/employees/:id"
                element={<h1>Employee Details Page</h1>}
            />

            {/* Unknown URLs go back to login. */}
            <Route
                path="*"
                element={<Navigate to="/login" replace />}
            />

        </Routes>
    );
}

export default AppRoutes;