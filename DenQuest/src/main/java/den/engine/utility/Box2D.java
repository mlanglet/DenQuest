package den.engine.utility;


public class Box2D {
	private int x,y,height,width;
	
	public Box2D(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		width = w;
		height = h;
	}
	
	public Box2D(Vector2D pos, int w, int h){
		x = (int) pos.getX();
		y = (int) pos.getY();
		width = w;
		height = h;
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setPosition(Vector2D v){
		this.x = (int) v.getX();
		this.y = (int) v.getY();
	}
	
	public boolean isWithin(Vector2D p){
		return isWithin(p.getX(), p.getY());
	}
	
	public boolean isWithin(float f, float g){
		return (f >= this.x && f <= (this.x + width) &&
				g >= this.y && g <= (this.y + height));
	}

	public boolean contains(Box2D box) {
		for(int i = this.x; i < (x + this.width); i++){
			for(int j = this.y; j < (y + this.height); j++){
				if(box.isWithin(new Vector2D(i, j))){
					return true;
				}
			}			
		}
		return false;
	}
	
	@Override
	public String toString(){ 
		return "Box2D: x = " + x + " y = " + y + " width = " + width + " height = " + height; 
	}
}
