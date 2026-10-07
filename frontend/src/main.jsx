import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter } from "react-router-dom";


import "./index.css";
import App from "./App.jsx";
import { AuthProvider } from "./context/AuthContext";

/*
 * BrowserRouter enables client-side routing in the application.
 *
 * It allows React Router to read the browser URL and
 * render the appropriate component without reloading
 * the complete webpage.
 */
createRoot(document.getElementById("root")).render(
    <StrictMode>
        <BrowserRouter>
            <AuthProvider>
                <App />
            </AuthProvider>
        </BrowserRouter>
    </StrictMode>
);