package den.engine.game.effects;

import java.util.ArrayList;

import den.engine.game.GameHandler;
import den.engine.game.GameEntity;
import den.engine.game.parts.Background;
import den.engine.game.parts.Wall;
import den.engine.game.units.Player;
import den.engine.game.units.Unit;
import den.engine.graphics.Texture;
import den.engine.utility.Box2D;
import den.engine.utility.Vector2D;
import den.engine.utility.Constants.DAMAGE_TYPE;

public class Projectile extends Unit {
	
	private Unit source;
	private Vector2D target;
	private Vector2D sourceOriginalPos, targetOriginalPos;
	private DAMAGE_TYPE damageType;
	
	public Projectile(GameHandler g, Unit source, Vector2D target, int d, DAMAGE_TYPE t) {
		super(g, g.getPlayer().getDirection());
		
		this.source = source;
		this.target = target;
		this.sourceOriginalPos = new Vector2D(source.getPosition());
		this.targetOriginalPos = new Vector2D(target);
		this.damage = d;
		this.damageType = t;
		
		init();
	}

	@Override
	public void die(String killedBy) {
		setVisible(false);
		System.out.println(killedBy);
		despawn();
	}

	@Override
	protected void init() {
		super.init();
		
		new ArrayList<GameEntity>();
		
	    setPosition(sourceOriginalPos);
	    setLayer(6);
	    setVisible(true);
		
		texture = new Texture();
		texture.setTexturePartCount(new Vector2D(3, 1));
		
		texture.setTextureSource("res/images/parts/projectile_sheet.png");
		
		switch (damageType) {
		case MAGIC:
			if(source instanceof Player)
				setTexturePosition(new Vector2D(0, 1));
			else
				setTexturePosition(new Vector2D(1, 1));
			break;
		case PHYSICAL:
			setTexturePosition(new Vector2D(2, 1));
			break;
		default:
			break;
		}
		
		collisionBox = new Box2D(getPosition(), 40, 40);
 	    vertexPositionData = new float[]{ 0.05f,-0.05f, .33f, 0f,
			     						 -0.05f,-0.05f, 0f, 0f, 
			     						  0.05f, 0.05f, .33f, 1f,
			     						 -0.05f, 0.05f, 0f, 1f }; 
		
 	    game.getResourceManager().loadNewDrawable(this);
 	    
		setSpeed(300);
		setDamage(25);
		
		velocityVector = new Vector2D((float)Math.cos(Vector2D.getAngleBetween(source.getPosition(), target)),
									  (float)Math.sin(Vector2D.getAngleBetween(source.getPosition(), target)));
	}

	@Override
	public void update(float delta) {
		float dist = (speed * delta);
		setPosition(Vector2D.add(position, Vector2D.multiplyScalar(velocityVector, dist)));
		collisionBox.setPosition(getPosition());
	
		ArrayList<GameEntity> targets = game.checkCollision(this);
		// Check collision with player and reduce player health on impact
		if( !targets.isEmpty() ){
			for(GameEntity t : targets){
				if(t instanceof Background) continue;
				if(t instanceof Projectile) continue;
				if(t instanceof Wall) die("Hit target: " + t.toString());
				if(t instanceof Unit){
					Unit u = (Unit)(t);
					if(u == source) continue;
					u.applyDamage(damage, damageType);
					die("Hit target: " + u.toString());
				}
			}
		} else {
			die("Out of bounds @ (" + getPosition().getX() + ", " + getPosition().getY() + ")");
		}
	}
	
	@Override
	protected void finalize(){
		System.out.println("Finalize of effect " + toString());
	}
}
