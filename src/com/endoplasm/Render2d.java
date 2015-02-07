package com.endoplasm;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Render2d {

	public static void square(float x, float y, float width, float height, float[] colour, TextureAsset tex) {
		glPushMatrix();
		{
			Render.setColor(colour);
			glTranslatef(x, y, 0f);
			tex.TEX.bind();
			glBegin(GL_QUADS);
			{
				glTexCoord2f(0, 1);
				glVertex2f(0, 0);
				glTexCoord2f(0, 0);
				glVertex2f(0, height);
				glTexCoord2f(1, 0);
				glVertex2f(width, height);
				glTexCoord2f(1, 1);
				glVertex2f(width, 0);
			}
			glEnd();
		}
		glPopMatrix();
	}
	
	public static void squareRot(float x, float y, float x1, float y1, float x2, float y2, float rotation, float[] colour, TextureAsset tex) {
		glPushMatrix();
		glTranslatef(x, y, 0);
		float dx = x1+x2;
		float dy = y1+y2;
		square(x1, y1, dx, dy, colour, tex);
		glPopMatrix();
		glPushMatrix();
		{
			Render.setColor(colour);
			glTranslatef(x, y, 0f);
			glRotatef(rotation, 0, 0, 1);
			tex.TEX.bind();
			glBegin(GL_QUADS);
			{
				glTexCoord2f(0, 1);
				glVertex2f(x1, y1);
				glTexCoord2f(0, 0);
				glVertex2f(x1, y2);
				glTexCoord2f(1, 0);
				glVertex2f(x2, y2);
				glTexCoord2f(1, 1);
				glVertex2f(x2, y1);
			}
			glEnd();
		}
		glPopMatrix();
	}

}
