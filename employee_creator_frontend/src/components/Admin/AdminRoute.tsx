import { Navigate } from "react-router-dom";
import { isAdmin, isLoggedIn } from "../../services/auth";

interface AdminRouteProps {
    children: React.ReactNode;
}

function AdminRoute({ children }: AdminRouteProps) {
    if (!isLoggedIn()) {
        return <Navigate to="/" replace />;
    }

    if (!isAdmin()) {
        return <Navigate to="/home" replace />;
    }

    return children;
}

export default AdminRoute;