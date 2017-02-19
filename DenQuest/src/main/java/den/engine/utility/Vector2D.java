package den.engine.utility;

public class Vector2D {
	private float x,y;

	public Vector2D(){
		this.x = 0;
		this.y = 0;
	}	
	
	public Vector2D(Vector2D v){
		this.x = v.x;
		this.y = v.y;	    
	}
	
	public Vector2D(float x, float y){
		this.x = x;
		this.y = y;	    
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
	
	/**
	 * Gets the length (aka magnitude or norm) ||v|| of vector v
	 * @param v 
	 * @return ||v|| = Math.sqrt(v.getX() + v.getY());
	 */
	public static float getLength(Vector2D v){
		return (float) Math.sqrt(v.getX() + v.getY());
	}
	
	public static float getAngleBetween(Vector2D source, Vector2D target){
		return (float) Math.atan2(target.getY() - source.getY() , target.getX() - source.getX());
	}
	
	/**
	 * normalizes the vector v
	 * @param v vector to nomalize
	 * @return GameVector2D(GameVector2D.scale(v, GameVector2D.getLength(v)));
	 */
	public static Vector2D normalize(Vector2D v){
		return new Vector2D(Vector2D.scale(v, Vector2D.getLength(v)));
	}
	
	/**
	 * Scales the vector v by scale
	 * @param v
	 * @param scale
	 * @return GameVector2D(v.x / scale, v.y / scale);
	 */
	public static Vector2D scale(Vector2D v, float scale){
		return new Vector2D(v.x / scale, v.y / scale);
	}
	
	/**
	 * Returns the scalar product of vector v1 and vector v2
	 * @param v1
	 * @param v2
	 * @return v1.x * v2.x + v1.y * v2.y
	 */
	public static float scalarProduct(Vector2D v1, Vector2D v2){
		return v1.x * v2.x + v1.y * v2.y;
	}
	
	/**
	 * returns a vector as a result of a sclar multiplication between v1 and scalar
	 * @param scalar
	 * @return GameVector2D(v.x * scalar, v.y * scalar);
	 */
	public static Vector2D multiplyScalar(Vector2D v, float scalar) {
		return new Vector2D(v.x * scalar, v.y * scalar);
	}
	
	/**
	 * Returns a vector equal to the sum of v1 and v2
	 * @param v1
	 * @param v2
	 * @return GameVector2D(v1.x + v2.x, v1.y + v2.y);
	 */
	public static Vector2D add(Vector2D v1, Vector2D v2){
		return new Vector2D(v1.x + v2.x, v1.y + v2.y);
	}
	
	/**
	 * Returns a vector equal to the subtraction of v1 and v2
	 * @param v1
	 * @param v2
	 * @return GameVector2D(v1.x - v2.x, v1.y - v2.y);
	 */
	public static Vector2D subtract(Vector2D v1, Vector2D v2){
		return new Vector2D(v1.x - v2.x, v1.y - v2.y);
	}
	
	/**
	 * Add vector v to this vector
	 * @param v 
	 */
	public void add(Vector2D v){
		this.x += v.x;
		this.y += v.y;
	}
	
	/**
	 * Subtract vector v from this vector
	 * @param v 
	 */
	public void subtract(Vector2D v){
		this.x -= v.x;
		this.y -= v.y;
	}

	/**
	 * Multiplies this vector with the scalar
	 * @param scalar
	 */
	public void multiplyScalar(float scalar) {
		this.x *= scalar;
		this.y *= scalar;
	}

	public String toString(){
		return "(" + x + ", " +  y + ")";
	}
}
