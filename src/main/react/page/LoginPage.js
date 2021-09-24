import React from 'react';

import './LoginPage.css';

function LoginPage() {
    return <div>
        <h1>Login</h1>
        <form method="post" action="/login">
            <div>
                <label>Email</label>
                <input id="email" name="email" type="email"/>
            </div>
            <div>
                <label>Password</label>
                <input id="password" name="password" type="password"/>
            </div>
            <button type="submit">Sign In</button>
        </form>
    </div>
}

export default LoginPage;