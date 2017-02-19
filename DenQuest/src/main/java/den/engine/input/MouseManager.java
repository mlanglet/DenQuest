package den.engine.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;


import den.engine.game.GameHandler;

public class MouseManager extends GLFWCursorPosCallback  {
	private GameHandler game;
	@SuppressWarnings("unused")
	private KeyBindings kb;
	
	public MouseManager(GameHandler game){
		this.game = game;
		
		setKeys();
	}
	
	public void applyNewKeyBinding(){
		setKeys();
	}

	private void setKeys() {
		// Read keybindings file and map keys to actions
		kb = new KeyBindings();
	}

	@Override
	public void invoke(long window, double xpos, double ypos)  {
		InputState pis = game.getPlayerInputState();
		pis.setAimX(xpos);
		pis.setAimY(ypos);
	}
}
