package den.graphics.ui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import den.engine.game.GameHandler;
import den.engine.graphics.Drawable;
import den.engine.render.Display;
import den.engine.utility.Vector2D;

public abstract class UIElement extends Drawable {
	
	protected GameHandler game;
	
	public UIElement(GameHandler g){
		super(g.getResourceManager());
		game = g;
		position = new Vector2D(0,0);
		layer = 10;
		g.addUIElement(this);
	}
	
	// Shape
	// Text	
	
	public Vector2D getPosition(){
	  return position;
	}
	
	public void setPostion(int x, int y){
		position.setX(x);
		position.setY(y);
	}
	
	@Override
	public void draw() {
		Vector2D unitPos = new Vector2D(getPosition());
		
		GL20.glUseProgram(glProgramHandle);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, glVBufferHandle);
		
		int glUnitPosHandle = GL20.glGetUniformLocation(glProgramHandle, "unitPos");
		GL20.glUniform2f(glUnitPosHandle, unitPos.getX(), unitPos.getY());
		
		int glScaleHandle = GL20.glGetUniformLocation(glProgramHandle, "scale");
		GL20.glUniform1f(glScaleHandle, scale);
		
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
}
