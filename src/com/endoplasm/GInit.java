package com.endoplasm;

public abstract class GInit {
	
	/**
	 * The init command is called to initialis the game before the game loop.
	 * Use this instead of the constructor, the constructor is called before the system inits.
	 */
	public abstract void init();
	
	/**
	 * The cleanup code is called before the system cleanup when the program is closed.
	 * Some uncaught exceptions can cause the program to close without calling this method.
	 */
	public abstract void cleanup();
	
	/**
	 * The update is called 60 times per second by the System Manager
	 */
	public abstract void update();
	
	/**
	 * The render is normally called 60 times per second by the System Manager
	 * The render method will be called less if the computer is struggling to keep up
	 * This is the first thing to be rendered per second
	 */
	public abstract void render3D();
	
	/**
	 * This class is used to render 2d aspects of the game.
	 * It is rendered ontop of the 3d call, but behind the System GUI layer
	 */
	public abstract void render2D();
	
	/**
	 * This method tells the System a vertex shader to use, default is null.
	 * @return String path to vertex shader file (.shader);
	 */
	public String getVertexShaderDir(){
		return null;
	}
	
	/**
	 * This method tells the System a fragment shader to use, default is null.
	 * @return String path to vertex fragment file (.fragment);
	 */
	public String getFragmentShaderDir(){
		return null;
	}

}
