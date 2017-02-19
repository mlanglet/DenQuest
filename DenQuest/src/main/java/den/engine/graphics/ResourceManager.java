package den.engine.graphics;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
import den.engine.game.GameHandler;
import den.engine.utility.Vector2D;
import den.engine.utility.Constants.SCENE;

public class ResourceManager {
	private ArrayList<Shader> loadedShaders;
	private ArrayList<Texture> loadedTextures;
	private ArrayList<Drawable> drawable;
	private GameHandler game;
	
	public ResourceManager(GameHandler game){
		this.game = game;
		
		loadedShaders = new ArrayList<Shader>();
		loadedTextures = new ArrayList<Texture>();
		drawable = new ArrayList<Drawable>();
	}

	public void loadScene(SCENE scene) {
		//System.out.println("RM: LOADING SCENE " + scene);
		loadScreen();
		System.runFinalization();
		System.gc();
		
		drawable = game.getAllDrawables();
		
		// load drawables
		for(Drawable d : drawable){
			loadDrawable(d);
		}		
	} 
	
	private void loadScreen() {
		// TODO Auto-generated method stub
	}

	private void loadDrawable(Drawable d){
		//System.out.println("Loading drawable: " + d.toString());
		
		if(d.getVertexData() != null)
			createVertexBuffer(d);
		
		for(Shader s : d.getShaders()){
			if(loadedShaders.contains(s)){	
				// Shader is already loaded, get the handle to the shader
				s.setShaderID(loadedShaders.get(loadedShaders.indexOf(s)).getShaderID());
			} else {
				createShader(s);
				//System.out.println("RM: LOADING Shader " + s.toString());
				loadedShaders.add(s);
			}
		}
		
		Texture t = d.getTexture();
		
		if(loadedTextures.contains(t)){
			t.setTextureID((loadedTextures.get(loadedTextures.indexOf(t)).getTextureID()));
			t.setTextureDimension(loadedTextures.get(loadedTextures.indexOf(t)).getTextureDimension());
		} else {
			loadPNGTexture(t);
			loadedTextures.add(t);
		}
		
		createShaderProgram(d);
	}
	
	public void loadNewDrawable(Drawable d){
		if(d.getVertexData() != null)
			createVertexBuffer(d);
		
		for(Shader s : d.getShaders()){
			if(loadedShaders.contains(s)){	
				// Shader is already loaded, get the handle to the shader
				s.setShaderID(loadedShaders.get(loadedShaders.indexOf(s)).getShaderID());
			} else {
				createShader(s);
				loadedShaders.add(s);
			}
		}
		
		Texture t = d.getTexture();
		
		if(loadedTextures.contains(t)){
			t.setTextureID((loadedTextures.get(loadedTextures.indexOf(t)).getTextureID()));
		} else {
			loadPNGTexture(t);
			loadedTextures.add(t);
		}
		
		createShaderProgram(d);
		
		game.addNewDrawable(d);
	}
	
	
	private void createVertexBuffer(Drawable d) {
        FloatBuffer dataBuffer = BufferUtils.createFloatBuffer(d.getVertexData().length);//position at 0.
        dataBuffer.put(d.getVertexData());
        dataBuffer.flip();
        
        int buffer = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, buffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, dataBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        d.setVBufferID(buffer);
	}

	private void createShader(Shader shader){
        int shaderID = GL20.glCreateShader(shader.getShaderType());
        GL20.glShaderSource(shaderID, shader.getShaderSource());
        GL20.glCompileShader(shaderID);
        int status = GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS);
        if (status == GL11.GL_FALSE){
            
            String error=GL20.glGetShaderInfoLog(shaderID);
            
            String ShaderTypeString = null;
            switch(shader.getShaderType()){
            case GL20.GL_VERTEX_SHADER: ShaderTypeString = "vertex"; break;
            case GL32.GL_GEOMETRY_SHADER: ShaderTypeString = "geometry"; break;
            case GL20.GL_FRAGMENT_SHADER: ShaderTypeString = "fragment"; break;
            }
            
            System.err.println( "Compile failure in " + shader.shaderName + "\n%s\n"+ShaderTypeString+error);
        }
        shader.setShaderID(shaderID);
    }

	/**
	 * Create the shader program for this drawable and store the handle in the drawable
	 * @param d
	 */
	public void createShaderProgram(Drawable d) {
		ArrayList<Shader> shaders = d.getShaders();
		
        int program = GL20.glCreateProgram();
        for (int i = 0; i < shaders.size(); i++) {
            GL20.glAttachShader(program, shaders.get(i).getShaderID());
        }
        GL20.glLinkProgram(program);
        
        int status = GL20.glGetShaderi(program, GL20.GL_LINK_STATUS);
        if (status == GL11.GL_FALSE){
            String error=GL20.glGetProgramInfoLog(program);
            System.err.println( "Linker failure: %s\n"+error);
        }
        for (int i = 0; i < shaders.size(); i++) {
            GL20.glDetachShader(program, shaders.get(i).getShaderID());
        }
       
    	d.setShaderProgramID(program);
	}
	
    public int createByteTexture(int width, int height, ByteBuffer buffer, int format){
        int texId = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, format, width, height, 0, format, GL11.GL_UNSIGNED_BYTE, buffer);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST );
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST );
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        
        return texId;
   }
    
    public void loadPNGTexture(Texture source){
    	InputStream inputStream = null;
        
        try {
        	inputStream = new BufferedInputStream(new FileInputStream(source.getTextureSource()));
        } catch (IOException e) {
            e.printStackTrace();
        }    			
    	
        int width = 0;
        int height = 0;
        ByteBuffer buf = null;
        try {
            // Link the PNG decoder to this stream
            PNGDecoder decoder = new PNGDecoder(inputStream);
            width=decoder.getWidth();
            height=decoder.getHeight();
            
            source.setTextureDimension(new Vector2D(width, height));
             
            // Decode the PNG file in a ByteBuffer
            buf = BufferUtils.createByteBuffer(4 * width * height);
            decoder.decodeFlipped(buf, decoder.getWidth() * 4, Format.RGBA);
            
            //System.out.println("Loaded texture: " + source.getTextureSource());
            buf.flip();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        
    	try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        source.setTextureID(createByteTexture(width, height, buf, GL11.GL_RGBA));
    }
}
