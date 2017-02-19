package den.engine.game.units;

import java.util.ArrayList;

import den.engine.game.GameEntity;
import den.engine.game.GameHandler;
import den.engine.game.effects.Projectile;
import den.engine.game.items.Item;
import den.engine.game.parts.Wall;
import den.engine.graphics.Texture;
import den.engine.input.InputState;
import den.engine.utility.Box2D;
import den.engine.utility.Vector2D;
import den.engine.utility.Constants.DAMAGE_TYPE;
import den.engine.utility.Constants.DIRECTION;

public class Player extends Unit {
	
	private ArrayList<Item> inventory;
	private InputState playerInputState;
	private boolean isJumping = false;
	private boolean isInvurnable = true;
	private boolean isShooting = false;
	private float invurnableCounter = 0;
	private float jumpCounter = 0;
	private float reloadCounter = 0;
	
	
	
	
	public Player(GameHandler g){
		super(g, DIRECTION.NORTH);
		
		init();
	}
	
	public Player(GameHandler g, InputState playerInputState){
		super(g, DIRECTION.NORTH);
		this.playerInputState = playerInputState;
		this.inventory = new ArrayList<Item>();
		
		init();
	}
	
	protected void init(){
		super.init();
		
	    setPosition(new Vector2D(400,300));
	    setLayer(5);
	    setVisible(true);
		
		texture = new Texture();
		texture.setTexturePartCount(new Vector2D(3, 1));
		texture.setTextureSource("res/images/units/player_sheet.png");
		collisionBox = new Box2D(getPosition(), 20, 40);
 	    vertexPositionData = new float[]{ 0.1f,-0.1f, .333f, 0f,
			     						 -0.1f,-0.1f, 0f, 0f, 
			     						  0.1f, 0.1f, .333f, 1f,
			     						 -0.1f, 0.1f, 0f, 1f };
		
		setSpeed(125);
		setHealth(100);
		setArmor(.5f); // 50%
		setMagicResistance(magicResistance = 1f); // 0%
	}
	
	public void jump() {
		// If already jumping just return
		if(isJumping)
			return;
		
		// Start jumping, play animation
		isJumping = true;
			
		setTexturePosition(new Vector2D(1, 0));
	}
	
	@Override 
	/*
	 * Applies damage only if player is not Invurnable (non-Javadoc)
	 * @see den.engine.game.units.Unit#applyDamage(int)
	 */
	public void applyDamage(int damage, DAMAGE_TYPE type){
		if(isInvurnable){
			return;
		} else {
			int final_damage = 0;
			switch(type){
			case PHYSICAL_MELEE:
				if(isJumping){
					final_damage = 0;
				} else {
					final_damage = (int) (damage * armor);
				}
				break;
			case PHYSICAL:
				final_damage = damage;
				break;
			case HP_REMOVAL:
				if(isJumping){
					final_damage = 0;
				} else {
					final_damage = damage;
				}
				break;
			case MAGIC:
				final_damage = (int) (damage * magicResistance);
				break;
			}
			
			health -= final_damage;
			
			if(final_damage > 0)
				isInvurnable = true;
			
			return;
		}
	}
	
	public boolean isJumping(){
		return isJumping;
	}
	
	public void addItem(Item item){
		if(inventory.size() == 6)
			return;
		
		inventory.add(item);
		game.addUIElement(item.getInventoryItem());
	}
	
	public void dropItem(Item item){
		inventory.remove(item);
		game.removeUIElement(item.getInventoryItem());
		item.drop();
	}

	@Override
	public void update(float delta) {
		if(isAlive()){
			if(health <= 0){
				die("Player Died.");
				return;
			}
			
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
			
			tickPosition(delta);
			tickInvurnable(delta);
			tickJump(delta);
			tickWeapon(delta);
		}
	}

