import React from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCircle} from "@fortawesome/free-regular-svg-icons";

import "./Task.css";

function Task(props) {
    console.log(props.id);
    return <div className="task-container">
        <div className="task-circle-checkbox">
            <FontAwesomeIcon icon={faCircle}/>
        </div>
        {props.title}
    </div>;
}

export default Task;