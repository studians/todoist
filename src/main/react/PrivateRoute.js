import React from 'react';
import {Redirect, Route} from 'react-router-dom';
import Utils from "./Utils";

function PrivateRoute ({ component: Component, ...rest }) {
    return (
        <Route
            {...rest}
            render = {props =>
                Utils.getAccessToken() ? (
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