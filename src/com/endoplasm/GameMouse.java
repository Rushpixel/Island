package com.endoplasm;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class GameMouse {
	
	public Vertex2f pos = new Vertex2f(Mouse.getX(), Mouse.getY());
	public Vertex2f lastPos = new Vertex2f(Mouse.getX(), Mouse.getY());
	public Vertex2f delta = new Vertex2f(0, 0);
	
	public void update(){
		try {
			Mouse.setNativeCursor(GUI.ActiveElement.getCursor());
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		lastPos = pos;
		pos = new Vertex2f(Mouse.getX() / (float)Display.getWidth() * Endogen.WIDTH, Mouse.getY() / (float)Display.getHeight() * Endogen.HEIGHT);
		delta = VectorMath.subtract(pos, lastPos);
	}
}
