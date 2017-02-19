package den.engine.game.parts;

import den.engine.game.GameHandler;
import den.engine.graphics.Texture;
import den.engine.utility.Box2D;
import den.engine.utility.Vector2D;
import den.engine.utility.Constants.DIRECTION;

public class Tile extends Part {

	public Tile(GameHandler g, DIRECTION facing, int x, int y) {
		super(g, facing);
		
		setPosition(new Vector2D(x * 80, y * 60));
		
		init();
	}

	protected void init(){
		super.init();
		
		scale = 2f;
		
	    setLayer(1);
	    setVisible(true);
		
		texture = new Texture();
		texture.setTexturePartCount(new Vector2D(8, 1));
		texture.setTextureSource("res/images/parts/tile_sheet.png");
		
		setTexturePosition(new Vector2D((int)(Math.random() * 9), 1));
		
		collisionBox = new Box2D(getPosition(), 800, 600);
 	    vertexPositionData = new float[]{ .125f,  .0f, .1f, 0f,
			     						   .0f,  .0f, 0f, 0f, 
			     						  .125f, .1666667f, .1f, 1f,
			     						   .0f, .1666667f, 0f, 1f  };
	}
	
	@Override
	public void die(String killedBy) {
	}

	@Override
	public void update(float delta) {
	}
}
