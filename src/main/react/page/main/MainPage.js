import React, {useEffect, useState} from 'react';
import axios from "axios";

import './MainPage.css';
import Header from "../../layout/Header";
import Sidebar from "../../layout/Sidebar";

function MainPage() {
    const [user, setUser] = useState('');

    useEffect(() => {
        axios.get(process.env.REACT_APP_URI + "/user").then(response => {
            setUser(response.data);
        });
    }, []);

    return <div>
        <Header/>
        <div className="main-container">
            <Sidebar/>
            <div style={{ textAlign: "center" }}>
                <h1>Hello, {user.username}</h1>
                <h2>Good to see you.</h2>
            </div>
        </div>
    </div>;
}

export default MainPage;