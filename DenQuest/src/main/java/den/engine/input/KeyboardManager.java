package den.engine.input;

import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.*;
import den.engine.game.GameHandler;

public class KeyboardManager extends GLFWKeyCallback {
	private GameHandler game;
	@SuppressWarnings("unused")
	private KeyBindings kb;
	
	public KeyboardManager(GameHandler game){
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
	public void invoke(long window, int key, int scancode, int action, int mods) {
		InputState pis = game.getPlayerInputState();
		
		if ( key == GLFW_KEY_F4 && mods == GLFW_MOD_ALT && action == GLFW_RELEASE )
			glfwSetWindowShouldClose(window, true);
	
		// Game controls
		switch(key){
		case GLFW_KEY_ESCAPE:
			switch(action){
			case GLFW_PRESS:
				pis.setPaused(true);
				break;		
			case GLFW_REPEAT:
				pis.setPaused(false);
				break;	
			}
			
			break;
		case GLFW_KEY_W:		
			switch(action){
			case GLFW_PRESS:
				pis.setForward(true);
				break;
			case GLFW_RELEASE:
				pis.setForward(false);
				break;		
			}
			break;
		case GLFW_KEY_A:			
			switch(action){
			case GLFW_PRESS:
				pis.setLeft(true);
				break;
			case GLFW_RELEASE:
				pis.setLeft(false);
				break;		
			}
			break;
		case GLFW_KEY_S:			
			switch(action){
			case GLFW_PRESS:
				pis.setBackward(true);
				break;
			case GLFW_RELEASE:
				pis.setBackward(false);
				break;
			}
			break;
		case GLFW_KEY_D:			
			switch(action){
			case GLFW_PRESS:
				pis.setRight(true);
				break;
			case GLFW_RELEASE:
				pis.setRight(false);
				break;
			}
			break;
		case GLFW_KEY_SPACE:
			switch(action){
			case GLFW_PRESS:
				pis.setJump(true);
				break;
			case GLFW_REPEAT:
				pis.setDoubleJump(true);
				break;
			case GLFW_RELEASE:
				pis.setDoubleJump(false);
				pis.setJump(false);
				break;	
			}
			break;
		case GLFW_KEY_TAB:
			switch(action){
			case GLFW_RELEASE:
				pis.setNextmap(true);
				break;
			}
			break;
		}
	}
}
