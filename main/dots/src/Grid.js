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

/* A simple grid with a variable size */
/* Most of the assignment involves changes to this class */

import React, {Component} from 'react';
import Button from './Button'

class Grid extends Component {
  constructor(props) {
    super(props);
    this.canvasReference = React.createRef();
  }

  componentDidMount() {
    this.redraw();
  }

  componentDidUpdate() {
    this.redraw()
  }

  redraw = () => {
    let ctx = this.canvasReference.current.getContext('2d');
    ctx.clearRect(0, 0, this.props.width, this.props.height);
    var background = new Image();
    background.onload = () => {
      ctx.drawImage(background,0,0);
      let coordinates = this.getCoordinates();
      coordinates.forEach(coordinate => {
        this.drawCircle(ctx, coordinate);
      });
    }
    background.src = this.props.src;
  };

  getCoordinates = () => {
    let result = [];
    let size = this.props.size;
    for (let i = 1; i <= size; i++) {
        for (let j = 1; j <= size; j++) {
            let temp = [];
            temp.push(i * (this.props.width / (parseInt(size) + 1)));
            temp.push(j * (this.props.height / (parseInt(size) + 1)));
            result.push(temp);
        }
    }
    return result;
  };

  drawCircle = (ctx, coordinate) => {
    ctx.beginPath();
    ctx.arc(coordinate[0], coordinate[1], 20 / this.props.size, 0, 2 * Math.PI);
    ctx.fill();
  };

  readEdges = (event) => {
    let ctx = this.canvasReference.current.getContext('2d');
    let edges = this.props.edges.trim().split('\n');
    var regex = /\d+,\d+ \d+,\d+ [a-zA-Z]+/;
    for (let i = 0; i < edges.length; i++) {
        if (regex.test(edges[i]) === false) {
            alert(edges[i] + " is in incorrect format. Please follow the correct format. Please click example to have a look at the correct format");
        } else {
            let edge = edges[i].split(' ');
            let firstPoint = edge[0].split(',');
            let secondPoint = edge[1].split(',');
            let color = edge[2];
            if (parseInt(firstPoint[0]) < 0 || parseInt(firstPoint[0]) >= this.props.size) {
                alert("your start point " + edge[0] + " has a invalid x Coordinate");
            } else if (parseInt(firstPoint[1]) < 0 || parseInt(firstPoint[1]) >= this.props.size) {
                alert("your start point " + edge[0] + " has a invalid y Coordinate");
            } else if (parseInt(secondPoint[0]) < 0 || parseInt(secondPoint[0]) >= this.props.size) {
                alert("your end point " + edge[1] + " has a invalid x Coordinate");
            } else if (parseInt(secondPoint[1]) < 0 || parseInt(secondPoint[1]) >= this.props.size) {
                alert("your end point " + edge[1] + " has a invalid y Coordinate");
            } else {
                this.drawLine(ctx, firstPoint, secondPoint, color);
            }
        }
    }
  }

  drawLine = (ctx, firstPoint, secondPoint, color) => {
    let xFirst = (parseInt(firstPoint[0]) + 1) * (this.props.width / (parseInt(this.props.size) + 1));
    let yFirst = (parseInt(firstPoint[1]) + 1) * (this.props.height / (parseInt(this.props.size) + 1));
    let xSecond = (parseInt(secondPoint[0]) + 1) * (this.props.width / (parseInt(this.props.size) + 1));
    let ySecond = (parseInt(secondPoint[1]) + 1) * (this.props.height / (parseInt(this.props.size) + 1));
    ctx.beginPath();
    ctx.strokeStyle = "black";
    ctx.strokeStyle = color;
    ctx.moveTo(xFirst, yFirst);
    ctx.lineTo(xSecond, ySecond);
    ctx.stroke();
  }

  render() {
    return (
      <div id="canvas-div">
        <canvas ref={this.canvasReference} width={this.props.width} height={this.props.height} />
        <div className="center-text">Current Grid Size: {this.props.size}</div>
        <Button color="primary" onClick={this.readEdges} value="Draw" />
        <Button color="secondary" onClick={this.redraw} value="Clear" />
      </div>
    );
  }
}

export default Grid;
