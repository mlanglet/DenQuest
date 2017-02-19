package den.engine.game;

import java.io.Serializable;
import java.util.ArrayList;

import den.engine.utility.Vector2D;

public class Map implements Serializable {

	/**
	 *  
	 */
	private static final long serialVersionUID = 3059372155535054343L;
	private ArrayList<GameRoom> mapObjects = new ArrayList<GameRoom>();
	private Vector2D playerPosition;
	
	public void addRoom(GameRoom g){
		mapObjects.add(g);
	}
	
	public ArrayList<GameRoom> getMapObjects() {
		return mapObjects;
	}
	
	public void setMapObjects(ArrayList<GameRoom> mapObjects) {
		this.mapObjects = mapObjects;
	}

	public Vector2D getPlayerPosition() {
		return playerPosition;
	}

	public void setPlayerPosition(Vector2D playerPosition) {
		this.playerPosition = playerPosition;
	}
}
