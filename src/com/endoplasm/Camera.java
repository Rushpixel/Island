package com.endoplasm;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.util.glu.GLU.gluLookAt;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import org.lwjgl.opengl.Display;

public class Camera {
	
	private Vertex3f pos1;
	private Vertex3f pos2;
	private Vertex3f up;

	private float FoV;
	private float aspect;
	private float near;
	private float far;

	public Camera() {
		pos1 = new Vertex3f(0, 0, 0);
		pos2 = new Vertex3f(0, 1, 0);
		up = new Vertex3f(0, 0, 1);

		this.FoV = 60;
		this.aspect = Display.getWidth() / (float)Display.getHeight();
		this.near = -20f;
		this.far = 100000;

		initProjection();
	}

	public void initProjection() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		setCamera();

		glMatrixMode(GL_MODELVIEW);

		glEnable(GL_DEPTH_TEST);
		// glEnable(GL_TEXTURE_2D);
		// glEnable(GL_CULL_FACE);
		// glCullFace(GL_BACK);

		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	/**
	 * Use this to update the camera's FOV, aspect ratio, near distance and far distance.
	 */
	public void setCamera(){
		//gluPerspective(FoV, aspect, near, far);
		glOrtho(-Endogen.WIDTH / 2, Endogen.WIDTH / 2, -Endogen.HEIGHT / 2, Endogen.HEIGHT / 2, near, far);
	}

	public void setPerspective() {
		glEnable(GL_DEPTH_TEST);
		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);
		glPopMatrix();

	}

	public void setOrtho() {
		glMatrixMode(GL_PROJECTION);
		glPushMatrix();

		glLoadIdentity();
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);

		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();

		glLoadIdentity();

		glDisable(GL_DEPTH_TEST);
	}
	
	public void setOrthoToDimensions(int Width, int Height) {
		glMatrixMode(GL_PROJECTION);
		glPushMatrix();

		glLoadIdentity();
		glOrtho(0, Width, 0, Height, -1, 1);

		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();

		glLoadIdentity();

		glDisable(GL_DEPTH_TEST);
	}

	public void useView() {
		gluLookAt(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ(), up.getX(), up.getY(), up.getZ());
	}
	
	public void use3DOrtho(){
		
	}

	public Vertex3f getPos1() {
		return pos1;
	}

	public void setPos1(Vertex3f pos1) {
		this.pos1 = pos1;
	}

	public Vertex3f getPos2() {
		return pos2;
	}

	public void setPos2(Vertex3f pos2) {
		this.pos2 = pos2;
	}
	
	public Vertex3f getUp() {
		return up;
	}

	public void setUp(Vertex3f up) {
		this.up = up;
	}

	public float getFoV() {
		return FoV;
	}

	public void setFoV(float foV) {
		FoV = foV;
	}

	public float getAspect() {
		return aspect;
	}

	public void setAspect(float aspect) {
		this.aspect = aspect;
	}

	public float getNear() {
		return near;
	}

	public void setNear(float near) {
		this.near = near;
	}

	public float getFar() {
		return far;
	}

	public void setFar(float far) {
		this.far = far;
	}


}
