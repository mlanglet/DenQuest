package den.engine.game.items;

import den.engine.game.GameHandler;
import den.engine.graphics.Texture;
import den.engine.utility.Box2D;
import den.engine.utility.Constants.DIRECTION;

public class ItemBomb extends Item {

	public ItemBomb(GameHandler g, DIRECTION dir) {
		super(g, dir);
	}

	protected void init(){
		texture = new Texture();
		texture.setTextureSource("res/images/items/bomb.png");
		
		collisionBox = new Box2D(getPosition(), 30, 30);
	}

	@Override
	public void die(String killedBy) {
		despawn();
	}
}
