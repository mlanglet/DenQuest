package den.engine.game.units;

import den.engine.game.GameHandler;
import den.engine.graphics.Texture;
import den.engine.utility.Box2D;
import den.engine.utility.Vector2D;
import den.engine.utility.Constants.DAMAGE_TYPE;
import den.engine.utility.Constants.DIRECTION;

public class Charger extends Unit {
	
	private float chargeCooldownCounter = 0;
	private boolean isCharging = false;
	
	public Charger(GameHandler g) {
		super(g, DIRECTION.NORTH);	
		init();
	}
	
	protected void init(){
		super.init();
		
		texture = new Texture();
		texture.setTexturePartCount(new Vector2D(2, 1));
		texture.setTextureSource("res/images/units/Charger_sheet.png");
		
	    vertexPositionData = new float[]{ 0.1f,-0.1f, .5f, 0f,
									 -0.1f,-0.1f, 0f, 0f, 
									  0.1f, 0.1f, .5f, 1f,
									 -0.1f, 0.1f, 0f, 1f  };
		   
	    setDamage(50);
	    setSpeed(100);
	    setHealth(200);
	    
	    setPosition(new Vector2D((int)(Math.random()*700),(int)(Math.random()*500)));
	    setLayer(4);
	    setVisible(true);
	    
	    chargeCooldownCounter = 50;
	    
	   	collisionBox = new Box2D(getPosition(), 20, 20);
	}
		
	
	@Override
	public void update(float delta) {
		if(isAlive() && game.getPlayer().isAlive()){
			velocityVector = new Vector2D((float) Math.cos(game.getPlayer().getAngleToPlayer(getPosition())),
					  					  (float) Math.sin(game.getPlayer().getAngleToPlayer(getPosition())));
			
			if(!isCharging) {
				speed = 100;
				chargeCooldownCounter += delta;
				if(chargeCooldownCounter >= 6){
					isCharging = true;
					chargeCooldownCounter = 0;
				}
			}
			else {
				speed = 200;
				chargeCooldownCounter += delta;
				if(chargeCooldownCounter >= 3){
					isCharging = false;
				}
			}
			
			float dist = (speed * delta);
			setPosition(Vector2D.add(getPosition(), Vector2D.multiplyScalar(velocityVector, dist)));
			collisionBox.setPosition(getPosition());	
			
			if(collisionBox.contains(game.getPlayer().getCollisionBox())){
				game.getPlayer().applyDamage(75, DAMAGE_TYPE.PHYSICAL_MELEE);
			}
			
			if(health <= 0)
				die("Charger died.");
		}		
	}
	
	@Override
	public void die(String killedBy) {
		setTexturePosition(new Vector2D(1, 0));
		setAlive(false);
		collisionBox = new Box2D(0,0,0,0);
	}
}
