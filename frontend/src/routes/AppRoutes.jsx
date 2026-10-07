import { Routes, Route, Navigate } from "react-router-dom";

import LoginPage from "../pages/LoginPage";
import RegisterPage from "../pages/RegisterPage";
import AuthLayout from "../layouts/AuthLayout";

import ProtectedRoute from "./ProtectedRoute";
import LogoutButton from "../components/auth/LogoutButton";

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

            {/* Protected application routes */}
            <Route element={<ProtectedRoute />}>
                {/* Dashboard requires authentication */}
                <Route
                    path="/dashboard"
                    element={
                        <div>
                            <h1>Dashboard Page</h1>

                            {/* Logout button for authenticated users */}
                            <LogoutButton />
                        </div>
                    }
                />

                {/* Employee list requires authentication */}
                <Route
                    path="/employees"
                    element={<h1>Employees Page</h1>}
                />

                {/* Employee details requires authentication */}
                <Route
                    path="/employees/:id"
                    element={<h1>Employee Details Page</h1>}
                />
            </Route>

            {/* Unknown URLs redirect to Login */}
            <Route
                path="*"
                element={<Navigate to="/login" replace />}
            />

        </Routes>
    );
}

export default AppRoutes;