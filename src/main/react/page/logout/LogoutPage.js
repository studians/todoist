import {useHistory} from "react-router-dom";
import Cookies from "js-cookie";

function LogoutPage() {
    Cookies.remove("refreshToken");

    const history = useHistory();
    history.push("/login");

    return null;
}

export default LogoutPage;