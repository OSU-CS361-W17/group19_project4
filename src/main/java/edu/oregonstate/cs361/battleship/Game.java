package edu.oregonstate.cs361.battleship;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Owner on 3/15/2017.
 */
public class Game {
    private static int max_hits;
    private static int boardHeight;
    private static int boardWidth;

    public Game(){
        this.max_hits = 0;
        this.boardHeight = 0;
        this.boardWidth = 0;
    }
    public Game(int max_hits, int boardHeight, int boardWidth){
        this.max_hits = max_hits;
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
    }

    public void scan(BattleshipModel model, int rowInt, int colInt) {

       model.scan(rowInt, colInt);
    }

    private void placeEasy(BattleshipModel model, Coord coord, String orientation, String id) {

        int row, col, AIRow, AICol;
        String AIOrientation;
        row = coord.get_x();
        col = coord.get_y();
        int easySet=0;
        if (id.equals("aircraftCarrier"))
            easySet=0;
        if (id.equals("battleship"))
            easySet=1;
        if (id.equals("clipper"))
            easySet=2;
        if (id.equals("dinghy"))
            easySet=3;
        if (id.equals("submarine"))
            easySet=4;

        Ship[] ships = model.getShipsByID(id);
        Random rand = new Random(System.currentTimeMillis());
        //intialize coordinate and orientation arrays for ez mode
        //non casual mode is handled within the actual placement
        Coord[] easyPlace = new Coord[5];
        String[] easyOrient = new String[5];

        for(int i=0; i<5; i++){
            if(i<3){
                easyPlace[i] = new Coord((i+1)*2,1);
                easyOrient[i] = "vertical";
            }
            else{
                easyPlace[i] = new Coord(1,i*2);
                easyOrient[i] = "horizontal";
            }
        }

        if (isValidLocation(model, row, col, orientation,ships[0].get_length(), true)) {
            ships[0].set_location(row, col, orientation);
            Ship temp =ships[1];

            //coords are now valid (in theory)
            AICol = easyPlace[easySet].get_x();
            AIRow = easyPlace[easySet].get_y();
            AIOrientation = easyOrient[easySet];


            ships[1].set_location(AIRow,AICol,AIOrientation);
        }

    }

    private void placeHard(BattleshipModel model, Coord coord, String orientation, String id){

        int row, col, AIRow, AICol;
        String AIOrientation;
        row = coord.get_x();
        col = coord.get_y();
        int easySet=0;
        if (id.equals("aircraftCarrier"))
            easySet=0;
        if (id.equals("battleship"))
            easySet=1;
        if (id.equals("clipper"))
            easySet=2;
        if (id.equals("dinghy"))
            easySet=3;
        if (id.equals("submarine"))
            easySet=4;

        Ship[] ships = model.getShipsByID(id);
        Random rand = new Random(System.currentTimeMillis());
        //intialize coordinate and orientation arrays for ez mode
        //non casual mode is handled within the actual placement
        Coord[] easyPlace = new Coord[5];
        String[] easyOrient = new String[5];

        for(int i=0; i<5; i++){
            if(i<3){
                easyPlace[i] = new Coord((i+1)*2,1);
                easyOrient[i] = "vertical";
            }
            else{
                easyPlace[i] = new Coord(1,i*2);
                easyOrient[i] = "horizontal";
            }
        }

        if (isValidLocation(model, row, col, orientation,ships[0].get_length(), true)) {
            ships[0].set_location(row, col, orientation);
            Ship temp =ships[1];

            do {
                AICol = rand.nextInt(boardWidth + 1) +1;
                AIRow = rand.nextInt(boardHeight + 1) +1;
                if (rand.nextInt(2) == 0)
                    AIOrientation = "horizontal";
                else
                    AIOrientation = "vertical";


            } while (!isValidLocation(model, AIRow, AICol, AIOrientation, temp.get_length(), false));

            ships[1].set_location(AIRow,AICol,AIOrientation);
        }
    }

