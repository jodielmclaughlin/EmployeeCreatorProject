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
            <nav>
                <Link
                    to="/home">
                    Home
                </Link>
                {isLoggedIn() ? (
                    <button onClick={handleLogout}>Logout</button>
                ) : (
                    <Link
                        to="/">Login
                    </Link>
                )}
            </nav>
        </header>
    )
}
export default Header