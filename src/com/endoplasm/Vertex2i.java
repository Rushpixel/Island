package com.endoplasm;

/**
 * Copyright EndoPlasm Gaming ©2013
 * Edited 14/12/2013
 */
public class Vertex2i {
	
	private int[] value = new int[2];
	
	public Vertex2i(int[] value){
		this.value[0] = value[0];
		this.value[1] = value[1];
	}
	
	public int getX(){
		return value[0];
	}
	
	public int getY(){
		return value[1];
	}
	
	public void setX(int x){
		value[0] = x;
	}
	
	public void setY(int y){
		value[1] = y;
	}

}
