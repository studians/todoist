import React, {useState} from 'react';
import dotenv from "dotenv";
import axios from "axios";
import {useHistory} from "react-router-dom";
import Bowser from "bowser";
import Cookies from "js-cookie";

import './LoginPage.css';
import Utils from "../../Utils";

dotenv.config();

function LoginPage() {
    const [username, setUsername] = useState('');
    const handleUsernameChange = (e) => setUsername(e.target.value);

    const [password, setPassword] = useState('');
    const handlePasswordChange = (e) => setPassword(e.target.value);

    const history = useHistory();

    function login() {
        const bowser = Bowser.getParser(window.navigator.userAgent);
        const device = `${bowser.getOS().name} ${bowser.getOSVersion()} (${bowser.getBrowser().name} ${bowser.getBrowserVersion()})`;

        const data = {username: username, password: password, device: device};
        axios.post(process.env.REACT_APP_URI + "/authenticate", JSON.stringify(data), {
            headers: {
                "Content-Type": "application/json"
            },
            withCredentials: true
        }).then(response => {
            forward(response.data.accessToken);
        }).catch(e => {
            console.log(e);
            console.log("login failed!");
        });
    }

    function forward(accessToken) {
        Utils.setAccessToken(accessToken);
        axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
        history.push("/");
    }

    const refreshToken = Cookies.get("refreshToken");
    if (refreshToken) {
        axios.post(process.env.REACT_APP_URI + "/refresh", null, {
            withCredentials: true
        }).then(response => {
            forward(response.data.accessToken);
        }).catch(e => {
            Cookies.remove("refreshToken");
        });
    }

    return <div>
        <div className="field-container">
            <label>Username or email address</label>
            <input type="text" name="username" value={username} onChange={handleUsernameChange} />
        </div>
        <div className="field-container">
            <label>Password</label>
            <input type="password" name="password" value={password} onChange={handlePasswordChange} />
        </div>
        <div className="button-container">
            <button type="button" onClick={login} >Sign in</button>
        </div>
        {/*
        <p></p>
        <div>
            New to Todoist? <a href="/register">Create an account.</a>
        </div>
        */}
    </div>;
}

export default LoginPage;