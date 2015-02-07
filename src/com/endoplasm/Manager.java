package com.endoplasm;

import static org.lwjgl.opengl.EXTFramebufferObject.GL_FRAMEBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindFramebufferEXT;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;

public class Manager {

	public void update() {
		// check to see if the window was resized, if so reset aspect ratio
		if (Endogen.isMatchDisplay()) {
			Endogen.WIDTH = Display.getWidth();
			Endogen.HEIGHT = Display.getHeight();
		}
		if (Display.wasResized() || Endogen.needsResize) {
			if (Display.wasResized()) {
				Endogen.camera.setAspect(Display.getWidth() / (float) Display.getHeight());
				Endogen.camera.setCamera();
				Endogen.endogen.initFBO();
				System.out.println(Display.getWidth() / (float) Display.getHeight());
				System.out.println("Window resized to " + Display.getWidth() + "*" + Display.getHeight());
				System.out.println();
				Endogen.needsResize = false;
			}
		}

		Endogen.mouse.update();
		Endogen.GUI.update();
		Endogen.game.update();
	}

	public void render() {

		// render game to FBO
		glViewport(0, 0, Endogen.WIDTH, Endogen.HEIGHT);

		glBindTexture(GL_TEXTURE_2D, 0);
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, Endogen.framebufferID);

		glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glLoadIdentity();

		// TODO use shader

		// render game layers
		glPushMatrix();
		Endogen.camera.setPerspective();
		Endogen.camera.useView();
		Endogen.game.render3D();
		Endogen.camera.setOrthoToDimensions(Endogen.WIDTH, Endogen.HEIGHT);
		Render2d.square(0, 0, 0, 0, new float[] { 0, 0, 0, 0 }, Endogen.SystemAssets.mask.BLANK);
		Endogen.game.render2D();
		Endogen.GUI.render();

		// TODO render GUI layer
		glPopMatrix();

		// render FBO to frameBuffer
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
		glBindTexture(GL_TEXTURE_2D, Endogen.colorTextureID);

		glClearColor(0.0f, 0.0f, 0.0f, 1f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glViewport(0, 0, Display.getWidth(), Display.getHeight());

		glLoadIdentity();

		Endogen.camera.setOrtho();
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		square(0, 0, Display.getWidth(), Display.getHeight(), 1, 1, 1, 1);

		Endogen.camera.setPerspective();

		glDisable(GL_TEXTURE_2D);

		Display.update();
	}

	public static void square(float x, float y, float width, float height, float r, float g, float b, float a) {
		glPushMatrix();
		{
			glColor4f(r, g, b, a);
			glTranslatef(x, y, 0f);
			glBegin(GL_QUADS);
			{
				glTexCoord2f(0, 0);
				glVertex2f(0, 0);
				glTexCoord2f(0, 1);
				glVertex2f(0, height);
				glTexCoord2f(1, 1);
				glVertex2f(width, height);
				glTexCoord2f(1, 0);
				glVertex2f(width, 0);
			}
			glEnd();
		}
		glPopMatrix();
	}

	public long timeSinceLastUpdate = 0;
	public long lastTimeSync = 0;
	public long lastTimeCheck = 0;
	public int fcounter = 0;
	public int ucounter = 0;
	public static final long utime = 1000000000 / Endogen.UPS;

	public void GameLoop() {
		lastTimeCheck = System.nanoTime();
		lastTimeSync = System.nanoTime();
		while (!Display.isCloseRequested()) {
			checkTime();
			if (timeSinceLastUpdate > utime) {
				update();
				ucounter++;
				timeSinceLastUpdate -= utime;
				if (timeSinceLastUpdate < utime * 2) {
					render();
					fcounter++;
				}
			}
		}
	}

	public void checkTime() {
		long time = System.nanoTime();
		long timeDelta = time - lastTimeCheck;
		;
		timeSinceLastUpdate += timeDelta;
		lastTimeSync += timeDelta;
		if (lastTimeSync > 1000000000) {
			Endogen.lastFPS = fcounter;
			Endogen.lastUPS = ucounter;
			fcounter = 0;
			ucounter = 0;
			lastTimeSync = 0;
		}
		lastTimeCheck = time;
	}

}
