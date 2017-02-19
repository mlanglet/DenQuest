package den.engine.game.items;

import den.engine.game.GameHandler;
import den.graphics.ui.UIElement;

public class InventoryItem extends UIElement {
	
	Item parent;

	public InventoryItem(GameHandler g, Item parent) {
		super(g);
		
		this.parent = parent;
	}

	@Override
	public void draw() {
	}

	@Override
	public void update(float delta) {
	}

	@Override
	protected void init() {
	}
}
