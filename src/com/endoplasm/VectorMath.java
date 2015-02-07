package com.endoplasm;

public class VectorMath {
	
	public static Vertex2f subtract(Vertex2f a, Vertex2f b){
		return new Vertex2f(a.getX() - b.getX(), a.getY() - b.getY());
	}
	
	public static Vertex2f add(Vertex2f a, Vertex2f b){
		return new Vertex2f(a.getX() + b.getX(), a.getY() + b.getY());
	}
	
	public static Vertex2f divide(Vertex2f a, Vertex2f b){
		return new Vertex2f(a.getX() / b.getX(), a.getY() / b.getY());
	}
	
	public static Vertex2f multiply(Vertex2f a, Vertex2f b){
		return new Vertex2f(a.getX() * b.getX(), a.getY() * b.getY());
	}
	
	public static float Magnitude(Vertex2f v){
		return (float) Math.sqrt(v.getX() * v.getX() + v.getY() * v.getY());
	}
	
	public static float Distance(Vertex2f v1, Vertex2f v2){
		Vertex2f v = subtract(v2, v1);
		return Magnitude(v);
	}
	
	public static float direction(float x1, float y1, float x2, float y2) {
		double dx = x1 - x2;
		double dy = y2 - y1;

		double inRads = Math.atan2(dy, dx);

		if (inRads < 0)
			inRads = Math.abs(inRads);
		else
			inRads = 2 * Math.PI - inRads;
		return (float) Math.toDegrees(inRads);
	}
	
	public static Vertex3f subtract(Vertex3f a, Vertex3f b){
		return new Vertex3f(a.getX() - b.getX(), a.getY() - b.getY(), a.getZ() - b.getZ());
	}
	
	public static Vertex3f add(Vertex3f a, Vertex3f b){
		return new Vertex3f(a.getX() + b.getX(), a.getY() + b.getY(), a.getZ() + b.getZ());
	}
	
	public static Vertex3f divide(Vertex3f a, Vertex3f b){
		return new Vertex3f(a.getX() / b.getX(), a.getY() / b.getY(), a.getZ() / b.getZ());
	}
	
	public static Vertex3f multiply(Vertex3f a, Vertex3f b){
		return new Vertex3f(a.getX() * b.getX(), a.getY() * b.getY(), a.getZ() * b.getZ());
	}

}
