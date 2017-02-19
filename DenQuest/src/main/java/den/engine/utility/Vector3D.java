package den.engine.utility;

public class Vector3D {
	private float x,y,z;

	public Vector3D(){
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}	
	
	public Vector3D(Vector3D v){
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public Vector3D(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
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
		return "(" + x + ", " +  y + ", " + z + ")";
	}
}
