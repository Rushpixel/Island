package com.endoplasm;

public class RectangleMath {
	
	/**
	 * @param pos The point you want to check
	 * @param a The lower left corner of the box.
	 * @param b The upper right corner of the box.
	 * @return returns true if pos is contained by the rectangle defined between a and b
	 */
	public static boolean doesContain(Vertex2f pos, Vertex2f a, Vertex2f b){
		if(pos.getX() < a.getX()) return false;
		if(pos.getY() < a.getY()) return false;
		if(pos.getX() > b.getX()) return false;
		if(pos.getY() > b.getY()) return false;
		return true;
	}
	
	/**
	 * Used to tell if 2 boxes intersect
	 * @param a1 Fist boxes lower left corner
	 * @param a2 First boxes upper right corner
	 * @param b1 Second boxes lower left corner
	 * @param b2 Second boxes upper right corner
	 * @return Whether or not box1 and box2 intersect assuming box1 and box2 are axis aligned.
	 */
	public static boolean doCollide(Vertex2f a1, Vertex2f a2, Vertex2f b1, Vertex2f b2){
		if(a1.getX() > b2.getX()) return false;
		if(a1.getY() > b2.getY()) return false;
		if(a2.getX() < b1.getX()) return false;
		if(a2.getY() < b1.getY()) return false;
		return true;
	}
	
	/**
	 * Return the area of a rectangle defined by ab
	 * @param a the bottom left corner of the rectangle
	 * @param b the top right corner of the rectangle 
	 * @return The area of the box defined by a and b
	 */
	public static float getArea(Vertex2f a, Vertex2f b){
		float width = b.getX() - a.getX();
		float height = b.getY() - a.getY();
		return width * height;
	}

}
