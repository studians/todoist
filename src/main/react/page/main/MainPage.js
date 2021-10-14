import React, {useEffect, useState} from 'react';
import axios from "axios";

import './MainPage.css';
import Header from "../../layout/Header";

function MainPage() {
    const [user, setUser] = useState('');

    useEffect(() => {
        axios.get(process.env.REACT_APP_URI + "/user").then(response => {
            setUser(response.data);
        });
    }, []);

    return <div className="main-container">
        <Header/>
        <div style={{ textAlign: "center" }}>
            <h1>Hello, {user.username}</h1>
            <h2>Good to see you.</h2>
        </div>
    </div>;
}

export default MainPage;