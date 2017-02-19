package den.engine.graphics;

import org.lwjgl.opengl.GL20;

public class UnitFragmentShader extends Shader {
	public UnitFragmentShader() {
		super("UnitFragmentShader");
		
		setShaderType(GL20.GL_FRAGMENT_SHADER);
	}
}
