package edu.oregonstate.cs361.battleship;

import java.util.ArrayList;


/**
 * This class is the model that contains all of the board information
 */
public class BattleshipModel {


	private Ship aircraftCarrier;
	private Ship battleship;
	private CivShip clipper;
	private CivShip dinghy;
	private Ship submarine;
	private Ship computer_aircraftCarrier;
	private Ship computer_battleship;
	private CivShip computer_clipper;
	private CivShip computer_dinghy;
	private Ship computer_submarine;
	private ArrayList<Coord> computerHits = new ArrayList<Coord>();
	private ArrayList<Coord> computerMisses = new ArrayList<Coord>();
	private ArrayList<Coord> playerHits = new ArrayList<Coord>();
	private ArrayList<Coord> playerMisses = new ArrayList<Coord>();
	private boolean scanResult = false;
	private boolean difficulty = false; //False means easy, true for hard mode
	private Coord AIShot = null;


	public BattleshipModel() {
		//Create the ship objects
		aircraftCarrier = new Ship("AircraftCarrier", 5, 0, 0, 0, 0);
		battleship = new Ship("BattleShip", 4, 0, 0, 0, 0);
		submarine = new Ship("Submarine", 2, 0, 0, 0, 0);
		clipper = new CivShip("Clipper", 3, 0,0,0,0);
		dinghy = new CivShip("Dinghy",1,0,0,0,0);
		computer_aircraftCarrier = new Ship("AircraftCarrier", 5, 0, 0, 0, 0);
		computer_battleship = new Ship("BattleShip", 4, 0, 0, 0, 0);
		computer_submarine = new Ship("Submarine", 2, 0, 0, 0, 0);
		computer_clipper = new CivShip("Clipper", 3, 0,0,0,0);
		computer_dinghy = new CivShip("Dinghy",1,0,0,0,0);
		
	}



	
	public Ship getAircraftCarrier() {
		return aircraftCarrier;
	}

	public void setAircraftCarrier(Ship aircraftCarrier) {
		this.aircraftCarrier = aircraftCarrier;
	}

	public Ship getBattleship() {
		return battleship;
	}

	public void setBattleship(Ship battleship) {
		this.battleship = battleship;
	}

	public CivShip getClipper() {
		return clipper;
	}

	public void setClipper(CivShip clipper) {
		this.clipper = clipper;
	}

	public CivShip getDinghy() {
		return dinghy;
	}

	public void setDinghy(CivShip dinghy) {
		this.dinghy = dinghy;
	}

	public Ship getSubmarine() {
		return submarine;
	}

	public void setSubmarine(Ship submarine) {
		this.submarine = submarine;
	}

	public Ship getAIaircraftCarrier() {
		return computer_aircraftCarrier;
	}

	public void setAIaircraftCarrier(Ship aIaircraftCarrier) {
		computer_aircraftCarrier = aIaircraftCarrier;
	}

	public Ship getAIbattleship() {
		return computer_battleship;
	}

	public void setAIbattleship(Ship aIbattleship) {
		computer_battleship = aIbattleship;
	}



	public Ship getAIsubmarine() {
		return computer_submarine;
	}

	public void setAIsubmarine(Ship aIsubmarine) {
		computer_submarine = aIsubmarine;
	}


	public void add_player_hit(Coord new_hit){
		playerHits.add(new_hit);
	}
	public void add_player_miss(Coord new_miss){
		playerMisses.add(new_miss);

	}
	public void add_computer_miss(Coord new_miss){
		computerMisses.add(new_miss);
	}
	public void add_computer_hit(Coord new_hit){
		computerHits.add(new_hit);
	}

	public ArrayList<Coord> get_player_hits(){ return playerHits; }

	public ArrayList<Coord> get_player_misses() {return playerMisses; }

	public ArrayList<Coord> get_computer_hits(){ return computerHits; }

	public ArrayList<Coord> get_computer_misses(){ return computerMisses; }


	public Ship getComputer_battleship() {
		return computer_battleship;
	}


	public void setComputer_battleship(Ship computer_battleship) {
		this.computer_battleship = computer_battleship;
	}


	public CivShip getComputer_clipper() {
		return computer_clipper;
	}


	public CivShip getComputer_dinghy() {
		return computer_dinghy;
	}



	public void setComputerHits(ArrayList<Coord> computerHits) {
		this.computerHits = computerHits;
	}



	public void setPlayerHits(ArrayList<Coord> playerHits) {
		this.playerHits = playerHits;
	}

	public Ship[] getShipsByID(String id){

		Ship[] ships = new Ship[2];

		if(id.equals("aircraftCarrier")) {
			ships[0] = aircraftCarrier;
			ships[1] = computer_aircraftCarrier;
		}
		if(id.equals("battleShip")) {
			ships[0] = battleship;
			ships[1] = computer_battleship;
		}
		if(id.equals("clipper")) {
			ships[0] = clipper;
			ships[1] = computer_clipper;
		}
		if(id.equals("dinghy")) {
			ships[0] = dinghy;
			ships[1] = computer_dinghy;
		}
		if(id.equals("submarine")) {
			ships[0] = submarine;
			ships[1] = computer_submarine;
		}

		return ships;
	}


	public void scan(int rowInt, int colInt) 
	{
        	 Coord coor = new Coord(rowInt,colInt);
		 scanResult = false;
		 if(computer_aircraftCarrier.scan(coor)){
		     scanResult = true;
		 }
		 else if (computer_battleship.scan(coor)){
		     scanResult = false;
		 }else if (computer_clipper.scan(coor)){
		     scanResult = true;
		 }else if (computer_dinghy.scan(coor)){
		     scanResult = true;
		 }else if (computer_submarine.scan(coor)){
		     scanResult = false;
		 } else {
		     scanResult = false;
		 }
	}
 
	public boolean getScanResult()
	{
		return scanResult;
	}

	public boolean isHard(){return difficulty;}

	public Coord getAIShot() {
		return AIShot;
	}

	public void setAIShot(Coord AIShot) {
		this.AIShot = AIShot;
	}

	public void setDifficulty(boolean difficulty) {
		this.difficulty = difficulty;
	}

	public void setAIclipper(CivShip AIclipper) {
		this.computer_clipper = AIclipper;
	}

	public void setAIdinghy(CivShip AIdinghy) {
		this.computer_dinghy = AIdinghy;
	}
}


