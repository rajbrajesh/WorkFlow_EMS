import { useAuth } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";

/**
 * Reusable logout button for WorkFlow.
 *
 * Clears the authentication state and redirects
 * the user back to the login page.
 */
function LogoutButton() {

    /*
     * Access the logout action from AuthContext.
     */
    const { logout } = useAuth();

    /*
     * Provides programmatic navigation after logout.
     */
    const navigate = useNavigate();

    /*
     * Handles user logout.
     */
    const handleLogout = () => {

        /*
         * Remove the JWT and update the global
         * authentication state.
         */
        logout();

        /*
         * Redirect the user to the login page.
         */
        navigate("/login", { replace: true });
    };

    return (
        <button
            type="button"
            onClick={handleLogout}
        >
            Logout
        </button>
    );
}

export default LogoutButton;