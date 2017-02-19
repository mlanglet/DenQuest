package den.engine.game;

import java.util.ArrayList;

import den.engine.game.parts.Background;
import den.engine.game.parts.Door;
import den.engine.game.parts.Tile;
import den.engine.game.units.Player;
import den.engine.graphics.Drawable;
import den.engine.graphics.ResourceManager;
import den.engine.input.InputState;
import den.engine.utility.Constants;
import den.engine.utility.Constants.SCENE;
import den.graphics.ui.HealthBar;
import den.graphics.ui.UIElement;


public class GameHandler {
	
	private ResourceManager resourceManager;
	
	private ArrayList<UIElement> UIItems;
	private ArrayList<GameEntity> gameEntities;	
	private ArrayList<Drawable> drawable;
	private ArrayList<Drawable> newDrawables;
	private ArrayList<Drawable> deletedDrawables;
	private MapHandler mapHandler;
	private GameRoom currentRoom;
	private SCENE scene;
	private Player player;
	private InputState inputState;
	
	public GameHandler(){
		gameEntities = new ArrayList<GameEntity>();
		UIItems = new ArrayList<UIElement>();
		drawable = new ArrayList<Drawable>();
		newDrawables = new ArrayList<Drawable>();
		deletedDrawables = new ArrayList<Drawable>();
		resourceManager = new ResourceManager(this);
		inputState = new InputState();
		mapHandler = new MapHandler();
		// Game starts in the main menu
		setScene(SCENE.MAIN_MENU);		
	}
	
	private void setScene(SCENE scene){
		//System.out.println("GAME: Setting scene " + scene);
		inputState.setNextmap(false);
		this.scene = scene;
		// Load shades and textures for this level	
		
		
		if(SCENE.MAIN_MENU != scene){
			createWorld();	
			createUI();
		} else
			new Background(this);
		
		setDrawables();
		resourceManager.loadScene(scene); 
	}
	
	private void setRoom(){
		for(Drawable d : drawable){
			//System.out.println("GAME: Adding delete event for drawable" + d.toString());
			deletedDrawables.add(d);
		}
		addDrawables();
	}

	private void createUI() {
		UIItems.clear();
		new HealthBar(this);
		//new AmmoCounter();
		//new Inventory();
		//new ();
		//new ();
	}

	public MapHandler getMap() {
		return mapHandler;
	}

	public void setMap(MapHandler mapHandler) {
		this.mapHandler = mapHandler;
	}

	private void createWorld(){
		gameEntities.clear();
		new Background(this);
		if(player == null || scene == SCENE.LEVEL_1 && currentRoom == null)
			player = new Player(this, inputState);
		else {
			addGameEntity(player);
		}
		
		// Create first room
		createRoom(null);
	}
	

	private void loadRoom(GameRoom room) {
		currentRoom = room;
		for(GameEntity e : room.getParts()){
			gameEntities.add(e);
		}
	}

	private void createRoom(Door door) {
		if(door == null){
			currentRoom = new GameRoom(this, null, null);
		} else {
			currentRoom = new GameRoom(this, door, door.direction);
		}
	}

	public ResourceManager getResourceManager(){
		return resourceManager;
	}
	
	public SCENE getScene(){
		return scene;
	}
	
	public void addGameEntity(GameEntity ge){
		gameEntities.add(ge);
	}
	
	public void removeGameEntity(GameEntity ge){
		this.deletedDrawables.add(ge);
		gameEntities.remove(ge);
	}
	
	public void addUIElement(UIElement uie){
		UIItems.add(uie);
	}
	
	public void removeUIElement(UIElement uie){
		UIItems.remove(uie);
	}
	
	public void addNewDrawable(Drawable newDrawable) {
		this.newDrawables.add(newDrawable);
	}
	
	public void removeDrawable(Drawable deletedDrawable) {
		this.deletedDrawables.add(deletedDrawable);
		if(deletedDrawable instanceof GameEntity)
			removeGameEntity((GameEntity)deletedDrawable);
	}

	public void update(float delta){
		if(inputState.getNextmap()){
			if(scene.ordinal() >= SCENE.values().length - 2) setScene(SCENE.MAIN_MENU);
			else setScene(SCENE.values()[scene.ordinal()+1]);
		}
		
		for(Drawable d : drawable){
			if(d instanceof Tile) continue;
			
			d.update(delta);
		}
			
		for(Drawable d : newDrawables){
			drawable.add(d);
		}
		
		for(Drawable d : deletedDrawables){
			//System.out.println("Deleting: " + d);
			drawable.remove(d);
		}
		
		newDrawables.clear();
		deletedDrawables.clear();
	}
	
	public ArrayList<UIElement> getUIItems(){
		return UIItems;
	}
	
	public void setDrawables() {
		drawable.clear();
		for(GameEntity g : gameEntities){
			//System.out.println("Adding drawable: " + g.toString());
			drawable.add(g);
		}
		
		for(UIElement u : UIItems){
			drawable.add(u);
		}
	}
	
	public void addDrawables() {
		for(GameEntity g : gameEntities){
			//System.out.println("Adding new drawable: " + g.toString());
			resourceManager.loadNewDrawable(g);
		}
		
		for(UIElement u : UIItems){
			resourceManager.loadNewDrawable(u);
		}
	}

	public ArrayList<Drawable> getVisibleDrawables() {
		ArrayList<Drawable> visibleDrawables = new ArrayList<Drawable>();
		
		// Figure out which drawables should be drawn in this frame and return them
		for(GameEntity g : gameEntities)
			if(g.isVisible())
				visibleDrawables.add(g);
		
		for(UIElement u : UIItems){
			if(u.isVisible())
				visibleDrawables.add(u);
		}
		
		return visibleDrawables;
	}
	
	public ArrayList<Drawable> getAllDrawables() {
		return drawable;
	}

	public void pause() {
	}

	public Player getPlayer(){
		return player;
	}
	
	public InputState getPlayerInputState() {
		return inputState;
	}
	
	public ArrayList<GameEntity> checkCollision(GameEntity e1){	
		ArrayList<GameEntity> targets = new ArrayList<GameEntity>();
		for(GameEntity e : gameEntities){
			if(e == e1) continue;
			if(e.collisionBox.contains(e1.collisionBox)){
				targets.add(e);
			}
		}
		return targets;
	}

	public void takeDoor(Door door) {
		gameEntities.clear();
		if(player == null || scene == SCENE.LEVEL_1 && currentRoom == null)
			player = new Player(this, inputState);
		else {
			addGameEntity(player);
		}
		
		Door twin = null;
		if( (twin = currentRoom.getTwin(door)) != null ){
			loadRoom(twin.getRoom());
			//System.out.println("Room " + GameRoom.getRoomNumberCounter() + " Loaded.");
		} else {
			createRoom(door);
		} 
		
		setRoom();
		
		player.setPosition(currentRoom.getDoor(Constants.getOppositeDirection(door.direction)).getPosition());
	}
}
