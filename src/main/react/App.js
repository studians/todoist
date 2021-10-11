import React from 'react';
import {BrowserRouter, Route, Switch} from "react-router-dom";

import './App.css';
import LoginPage from './page/login/LoginPage';
import LogoutPage from "./page/logout/LogoutPage";
import MainPage from './page/main/MainPage';
import PrivateRoute from "./PrivateRoute";

function App() {
  return <div id="app">
    <BrowserRouter>
      <Switch>
        <Route path="/login" component={LoginPage}/>
        <Route path="/logout" component={LogoutPage}/>
        <PrivateRoute path="/" component={MainPage}/>
      </Switch>
    </BrowserRouter>
  </div>
}

export default App;
