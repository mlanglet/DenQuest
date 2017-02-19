package den.engine.game.parts;

import den.engine.game.GameHandler;
import den.engine.utility.Constants.DIRECTION;

public class Flavour extends Part {

	public Flavour(GameHandler g, DIRECTION facing) {
		super(g, facing);
		
		init();
	}
	
	protected void init(){
		super.init();
	}

	@Override
	public void die(String killedBy) {
	}

	@Override
	public void update(float delta) {
	}
}
