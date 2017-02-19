package den.engine.game;

import java.util.ArrayList;

import den.engine.game.parts.Background;
import den.engine.game.parts.Door;
import den.engine.game.parts.Wall;
import den.engine.game.units.Charger;
import den.engine.game.units.Crawler;
import den.engine.game.units.Magi;
import den.engine.game.units.Trap;
import den.engine.utility.Constants;
import den.engine.utility.Constants.WALL_TYPE;
import den.engine.utility.Vector2D;
import den.engine.utility.Constants.DIRECTION;

public class GameRoom {
	
	private static int roomNumberCounter = 0;
	private GameHandler gameHandler;
	private Door northDoor;
	private Door eastDoor;
	private Door southDoor;
	private Door westDoor;
	private Door northTwin;
	private Door eastTwin;
	private Door southTwin;
	private Door westTwin;
	private Vector2D coordinate;
	
	private ArrayList<GameEntity> parts;
	
	private int roomNumber;
	public static int getRoomNumberCounter() {
		return roomNumberCounter;
	}
	
	public GameRoom(GameHandler gameHandler, Door prev, DIRECTION previousDirection){
		this.gameHandler = gameHandler;
		parts = new ArrayList<GameEntity>();
		
		roomNumberCounter++;
		roomNumber = roomNumberCounter;
		
		if(prev != null){
			setConnection(prev, previousDirection);
			addPart(new Door(gameHandler, Constants.getOppositeDirection(prev.direction), this));
		}
		
		// Standard parts
		addPart(new Background(gameHandler));
		addPart(new Wall(gameHandler, DIRECTION.NORTH, WALL_TYPE.LONG));
		addPart(new Wall(gameHandler, DIRECTION.SOUTH, WALL_TYPE.LONG));
		addPart(new Wall(gameHandler, DIRECTION.WEST, WALL_TYPE.SHORT));
		addPart(new Wall(gameHandler, DIRECTION.EAST, WALL_TYPE.SHORT));
		
		// Generate additional items
		generateFlavour();
		generateMobs();
		generateDoor(prev);		
		
		gameHandler.getMap().addRoom(this);
	}

	private void generateMobs() {
		//new Magi(gameHandler);
		//new Magi(gameHandler);	
		//new Crawler(gameHandler);
		//new Crawler(gameHandler);
		//new Crawler(gameHandler);
		//new Crawler(gameHandler);
		//new Charger(gameHandler);
		//new Trap(gameHandler);	
		
//		double r = Math.random(), t;
//		
//		int mobs = 0;
//		
//		if(r < .1) mobs = 1;
//		if(r >= .1 && r < .2) mobs = 2;
//		if(r >= .2 && r < .3) mobs = 3;
//		if(r >= .3 && r < .4) mobs = 4; 
//		if(r >= .4 && r < .5) mobs = 5;
//		if(r >= .5 && r < .6) mobs = 6;
//		if(r >= .6 && r < .7) mobs = 7;
//		if(r >= .7 && r < .8) mobs = 8;
//		if(r >= .8 && r < .9) mobs = 9;
//		if(r >= .9) mobs = 10;
//		
//		
//		mobs = 1;
//		for(int i = 0; i < mobs; i++){
//			t = Math.random();
//			if(t < .33)
//				new Crawler(gameHandler);
//				new Charger(gameHandler);
//			if(t >= .33 && t < .66)
//				new Charger(gameHandler);
//			if(t >= .66)
//				new Charger(gameHandler);
//				new Trap(gameHandler);	
//		}
	}

	private void generateFlavour() {
		// Add some visual objects to differentiate the rooms
		// For random
		//currentRoom.addPart(new RoomFlavourPart(gameHandler, DIRECTION.NORTH));
	}
	
