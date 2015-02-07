package com.endoplasm;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Render3d {

	public static void cube(float x1, float y1, float z1, float x2, float y2, float z2, float r, float g, float b, float a, TextureAsset tex) {
		glPushMatrix();
		{
			glColor4f(r, g, b, a);
			tex.TEX.bind();
			glBegin(GL_QUADS);
			{
				// left face
				glTexCoord2f(0, 0);
				glVertex3f(x1, y1, z1);
				glTexCoord2f(0, 1);
				glVertex3f(x1, y2, z1);
				glTexCoord2f(1, 1);
				glVertex3f(x1, y2, z2);
				glTexCoord2f(1, 0);
				glVertex3f(x1, y1, z2);

				// right face
				glTexCoord2f(0, 0);
				glVertex3f(x2, y1, z1);
				glTexCoord2f(0, 1);
				glVertex3f(x2, y2, z1);
				glTexCoord2f(1, 1);
				glVertex3f(x2, y2, z2);
				glTexCoord2f(1, 0);
				glVertex3f(x2, y1, z2);

				// bottom face
				glTexCoord2f(0, 0);
				glVertex3f(x1, y1, z1);
				glTexCoord2f(0, 1);
				glVertex3f(x2, y1, z1);
				glTexCoord2f(1, 1);
				glVertex3f(x2, y1, z2);
				glTexCoord2f(1, 0);
				glVertex3f(x1, y1, z2);

				// top face
				glTexCoord2f(0, 0);
				glVertex3f(x1, y2, z1);
				glTexCoord2f(0, 1);
				glVertex3f(x2, y2, z1);
				glTexCoord2f(1, 1);
				glVertex3f(x2, y2, z2);
				glTexCoord2f(1, 0);
				glVertex3f(x1, y2, z2);

				// front face
				glTexCoord2f(0, 0);
				glVertex3f(x1, y1, z1);
				glTexCoord2f(0, 1);
				glVertex3f(x1, y2, z1);
				glTexCoord2f(1, 1);
				glVertex3f(x2, y2, z1);
				glTexCoord2f(1, 0);
				glVertex3f(x2, y1, z1);
			}
			glEnd();
		}
		glPopMatrix();
	}

	public static void Model(Vertex3f pos, ModelAsset Model, TextureAsset Texture, float[] color) {
		if (Model == null) {
			System.err.println("model = null");
			return;
		}
		if (Model.MODEL == null) {
			System.err.println("model not loaded, call model.load()");
			return;
		}
		Model model = Model.MODEL;
		glPushMatrix();
		{
			if (Texture == null) Texture = Endogen.SystemAssets.mask.BLANK;
			if (color == null) color = new float[] { 1, 1, 1, 1 };
			if (color.length != 4) color = new float[] { 1, 1, 1, 1 };
			Render.setColor(color);
			Render.setTexture(Texture.TEX);
			glTranslatef(pos.getX(), pos.getY(), pos.getZ());

			for (Polygon f : model.f) {
				glBegin(GL_POLYGON);
				for (Vertex3i v : f.v) {
					if (model.HASUV) glTexCoord2f(model.vt[v.getY()].getX(), model.vt[v.getY()].getY());
					if (model.HASNORMALS) GL11.glNormal3f(model.vn[v.getZ()].getX(), model.vn[v.getZ()].getY(), model.vn[v.getZ()].getZ());
					glVertex3f(model.v[v.getX()].getX(), model.v[v.getX()].getY(), model.v[v.getX()].getZ());
				}
				glEnd();
			}
		}
		glPopMatrix();
	}

}
