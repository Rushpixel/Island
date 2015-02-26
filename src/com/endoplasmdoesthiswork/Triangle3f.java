package com.endoplasmdoesthiswork;

import com.endoplasm.MathUtil;
import com.endoplasm.Vertex2f;

public class Triangle3f {
	
	public Vertex2f pos;
	// a and b are used to make up the triangles AABB
	public Vertex2f a; //lowest x/y values for the triangle
	public Vertex2f b; //highest x/y values for the triangle
	
	public Vertex2f c1;
	public Vertex2f c2;
	public Vertex2f c3;
	
	public Triangle3f(Vertex2f c1, Vertex2f c2, Vertex2f c3){
		this.c1 = new Vertex2f(c1.getX(), c1.getY());
		this.c2 = new Vertex2f(c2.getX(), c2.getY());
		this.c3 = new Vertex2f(c3.getX(), c3.getY());
		calcPosRange();
	}
	
	public void calcPosRange(){
		pos = new Vertex2f(0, 0);
		pos.setX((c1.getX() + c2.getX() + c3.getX()) / 3);
		pos.setY((c1.getX() + c2.getX() + c3.getX()) / 3);
		float[] dists = new float[]{
				MathUtil.distance(pos.getX(), pos.getY(), c1.getX(), c1.getY()),
				MathUtil.distance(pos.getX(), pos.getY(), c2.getX(), c2.getY()),
				MathUtil.distance(pos.getX(), pos.getY(), c3.getX(), c3.getY()),
		};
		a = new Vertex2f(0,0);
		a.setX(MathUtil.getMin(new float[]{c1.getX(), c2.getX(), c3.getX()}));
		a.setY(MathUtil.getMin(new float[]{c1.getY(), c2.getY(), c3.getY()}));
		b = new Vertex2f(0,0);
		b.setX(MathUtil.getMax(new float[]{c1.getX(), c2.getX(), c3.getX()}));
		b.setY(MathUtil.getMax(new float[]{c1.getY(), c2.getY(), c3.getY()}));
	}
	
	public float getArea(){
		float s1 = MathUtil.distance(c1.getX(), c1.getY(), c2.getX(), c2.getY());
		float s2 = MathUtil.distance(c2.getX(), c2.getY(), c3.getX(), c3.getY());
		float s3 = MathUtil.distance(c3.getX(), c3.getY(), c1.getX(), c1.getY());
		float s = (s1 + s2 + s3) / 2;
		return (float) Math.sqrt(s * (s - s1) * (s - s2) * (s - s3));
	}

}
