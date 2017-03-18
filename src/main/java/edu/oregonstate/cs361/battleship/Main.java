package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import spark.Request;

import static spark.Spark.*;

public class Main {

    private static Game game_manager = new Game(15,10,10);

    public static void main(String[] args) {


        //This will allow us to server the static pages such as index.html, app.js, etc.
        staticFiles.location("/public");
        //This will listen to GET requests to /model and return a clean new model
        get("/model", (req, res) -> newModel());
        //This will listen to POST requests and expects to receive a game model, as well as location to fire to
        post("/fire/:row/:col", (req, res) -> prepFire(req));
        //This will listen to POST requests and expects to receive a game model, as well as location to scan
        post("/scan/:row/:col", (req, res) -> scan(req));
        //This will listen to POST requests and expects to receive a game model, as well as location to place the ship
        post("/placeShip/:id/:row/:col/:orientation", (req, res) -> placeShip(req));

        post("/difficultySelect/:difficulty", (req, res) -> difficultySelect(req));

    }

    //This function should return a new model
    public static String newModel() {

        BattleshipModel model = new BattleshipModel();
        //Ship ship = new Ship("?",1,0,0,0,0);
        Gson gson = new Gson();
        //System.out.println(gson.toJson(ship));
        //System.out.println(gson.toJson(model));
        return gson.toJson(model);
    }

    //This function should accept an HTTP request and deseralize it into an actual Java object.

    private static BattleshipModel getModelFromReq(Request req){
        String boardState = req.body(); //should be a JSON stored as a string
        Gson gson = new Gson();
        return gson.fromJson(boardState, BattleshipModel.class); //returns the battleship model
    }

    private static String prepFire(Request req)
    {
        BattleshipModel model = getModelFromReq(req);
        if(model == null){
            model = new BattleshipModel();
        }
        Gson gson = new Gson();
        int[] pos = new int[]{0, 0};                                //to be passed to the position helper function
        pos[0] = Integer.parseInt(req.params(":col"));
        pos[1] = Integer.parseInt(req.params(":row"));
        Coord shot = new Coord(pos[0], pos[1]);

        game_manager.prepFire(model, shot);

        return gson.toJson(model);
    }

    private static String scan(Request req)
    {

        BattleshipModel currModel = getModelFromReq(req);
        String row = req.params("row");
        String col = req.params("col");
        int rowInt = Integer.parseInt(row);
        int colInt = Integer.parseInt(col);
        game_manager.scan(currModel, rowInt,colInt);
        Gson gson = new Gson();
        return gson.toJson(currModel);
    }

    private static String placeShip(Request req)
    {
        BattleshipModel model = getModelFromReq(req); //calls above function to create an object from board state
        if(model == null){
            model = new BattleshipModel();
        }
        Gson gson = new Gson();

        //declares variables for the details specified for the ship
        String id = req.params(":id");
        int row = Integer.parseInt(req.params(":row"));
        int col = Integer.parseInt(req.params(":col"));
        String orientation = req.params(":orientation");


        game_manager.placeShip(model, new Coord(row, col), orientation, id);

        return gson.toJson(model);
    }

    private static String difficultySelect(Request req)
    {
        BattleshipModel model = getModelFromReq(req);
        Gson gson = new Gson();
        String difficulty = req.params(":difficulty");

        if(difficulty.equals("easy"))
            model.setDifficulty(false);
        else if (difficulty.equals("hard"))
                model.setDifficulty(true);
        else
            System.out.println("Inproper String detected in request");
        return gson.toJson(model);
    }

}

