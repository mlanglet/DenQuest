package den.engine.input;

public class InputState {
	private boolean forward, left, right, backward, jump, doubleJump, mouse1, mouse2, mouse3, nextmap;
	private double aimX, aimY;
	private boolean paused;
	
	public void setShooting(boolean b){
		mouse1 = b;
	}
	
	public void setForward(boolean b) {
		forward = b;
	}
	public void setLeft(boolean b) {
		left = b;
	}
	public void setRight(boolean b) {
		right = b;
	}
	public void setBackward(boolean b) {
		backward = b;
	}
	public void setJump(boolean b) {
		jump = b;
	}
	public void setDoubleJump(boolean b) {
		doubleJump = b;
	}
	
	public boolean getShooting(){
		return mouse1;
	}
	
	public boolean getForward() {
		return forward;
	}
	public boolean getLeft() {
		return left;
	}
	public boolean getRight() {
		return right;
	}
	public boolean getBackward() {
		return backward;
	}
	public boolean getJump() {
		return jump;
	}
	public boolean getDoubleJump() {
		return doubleJump;
	}
	
	@Override
	public String toString(){
		return new String("forward: " + forward
						 +"| left:" + left
						 +"| backward:" + backward 
						 +"| right:" + right
						 +"| jump:" + jump
						 +"| doubleJump:" + doubleJump);
						 
						 
	}

	public boolean getMouse2() {
		return mouse2;
	}

	public void setMouse2(boolean mouse2) {
		this.mouse2 = mouse2;
	}

	public boolean getMouse3() {
		return mouse3;
	}

	public void setMouse3(boolean mouse3) {
		this.mouse3 = mouse3;
	}

	public double getAimX() {
		return aimX;
	}

	public void setAimX(double aimX) {
		this.aimX = aimX;
	}

	public double getAimY() {
		return aimY;
	}

	public void setAimY(double aimY) {
		this.aimY = aimY;
	}

	public boolean getNextmap() {
		return nextmap;
	}

	public void setNextmap(boolean nextmap) {
		this.nextmap = nextmap;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

}
