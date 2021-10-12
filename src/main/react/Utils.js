class Utils {

    static accessToken;

    static setAccessToken(accessToken) {
        this.accessToken = accessToken;
    }

    static getAccessToken() {
        return this.accessToken;
    }

}

export default Utils;