import React, {useEffect, useState} from 'react';
import dotenv from "dotenv";
import axios from "axios";
import {useHistory} from "react-router-dom";
import Bowser from "bowser";

import './LoginPage.css';
import Utils from "../../Utils";

dotenv.config();

function LoginPage() {
    const [username, setUsername] = useState('');
    const handleUsernameChange = (e) => setUsername(e.target.value);

    const [password, setPassword] = useState('');
    const handlePasswordChange = (e) => setPassword(e.target.value);
    const handlePasswordKeyDown = (e) => {
        if (e.key == "Enter") {
            login();
        }
    };

    const history = useHistory();

    useEffect(() => {
        Utils.refreshToken(response => {
            Utils.startTokenRefreshTimer(response.data.expiry - 30, () => {
                history.push("/logout");
            });
            history.push("/");
        });
    }, []);

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
            Utils.setAccessToken(response.data.accessToken);
            Utils.startTokenRefreshTimer(response.data.expiry - 30, () => {
                history.push("/logout");
            });
            history.push("/");
        }).catch(e => {
            console.log(e);
            console.log("login failed!");
        });
    }

    return <div>
        <div className="field-container">
            <label>Username or email address</label>
            <input type="text" name="username" value={username} onChange={handleUsernameChange} />
        </div>
        <div className="field-container">
            <label>Password</label>
            <input type="password" name="password" value={password} onChange={handlePasswordChange} onKeyDown={handlePasswordKeyDown} />
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