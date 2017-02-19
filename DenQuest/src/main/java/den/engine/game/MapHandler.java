package den.engine.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MapHandler {

	private Map curmap;
	private File mapFile;
	private ObjectInputStream mapReader;
	private ObjectOutputStream mapWriter;
	
	public MapHandler(){
		curmap = new Map();
	}
	
	public MapHandler(String map){
		loadMap(map);
	}
	
	public Map getCurmap() {
		return curmap;
	}

	public void setCurmap(Map curmap) {
		this.curmap = curmap;
	}
	
	public File getMapFile() {
		return mapFile;
	}

	public void setMapFile(File mapFile) {
		this.mapFile = mapFile;
	}
	
	public void addRoom(GameRoom r){
		curmap.addRoom(r);
	}
	
	public void loadMap(String saveGame){
		mapFile = new File("res/maps/" + saveGame);
		try {
			mapReader = new ObjectInputStream(new FileInputStream(mapFile));
		} catch (IOException e) {
			// Savegame not found!
			e.printStackTrace();
		}
		
		try {
			curmap = (Map) mapReader.readObject();
			mapReader.close();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveMap(){
		mapFile = new File("res/maps/" + curmap.hashCode());
		try {
			mapWriter = new ObjectOutputStream(new FileOutputStream(mapFile));
		} catch (IOException e) {
			// Savegame not found!
			e.printStackTrace();
		}
		
		try {
			mapWriter.writeObject(curmap);
			mapWriter.flush();
			mapWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
