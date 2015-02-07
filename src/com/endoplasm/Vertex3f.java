package com.endoplasm;

public class Vertex3f {
	
	// float array of the values, value[0] = x, value[1] = y, value[2] = z
	private float[] value = new float[3];
	
	public Vertex3f(float[] value){
		this.value[0] = value[0];
		this.value[1] = value[1];
		this.value[2] = value[2];
	}
	
	public Vertex3f(float x, float y, float z){
		this.value[0] = x;
		this.value[1] = y;
		this.value[2] = z;
	}
	
	public Vertex3f clone(){
		return new Vertex3f(value[0],value[1],value[2]);
	}
	
	public float getX(){
		return value[0];
	}
	
	public float getY(){
		return value[1];
	}
	public float getZ(){
		return value[2];
	}
	
	public void setX(float x){
		value[0] = x;
	}
	
	public void setY(float y){
		value[1] = y;
	}
	
	public void setZ(float z){
		value[2] = z;
	}
	
	public void addX(float x){
		value[0] += x;
	}
	
	public void addY(float y){
		value[1] += y;
	}
	
	public void addZ(float z){
		value[2] += z;
	}

}
