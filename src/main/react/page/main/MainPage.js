import React, {useEffect, useRef, useState} from 'react';
import axios from "axios";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPencilAlt} from "@fortawesome/free-solid-svg-icons";

import "./MainPage.css";
import Header from "../../layout/Header";
import Sidebar from "../../layout/Sidebar";
import Popup from "reactjs-popup";
import "reactjs-popup/dist/index.css"

function MainPage() {
    const [user, setUser] = useState('');

    const ref = useRef();
    const [title, setTitle] = useState('');
    const handleTitleChange = (e) => setTitle(e.target.value);
    const handleTitleKeyDown = (e) => {
        if (e.key == "Enter" && (!e.ctrlKey && !e.altKey && !e.shiftKey && !e.metaKey)) {
            post();
        }
    };

    useEffect(() => {
        axios.get(process.env.REACT_APP_URI + "/user").then(response => {
            setUser(response.data);
        });
    }, []);

    function post() {
        console.log(title);
        ref.current.close();
    }

    return <div>
        <Header/>
        <div className="main-container">
            <Sidebar/>
            <div style={{ textAlign: "center" }}>
                <h1>Hello, {user.username}</h1>
                <h2>Good to see you.</h2>
            </div>
            <Popup ref={ref} trigger={<div className="post-button-container flex-center">
                <FontAwesomeIcon icon={faPencilAlt} className="post-button"/>
            </div>} modal>
                <div className="post-popup-container">
                    <div className="field-container">
                        <label>Task title</label>
                        <input type="text" onChange={handleTitleChange} onKeyDown={handleTitleKeyDown} />
                    </div>
                    <div className="button-container">
                        <button type="button" onClick={post} >Create</button>
                    </div>
                </div>
            </Popup>
        </div>
    </div>;
}

export default MainPage;