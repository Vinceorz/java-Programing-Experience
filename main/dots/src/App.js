/*
 * Copyright ©2019 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2019 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

/* The main React class that is called from index.js */
/* index.js creates this Component and everything else is inside it. */

import React, {Component} from 'react';
import './App.css';
import MainContainer from './MainContainer';

class App extends Component {
  render() {
    return (
      <div className="App">
        <MainContainer/>
      </div>
    );
  }
}

export default App;
