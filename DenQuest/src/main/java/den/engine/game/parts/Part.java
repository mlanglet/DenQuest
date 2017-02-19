package den.engine.game.parts;

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

public abstract class Part extends GameEntity {
	public Part(GameHandler g, DIRECTION facing) {
		super(g, facing);
		
		scale = 1f;
	}
	
	protected void init(){
	    shaders = new ArrayList<Shader>();
	    shaders.add(new UnitVertexShader());
	    shaders.add(new UnitFragmentShader());
	}
	
	@Override
	public void draw() {      
		Vector2D unitPos = new Vector2D(getPosition());
		Vector4D rotationVector = new Vector4D(0f, 0f, 0f, 1f);
		
		GL20.glUseProgram(glProgramHandle);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, glVBufferHandle);
		
		int glUnitPosHandle = GL20.glGetUniformLocation(glProgramHandle, "unitPos");
		//unitPos = Display.translatePosition(new GameVector2D(unitPos.getX() + 400, unitPos.getY() + 300));
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
	
	
	
	
//	@Override
//	public void draw() {
//		
//		GL20.glUseProgram(glProgramHandle);
//        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, glVBufferHandle);		
//		
//        GL13.glActiveTexture(GL13.GL_TEXTURE0);
//        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
//        int glTextureSamplerHandle = GL20.glGetUniformLocation(glProgramHandle, "tex");
//        GL20.glUniform1i(glTextureSamplerHandle, GL13.GL_TEXTURE0);
//        
//        int in_vertex = GL20.glGetAttribLocation(glProgramHandle, "in_vertex");
//		GL20.glEnableVertexAttribArray(in_vertex);
//		GL20.glVertexAttribPointer(in_vertex, 2, GL11.GL_FLOAT, false, 16, 0);
//		
//		int in_tex_coord = GL20.glGetAttribLocation(glProgramHandle, "in_tex_coord");
//		GL20.glEnableVertexAttribArray(in_tex_coord);
//		GL20.glVertexAttribPointer(in_tex_coord, 2, GL11.GL_FLOAT, false, 16, 8);
//
//		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
//
//		GL20.glDisableVertexAttribArray(in_vertex);
//		GL20.glDisableVertexAttribArray(in_tex_coord);
//
//		GL20.glUseProgram(0);
//	}
}
