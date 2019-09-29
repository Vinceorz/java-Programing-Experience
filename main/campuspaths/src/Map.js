import React, {Component} from 'react';
import Select from '@material-ui/core/Select';
import Button from '@material-ui/core/Button';
import MenuItem from '@material-ui/core/MenuItem';
import * as fetch from "node-fetch";
import "./Map.css";

class Map extends Component {

  // NOTE:
  // This component is a suggestion for you to use, if you would like to.
  // It has some skeleton code that helps set up some of the more difficult parts
  // of getting <canvas> elements to display nicely.
  //
  // If you don't want to use this component, you're free to delete it.

  // The constructor initialize the class with two states: start and end which are
  // the start and destination building of the path, also it set the image to be
  // the campus image
  constructor(props) {
    super(props);
    this.backgroundImage = new Image();
    this.handleStartChange = this.handleStartChange.bind(this);
    this.handleEndChange = this.handleEndChange.bind(this);
    this.drawBackgroundImage = this.drawBackgroundImage.bind(this);
    this.drawPath = this.drawPath.bind(this);
    this.state = {
          start: "",
          end: "",
    }
    this.canvasReference = React.createRef();
    this.backgroundImage.onload = () => {
        this.drawBackgroundImage();
    };
    this.backgroundImage.src = 'campus_map.jpg';
  }

  // clear the exist roads if user want to change either start or end
  componentDidMount() {
    this.drawBackgroundImage();
  }

  // clear the exist roads if user want to change either start or end
  componentDidUpdate() {
    this.drawBackgroundImage();
  }

  // according to the size of the image to draw a canvas on it and clear any existing roads on the map
  drawBackgroundImage() {
    let canvas = this.canvasReference;
    let ctx = canvas.current.getContext("2d");
    //
    if (this.backgroundImage.complete) {
      canvas.current.width = this.backgroundImage.width;
      canvas.current.height = this.backgroundImage.height;
      ctx.drawImage(this.backgroundImage, 0, 0);
    }
  }

  // Called when the find path button is clicked and draw the path between two building if there is path
  // exist or it alert the user there is no path. if the start and destination are the same or any input is
  // empty, it would alert the condition to the user
  drawPath() {
    let findPathURL = 'http://localhost:4567/path?start=' + this.state.start + "&end=" + this.state.end;
    if (this.state.start === "" || this.state.end === "") {
        alert("Give me both start and destination building")
    } else if (this.state.start === this.state.end) {
        alert("Don't move.. You already there")
    } else {
        fetch(findPathURL)
        .then(results => {
            return results.json();
        }).then(data => {
            let ctx = this.canvasReference.current.getContext('2d');
            for (let i = 0; i < data.path.length; i++) {
                let segmentInfo = data.path[i];
                ctx.beginPath();
                ctx.strokeStyle = "blue";
                ctx.lineWidth = 10;
                ctx.moveTo(segmentInfo.start.x, segmentInfo.start.y);
                ctx.lineTo(segmentInfo.end.x, segmentInfo.end.y);
                ctx.stroke();
            }
        }).catch(() => {alert("We cannot access the data")})
    }
  }

  // change state start based on what user choose for the first drop down
  handleStartChange(event) {
      this.setState({start: event.target.value});
  }


  // change state end based on what user choose for the second drop down
  handleEndChange(event) {
        this.setState({end: event.target.value});
  }

