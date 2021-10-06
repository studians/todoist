import React from 'react';
import {BrowserRouter, Route, Switch} from "react-router-dom";

import './App.css';
import LoginPage from './page/login/LoginPage';
import MainPage from './page/main/MainPage';

function App() {
  return <div id="app">
    <BrowserRouter>
      <Switch>
        <Route path="/login" component={LoginPage}/>
        <Route path="/" component={MainPage}/>
      </Switch>
    </BrowserRouter>
  </div>
}

export default App;
