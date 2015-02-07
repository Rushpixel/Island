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

}