    public boolean isValidLocation(BattleshipModel model, int row, int col, String orientation, int length, boolean isPlayer)//Needs to check to see if a given coordiante is valid for the ship to be placed at.  OTHER PARAMS MAY BE NEEDED
    {
        if(isPlayer)
        {
            // aircraft carrier check
            if(     (row == model.getAircraftCarrier().get_start().get_x() ) &&
                    (col == model.getAircraftCarrier().get_start().get_y() )   ) {
                return false;
            }
            else{

                Ship s = model.getAircraftCarrier();

                if (orientation.equals("horizontal")) {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row, col + i);
                        if (posHelper(s, c))
                            return false;
                    }
                } else {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row + i, col);
                        if (posHelper(s, c))
                            return false;
                    }
                }
            }
            // battle ship check
            if(     (row == model.getBattleship().get_start().get_x() ) &&
                    (col == model.getBattleship().get_start().get_y() )   ) {
                return false;
            }
            else {
                Ship s = model.getBattleship();

                if (orientation.equals("horizontal")) {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row, col + i);
                        if (posHelper(s, c))
                            return false;
                    }
                } else {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row + i, col);
                        if (posHelper(s, c))
                            return false;
                    }
                }
            }
            // dinghy check
            if(     (row == model.getDinghy().get_start().get_x() ) &&
                    (col == model.getDinghy().get_start().get_y() )   ) {

                return false;
            }
            else{
                Ship s = model.getDinghy();

                if (orientation.equals("horizontal")) {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row, col + i);
                        if (posHelper(s, c))
                            return false;
                    }
                } else {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row + i, col);
                        if (posHelper(s, c))
                            return false;
                    }
                }
            }
            // clipper check
            if(     (row == model.getClipper().get_start().get_x() ) &&
                    (col == model.getClipper().get_start().get_y() )   ) {
                return false;
            }
            else{
                Ship s = model.getClipper();

                if (orientation.equals("horizontal")) {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row, col + i);
                        if (posHelper(s, c))
                            return false;
                    }
                } else {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row + i, col);
                        if (posHelper(s, c))
                            return false;
                    }
                }
            }
            //submarine check
            if(     (row == model.getSubmarine().get_start().get_x() ) &&
                    (col == model.getSubmarine().get_start().get_y() )   ) {
                return false;
            }
            else{
                Ship s = model.getSubmarine();

                if (orientation.equals("horizontal")) {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row, col + i);
                        if (posHelper(s, c))
                            return false;
                    }
                } else {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row + i, col);
                        if (posHelper(s, c))
                            return false;
                    }
                }
            }
            return true;
        }
        else
        {
            // aircraft carrier check
            if(     (row == model.getAIaircraftCarrier().get_start().get_x() ) &&
                    (col == model.getAIaircraftCarrier().get_start().get_y() )   ) {
                return false;
            }
            else{
                Ship s = model.getAIaircraftCarrier();

                if (orientation.equals("horizontal")) {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row, col + i);
                        if (posHelper(s, c))
                            return false;
                    }
                } else {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row + i, col);
                        if (posHelper(s, c))
                            return false;
                    }
                }
            }
            // battle ship check
            if(     (row == model.getAIbattleship().get_start().get_x() ) &&
                    (col == model.getAIbattleship().get_start().get_y() )   ) {
                return false;
            }
            else{
                Ship s = model.getAIbattleship();

                if (orientation.equals("horizontal")) {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row, col + i);
                        if (posHelper(s, c))
                            return false;
                    }
                } else {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row + i, col);
                        if (posHelper(s, c))
                            return false;
                    }
                }
            }
            // dinghy check
            if(     (row == model.getComputer_dinghy().get_start().get_x() ) &&
                    (col == model.getComputer_dinghy().get_start().get_y() )   ) {
                return false;
            }
            else{
                Ship s = model.getComputer_dinghy();

                if (orientation.equals("horizontal")) {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row, col + i);
                        if (posHelper(s, c))
                            return false;
                    }
                } else {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row + i, col);
                        if (posHelper(s, c))
                            return false;
                    }
                }
            }
            // clipper check
            if(     (row == model.getComputer_clipper().get_start().get_x() ) &&
                    (col == model.getComputer_clipper().get_start().get_y() )   ) {
                return false;
            }
            else{
                Ship s = model.getComputer_clipper();

                if (orientation.equals("horizontal")) {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row, col + i);
                        if (posHelper(s, c))
                            return false;
                    }
                } else {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row + i, col);
                        if (posHelper(s, c))
                            return false;
                    }
                }
            }
            //submarine check
            if(     (row == model.getAIsubmarine().get_start().get_x() ) &&
                    (col == model.getAIsubmarine().get_start().get_y() )   ) {
                return false;
            }
            else{
                Ship s = model.getAIsubmarine();

                if (orientation.equals("horizontal")) {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row, col + i);
                        if (posHelper(s, c))
                            return false;
                    }
                } else {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row + i, col);
                        if (posHelper(s, c))
                            return false;
                    }
                }
            }
            return true;
        }
    }


    //This controller should take a json object from the front end, and place the ship as requested, and then return the object.
    public void placeShip(BattleshipModel model, Coord coord, String orientation, String id) {

        //declares variables for the details specified for the ship

        if(model.isHard())
            placeHard(model,coord,orientation,id);
        else
            placeEasy(model,coord,orientation,id);

    }

    public void prepFire(BattleshipModel model, Coord shot)
    {
        model = fireAt(model, shot);
        game_over(model);
    }

    public boolean checkValidShot(BattleshipModel model, Coord coord)//Checks to see if a shot being done by the AI has already been done
    {
        //Check to see if it is off the map
        if(coord.get_x() < 1 || coord.get_x() >= boardWidth + 1 || coord.get_y() < 1 || coord.get_y() >= boardHeight + 1)
        {
            return false;
        }
        //Check to see if thats been fired before
        for (int i = 0; i < model.get_player_hits().size(); i++) {
            if(coord.get_x() == model.get_player_hits().get(i).get_x())//check if they have matching X coords
            {
                if(coord.get_y() == model.get_player_hits().get(i).get_y())//check if they have matching Y coords as well
                {
                    return false;
                }
            }
        }

        for (int i = 0; i < model.get_player_misses().size(); i++) {
            if(coord.get_x() == model.get_player_misses().get(i).get_x())//check if they have matching X coords
            {
                if(coord.get_y() == model.get_player_misses().get(i).get_y())//check if they have matching Y coords as well
                {
                    return false;
                }
            }
        }

        return true;
    }

    //Used to make sure the player does not fire at the same spot more than once.
    public boolean checkPlayerShot(BattleshipModel model, Coord coord)//Checks to see if a shot being done by the AI has already been done
    {
        //Check to see if thats been fired before
        for (int i = 0; i < model.get_computer_hits().size(); i++) {
            if(coord.get_x() == model.get_computer_hits().get(i).get_x())//check if they have matching X coords
            {
                if(coord.get_y() == model.get_computer_hits().get(i).get_y())//check if they have matching Y coords as well
                {
                    //System.out.println("False");
                    return false;
                }
            }
        }

        for (int i = 0; i < model.get_computer_misses().size(); i++) {
            if(coord.get_x() == model.get_computer_misses().get(i).get_x())//check if they have matching X coords
            {
                if(coord.get_y() == model.get_computer_misses().get(i).get_y())//check if they have matching Y coords as well
                {
                    //System.out.println("False");
                    return false;
                }
            }
        }
        //System.out.println("checkplayershot true");
        return true;
    }


    public BattleshipModel fireAt(BattleshipModel model, Coord shot){
        if(checkPlayerShot(model, shot)) {

            //Execute player shot

            playerShot(model, shot);

            //Execution of the AI's shot

            AIFire(model);
        }
        return model;
    }

    private void playerShot(BattleshipModel model, Coord shot) {
        //if we register any hits
        if (posHelper(model.getAIaircraftCarrier(), shot) || posHelper(model.getAIbattleship(), shot) || posHelper(model.getAIsubmarine(), shot)) {
            //mark as a hit for the player
            model.add_computer_hit(shot);
        }
        else if(posHelper(model.getComputer_clipper(), shot)){
            model.getComputer_clipper().hit(model, true);
        }
        else if(posHelper(model.getComputer_dinghy(), shot)){
            model.getComputer_dinghy().hit(model, true);
        }
        else{
            //mark as a miss for the player
            model.add_computer_miss(shot);

        }
    }

    private void AIFire(BattleshipModel model) {

        Coord mycoord;

        //Get the coordinate
        if(model.isHard())
            //If it is hard mode
            mycoord = AIHardFire(model);
        else
            //If set to easy mode
            mycoord = AIEasyFire(model);

        //check to see if the shot hits or misses

        //if we register any hits
        if (posHelper(model.getAircraftCarrier(), mycoord) || posHelper(model.getBattleship(), mycoord) || posHelper(model.getSubmarine(), mycoord)) {
            //mark as a hit for the computer
            model.add_player_hit(mycoord);
            model.setAIShot(mycoord);
        }
        else if( posHelper(model.getClipper(), mycoord)){
            model.getClipper().hit(model, false);
            model.setAIShot(mycoord);
        }
        else if( posHelper(model.getDinghy(), mycoord )){
            model.getDinghy().hit(model, false);
            model.setAIShot(mycoord);
        }
        else {
            //mark as a miss for the computer
            model.add_player_miss(mycoord);

        }
    }

    private Coord AIHardFire(BattleshipModel model) {
        Random rand = new Random(System.currentTimeMillis());
        Coord mycoord;
        Coord AIShot = model.getAIShot();

        //If it was a hit
        //Shot the areas that are around the preious shot because that is likely to be another hit
        if(AIShot != null)
        {
            //Checks to see if any surrounding areas are available to be shot at
            for(int i = 0; i < 4; i++)
            {
                switch(i) {
                    case 0:
                        mycoord = new Coord(AIShot.get_x() + 1, AIShot.get_y());
                        break;
                    case 1:
                        mycoord = new Coord(AIShot.get_x() - 1, AIShot.get_y());
                        break;

                    case 2:
                        mycoord = new Coord(AIShot.get_x(), AIShot.get_y()+1);
                        break;

                    case 3:
                        mycoord = new Coord(AIShot.get_x(), AIShot.get_y()-1);
                        break;
                    default:
                        mycoord = new Coord(-1,-1);
                        break;
                }
                if (checkValidShot(model, mycoord))
                    return mycoord;
                model.setAIShot(null);
            }
        }
        //If the previious shot was not a hit, shoot randomly
        do {
            mycoord = new Coord(rand.nextInt(boardHeight+1), rand.nextInt(boardWidth+1));

        } while (!checkValidShot(model, mycoord));//while the shot has already been done

        return mycoord;
    }

    private Coord AIEasyFire(BattleshipModel model) {
        Coord mycoord;
        Random rand = new Random(System.currentTimeMillis());

        //Must be changed, some pattern for firing must be created
        do {
            mycoord = new Coord(rand.nextInt(boardHeight+1), rand.nextInt(boardWidth+1));
        } while (!checkValidShot(model, mycoord));

        return mycoord;
    }

    public boolean posHelper(Ship model, Coord pos){
        Coord start = model.get_start();
        Coord end = model.get_end();

        if(pos.get_x() >= start.get_x() && pos.get_x() <= end.get_x()) {           //if the x of the shot is within x bounds of ship
            if (pos.get_y() >= start.get_y() && pos.get_y() <= end.get_y()) {      //if the y of the shot is within y bounds of ship
                //System.out.println("True");
                return true;
            }
        }
        //System.out.println("False");
        return false;
    }
    public void game_over(BattleshipModel model){

        if(model.get_player_hits().size() == max_hits)
        {
            game_complete(model, true);
        }

        if(model.get_computer_hits().size() == max_hits)
        {
            game_complete(model, false);
        }
    }

    public void game_complete(BattleshipModel model, boolean isPlayer)
    {
        ArrayList<Coord> temp = new ArrayList<Coord>();
        model.get_player_hits().clear();
        model.get_computer_hits().clear();
        model.get_computer_misses().clear();
        model.get_player_misses().clear();
        if(isPlayer)
        {

            //Add in the coords for the W and L for the winner and loser
            //MAKES THE W
            temp.add(new Coord(2,1));temp.add(new Coord(2,2));temp.add(new Coord(3,1));
            temp.add(new Coord(3,2));temp.add(new Coord(4,1));temp.add(new Coord(4,2));
            temp.add(new Coord(4,3));temp.add(new Coord(5,2));temp.add(new Coord(5,3));
            temp.add(new Coord(6,2));temp.add(new Coord(6,3));temp.add(new Coord(6,4));
            temp.add(new Coord(7,3));temp.add(new Coord(7,4));temp.add(new Coord(7,5));
            temp.add(new Coord(8,4));temp.add(new Coord(8,5));temp.add(new Coord(7,6));
            temp.add(new Coord(6,6));temp.add(new Coord(7,7));temp.add(new Coord(8,7));
            temp.add(new Coord(8,7));temp.add(new Coord(7,8));temp.add(new Coord(6,8));
            temp.add(new Coord(6,9));temp.add(new Coord(5,8));temp.add(new Coord(5,9));
            temp.add(new Coord(4,8));temp.add(new Coord(4,9));temp.add(new Coord(4,10));
            temp.add(new Coord(3,9));temp.add(new Coord(3,10));temp.add(new Coord(2,9));
            temp.add(new Coord(2,10));

            model.setPlayerHits(temp);


            //MAKES THE L
            temp = new ArrayList<Coord>();

            temp.add(new Coord(2,4));temp.add(new Coord(2,5));temp.add(new Coord(3,4));
            temp.add(new Coord(3,5));temp.add(new Coord(4,4));temp.add(new Coord(4,5));
            temp.add(new Coord(5,4));temp.add(new Coord(5,5));temp.add(new Coord(6,4));
            temp.add(new Coord(6,5));temp.add(new Coord(7,4));temp.add(new Coord(7,5));
            temp.add(new Coord(8,4));temp.add(new Coord(8,5));temp.add(new Coord(8,6));
            temp.add(new Coord(8,7));temp.add(new Coord(8,8));temp.add(new Coord(9,4));
            temp.add(new Coord(9,5));temp.add(new Coord(9,6));temp.add(new Coord(9,7));
            temp.add(new Coord(9,8));

            model.setComputerHits(temp);

        }
        else
        {
            //Add in the coords for the W and L for the winner and loser
            //MAKES THE W
            temp.add(new Coord(2,1));temp.add(new Coord(2,2));temp.add(new Coord(3,1));
            temp.add(new Coord(3,2));temp.add(new Coord(4,1));temp.add(new Coord(4,2));
            temp.add(new Coord(4,3));temp.add(new Coord(5,2));temp.add(new Coord(5,3));
            temp.add(new Coord(6,2));temp.add(new Coord(6,3));temp.add(new Coord(6,4));
            temp.add(new Coord(7,3));temp.add(new Coord(7,4));temp.add(new Coord(7,5));
            temp.add(new Coord(8,4));temp.add(new Coord(8,5));temp.add(new Coord(7,6));
            temp.add(new Coord(6,6));temp.add(new Coord(7,7));temp.add(new Coord(8,7));
            temp.add(new Coord(8,7));temp.add(new Coord(7,8));temp.add(new Coord(6,8));
            temp.add(new Coord(6,9));temp.add(new Coord(5,8));temp.add(new Coord(5,9));
            temp.add(new Coord(4,8));temp.add(new Coord(4,9));temp.add(new Coord(4,10));
            temp.add(new Coord(3,9));temp.add(new Coord(3,10));temp.add(new Coord(2,9));
            temp.add(new Coord(2,10));

            model.setComputerHits(temp);


            //MAKES THE L
            temp = new ArrayList<Coord>();

            temp.add(new Coord(2,4));temp.add(new Coord(2,5));temp.add(new Coord(3,4));
            temp.add(new Coord(3,5));temp.add(new Coord(4,4));temp.add(new Coord(4,5));
            temp.add(new Coord(5,4));temp.add(new Coord(5,5));temp.add(new Coord(6,4));
            temp.add(new Coord(6,5));temp.add(new Coord(7,4));temp.add(new Coord(7,5));
            temp.add(new Coord(8,4));temp.add(new Coord(8,5));temp.add(new Coord(8,6));
            temp.add(new Coord(8,7));temp.add(new Coord(8,8));temp.add(new Coord(9,4));
            temp.add(new Coord(9,5));temp.add(new Coord(9,6));temp.add(new Coord(9,7));
            temp.add(new Coord(9,8));

            model.setPlayerHits(temp);
        }
    }
}
