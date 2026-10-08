import "./Dashboard.css";
import DashboardCard from "../components/dashboard/DashboardCard";

// Dashboard.jsx
// Main dashboard page.
// This page contains the dashboard content displayed inside DashboardLayout.

const Dashboard = () => {
    return (
        <div className="dashboard-page">

            {/* Dashboard welcome section */}
            <div className="dashboard-header">
                <h1>Welcome to WorkFlow</h1>

                <p>
                    Manage employees and keep your workflow organized.
                </p>
            </div>

            {/* Dashboard summary cards */}
            <div className="dashboard-cards">

                {/* Total employees */}
                <DashboardCard
                    title="Total Employees"
                    value="--"
                />

                {/* Active employees */}
                <DashboardCard
                    title="Active Employees"
                    value="--"
                />

                {/* Departments */}
                <DashboardCard
                    title="Departments"
                    value="--"
                />

                {/* Pending tasks */}
                <DashboardCard
                    title="Pending Tasks"
                    value="--"
                />

            </div>

            {/* Recent activity section */}
            <div className="dashboard-section">

                {/* Section heading */}
                <div className="dashboard-section-header">
                    <h2>Recent Activity</h2>

                    <p>
                        Recent workflow activity will appear here.
                    </p>
                </div>

                {/* Activity placeholder */}
                <div className="activity-placeholder">
                    <p>No recent activity available.</p>
                </div>

            </div>

        </div>
    );
};

export default Dashboard;