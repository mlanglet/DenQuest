package den.engine.game.parts;

import java.util.ArrayList;

import den.engine.game.units.Player;
import den.engine.game.GameEntity;
import den.engine.game.GameHandler;
import den.engine.game.GameRoom;
import den.engine.graphics.Texture;
import den.engine.utility.Box2D;
import den.engine.utility.Vector2D;
import den.engine.utility.Constants.DIRECTION;

public class Door extends Part {

	static float doorCooldown = 0;
	static boolean doorEnabled = true;
	
	private GameRoom room;
		
	public Door(GameHandler g, DIRECTION facing, GameRoom room) {
		super(g, facing);
		
		this.room = room;
		
		System.out.println(this + " @ " +facing);
		
		init();
	}

	protected void init(){
		super.init();
		
	    setLayer(4);
	    setVisible(true);
		
		texture = new Texture();
		texture.setTexturePartCount(new Vector2D(4, 1));
		texture.setTextureSource("res/images/parts/door.png");
		
		
		switch(direction){
		case EAST:
			setTexturePosition(new Vector2D(1, 1));
			setPosition(new Vector2D(760, 300));
			break;
		case NORTH:
			setTexturePosition(new Vector2D(4, 1));
			setPosition(new Vector2D(400, 560));
			break;
		case SOUTH:
			setTexturePosition(new Vector2D(2, 1));
			setPosition(new Vector2D(400, 40));
			break;
		case WEST:
			setTexturePosition(new Vector2D(3, 1));
			setPosition(new Vector2D(40, 300));
			break;
		default:
			break;
		}
		
		collisionBox = new Box2D(getPosition(), 50, 50);
		vertexPositionData = new float[]{ 0.15f,-0.15f, .25f, 0f,
										 -0.15f,-0.15f, 0f, 0f, 
										  0.15f, 0.15f, .25f, 1f,
										 -0.15f, 0.15f, 0f, 1f  };
	}
	
	@Override
	public void die(String killedBy) {
	}

	@Override
	public void update(float delta) {
		if(!doorEnabled){
			doorCooldown += delta;
			if(doorCooldown >= 3){
				doorEnabled = true;
				doorCooldown = 0;
			} else {
				return;
			}			
		}
		
		ArrayList<GameEntity> targets = game.checkCollision(this);
		// Check collision with player and reduce player health on impact
		if( !targets.isEmpty() ){
			for(GameEntity t : targets){
				if(t instanceof Player){
					game.takeDoor(this);
					doorEnabled = false;
				}
			}
		} 
	}

	public GameRoom getRoom() {
		return room;
	}

	public void setRoom(GameRoom room) {
		this.room = room;
	}
}
