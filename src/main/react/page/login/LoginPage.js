import React, { useState } from 'react';
import dotenv from "dotenv";
import axios from "axios";

import './LoginPage.css';

dotenv.config();

function LoginPage() {
    const [username, setUsername] = useState('');
    const handleUsernameChange = (e) => setUsername(e.target.value);

    const [password, setPassword] = useState('');
    const handlePasswordChange = (e) => setPassword(e.target.value);

    function login() {
        const data = {username: username, password: password};
        axios.post(process.env.REACT_APP_URI + "/login", JSON.stringify(data), {
            headers: {
                "Content-Type": "application/json"
            }
        }).then(response => {
            console.log("response.data.accessToken: " + response.data);
            axios.defaults.headers.common['Authorization'] = "Bearer " + response.data;
        }).catch(e => {
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
            <input type="password" name="password" value={password} onChange={handlePasswordChange} />
        </div>
        <div className="button-container">
            <button type="button" onClick={login} >Sign in</button>
        </div>
        <p></p>
        <div>
            New to Todoist? <a href="/register">Create an account.</a>
        </div>
    </div>
}

export default LoginPage;