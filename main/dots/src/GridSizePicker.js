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

/* A simple TextField that only allows numerical input */

import TextField from '@material-ui/core/TextField';
import React, {Component} from 'react';

class GridSizePicker extends Component {
  render() {
    return (
      <div className="center-text">
        <p>Pick the Grid Size:</p>
        <TextField
          id={this.props.id}
          label={this.props.label}
          value={this.props.value}
          onChange={this.props.onChange}
          type="number"
          className={this.props.className}
          InputLabelProps={{
            shrink: true,
          }}
          autoFocus
        />
      </div>
    );
  }
}

export default GridSizePicker;
