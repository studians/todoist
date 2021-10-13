import Cookies from "js-cookie";
import axios from "axios";

class Utils {

    static accessToken;

    static getAccessToken() {
        return this.accessToken;
    }

    static setAccessToken(accessToken) {
        this.accessToken = accessToken;
        axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
    }

    static refreshToken(successHandler = (response) => {}, failureHandler = () => {}) {
        const refreshToken = Cookies.get("refreshToken");
        if (refreshToken) {
            axios.post(process.env.REACT_APP_URI + "/refresh", null, {
                withCredentials: true
            }).then(response => {
                this.setAccessToken(response.data.accessToken);
                successHandler(response);
            }).catch(e => {
                Cookies.remove("refreshToken");
                failureHandler();
            });
        } else {
            failureHandler();
        }
    }

    static startTokenRefreshTimer(expiry, failureHandler) {
        setTimeout(() => {
            this.refreshToken(response => {
                this.startTokenRefreshTimer(response.data.expiry - 30, failureHandler);
            }, failureHandler);
        }, expiry * 1000);
    }

}

export default Utils;