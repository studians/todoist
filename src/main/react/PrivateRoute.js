import React from 'react';
import { Redirect, Route } from 'react-router-dom';
import axios from "axios";

function PrivateRoute ({ component: Component, ...rest }) {
    const accessToken = localStorage.getItem("accessToken");
    if (accessToken) {
        // TODO validate and refresh
        axios.defaults.headers.common['Authorization'] = "Bearer " + accessToken;
    }

    return (
        <Route
            {...rest}
            render = {props =>
                localStorage.getItem('accessToken') ? (
                    <Component {...props} />
                ) : (
                    <Redirect to={{
                        pathname: '/login',
                        state: {from: props.location}
                    }}
                    />
                )
            }
        />
    )
}

export default PrivateRoute;