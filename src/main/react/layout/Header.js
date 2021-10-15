import {Link} from "react-router-dom";
import React from "react";

import "./Header.css";
import TodoistIcon from "./todoist-icon.svg"

function Header() {
    return <div className="header-container">
        <div className="flex-center">
            <Link to="/" style={{ height: "24px" }} ><img src={TodoistIcon} width="24" /></Link>
        </div>
        <div className="flex-center">
            <Link to="/logout"><button type="button" className="logout-button">Sign out</button></Link>
        </div>
    </div>;
}

export default Header;