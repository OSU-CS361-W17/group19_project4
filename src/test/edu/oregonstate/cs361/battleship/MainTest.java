package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spark.Spark;
import spark.Request;
import spark.utils.IOUtils;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static spark.Spark.awaitInitialization;


/**
 * Created by michaelhilton on 1/26/17.
 */
class MainTest {

    @BeforeAll
    public static void beforeClass() {
        Main.main(null);
        awaitInitialization();
    }

    @AfterAll
    public static void afterClass() {
        Spark.stop();
    }

    @Test
    public void testGetModel() {
        TestResponse res = request("GET", "/model");
        assertEquals(200, res.status);

        assertEquals("{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"length\":5},\"battleship\":{\"name\":\"BattleShip\",\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"length\":4},\"clipper\":{\"name\":\"Clipper\",\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"length\":3},\"dinghy\":{\"name\":\"Dinghy\",\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"length\":1},\"submarine\":{\"name\":\"Submarine\",\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"length\":2},\"computer_aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"length\":5},\"computer_battleship\":{\"name\":\"BattleShip\",\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"length\":4},\"computer_clipper\":{\"name\":\"Clipper\",\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"length\":3},\"computer_dinghy\":{\"name\":\"Dinghy\",\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"length\":1},\"computer_submarine\":{\"name\":\"Submarine\",\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"length\":2},\"computerHits\":[],\"computerMisses\":[],\"playerHits\":[],\"playerMisses\":[],\"scanResult\":false,\"difficulty\":false}",res.body);

    }

    @Test
    public void testPrepFire()
    {
        TestResponse res = request("POST", "/fire/6/6");
        assertEquals(200, res.status);
    }

    @Test
    public void testScan()
    {
        TestResponse res = request("GET", "/model");
        assertEquals(200, res.status);

        res = request("POST", "/scan/6/6");
        assertEquals(200, res.status);
    }

    @Test
    public void testDiffSelect()
    {
        BattleshipModel model;
        Gson gson = new Gson();

        TestResponse res = request("GET", "/model");
        assertEquals(200, res.status);

        model = gson.fromJson(res.body, BattleshipModel.class);

        assertEquals(false, model.isHard());

        res = request("POST", "/difficultySelect/hard");
        assertEquals(200, res.status);

        model = gson.fromJson(res.body, BattleshipModel.class);

        assertEquals(true, model.isHard());
    }
    /*
    @Test
    public void testFireAt(){
        TestResponse res = request("POST", "/fire/1/1");
        Gson gson = new Gson();
        BattleshipModel model = gson.fromJson(res.body, BattleshipModel.class);
        ArrayList<Coord> miss = new ArrayList<Coord>();
        assertEquals(miss, model.get_computer_hits());

    }*/
    
   /*
   This function tests the second and third user story. The first user story is the ability to place ships while avoiding 
   invalid location. This test goes through all of the ships, making sure that they are placed at the right location, and the 
   JSON is updated. Additionally, the 3rd user story, the ability for the AI to work, is tested in this function because  
   every time that the placeShip function is called, an AI place ship is called too, which is then tested in this function. 
   */
    @Test
    public void testPlaceShip() {
        //looped so the random element for AI placing is accounted for
        for(int i = 0; i<100; i++){
            BattleshipModel model;
            Gson gson = new Gson();
            TestResponse res = request("POST", "/placeShip/aircraftCarrier/1/1/horizontal");
            assertEquals(200, res.status);
            model = gson.fromJson(res.body, BattleshipModel.class);
            assertEquals(1, model.getAircraftCarrier().get_start().get_x());
            assertEquals(1, model.getAircraftCarrier().get_start().get_y());
            assertEquals(1, model.getAircraftCarrier().get_end().get_x());
            assertEquals(5, model.getAircraftCarrier().get_end().get_y());
            //name
            assertEquals("AircraftCarrier", model.getAircraftCarrier().get_name());

            //model = gson.fromJson(, BattleshipModel.class);

            res = request("POST", "/placeShip/submarine/1/1/horizontal");
            model = gson.fromJson(res.body, BattleshipModel.class);
            assertEquals(1, model.getSubmarine().get_start().get_x());
            res = request("POST", "/placeShip/battleship/1/1/horizontal");
            model = gson.fromJson(res.body, BattleshipModel.class);
            assertEquals(1, model.getBattleship().get_start().get_x());

            //out of bounds
            res = request("POST", "/placeShip/aircraftCarrier/12/12/horizontal");
            model = gson.fromJson(res.body, BattleshipModel.class);
            assertEquals(0, model.getAircraftCarrier().get_end().get_x());

            //vertical
            res = request("POST", "/placeShip/aircraftCarrier/1/1/vertical");
            model = gson.fromJson(res.body, BattleshipModel.class);
            assertEquals(1, model.getAircraftCarrier().get_start().get_x());
            res = request("POST", "/placeShip/submarine/1/1/vertical");
            model = gson.fromJson(res.body, BattleshipModel.class);
            assertEquals(1, model.getSubmarine().get_start().get_x());
            res = request("POST", "/placeShip/battleship/1/1/vertical");
            model = gson.fromJson(res.body, BattleshipModel.class);
            assertEquals(1, model.getBattleship().get_start().get_x());
        }
    }

    private TestResponse request(String method, String path) {
        try {
            URL url = new URL("http://localhost:4567" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.connect();
            String body = IOUtils.toString(connection.getInputStream());
            return new TestResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }

    private static class TestResponse {

        public final String body;
        public final int status;

        public TestResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }

        public Map<String,String> json() {
            return new Gson().fromJson(body, HashMap.class);
        }
    }


}
