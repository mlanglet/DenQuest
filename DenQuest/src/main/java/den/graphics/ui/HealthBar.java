package den.graphics.ui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import den.engine.game.GameHandler;
import den.engine.graphics.Shader;
import den.engine.graphics.Texture;
import den.engine.graphics.UnitFragmentShader;
import den.engine.graphics.UnitVertexShader;
import den.engine.render.Display;
import den.engine.utility.Vector2D;


public class HealthBar extends UIElement {

	public HealthBar(GameHandler g) {
		super(g);
		
		init();
	}

	@Override
	public void update(float delta) {
		setPosition(new Vector2D(game.getPlayer().getPosition().getX(), game.getPlayer().getPosition().getY() + 40));
		
		int hp = game.getPlayer().getHealth();
		
		if(hp <= 0)
			setVisible(false);
		else if(hp <= 10)
			setTexturePosition(0f, 0f);
		else if(hp <= 20)
			setTexturePosition(0f, 1f);
		else if(hp <= 30)
			setTexturePosition(0f, 2f);
		else if(hp <= 40)
			setTexturePosition(0f, 3f);
		else if(hp <= 50)
			setTexturePosition(0f, 4f);
		else if(hp <= 60)
			setTexturePosition(0f, 5f);
		else if(hp <= 70)
			setTexturePosition(0f, 6f);
		else if(hp <= 80)
			setTexturePosition(0f, 7f);
		else if(hp <= 90)
			setTexturePosition(0f, 8f);
		else
			setTexturePosition(0f, 9f);
	}

	@Override
	protected void init() {
	    shaders = new ArrayList<Shader>();
	    shaders.add(new UnitVertexShader());
	    shaders.add(new UnitFragmentShader());
		
	    setPosition(new Vector2D(400, 270));
	    setLayer(10);
	    setVisible(true);
		
		texture = new Texture();
		texture.setTexturePartCount(new Vector2D(1, 10));
		texture.setTextureSource("res/images/ui/healthbar_sheet.png");
 	    vertexPositionData = new float[]{ 0.06f,-0.04f, 1f, 0f,
			     						 -0.06f,-0.04f, 0f, 0f, 
			     						  0.06f, 0f, 1f, .1f,
			     						 -0.06f, 0f, 0f, .1f };
	}
}
