import {useHistory} from "react-router-dom";

function LogoutPage() {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");

    const history = useHistory();
    history.push('/login');

    return null;
}

export default LogoutPage;