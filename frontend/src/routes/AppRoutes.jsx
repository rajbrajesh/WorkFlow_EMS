import { Routes, Route, Navigate } from "react-router-dom";

import LoginPage from "../pages/LoginPage";
import RegisterPage from "../pages/RegisterPage";
import AuthLayout from "../layouts/AuthLayout";

/**
 * Defines all application routes for WorkFlow.
 *
 * Authentication pages such as Login and Register
 * share the common AuthLayout.
 */
function AppRoutes() {
    return (
        <Routes>

            {/* 
             * Authentication routes.
             *
             * Both Login and Register use the same
             * AuthLayout for their common UI.
             */}
            <Route element={<AuthLayout />}>

                {/* Login page */}
                <Route
                    path="/login"
                    element={<LoginPage />}
                />

                {/* Register page */}
                <Route
                    path="/register"
                    element={<RegisterPage />}
                />

            </Route>

            {/* 
             * Root URL redirects to Login.
             */}
            <Route
                path="/"
                element={<Navigate to="/login" replace />}
            />

            {/* Temporary dashboard page */}
            <Route
                path="/dashboard"
                element={<h1>Dashboard Page</h1>}
            />

            {/* Temporary employees page */}
            <Route
                path="/employees"
                element={<h1>Employees Page</h1>}
            />

            {/* Temporary employee details page */}
            <Route
                path="/employees/:id"
                element={<h1>Employee Details Page</h1>}
            />

            {/* Unknown URLs redirect to Login */}
            <Route
                path="*"
                element={<Navigate to="/login" replace />}
            />

        </Routes>
    );
}

export default AppRoutes;