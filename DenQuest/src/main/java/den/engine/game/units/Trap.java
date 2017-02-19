package den.engine.game.units;

import den.engine.game.GameHandler;
import den.engine.graphics.Texture;
import den.engine.utility.Box2D;
import den.engine.utility.Vector2D;
import den.engine.utility.Constants.DAMAGE_TYPE;
import den.engine.utility.Constants.DIRECTION;

public class Trap extends Unit {

	public Trap(GameHandler g) {
		super(g, DIRECTION.NORTH);
		init();
	}
	
	protected void init(){
		super.init();
		
		texture = new Texture();
		texture.setTexturePartCount(new Vector2D(1, 1));
		texture.setTextureSource("res/images/units/trap.png");

		setTexturePosition(new Vector2D(1, 1));
		
		vertexPositionData = new float[]{ 0.1f,-0.1f, 1f, 0f,
									 	 -0.1f,-0.1f, 0f, 0f, 
									 	  0.1f, 0.1f, 1f, 1f,
								 		 -0.1f, 0.1f, 0f, 1f  };
 	    
 	    setHealth(250);
 	    setDamage(100);
	    
	    setPosition(new Vector2D((int)(Math.random()*500),(int)(Math.random()*400)));
	    setLayer(3);
	    setVisible(true);
	    
	    collisionBox = new Box2D(getPosition(), 30, 30);
	}

	@Override
	public void update(float delta) {
		// Check collision with player and reduce player health on impact
		if(collisionBox.contains(game.getPlayer().getCollisionBox())){
			game.getPlayer().applyDamage(100, DAMAGE_TYPE.HP_REMOVAL);
		}
		
		if(health <= 0)
			die("Den Trap Died");
	}

	@Override
	public void die(String killedBy) {
		// TODO Auto-generated method stub
	}
}
