/*
 * AppRoutes contains all application routes.
 */
import AppRoutes from "./routes/AppRoutes";

/*
 * App-specific styling.
 */
import "./App.css";

/**
 * Root component of the WorkFlow frontend.
 *
 * App is responsible for the common application wrapper,
 * while AppRoutes decides which page should be displayed.
 */
function App() {
    return (
        <div className="app-container">

            {/* Application content is controlled by React Router. */}
            <AppRoutes />

        </div>
    );
}

export default App;