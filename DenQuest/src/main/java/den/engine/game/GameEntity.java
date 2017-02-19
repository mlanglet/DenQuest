package den.engine.game;

import den.engine.graphics.Drawable;
import den.engine.utility.Box2D;
import den.engine.utility.Constants.*;

public abstract class GameEntity extends Drawable {	
	protected DIRECTION direction;
	protected Box2D collisionBox;
	protected boolean[] colliding;
	
	protected GameHandler game;
	
	public GameEntity(GameHandler g, DIRECTION dir){
		super(g.getResourceManager());
		
		this.game = g;
		this.direction = dir;
		this.game.addGameEntity(this);
	}
	
	public void despawn(){
		this.game.removeGameEntity(this);
	}
	
	public Box2D getCollisionBox(){
		return collisionBox;
	}
	
	public DIRECTION getDirection(){
		return direction;
	}
	
	public abstract void die(String killedBy);
}
