package com.endoplasm;

import java.util.ArrayList;

import org.lwjgl.input.Cursor;

public abstract class GUIElement {
	
	/**
	 * The position of the GUIElement's top left corner.
	 * A GUIElement's position is relative to it's parent's top left corner.
	 */
	protected Vertex2f pos = new Vertex2f(0, 0);
	
	public GUIElement parent;
	public ArrayList<GUIElement> children;
	
	
	public GUIElement(GUIElement parent){
		this.parent = parent;
		children = new ArrayList<GUIElement>();
	}

	public abstract boolean doesContain(Vertex2f parentOffset);
	public abstract Cursor getCursor();
	public abstract void render();
	public abstract void update();

	public Vertex2f getPos() {
		return pos;
	}

	public void setPos(Vertex2f pos) {
		this.pos = pos;
	}
}
