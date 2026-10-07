import {Link, useNavigate} from "react-router-dom";
import {isLoggedIn, removeToken} from "../../services/auth.ts";

function Header(){
    const navigate = useNavigate();
    const handleLogout = () => {
        removeToken();
        navigate("/");
    };
    return(
        <header>
            <nav className="sticky top-0 z-50 bg-white shadow-sm flex justify-between px-4">
                <Link
                    to="/home"
                    className="font-medium text-zinc-700 transition-colors hover:text-zinc-950">
                    Home
                </Link>
                {isLoggedIn() ? (
                    <button onClick={handleLogout} className="font-medium text-zinc-700 transition-colors hover:text-zinc-950">Logout</button>
                ) : (
                    <Link
                        to="/"
                        className="font-medium text-zinc-700 transition-colors hover:text-zinc-950"
                    >
                        Login
                    </Link>
                )}
            </nav>
        </header>
    )
}
export default Header