	private void tickPosition(float delta) {
		float dist = (speed * delta);
		Vector2D velocityVector = new Vector2D();
		if(playerInputState.getForward() && !colliding[DIRECTION.NORTH.ordinal()] ){
			velocityVector.setY(dist);
		}		
		if(playerInputState.getLeft() && !colliding[DIRECTION.WEST.ordinal()] ){
			velocityVector.setX(-dist);
		}		
		
		if(playerInputState.getRight() && !colliding[DIRECTION.EAST.ordinal()] ){
			velocityVector.setX(velocityVector.getX()+dist);
		}		
		if(playerInputState.getBackward() && !colliding[DIRECTION.SOUTH.ordinal()] ){
			velocityVector.setY(velocityVector.getY()-dist);
		}		
		
		setPosition(Vector2D.add(position, Vector2D.multiplyScalar(velocityVector, dist)));
		collisionBox.setPosition(getPosition());

//		GameVector2D velocityVector = new GameVector2D((float)Math.cos(game.getPlayer().getAimAngle()),
//					(float)Math.sin(game.getPlayer().getAimAngle()));
//		GameVector2D perpendicularVelocityVectorp;
//		if(playerInputState.getForward()){
//			System.out.println("Forward: " + velocityVector);
//			
//		}
//		if(playerInputState.getBackward()){
//			System.out.println("Backward: " + velocityVector);
//			setPosition(pos.subtract(velocityVector.multiplyScalar(dist)));
//		}
//		if(playerInputState.getLeft()){
//			perpendicularVelocityVectorp = new GameVector2D((float)Math.cos(game.getPlayer().getPerpendicularToAimAngle()),
//					 (float)Math.sin(game.getPlayer().getPerpendicularToAimAngle()));
//			// movement left perpendicular to aim angle 
//			System.out.println(game.getPlayer().getPerpendicularToAimAngle()+ "<- perpendicular to ->" + game.getPlayer().getAimAngle());
//			System.out.println("Left: " + perpendicularVelocityVectorp);
//			setPosition(pos.add(perpendicularVelocityVectorp.multiplyScalar(dist)));
//		}
//		if(playerInputState.getRight()){
//			perpendicularVelocityVectorp = new GameVector2D((float)Math.cos(game.getPlayer().getPerpendicularToAimAngle()),
//					 (float)Math.sin(game.getPlayer().getPerpendicularToAimAngle()));
//			
//			System.out.println(game.getPlayer().getPerpendicularToAimAngle()+ "<- perpendicular to ->" + game.getPlayer().getAimAngle());
//			System.out.println("Right: " + perpendicularVelocityVectorp);
//			setPosition(pos.subtract(perpendicularVelocityVectorp.multiplyScalar(dist)));			
//		}
//		collisionBox.setPosition(getPosition());
	}

	private void tickWeapon(float delta) {
		if(playerInputState.getShooting() && !isShooting){
			isShooting = true;
			new Projectile(game, this, getAimCoordinates(), getDamage(), DAMAGE_TYPE.MAGIC);
		}
		
		if(isShooting){
			reloadCounter += delta;
			if(reloadCounter >= .25){
				reloadCounter = 0;
				isShooting = false;
			}
		}
	}

	private void tickJump(float delta) {
		if(playerInputState.getJump() && !isJumping)
			jump();
		
		if(isJumping){
			jumpCounter += delta;
			if(jumpCounter >= .5){
				jumpCounter = 0;
				isJumping = false;
				setTexturePosition(new Vector2D(0, 0));
			}
		}
	}

	private void tickInvurnable(float delta) {
		if(isInvurnable){
			invurnableCounter += delta;
			if((invurnableCounter > .0 && invurnableCounter < .1) ||
			   (invurnableCounter > .3 && invurnableCounter < .4) ||
			   (invurnableCounter > .6 && invurnableCounter < .7) ||
			   (invurnableCounter > .9 && invurnableCounter < 1) ||
			   (invurnableCounter > 1.2 && invurnableCounter < 1.3) ||
			   (invurnableCounter > 1.5 && invurnableCounter < 1.6)){
				setVisible(false);
			} else {
				setVisible(true);
			}
			
			if(invurnableCounter >= 1.6){
				isInvurnable = false;
				invurnableCounter = 0;
			}
		}
	}

	@Override
	public void die(String killedBy) {
		setTexturePosition(new Vector2D(2, 0));
		alive = false;
	}

	public float getAimAngleDegrees() {
		Vector2D aimCoord = getAimCoordinates();
		float dX = aimCoord.getX() - getPosition().getX();
		float dY = aimCoord.getY() - getPosition().getY();
		return (float) (Math.atan2(dY , dX) * 180 / Math.PI);
	}
	
	public float getAimAngle() {
		return Vector2D.getAngleBetween(getPosition(),  getAimCoordinates());
	}
	
	public float getAngleToPlayer(Vector2D v) {
		return Vector2D.getAngleBetween(v,  getPosition());
	}
	
	public float getPerpendicularToAimAngle() {
		Vector2D aimCoord = getAimCoordinates();
		float slopeX = getPosition().getX() - aimCoord.getX();
		float slopeY = getPosition().getY() - aimCoord.getY();
		float slopeP = -1 / (slopeY / slopeX);
		float x1 = getPosition().getX();
		float x2 = 2;
		float y1 = slopeP * x1;
		float y2 = slopeP * x2;
		float dX = x2 - x1;
		float dY = y2 - y1;
		return (float) Math.atan2((-1/dY) , dX);
	}
	
	public Vector2D getAimCoordinates() {
		Vector2D aimCoord =  new Vector2D((float)playerInputState.getAimX(), (float)playerInputState.getAimY());
		float fmax = 600f;
		aimCoord.setY(fmax - aimCoord.getY());
		return aimCoord;
	}
}
