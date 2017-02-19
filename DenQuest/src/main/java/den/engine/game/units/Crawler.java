package den.engine.game.units;

import java.util.ArrayList;

import den.engine.game.GameEntity;
import den.engine.game.GameHandler;
import den.engine.game.parts.Wall;
import den.engine.graphics.Texture;
import den.engine.utility.Box2D;
import den.engine.utility.Vector2D;
import den.engine.utility.Constants.DAMAGE_TYPE;
import den.engine.utility.Constants.DIRECTION;

public class Crawler extends Unit {
	
	private float directionCounter = 0;
	private int dirIndex = 0;
	
	public Crawler(GameHandler g) {	
		super(g, DIRECTION.NORTH);	
		init();
	}

	protected void init(){
		super.init();
		
		texture = new Texture();
		texture.setTexturePartCount(new Vector2D(2, 1));
		texture.setTextureSource("res/images/units/crawler_sheet.png");
		
 	    vertexPositionData = new float[]{ 0.1f,-0.1f, .5f, 0f,
    									 -0.1f,-0.1f, 0f, 0f, 
    									  0.1f, 0.1f, .5f, 1f,
    									 -0.1f, 0.1f, 0f, 1f  };
 	   
 	    setDamage(50);
 	    setSpeed(200);
 	    setHealth(50);
 	    
 	    setPosition(new Vector2D((int)(Math.random()*700),(int)(Math.random()*500)));
 	    setLayer(4);
 	    setVisible(true);
	    
	   	collisionBox = new Box2D(getPosition(), 20, 20);
	}

	
	@Override
	public void update(float delta) {
		directionCounter += delta;
		if(directionCounter >= 2){
			DIRECTION[] dirArray = DIRECTION.values();		
			dirIndex = (int)(Math.random()*9);
			if(dirIndex < 8)
			   direction = dirArray[dirIndex];
			
			directionCounter = 0;
		}   
		
		if(isAlive()){
			colliding = new boolean[]{false, false, false, false};
			ArrayList<GameEntity> targets = game.checkCollision(this);
			// Check collision with player and reduce player health on impact
			if( !targets.isEmpty() ){
				for(GameEntity t : targets){
					if(t instanceof Wall){
						Wall w = (Wall) t;
						DIRECTION d;
						switch(d = w.getDirection()){
						case EAST:
							colliding[d.ordinal()] = true;
							break;
						case NORTH:
							colliding[d.ordinal()] = true;
							break;
						case SOUTH:
							colliding[d.ordinal()] = true;
							break;
						case WEST:
							colliding[d.ordinal()] = true;
							break;
						default:
							break;
						}
					}
				}
			}			
			
			Vector2D velocityVector;
			float dist = (speed * delta);
			if(dirIndex == 8){
				velocityVector = new Vector2D((float) Math.cos(game.getPlayer().getAngleToPlayer(getPosition())),
	  					  					  (float) Math.sin(game.getPlayer().getAngleToPlayer(getPosition())));
			} else { 		
				velocityVector = new Vector2D();
				switch(direction){
				case NORTH:
					if(!colliding[DIRECTION.NORTH.ordinal()])
						velocityVector.setY(1);
					break;
				case EAST:
					if(!colliding[DIRECTION.EAST.ordinal()])
						velocityVector.setX(1);
					break;
				case NORTH_EAST:
					if(!colliding[DIRECTION.EAST.ordinal()])
						velocityVector.setX(1);
					if(!colliding[DIRECTION.NORTH.ordinal()])
						velocityVector.setY(1);
					break;
				case NORTH_WEST:
					if(!colliding[DIRECTION.NORTH.ordinal()])
						velocityVector.setY(1);
					if(!colliding[DIRECTION.WEST.ordinal()])
						velocityVector.setX(-1);
					break;
				case SOUTH:
					if(!colliding[DIRECTION.SOUTH.ordinal()])
						velocityVector.setY(-1);
					break;
				case SOUTH_EAST:
					if(!colliding[DIRECTION.SOUTH.ordinal()])
						velocityVector.setY(-1);
					if(!colliding[DIRECTION.EAST.ordinal()])
						velocityVector.setX(+1);
					break;
				case SOUTH_WEST:
					if(!colliding[DIRECTION.SOUTH.ordinal()])
						velocityVector.setY(-1);
					if(!colliding[DIRECTION.EAST.ordinal()])
						velocityVector.setX(+1);
					break;
				case WEST:
					if(!colliding[DIRECTION.WEST.ordinal()])
						velocityVector.setX(-1);
					break;
				default:
					break;
				}
			}
			
			setPosition(Vector2D.add(getPosition(), Vector2D.multiplyScalar(velocityVector, dist)));
			collisionBox.setPosition(getPosition());
			
			// Check collision with player and reduce player health on impact
			if(collisionBox.contains(game.getPlayer().getCollisionBox())){
				game.getPlayer().applyDamage(50, DAMAGE_TYPE.PHYSICAL_MELEE);
			}
			
			if(health <= 0)
				die("DenCrawler died.");
		}
	}

	@Override
	public void die(String killedBy) {
		setTexturePosition(new Vector2D(1, 0));
		setAlive(false);
		collisionBox = new Box2D(0,0,0,0);
	}
}
