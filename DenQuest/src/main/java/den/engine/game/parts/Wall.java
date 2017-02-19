package den.engine.game.parts;

import den.engine.game.GameHandler;
import den.engine.graphics.Texture;
import den.engine.utility.Box2D;
import den.engine.utility.Constants.WALL_TYPE;
import den.engine.utility.Vector2D;
import den.engine.utility.Constants.DIRECTION;

public class Wall extends Part {
	
	private WALL_TYPE type;
	
	public Wall(GameHandler g, DIRECTION facing, WALL_TYPE type) {
		super(g, facing);
		
		this.type = type; 
		
		init();
	}

	@Override
	public void update(float delta) {
		// Walls don't update
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	protected void init() {
		super.init();
		
	    setLayer(2);
	    setVisible(true);
		
		switch(type){
		case LONG:
			texture = new Texture();
			texture.setTexturePartCount(new Vector2D(1, 2));
			texture.setTextureSource("res/images/parts/Long_Wall.png");
			
			scale = 2f;
			
	 	    vertexPositionData = new float[]{ 1.0f, -0.075f, 1f, 0f,
											  0f, -0.075f, 0f, 0f, 
											  1.0f,  0.075f, 1f, .5f,
											  0f,  0.075f, 0f, .5f  };
			
	 	    switch(direction){
	 	    case NORTH:
	 	    	setTexturePosition(0, 0);
	 	    	setPosition(0, 600);
	 	    	break;
	 	    case SOUTH:
	 	    	setTexturePosition(0, 1);
	 	    	setPosition(0, 0);
	 	    	break;
	 	    }
	 	    
	 	    collisionBox = new Box2D(getPosition(), 800, 60);
	 	    
			break;
		case SHORT:
			texture = new Texture();
			texture.setTextureSource("res/images/parts/shortwall.png");
			texture.setTexturePartCount(new Vector2D(1, 1));
			
	 	    vertexPositionData = new float[]{ 	-1f,  0.1f, 1f, 0f,
 	    										-1f, -0.1f, 0f, 0f, 
 	    										.75f, 0.1f, 1f, 1f,
	 	    									.75f,-0.1f, 0f, 1f  };
			
	 	    switch(direction){
	 	    case EAST:
	 	    	setTexturePosition(0, 0);
	 	    	setPosition(760f, 0);
	 	    	break;
	 	    case WEST:
	 	    	setTexturePosition(0, 0);
	 	    	setPosition(0, 0);
	 	    	break;
	 	    }
	 	    
	 	    collisionBox = new Box2D(getPosition(), 60, 600);
	 	    
			break;
		}
	}

	@Override
	public void die(String killedBy) {
		despawn();
	}
}
