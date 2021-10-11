import React, {useState, useEffect} from 'react';
import axios from "axios";

import './MainPage.css';

function MainPage() {
    const [user, setUser] = useState('');

    useEffect(() => {
        const fetchUser = async () => {
            const result = await axios.get(process.env.REACT_APP_URI + "/user");
            setUser(result.data);
        };
        fetchUser();
    }, []);

    return <div>
        <h1>Hello, {user.username}</h1>
        <h2>Good to see you.</h2>
    </div>;
}

export default MainPage;