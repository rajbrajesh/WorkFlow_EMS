// DashboardCard.jsx
// Reusable card component used to display dashboard summary information.

const DashboardCard = ({ title, value }) => {
    return (
        <div className="dashboard-card">

            {/* Card title */}
            <h3>{title}</h3>

            {/* Card value */}
            <p>{value}</p>

        </div>
    );
};

export default DashboardCard;