import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faInbox} from "@fortawesome/free-solid-svg-icons";

import "./Sidebar.css";

function Sidebar() {
    return <div className="sidebar-container">
        <div className="menuitem menuitem-selected">
            <ul>
                <li><FontAwesomeIcon icon={faInbox} style={{"font-size": "21px"}} /><span>Inbox</span></li>
            </ul>
        </div>
    </div>;
}

export default Sidebar;