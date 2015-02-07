package com.endoplasm;

/**
 * Copyright EndoPlasm Gaming ©2013
 * Edited 14/12/2013
 */
public class Vertex2f implements Cloneable{
	
	// float array of the values, value[0] = x, value[1] = y
	private float[] value = new float[2];
	
	public Vertex2f(float[] value){
		this.value[0] = value[0];
		this.value[1] = value[1];
	}
	
	public Vertex2f(float x, float y){
		this.value[0] = x;
		this.value[1] = y;
	}
	
	public float getX(){
		return value[0];
	}
	
	public float getY(){
		return value[1];
	}
	
	public void setX(float x){
		value[0] = x;
	}
	
	public void setY(float y){
		value[1] = y;
	}
	
	public void addX(float x){
		value[0] = value[0] + x;
	}
	
	public void addY(float y){
		value[1] = value[1] + y;
	}

}
