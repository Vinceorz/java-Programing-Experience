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

/* The main component that holds all the other elements of the React App */

import React, {Component} from 'react';
import GridSizePicker from './GridSizePicker'
import Grid from './Grid'
import EdgeList from './EdgeList';
import Button from './Button'

class MainContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      size: 3,
      edges: "",
      open: false,
      src: 'image.jpg'
    }
    this.handleChange = this.handleChange.bind(this);
    this.handleEdgeChange = this.handleEdgeChange.bind(this);
    this.handleButtonClick = this.handleButtonClick.bind(this);
  }

  handleChange(event) {
    if (event.target.value <= 200 && event.target.value >= 0){
     this.setState({size: event.target.value});
    } else {
        this.setState({size: 3});
        alert("We don't take negative number and number greater than 200");
    }
  }

  handleEdgeChange(event) {
    this.setState({edges: event.target.value});
  }

  showExample = (event) => {
    this.setState({size: 4});
    this.setState({edges: "1,1 1,2 black\n1,1 2,1 red\n1,2 2,2 yellow\n2,1 2,2 green"});
  }

  handleButtonClick(event) {
    if (this.state.open === false) {
        this.setState({open: true});
    } else {
        this.setState({open: false});
    }
  }

  render() {
    let gridSize = 400;
    return (
      <div>
        <Button color="primary" onClick={this.showExample} value="Example" />
        <Button type="button" class="button" onClick={this.handleButtonClick} value="Change Background">
            ☰
        </Button>
          {this.state.open && (
            <div class="dropdown">
              <Button color="secondary" onClick={() => {this.setState({src: 'image.jpg'})}} value="Default" />
              <Button color="primary" onClick={() => {this.setState({src: 'Beijing.jpg'})}} value="Beijing" />
              <Button color="secondary" onClick={() => {this.setState({src: 'Hongkong.jpg'})}} value="Hongkong" />
              <Button color="inherit" onClick={() => {this.setState({src: 'Guangzhou.jpg'})}} value="Guangzhou" />
              <Button color="primary" onClick={() => {this.setState({src: 'Shanghai.jpg'})}} value="Shanghai" />
            </div>
          )}
        <GridSizePicker value={this.state.size} onChange={this.handleChange} />
        <Grid size={this.state.size} width={gridSize} height={gridSize} edges={this.state.edges} src={this.state.src} />
        <EdgeList value={this.state.edges} rows={5} onChange={this.handleEdgeChange}/>
      </div>
    );
  }
}

export default MainContainer;
