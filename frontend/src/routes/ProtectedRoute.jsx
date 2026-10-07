import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

/**
 * Protects routes that require authentication.
 *
 * If the user is authenticated, the requested
 * child route is rendered through Outlet.
 *
 * If the user is not authenticated, the user
 * is redirected to the login page.
 */
function ProtectedRoute() {

    /*
     * Get the current authentication state
     * from AuthContext.
     */
    const { isAuthenticated } = useAuth();

    /*
     * If the user is not authenticated,
     * redirect them to the login page.
     *
     * replace prevents the protected URL from
     * remaining in browser history.
     */
    if (!isAuthenticated) {
        return <Navigate to="/login" replace />;
    }

    /*
     * User is authenticated, so render
     * the nested protected route.
     */
    return <Outlet />;
}

export default ProtectedRoute;