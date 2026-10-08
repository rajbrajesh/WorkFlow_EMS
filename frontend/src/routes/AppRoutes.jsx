import { Routes, Route, Navigate } from "react-router-dom";

import LoginPage from "../pages/LoginPage";
import RegisterPage from "../pages/RegisterPage";
import AuthLayout from "../layouts/AuthLayout";

import ProtectedRoute from "./ProtectedRoute";
import DashboardLayout from "../layouts/DashboardLayout";
import Dashboard from "../pages/Dashboard";

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
                {/* Common layout for all authenticated pages */}
                <Route element={<DashboardLayout />}>
                    
                    {/* Dashboard page */}
                    <Route
                        path="/dashboard"
                        element={<Dashboard />}
                    />

                    {/* Employee list page */}
                    <Route
                        path="/employees"
                        element={<h1>Employees Page</h1>}
                    />

                    {/* Employee details page */}
                    <Route
                        path="/employees/:id"
                        element={<h1>Employee Details Page</h1>}
                    />

                </Route>
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