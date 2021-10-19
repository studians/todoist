import React, {useState} from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCircle} from "@fortawesome/free-regular-svg-icons";

import "./Task.css";
import axios from "axios";

function Task(props) {
    const [hide, setHide] = useState(false);

    function deleteTask() {
        axios.delete(process.env.REACT_APP_URI + `/task/${props.id}`).then(response => {
            setHide(true);
        });
    }

    return hide ? null : <div className="task-container">
        <div className="task-circle-checkbox" onClick={deleteTask}>
            <FontAwesomeIcon icon={faCircle}/>
        </div>
        {props.title}
    </div>;
}

export default Task;