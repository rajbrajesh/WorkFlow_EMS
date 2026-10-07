import { createContext, useContext, useState } from "react";

/*
 * Creates the authentication context.
 *
 * This context will allow different components
 * to access the user's authentication state.
 */
const AuthContext = createContext(null);

/*
 * Provides authentication information to the
 * components inside this provider.
 */
export function AuthProvider({ children }) {

    /*
     * Check whether a JWT token already exists
     * when the application starts.
     *
     * Boolean(true/false) is enough for now because
     * the actual token is already stored in localStorage.
     */
    const [isAuthenticated, setIsAuthenticated] = useState(
        Boolean(localStorage.getItem("workflow_token"))
    );

    /*
     * Marks the user as authenticated after login.
     */
    const login = () => {
        setIsAuthenticated(true);
    };

    /*
     * Marks the user as unauthenticated after logout.
     */
    const logout = () => {
        localStorage.removeItem("workflow_token");
        setIsAuthenticated(false);
    };

    /*
     * Make authentication state and actions
     * available to child components.
     */
    return (
        <AuthContext.Provider
            value={{
                isAuthenticated,
                login,
                logout
            }}
        >
            {children}
        </AuthContext.Provider>
    );
}

/*
 * Custom hook for accessing authentication state.
 *
 * Components can use:
 * const { isAuthenticated, login, logout } = useAuth();
 */
export function useAuth() {
    return useContext(AuthContext);
}