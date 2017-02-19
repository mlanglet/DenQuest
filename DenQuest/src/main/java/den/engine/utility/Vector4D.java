package den.engine.utility;



public class Vector4D {
	private float w,x,y,z;

	public Vector4D(){
		this.w = 0;
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}	
	
	public Vector4D(float x, float y){
		this.x = x;
		this.y = y;	    
	}
	
	public Vector4D(Vector4D v){
		this.w = v.w;
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public Vector4D(float w, float x, float y, float z){
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float getW(){
		return w;
	}
	
	public void setW(float w){
		this.w = w;
	}
	
	public float getX(){
		return x;
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	public float getY(){
		return y;
	}
	
	public void setY(float y){
		this.y = y;
	}
	
	public float getZ(){
		return z;
	}
	
	public void setZ(float z){
		this.z = z;
	}
	
	public String toString(){
		return "(" + w + ", " + x + ", " +  y + ", " + z + ")";
	}
}
