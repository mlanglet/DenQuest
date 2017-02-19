package den.engine.graphics;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class Shader {
	protected String shaderName;
	protected String shaderSource;
	
	/* GL20.GL_VERTEX_SHADER
     * GL32.GL_GEOMETRY_SHADER
     * GL20.GL_FRAGMENT_SHADER
	 */
	protected int shaderType;
	protected int shaderID;
	
	@Override
	public boolean equals(Object o){
		if(o==null) return false;
		if(!(o instanceof Shader))
			return false;
		
		Shader s = (Shader) o;
		if(this.shaderName.equals(s.getShaderName()))
			return true;
		else
			return false;
	}
	
	public Shader(String name){
		shaderName = name;
		
		StringBuilder shaderSource = new StringBuilder();
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(("src/main/shaders/" + shaderName + ".glsl"))));
			String line;
			while((line = reader.readLine()) != null){
				shaderSource.append(line).append('\n');
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setShaderSource(shaderSource.toString());
	}
	
	public String getShaderName(){
		return shaderName;
	}
	
	public String getShaderSource(){
		return shaderSource;
	}
	
	public int getShaderID(){
		return shaderID;
	}
	
	public void setShaderSource(String s){
		shaderSource = s;
	}
	
	public void setShaderID(int id){
		shaderID = id;
	}
	
	public void setShaderType(int type){
		shaderType = type;
	}
	
	public int getShaderType(){
		return shaderType;
	}
}
