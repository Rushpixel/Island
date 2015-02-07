package com.endoplasm;

import org.lwjgl.input.Cursor;
import org.lwjgl.opengl.Display;

public class GUIField extends GUIElement{

	public GUIField(GUIElement parent) {
		super(parent);
		pos = new Vertex2f(0, 0);
	}

	@Override
	public boolean doesContain(Vertex2f parentOffset) {
		return true;
	}

	@Override
	public Cursor getCursor() {
		return Endogen.SystemAssets.mask.CURSORDEFAULT;
	}

	@Override
	public void render() {}

	@Override
	public void update() {}
	
	public Vertex2f getPos(){
		return new Vertex2f(0, 0);
	}

}
