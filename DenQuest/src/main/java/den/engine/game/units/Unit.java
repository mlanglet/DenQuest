package den.engine.game.units;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import den.engine.game.GameHandler;
import den.engine.game.GameEntity;
import den.engine.graphics.Shader;
import den.engine.graphics.UnitFragmentShader;
import den.engine.graphics.UnitVertexShader;
import den.engine.render.Display;
import den.engine.utility.Vector2D;
import den.engine.utility.Vector4D;
import den.engine.utility.Constants.*;


public abstract class Unit extends GameEntity {
	protected DIRECTION direction;
	protected Vector2D velocityVector;
	protected float magicResistance = 1f; // 0%
	protected float armor = 1f; // 0%
	protected boolean alive = true;
	protected int damage = 0;
	protected int speed = 0;
	protected int health = 0;
	
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public float getArmor() {
		return armor;
	}

	public void setArmor(float armor) {
		this.armor = armor;
	}
	
	public float getMagicResistance() {
		return magicResistance;
	}

	public void setMagicResistance(float magicResistance) {
		this.magicResistance = magicResistance;
	}

	public Unit(GameHandler g, DIRECTION dir) {
		super(g, dir);
		direction = dir;
	}

	public void applyDamage(int damage, DAMAGE_TYPE type){
		int final_damage = 0;
		switch(type){
		case PHYSICAL_MELEE:
			final_damage = (int) (damage * armor);
			break;
		case PHYSICAL:
			final_damage = damage;
			break;
		case MAGIC:
			final_damage = (int) (damage * magicResistance);
			break;
		case HP_REMOVAL:
			final_damage = damage;
			break;
		default:
			break;
		}
		
		health -= final_damage;
		
		if(health <= 0){
			die(toString() + " died: " + damage + type + " damage." );
		}
	}
	
	public void applyHealing(int healing){
		health += healing;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	@Override
	protected void init(){ 		   
	    shaders = new ArrayList<Shader>();
	    shaders.add(new UnitVertexShader());
	    shaders.add(new UnitFragmentShader());
	}
	
	@Override
	public void draw() {      
		Vector2D unitPos = new Vector2D(getPosition());
		Vector4D rotationVector = getRotation();
		
		GL20.glUseProgram(glProgramHandle);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, glVBufferHandle);
		
		int glUnitPosHandle = GL20.glGetUniformLocation(glProgramHandle, "unitPos");
		GL20.glUniform2f(glUnitPosHandle, unitPos.getX(), unitPos.getY());
		
		int glScaleHandle = GL20.glGetUniformLocation(glProgramHandle, "scale");
		GL20.glUniform1f(glScaleHandle, scale);
		
		int glRotateHandle = GL20.glGetUniformLocation(glProgramHandle, "rotate");
		GL20.glUniform4f(glRotateHandle, rotationVector.getW(), rotationVector.getX(), rotationVector.getY(), rotationVector.getZ());
		
		int glTexturePositionHandle = GL20.glGetUniformLocation(glProgramHandle, "texture_offset");
		GL20.glUniform2f(glTexturePositionHandle, getTextureOffset().getX(), getTextureOffset().getY());
		
		int glResolutionHandle = GL20.glGetUniformLocation(glProgramHandle, "resolution");
		GL20.glUniform2f(glResolutionHandle, (float) Display.WIDTH, (float) Display.HEIGHT);
		
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
        int glTextureSamplerHandle = GL20.glGetUniformLocation(glProgramHandle, "tex");
        GL20.glUniform1i(glTextureSamplerHandle, GL13.GL_TEXTURE0);
		
        int in_vertex = GL20.glGetAttribLocation(glProgramHandle, "in_vertex");
		GL20.glEnableVertexAttribArray(in_vertex);
		GL20.glVertexAttribPointer(in_vertex, 2, GL11.GL_FLOAT, false, 16, 0);
		
		int in_tex_coord = GL20.glGetAttribLocation(glProgramHandle, "in_tex_coord");
		GL20.glEnableVertexAttribArray(in_tex_coord);
		GL20.glVertexAttribPointer(in_tex_coord, 2, GL11.GL_FLOAT, false, 16, 8);

		//GL11.GL_TRIANGLES GL11.GL_TRIANGLE_STRIP GL11.GL_TRIANGLE_FAN  GL11.GL_LINES GL11.GL_LINE_STRIP GL11.GL_LINE_LOOP GL11.GL_POINTS
        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);

		GL20.glDisableVertexAttribArray(in_vertex);
		GL20.glDisableVertexAttribArray(in_tex_coord);

		GL20.glUseProgram(0);
	}

	private Vector4D getRotation() {
		return new Vector4D(0f, 0f, 0f, 1f);
	}
}