	private void generateDoor(Door prev) {
		double r = Math.random();
		DIRECTION doorDir;
		
		if(prev == null)
			doorDir = DIRECTION.NORTH;
		else
			doorDir = prev.direction;
		
		switch (Constants.getOppositeDirection(doorDir)) {
		case EAST:
			if(r < .33)
				doorDir = DIRECTION.NORTH;
			if(r >= .33 && r < .66)
				doorDir = DIRECTION.WEST;
			if(r >= .66)
				doorDir = DIRECTION.SOUTH;
			break;
		case NORTH:
			if(r < .33)
				doorDir = DIRECTION.EAST;
			if(r >= .33 && r < .66)
				doorDir = DIRECTION.WEST;
			if(r >= .66)
				doorDir = DIRECTION.SOUTH;
			break;
		case SOUTH:
			if(r < .33)
				doorDir = DIRECTION.NORTH;
			if(r >= .33 && r < .66)
				doorDir = DIRECTION.WEST;
			if(r >= .66)
				doorDir = DIRECTION.EAST;
			break;
		case WEST:
			if(r < .33)
				doorDir = DIRECTION.NORTH;
			if(r >= .33 && r < .66)
				doorDir = DIRECTION.EAST;
			if(r >= .66)
				doorDir = DIRECTION.SOUTH;
			break;
		default:
			break;
		}
		
		addPart(new Door(gameHandler, doorDir, this));
	}
	
	public Door getDoor(DIRECTION d){
		switch(d){
		case EAST:
			return eastDoor;
		case NORTH:
			return northDoor;
		case SOUTH:
			return southDoor;
		case WEST:
			return westDoor;
		default:
			break;
		}
		return null;
	}
	
	public Vector2D getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Vector2D coordinate) {
		this.coordinate = coordinate;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public Door getNorth() {
		return northDoor;
	}

	public void setNorth(Door north) {
		this.northDoor = north;
	}

	public Door getEast() {
		return eastDoor;
	}

	public void setEast(Door east) {
		this.eastDoor = east;
	}

	public Door getSouth() {
		return southDoor;
	}

	public void setSouth(Door south) {
		this.southDoor = south;
	}

	public Door getWest() {
		return westDoor;
	}

	public void setWest(Door west) {
		this.westDoor = west;
	}

	public Door getNorthTwin() {
		return northTwin;
	}

	public void setNorthTwin(Door northTwin) {
		this.northTwin = northTwin;
	}

	public Door getEastTwin() {
		return eastTwin;
	}

	public void setEastTwin(Door eastTwin) {
		this.eastTwin = eastTwin;
	}

	public Door getSouthTwin() {
		return southTwin;
	}

	public void setSouthTwin(Door southTwin) {
		this.southTwin = southTwin;
	}

	public Door getWestTwin() {
		return westTwin;
	}

	public void setWestTwin(Door westTwin) {
		this.westTwin = westTwin;
	}

	public static void setRoomNumberCounter(int roomNumberCounter) {
		GameRoom.roomNumberCounter = roomNumberCounter;
	}

	public void setParts(ArrayList<GameEntity> parts) {
		this.parts = parts;
	}
	
	public void addPart(GameEntity p){
		parts.add(p);
		
		if(p instanceof Door){
			Door d = (Door) p;
			switch(p.getDirection()){
			case EAST:
				setEast(d);
				break;
			case NORTH:
				setNorth(d);
				break;
			case SOUTH:
				setSouth(d);
				break;
			case WEST:
				setWest(d);
				break;
			default:
				break;
			
			}
		}
	}
	
	public ArrayList<GameEntity> getParts() {
		return parts;
	}
	
	
	public void setConnectionCallback(Door prev, DIRECTION d) {
		switch (d) {
		case EAST:
			setEastTwin(prev);
			break;
		case NORTH:
			setNorthTwin(prev);
			break;
		case SOUTH:
			setSouthTwin(prev);
			break;
		case WEST:
			setWestTwin(prev);
			break;
		default:
			break;
		}
	}

	public void setConnection(Door prev, DIRECTION d) {
		if(prev == null)
			return;
		
		switch (Constants.getOppositeDirection(d)) {
		case EAST:
			setEastTwin(prev);
			break;
		case NORTH:
			setNorthTwin(prev);	
			break;
		case SOUTH:
			setSouthTwin(prev);	
			break;
		case WEST:
			setWestTwin(prev);
			break;
		default:
			break;
		}
	}

	public Door getTwin(Door d) {
		switch(d.getDirection()){
		case EAST:
			return eastTwin;
		case NORTH:
			return northTwin;
		case SOUTH:
			return southTwin;
		case WEST:
			return westTwin;
		default:
			break;
		}
		return null;
	}
}
