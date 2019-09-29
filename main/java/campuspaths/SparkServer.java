package campuspaths;

import campuspaths.utils.CORSFilter;
import com.google.gson.Gson;
import pathfinder.ModelConnector;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class SparkServer {

  public static void main(String[] args) {
    CORSFilter corsFilter = new CORSFilter();
    corsFilter.apply();
    // The above two lines help set up some settings that allow the
    // React application to make requests to the Spark server, even though it
    // comes from a different server.
    // You should leave these two lines at the very beginning of main().

    // Let's create a more interesting object that holds a whole bunch of
    // data about a range of numbers, and return that using Gson to turn it into a
    // JSON string first.
    ModelConnector campusMap = new ModelConnector();
    Spark.get("/path", new Route() {
      @Override
      public Object handle(Request request, Response response) throws Exception {
        String startBuilding = request.queryParams("start");
        String endBuilding = request.queryParams("end");
        if(startBuilding == null || endBuilding == null) {
          Spark.halt(400, "must have start and end");
        }
        Path<Point> result = campusMap.findShortestPath(startBuilding, endBuilding);
        Gson gson = new Gson();
        return gson.toJson(result);
      }
    });
  }

}
