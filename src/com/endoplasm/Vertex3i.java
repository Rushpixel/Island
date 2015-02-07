package com.endoplasm;

public class Vertex3i {

private int[] value = new int[3];
	
	public Vertex3i(int[] value){
		this.value[0] = value[0];
		this.value[1] = value[1];
		this.value[2] = value[2];
	}
	
	public Vertex3i(int x, int y, int z) {
		this.value[0] = x;
		this.value[1] = y;
		this.value[2] = z;
	}

	public int getX(){
		return value[0];
	}
	
	public int getY(){
		return value[1];
	}
	public int getZ(){
		return value[2];
	}
	
	public void setX(int x){
		value[0] = x;
	}
	
	public void setY(int y){
		value[1] = y;
	}
	
	public void setZ(int z){
		value[2] = z;
	}
	
}