  render() {
    // that's set up to center the canvas for you. See Map.css for more details.
    // Make sure you set up the React references for the canvas correctly so you
    // can get the canvas object and call methods on it.
    return (
      <div className="canvasHolder">
        <div className="buildingChoice">
            <div> Choose where you start with</div>
            <Select value={this.state.start} onChange={this.handleStartChange} >
                <MenuItem value={"BAG"}>Bagley Hall (East Entrance)</MenuItem>
                <MenuItem value={"BAG (NE)"}>Bagley Hall (Northeast Entrance)</MenuItem>
                <MenuItem value={"BGR"}>By George</MenuItem>
                <MenuItem value={"CHL (NE)"}>Chemistry Library (Northeast Entrance)</MenuItem>
                <MenuItem value={"CHL (SE)"}>Chemistry Library (Southeast Entrance)</MenuItem>
                <MenuItem value={"CHL"}>Chemistry Library (West Entrance)</MenuItem>
                <MenuItem value={"CMU"}>Communications Building</MenuItem>
                <MenuItem value={"DEN"}>Denny Hall</MenuItem>
                <MenuItem value={"EEB"}>Electrical Engineering Building (North Entrance)</MenuItem>
                <MenuItem value={"EEB (S)"}>Electrical Engineering Building (South Entrance)</MenuItem>
                <MenuItem value={"FSH"}>Fishery Sciences Building</MenuItem>
                <MenuItem value={"GWN"}>Gowen Hall</MenuItem>
                <MenuItem value={"IMA"}>Intramural Activities Building</MenuItem>
                <MenuItem value={"KNE (E)"}>Kane Hall (East Entrance)</MenuItem>
                <MenuItem value={"KNE"}>Kane Hall (North Entrance)</MenuItem>
                <MenuItem value={"KNE (S)"}>Kane Hall (South Entrance)</MenuItem>
                <MenuItem value={"KNE (SE)"}>Kane Hall (Southeast Entrance)</MenuItem>
                <MenuItem value={"KNE (SW)"}>Kane Hall (Southwest Entrance)</MenuItem>
                <MenuItem value={"LOW"}>Loew Hall</MenuItem>
                <MenuItem value={"MGH (E)"}>Mary Gates Hall (East Entrance)</MenuItem>
                <MenuItem value={"MGH"}>Mary Gates Hall (North Entrance)</MenuItem>
                <MenuItem value={"MGH (S)"}>Mary Gates Hall (South Entrance)</MenuItem>
                <MenuItem value={"MGH (SW)"}>Mary Gates Hall (Southwest Entrance)</MenuItem>
                <MenuItem value={"MCC"}>McCarty Hall (Main Entrance)</MenuItem>
                <MenuItem value={"MCC (S)"}>McCarty Hall (South Entrance)</MenuItem>
                <MenuItem value={"MCM"}>McMahon Hall (Northwest Entrance)</MenuItem>
                <MenuItem value={"MCM (SW)"}>McMahon Hall (Southwest Entrance)</MenuItem>
                <MenuItem value={"MNY"}>Meany Hall (Northeast Entrance)</MenuItem>
                <MenuItem value={"MNY (NW)"}>Meany Hall (Northwest Entrance)</MenuItem>
                <MenuItem value={"MLR"}>Miller Hall</MenuItem>
                <MenuItem value={"MOR"}>Moore Hall</MenuItem>
                <MenuItem value={"MUS (E)"}>Music Building (East Entrance)</MenuItem>
                <MenuItem value={"MUS"}>Music Building (Northwest Entrance)</MenuItem>
                <MenuItem value={"MUS (S)"}>Music Building (South Entrance)</MenuItem>
                <MenuItem value={"MUS (SW)"}>Music Building (Southwest Entrance)</MenuItem>
                <MenuItem value={"OUG"}>Odegaard Undergraduate Library</MenuItem>
                <MenuItem value={"PAR"}>Parrington Hall</MenuItem>
                <MenuItem value={"CSE"}>Paul G. Allen Center for Computer Science & Engineering</MenuItem>
                <MenuItem value={"PAB"}>Physics/Astronomy Building</MenuItem>
                <MenuItem value={"PAA"}>Physics/Astronomy Building A</MenuItem>
                <MenuItem value={"RAI (E)"}>Raitt Hall (East Entrance)</MenuItem>
                <MenuItem value={"RAI"}>Raitt Hall (West Entrance)</MenuItem>
                <MenuItem value={"ROB"}>Roberts Hall</MenuItem>
                <MenuItem value={"SAV"}>Savery Hall</MenuItem>
                <MenuItem value={"HUB"}>Student Union Building (Main Entrance)</MenuItem>
                <MenuItem value={"HUB (Food, S)"}>Student Union Building (South Food Entrance)</MenuItem>
                <MenuItem value={"HUB (Food, W)"}>Student Union Building (West Food Entrance)</MenuItem>
                <MenuItem value={"SUZ"}>Suzzallo Library</MenuItem>
                <MenuItem value={"T65"}>Thai 65</MenuItem>
                <MenuItem value={"UBS"}>University Bookstore</MenuItem>
                <MenuItem value={"UBS (Secret)"}>University Bookstore (Secret Entrance)</MenuItem>
            </Select>
            <div> Choose your destination </div>
            <Select value={this.state.end} onChange={this.handleEndChange} >
                <MenuItem value={"BAG"}>Bagley Hall (East Entrance)</MenuItem>
                <MenuItem value={"BAG (NE)"}>Bagley Hall (Northeast Entrance)</MenuItem>
                <MenuItem value={"BGR"}>By George</MenuItem>
                <MenuItem value={"CHL (NE)"}>Chemistry Library (Northeast Entrance)</MenuItem>
                <MenuItem value={"CHL (SE)"}>Chemistry Library (Southeast Entrance)</MenuItem>
                <MenuItem value={"CHL"}>Chemistry Library (West Entrance)</MenuItem>
                <MenuItem value={"CMU"}>Communications Building</MenuItem>
                <MenuItem value={"DEN"}>Denny Hall</MenuItem>
                <MenuItem value={"EEB"}>Electrical Engineering Building (North Entrance)</MenuItem>
                <MenuItem value={"EEB (S)"}>Electrical Engineering Building (South Entrance)</MenuItem>
                <MenuItem value={"FSH"}>Fishery Sciences Building</MenuItem>
                <MenuItem value={"GWN"}>Gowen Hall</MenuItem>
                <MenuItem value={"IMA"}>Intramural Activities Building</MenuItem>
                <MenuItem value={"KNE (E)"}>Kane Hall (East Entrance)</MenuItem>
                <MenuItem value={"KNE"}>Kane Hall (North Entrance)</MenuItem>
                <MenuItem value={"KNE (S)"}>Kane Hall (South Entrance)</MenuItem>
                <MenuItem value={"KNE (SE)"}>Kane Hall (Southeast Entrance)</MenuItem>
                <MenuItem value={"KNE (SW)"}>Kane Hall (Southwest Entrance)</MenuItem>
                <MenuItem value={"LOW"}>Loew Hall</MenuItem>
                <MenuItem value={"MGH (E)"}>Mary Gates Hall (East Entrance)</MenuItem>
                <MenuItem value={"MGH"}>Mary Gates Hall (North Entrance)</MenuItem>
                <MenuItem value={"MGH (S)"}>Mary Gates Hall (South Entrance)</MenuItem>
                <MenuItem value={"MGH (SW)"}>Mary Gates Hall (Southwest Entrance)</MenuItem>
                <MenuItem value={"MCC"}>McCarty Hall (Main Entrance)</MenuItem>
                <MenuItem value={"MCC (S)"}>McCarty Hall (South Entrance)</MenuItem>
                <MenuItem value={"MCM"}>McMahon Hall (Northwest Entrance)</MenuItem>
                <MenuItem value={"MCM (SW)"}>McMahon Hall (Southwest Entrance)</MenuItem>
                <MenuItem value={"MNY"}>Meany Hall (Northeast Entrance)</MenuItem>
                <MenuItem value={"MNY (NW)"}>Meany Hall (Northwest Entrance)</MenuItem>
                <MenuItem value={"MLR"}>Miller Hall</MenuItem>
                <MenuItem value={"MOR"}>Moore Hall</MenuItem>
                <MenuItem value={"MUS (E)"}>Music Building (East Entrance)</MenuItem>
                <MenuItem value={"MUS"}>Music Building (Northwest Entrance)</MenuItem>
                <MenuItem value={"MUS (S)"}>Music Building (South Entrance)</MenuItem>
                <MenuItem value={"MUS (SW)"}>Music Building (Southwest Entrance)</MenuItem>
                <MenuItem value={"OUG"}>Odegaard Undergraduate Library</MenuItem>
                <MenuItem value={"PAR"}>Parrington Hall</MenuItem>
                <MenuItem value={"CSE"}>Paul G. Allen Center for Computer Science & Engineering</MenuItem>
                <MenuItem value={"PAB"}>Physics/Astronomy Building</MenuItem>
                <MenuItem value={"PAA"}>Physics/Astronomy Building A</MenuItem>
                <MenuItem value={"RAI (E)"}>Raitt Hall (East Entrance)</MenuItem>
                <MenuItem value={"RAI"}>Raitt Hall (West Entrance)</MenuItem>
                <MenuItem value={"ROB"}>Roberts Hall</MenuItem>
                <MenuItem value={"SAV"}>Savery Hall</MenuItem>
                <MenuItem value={"HUB"}>Student Union Building (Main Entrance)</MenuItem>
                <MenuItem value={"HUB (Food, S)"}>Student Union Building (South Food Entrance)</MenuItem>
                <MenuItem value={"HUB (Food, W)"}>Student Union Building (West Food Entrance)</MenuItem>
                <MenuItem value={"SUZ"}>Suzzallo Library</MenuItem>
                <MenuItem value={"T65"}>Thai 65</MenuItem>
                <MenuItem value={"UBS"}>University Bookstore</MenuItem>
                <MenuItem value={"UBS (Secret)"}>University Bookstore (Secret Entrance)</MenuItem>
            </Select>
            <Button variant="contained" color="primary" onClick={this.drawPath} children="Find Path" />
            <Button variant="contained" color="primary" onClick={this.drawBackgroundImage} children="Clear" />
        </div>
        <canvas ref={this.canvasReference} />
      </div>
    )
  }
}

export default Map;