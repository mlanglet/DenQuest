package den.engine.game.units;

import den.engine.game.GameHandler;
import den.engine.graphics.Texture;
import den.engine.utility.Box2D;
import den.engine.utility.Vector2D;
import den.engine.utility.Constants.DIRECTION;

public class Skeleton extends Unit {
	public Skeleton(GameHandler g) {
		super(g, DIRECTION.NORTH);	
		init();
	}
	
	protected void init(){
		super.init();
		
		texture = new Texture();
		texture.setTexturePartCount(new Vector2D(1, 1));
		texture.setTextureSource("res/images/units/Skeleton_sheet.png");
		
		    vertexPositionData = new float[]{ 0.1f,-0.1f, .5f, 0f,
										 -0.1f,-0.1f, 0f, 0f, 
										  0.1f, 0.1f, .5f, 1f,
										 -0.1f, 0.1f, 0f, 1f  };
		   
		    setDamage(25);
		    setSpeed(150);
		    setHealth(100);
		    
		    setPosition(new Vector2D((int)(Math.random()*700),(int)(Math.random()*500)));
		    setLayer(4);
		    setVisible(true);
	    
	   	collisionBox = new Box2D(getPosition(), 20, 20);
	}
	
	
	@Override
	public void update(float delta) {
	}
	
	@Override
	public void die(String killedBy) {
	}
}
