import {Link} from "react-router-dom";
import React from "react";

import "./Header.css";
import TodoistIcon from "./todoist-icon.svg"

function Header() {
    return <div className="header-container">
        <div className="left-area">
            <img className="todoist-icon" src={TodoistIcon} width="24" />
        </div>
        <div className="center-area"/>
        <div className="right-area">
            <Link to="/logout"><button type="button" className="logout-button">Sign out</button></Link>
        </div>
    </div>;
}

export default Header;