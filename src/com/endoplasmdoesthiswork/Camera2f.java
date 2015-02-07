package com.endoplasmdoesthiswork;

import org.lwjgl.opengl.GL11;

import com.endoplasm.Endogen;
import com.endoplasm.VectorMath;
import com.endoplasm.Vertex2f;

import static org.lwjgl.opengl.GL11.*;

public class Camera2f {
	
	public Vertex2f centre = new Vertex2f(0, 0);
	public float scale = 1;
	public float horSize = 350;
	public float verSize = 0;
	
	public static Vertex2f mousePos = new Vertex2f(0, 0);
	
	public void setCamera(){
		scale = Endogen.WIDTH / horSize;
		verSize = (Endogen.HEIGHT / scale);
		glScalef(scale, scale, 0);
		glTranslatef(-centre.getX() + horSize / 2, -centre.getY() + verSize / 2, 0);
	}
	
	public void updateMouse(){
		mousePos = VectorMath.divide(Endogen.mouse.pos, new Vertex2f(scale, scale));
		mousePos = VectorMath.add(mousePos, VectorMath.subtract(centre, new Vertex2f(horSize / 2, verSize / 2)));
	}

}
