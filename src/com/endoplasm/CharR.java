package com.endoplasm;

import org.newdawn.slick.opengl.Texture;

public class CharR {
	
	public float u1;
	public float v1;
	public float u2;
	public float v2;
	
	public float widthFactor;
	
	public CharR(float u1, float v1, float u2, float v2, float widthFactor){
		this.u1 = u1;    
		this.v1 = v1;    
		this.u2 = u2;    
		this.v2 = v2;    
		this.widthFactor = widthFactor;
	}
	
	public void render(float x, float y, float width, float height, Texture tex, float r, float g, float b){
		//Shape.square(x, y, width, height, u1, v1, u2, v2, tex, r, g, b, 1);
	}

}
