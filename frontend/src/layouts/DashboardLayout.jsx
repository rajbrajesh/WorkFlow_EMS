import { Outlet } from "react-router-dom";
import LogoutButton from "../components/auth/LogoutButton";

/**
 * Layout used for authenticated application pages.
 *
 * This layout provides common UI elements such as
 * the application header and logout button.
 *
 * Outlet renders the currently active protected route.
 */
function DashboardLayout() {
    return (
        <div className="dashboard-layout">

            {/* Common application header */}
            <header className="dashboard-header">
                <h1>WorkFlow</h1>

                {/* Logout button available on authenticated pages */}
                <LogoutButton />
            </header>

            {/* Page-specific content is rendered here */}
            <main className="dashboard-content">
                <Outlet />
            </main>

        </div>
    );
}

export default DashboardLayout;