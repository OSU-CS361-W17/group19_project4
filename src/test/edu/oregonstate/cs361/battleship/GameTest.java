/**
 * Created by Zaengle on 2/4/2017.
 */

/**
 * These tests check to see that all the helper
 * fucntions for fireAt() in Game work properly.
 * */

package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    public Game game = new Game(15,10,10);

    //This function tests to make sure the game over function returnes the correct value when the number of hits
    // by the player OR the AI reaches the maximum number of hits for their respective board. IE: all ships for
    // that board have been sunk.
    //This covers the fire user story, the AI user story, the place ships user story, and the win condition user story


    @Test
    public void testValidLocation()
    {
        BattleshipModel model = new BattleshipModel();
        new Game();

        assertEquals(true, game.isValidLocation(model, 3,3,"vertical",3,true));
        assertEquals(true, game.isValidLocation(model, 3,3,"vertical",3,false));

        assertEquals(true, game.isValidLocation(model, 8,3,"vertical",3,true));
        assertEquals(true, game.isValidLocation(model, 8,3,"vertical",3,false));

        assertEquals(false, game.isValidLocation(model, 9,3,"vertical",3,true));
        assertEquals(false, game.isValidLocation(model, 9,3,"vertical",3,false));
    }

    @Test
    public void gameOverTest() {
        BattleshipModel model = new BattleshipModel();
        BattleshipModel model2 = new BattleshipModel();

        for (int i = 0; i <= 15; i++) {
            model.add_player_hit(new Coord(1, i));

            for (int j = 0; j <= 15; j++) {
                model2.add_computer_hit(new Coord(1, i));
            }
        }
    }

    //This function is used to make sure there is no colision with the passed ship at the given position
    //This makes sure the user's hits/ misses register as they should
    //This makes sure the AI's hits/missses register as they should
    //This makes sure the ships can NOT be overlaping at any point
    //This covers The fire user story, the AI user story, and the place ships user story
    @Test
    public void posHelperTest() {
        Ship model = new Ship("Tester", 5, 1, 2, 1, 6);

        Coord starthit = new Coord(1, 2);
        Coord endmiss = new Coord(1, 7);

        assertEquals(true, game.posHelper(model, starthit));
        assertEquals(false, game.posHelper(model, endmiss));
    }

    //This test makes sure the user can only fire at each location once and is used to make sure the AI doesn't fire more than the player
    @Test
    public void checkPlayerShotTest() {
        BattleshipModel model = new BattleshipModel();
        model.add_computer_hit(new Coord(1, 2));
        model.add_computer_miss(new Coord(1, 1));

        assertEquals(false, game.checkPlayerShot(model, new Coord(1, 2)));
        assertEquals(false, game.checkPlayerShot(model, new Coord(1, 1)));
        assertEquals(true, game.checkPlayerShot(model, new Coord(4, 4)));
    }

    //This is a test of the function that makes sure the AI doenst shoot the same place twice and doesn't shoot out of bounds
    //This covers part of the fire user story AND the AI user story
    //This ALSO covers part of the AI user story as the function prevents the AI from firing if the user tries to fire at the same location
    @Test
    public void checkShotTests() {
        BattleshipModel model = new BattleshipModel();
        model.add_player_hit(new Coord(1, 2));
        model.add_player_miss(new Coord(1, 1));
        model.add_computer_hit(new Coord(1, 2));
        model.add_computer_miss(new Coord(1, 1));

        assertEquals(false, game.checkValidShot(model, new Coord(1, 2)));
        assertEquals(false, game.checkValidShot(model, new Coord(1, 1)));
        assertEquals(true, game.checkValidShot(model, new Coord(4, 4)));
        assertEquals(false, game.checkValidShot(model, new Coord(11, 4)));

        assertEquals(false, game.checkPlayerShot(model, new Coord(1, 2)));
        assertEquals(false, game.checkPlayerShot(model, new Coord(1, 1)));
        assertEquals(true, game.checkValidShot(model, new Coord(4, 4)));
    }

    //This tests the fire user story and the AI user story
    @Test
    public void testDoFire() {
        BattleshipModel model = new BattleshipModel();

        game.placeShip(model,new Coord(1,2),"vertical","aircraftCarrier");

        game.prepFire(model, new Coord(1, 1));

        assertEquals(0,model.get_computer_hits().size());
        assertEquals(1,model.get_computer_misses().size());

        game.prepFire(model, new Coord(1, 2));

        assertEquals(1,model.get_computer_hits().size());
        assertEquals(1,model.get_computer_misses().size());

        assertEquals(2,model.get_player_hits().size()+model.get_player_misses().size());

        model = new BattleshipModel();
        model.setDifficulty(true);

        game.placeShip(model,new Coord(1,2),"vertical","aircraftCarrier");

        game.prepFire(model, new Coord(1, 1));

        game.prepFire(model, new Coord(2, 1));

        assertEquals(2,model.get_player_hits().size()+model.get_player_misses().size());


    }


    //This just makes sure the change is made, we know it displays "W" "L" on the boards
    @Test
    public void testGameCompleteDraw() {
        BattleshipModel model = new BattleshipModel();
        game.game_complete(model, true);
        game.game_complete(model, false);
    }

    @Test
    public void testHardFire()
    {
        Random rand = new Random(System.currentTimeMillis());
        Coord shot = null;
        int properShotCount = 0;
        BattleshipModel model = new BattleshipModel();
        model.setDifficulty(true);
        game.placeShip(model,new Coord(1,2),"vertical","aircraftCarrier");

        for (int i = 0; i < 99; i ++)
        {
            game.prepFire(model,new Coord(rand.nextInt(11),rand.nextInt(11)));
            if(model.getAIShot() != shot && model.getAIShot() != null)
                properShotCount ++;
            shot = model.getAIShot();

        }

        assertEquals(properShotCount, model.get_player_hits().size());
    }

}