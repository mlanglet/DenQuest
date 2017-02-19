package den.engine.graphics;

import den.engine.utility.Vector2D;

public class Texture {
	protected String textureSource;
	
	protected int textureType;
	protected int textureID;
	protected int width, height;
	protected int rows, cols;
	
	@Override
	public boolean equals(Object o){
		if(o==null) return false;
		if(!(o instanceof Texture))
			return false;
		
		Texture s = (Texture) o;
		if(this.textureSource.equals(s.getTextureSource()))
			return true;
		else
			return false;
	}
	
	public String getTextureSource(){
		return textureSource;
	}
	
	public int getTextureID(){
		return textureID;
	}
	
	public void setTextureDimension(Vector2D v){
		width = (int) v.getX();
		height = (int) v.getY();
	}
	
	public Vector2D getTextureDimension(){
		return new Vector2D(width, height);
	}
	
	public void setTextureSource(String s){
		textureSource = s;
	}
	
	public void setTextureID(int id){
		textureID = id;
	}
	
	public void setTextureType(int type){
		textureType = type;
	}
	
	public int getTextureType(){
		return textureType;
	}

	public void setTexturePartCount(Vector2D v) {
		rows = (int) v.getX();
		cols = (int) v.getY();
	}
	
	public Vector2D getTexturePartCount() {
		return new Vector2D(rows, cols);
	}
}
