package den.engine.game.parts;

import den.engine.game.GameHandler;
import den.engine.graphics.Texture;
import den.engine.utility.Box2D;
import den.engine.utility.Vector2D;
import den.engine.utility.Constants.DIRECTION;

public class Background extends Part {
	public Background(GameHandler g) {
		super(g, DIRECTION.NORTH);
		
		init();
	}
	
	protected void init(){
		super.init();
		
	    setPosition(new Vector2D(0, 0));
	    setLayer(1);
	    setVisible(true);
		
		texture = new Texture();
		texture.setTexturePartCount(new Vector2D(1, 1));
		
		scale = 2f;
		
		switch(game.getScene()){
		case MAIN_MENU:
			texture.setTextureSource("res/images/screens/MainMenu.png");
			break;
		default:
			//texture.setTextureSource("res/images/parts/floor1.png");
			texture.setTextureSource("res/images/parts/empty.png");
			generateFloor();
			break;
		}
		
		collisionBox = new Box2D(getPosition(), 800, 600);
 	    vertexPositionData = new float[]{ 1.0f,  .0f, 1f, 0f,
			     						   .0f,  .0f, 0f, 0f, 
			     						  1.0f, 1.0f, 1f, 1f,
			     						   .0f, 1.0f, 0f, 1f  };
	}

	private void generateFloor() {
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				new Tile(game, DIRECTION.NORTH, i, j);
			}
		}
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void die(String killedBy) {
		despawn();
	}
}
