package edu.oregonstate.cs361.battleship;

/**
 * Created by MarkSprouse on 3/1/2017.
 */
public class CivShip extends Ship {


    public CivShip(){
        super();
    }
    public CivShip(String id, int length, int x1, int y1, int x2, int y2) {
        super(id,length,x1,y1,x2,y2);
    }

    public void hit(BattleshipModel model, boolean isAI)//Occurs when a civilian ship is shot
    {
        int diffX = (end.get_x() - start.get_x())/2;
        int diffY = (end.get_y() - start.get_y())/2;
        //model.add_computer_hit(start);
        if(isAI)
        {
            model.add_computer_hit(start);
            model.add_computer_hit(new Coord(start.get_x() + diffX, start.get_y() + diffY));
            model.add_computer_hit(end);
        }
        else
        {
            model.add_player_hit(start);
            model.add_player_hit(new Coord(start.get_x() + diffX, start.get_y() + diffY));
            model.add_player_hit(end);
        }
        //Need to find which orientation the ship is in

    }


}
