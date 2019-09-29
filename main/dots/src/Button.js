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

/* A simple Button class */

import Button from '@material-ui/core/Button';
import React, {Component} from 'react';

class CustomButton extends Component {
  render() {
    return (
      <div className="center-text button">
        <Button variant="contained" color={this.props.color} onClick={this.props.onClick}>
          {this.props.value}
        </Button>
      </div>
    );
  }
}

export default CustomButton;
