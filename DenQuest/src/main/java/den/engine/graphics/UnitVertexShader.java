package den.engine.graphics;

import org.lwjgl.opengl.GL20;

public class UnitVertexShader extends Shader {
	public UnitVertexShader() {
		super("UnitVertexShader");
		
		setShaderType(GL20.GL_VERTEX_SHADER);
	}
}
