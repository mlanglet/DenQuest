package den.engine.graphics;

import java.util.ArrayList;

import den.engine.utility.Vector2D;

public abstract class Drawable implements Comparable<Drawable> {
	
	protected Vector2D position;
	protected int layer;
	protected boolean onScreen;
	protected float scale = 1f;
	
	protected ArrayList<Shader> shaders;
	protected Texture texture;
	protected float[] vertexPositionData;
	protected float[] vertexTextureCoordinateData;
	protected Vector2D texturePosition;
	
	protected int glProgramHandle;
	protected int glVBufferHandle;
	protected int glTBufferHandle;
	
	protected ResourceManager rm;
	
	public Drawable(ResourceManager rm){
		this.rm = rm;
		
		shaders = new ArrayList<Shader>();
		texturePosition = new Vector2D(0, 0);
	}
	
	public Vector2D getPosition(){ 
		return position; 
	}
	
	public void setPosition(Vector2D pos){ 
		this.position = pos;
	}
	
	public void setPosition(float x, float y){ 
		if(getPosition() != null){
			this.position.setX(x);
			this.position.setY(y);
		} else {
			this.position = new Vector2D(x, y);
		}			
	}
	
	public int getLayer(){ 
		return layer; 
	}
	
	public void setLayer(int layer){
		this.layer = layer; 
	}
	
	public float getScale(){
		return scale;
	}
	
	public void setScale(float scale){
		this.scale = scale;
	}
	
	public boolean isVisible() { 
		return onScreen; 
	}
	
	public void setVisible(boolean b) { 
		onScreen = b; 
	}
	
	public ArrayList<Shader> getShaders(){ 
		return shaders; 
	}

	public float[] getVertexData() {
		return vertexPositionData;
	}	
	
	public float[] getVertexTextureData() {
		return vertexTextureCoordinateData;
	}	
	
	public Texture getTexture() {
		return texture;
	}	
	
	public Vector2D getTextureOffset(){
		Vector2D offset = new Vector2D();
		offset.setX((float) ((1.0 / texture.getTexturePartCount().getX()) * texturePosition.getX()));
		offset.setY((float) ((1.0 / texture.getTexturePartCount().getY()) * texturePosition.getY()));
		return offset;
	}
	
	public Vector2D getTexturePosition() {
		return texturePosition;
	}
	
	public void setTexturePosition(Vector2D v) {
		texturePosition = v;
	}
	
	public void setTexturePosition(float x, float y) {
		if(getTexturePosition() != null){
			this.texturePosition.setX(x);
			this.texturePosition.setY(y);
		} else {
			this.texturePosition = new Vector2D(x, y);
		}		
	}
	
	public int compareTo(Drawable d){
		if(this.layer > d.layer)
			return 1;
		else if(this.layer == d.layer)
			return 0;
		else
			return -1;
	}

	public void setVBufferID(int id) {
		glVBufferHandle = id;
	}
	
	public void setTBufferID(int id) {
		glTBufferHandle = id;
	}
	
	public void setShaderProgramID(int shader){
		glProgramHandle = shader;
	}
	
	protected abstract void init();
	public abstract void draw();
	
	/**
	 * Called for each frame
	 * @param delta is the number of seconds that has passed since the last frame
	 */
	public abstract void update(float delta);
}
