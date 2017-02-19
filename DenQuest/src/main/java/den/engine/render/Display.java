package den.engine.render;

import den.engine.game.GameHandler;
import den.engine.graphics.Drawable;
import den.engine.input.KeyboardManager;
import den.engine.input.MouseManager;
import den.engine.utility.Vector2D;

import java.util.ArrayList;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
 
public class Display {
 
    public static int WIDTH = 800;
    public static int HEIGHT = 600;
 
    // The window handle
    private long window;
    
    // Handles the GLFWKeyCallback interface to populate the input data
    KeyboardManager keyboardInputManager;
    MouseManager mouseInputManager;  
    
    // The Game
    GameHandler game;
 
    public void run() {
        try {
            init();           
            loop();
 
            glfwFreeCallbacks(window);
            glfwDestroyWindow(window);

        } catch(Exception e){  
        	e.printStackTrace();
        } finally {
    		glfwTerminate();
    		glfwSetErrorCallback(null).free();
        }
    }

	private void init() {   	
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();     
 
		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");
 
        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE); // the window will be resizable
 
        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "DenQuest!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");
 
		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically        
        
        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);
 
        // Make the window visible
        glfwShowWindow(window);
        
        glEnable(GL_BLEND);
        glDisable(GL_ALPHA_TEST);
        glBlendFunc (GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
    	game = new GameHandler();
    	keyboardInputManager = new KeyboardManager(game);
    	mouseInputManager = new MouseManager(game);
    	
        // Setup a key callback. Invoked by the key polling the main loop
        glfwSetKeyCallback(window, keyboardInputManager);
        glfwSetCursorPosCallback(window, mouseInputManager);

    }
 
    private void loop() {
    	float now = 0, last = 0, delta = 0;
    	
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();    	
    	 
        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
 
        // Run the game loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
		while ( !glfwWindowShouldClose(window) ) {
            now = (float) glfwGetTime();
            delta = now - last;
            last = now;
        	
            input(); 
            
            game.update(delta); 
            System.out.println(delta);
            
            render(window);
        }
    }
    
    private void input(){
        glfwPollEvents(); // Populate input object
		if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS){
			game.getPlayerInputState().setShooting(true);
		} 

		if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == GLFW_RELEASE){
			game.getPlayerInputState().setShooting(false);
		} 

    }
    
	private void render(long window){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the frame buffer
		ArrayList<Drawable> drawables;
		
		drawables = game.getVisibleDrawables();
		drawables.sort(null); // Sort on layer
		
		for(Drawable d : drawables){
			d.draw();
		}
		
		glfwSwapBuffers(window); // swap the colour buffers
	}
 
	public static Vector2D translatePosition(Vector2D originalPos){
		Vector2D translatedPos = new Vector2D();
		
		float OldMaxX = WIDTH, OldMinX = 0, NewMaxX = 1, NewMinX = -1;
		float OldMaxY = HEIGHT, OldMinY = 0, NewMaxY = 1, NewMinY = -1;
		float OldRangeX = (OldMaxX - OldMinX), NewRangeX = (NewMaxX - NewMinX);  
		float OldRangeY = (OldMaxY - OldMinY), NewRangeY = (NewMaxY - NewMinY);  
		
		translatedPos.setX((((originalPos.getX() - OldMinX) * NewRangeX) / OldRangeX) + NewMinX);
		translatedPos.setY((((originalPos.getY() - OldMinY) * NewRangeY) / OldRangeY) + NewMinY);	
			
		return translatedPos;
	}
	
    public static void main(String[] args) {
//    	Runtime rt = Runtime.getRuntime();
//    	System.out.println("Available Free Memory: " + rt.freeMemory());
        new Display().run();
//        System.out.println("Free Memory before call to gc(): " +
//        		rt.freeMemory());
//        		System.runFinalization();
//        		System.gc();
//        		System.out.println(" Free Memory after call to gc(): " +
//        		rt.freeMemory()); 
    }
}