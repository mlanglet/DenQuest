package den.engine.game.items;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import den.engine.game.*;
import den.engine.utility.Constants.DIRECTION;

public abstract class Item extends GameEntity {

	private InventoryItem inventoryItem;
	private boolean accessible = true;
	private float dropedTime; 
	
	public Item(GameHandler g, DIRECTION dir) {
		super(g, dir);
		inventoryItem = new InventoryItem(g, this);
	}

	@Override
	public void draw() {
		//
	}

	@Override
	public void update(float delta) {
		// Check state
		if(accessible){
		// Check collision and add itself to player
		    if(game.getPlayer().getCollisionBox().contains(collisionBox)){
		    	accessible = false;
				game.getPlayer().addItem(this);
				setVisible(false);
		    }
		// If item is not accessible and the time since items was dropped has been exceeded 
		} else if(!accessible && (glfwGetTime() - dropedTime) >= 2){
			accessible = true;
	 	}
	}

	public void drop() {
		dropedTime = (float) glfwGetTime();	
		setVisible(true);
		setPosition(game.getPlayer().getPosition());
		
		// Start blink animation
	}
	
	public InventoryItem getInventoryItem(){
		return inventoryItem;
	}
}
