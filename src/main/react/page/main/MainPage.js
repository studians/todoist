import React, {useEffect, useRef, useState} from 'react';
import axios from "axios";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPencilAlt} from "@fortawesome/free-solid-svg-icons";

import "./MainPage.css";
import Header from "../../layout/Header";
import Sidebar from "../../layout/Sidebar";
import Popup from "reactjs-popup";
import "reactjs-popup/dist/index.css"
import Task from "../../component/Task";

function MainPage() {
    const [tasks, setTasks] = useState([]);

    const ref = useRef();
    const [title, setTitle] = useState("");
    const handleTitleChange = (e) => setTitle(e.target.value);
    const handleTitleKeyDown = (e) => {
        if (e.key == "Enter" && (!e.ctrlKey && !e.altKey && !e.shiftKey && !e.metaKey)) {
            post();
        }
    };

    useEffect(() => {
        fetch();
    }, []);

    function fetch() {
        axios.get(process.env.REACT_APP_URI + "/tasks").then(response => {
            setTasks(response.data);
        });
    }

    function post() {
        axios.post(process.env.REACT_APP_URI + "/task", {"title": title}).then(response => {
            fetch();
            ref.current.close();
        });
    }

    return <div>
        <Header/>
        <div className="main-container">
            <Sidebar/>
            <div className="tasks-container">
                <div className="tasks-view">
                    <h2>Inbox</h2>
                    {tasks.map(task => {
                        return <Task key={task.id} id={task.id} title={task.title}/>;
                    })}
                </div>
            </div>
            <Popup ref={ref} trigger={<div className="post-button-container flex-center">
                <FontAwesomeIcon icon={faPencilAlt} className="post-button"/>
            </div>} modal>
                <div>
                    <div className="post-popup-field-container">
                        <label>Task title</label>
                        <input type="text" onChange={handleTitleChange} onKeyDown={handleTitleKeyDown} />
                    </div>
                    <div className="post-popup-button-container">
                        <button type="button" className="post-popup-button" onClick={post} >Create</button>
                    </div>
                </div>
            </Popup>
        </div>
    </div>;
}

export default MainPage;