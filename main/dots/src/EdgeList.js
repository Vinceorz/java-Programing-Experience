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

/* A Textfield that stores the list of Edges to be created */
/* See the material-ui documentation for more about TextField */

import TextField from '@material-ui/core/TextField';
import React, {Component} from 'react';

class EdgeList extends Component {
  render() {
    return (
      <div className="center-text">
        <TextField
          id={this.props.id}
          label="Edges"
          multiline
          rowsMax={this.props.rows}
          rows={this.props.rows}
          value={this.props.value}
          onChange={this.props.onChange}
          className={this.props.className}
          margin="normal"
        />
      </div>
    );
  }
}

export default EdgeList;
