package com.endoplasm;

import static org.lwjgl.opengl.GL11.glColor4f;

import org.newdawn.slick.opengl.Texture;

public class Render {

	public static void setColor(float[] RGBA) {
		glColor4f(RGBA[0], RGBA[1], RGBA[2], RGBA[3]);
	}
	
	public static void setTexture(Texture texture) {
		texture.bind();
	}

}
