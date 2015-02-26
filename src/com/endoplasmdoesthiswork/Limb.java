package com.endoplasmdoesthiswork;

import static org.lwjgl.opengl.GL11.*;

import com.endoplasm.Endogen;
import com.endoplasm.MathUtil;
import com.endoplasm.Render;
import com.endoplasm.Render2d;
import com.endoplasm.TextureAsset;
import com.endoplasm.VectorMath;
import com.endoplasm.Vertex2f;

public class Limb {
	
	public Vertex2f pos;
	public int numNodes;
	public Vertex2f[] nodes;
	public float[] joints;
	public Vertex2f[] UVs;
	public float[] widths;
	public float[] lengths;
	public float[] angles;
	public TextureAsset tex;
	public float[] colour;
	
	public Limb(Vertex2f pos, int numNodes, float[] widths, float[] lengths, float[] angles, Vertex2f[] UVs, TextureAsset tex, float[] colour){
		this.pos = new Vertex2f(pos.getX(), pos.getY());
		this.UVs = UVs;
		this.numNodes = numNodes;
		this.widths = widths;
		this.lengths = lengths;
		this.angles = angles;
		this.colour = colour;
		this.tex = tex;
		this.nodes = new Vertex2f[widths.length];
		calc();
	}
	
	public void calc(){
		Vertex2f offset = new Vertex2f(0,0);
		nodes[0] = new Vertex2f(0,0);
		for(int i = 1; i < numNodes; i++){
			offset.addX(MathUtil.getXSpeed(angles[i - 1], lengths[i - 1]));
			offset.addY(MathUtil.getYSpeed(angles[i - 1], lengths[i - 1]));
			nodes[i] = new Vertex2f(offset.getX(), offset.getY());
		}
		joints = new float[nodes.length];
		for(int i = 0; i < joints.length; i++){
			//calc which angles to compare
			float a1 = 0;
			float a2 = 0;
			if(i == 0) {
				a1 = angles[i];
				a2 = angles[i];
			} else if(i == joints.length-1){
				a1 = angles[i-1];
				a2 = angles[i-1];
			} else{
				a1 = angles[i - 1];
				a2 = angles[i];
			}
			//calc joint
			float dif = -MathUtil.getAngleBetween(a1, a2);
			joints[i] = a1 + dif / 2 + 90;
		}
	}
	
	public void render(){
		glPushMatrix();
		{
			glTranslatef(pos.getX(), pos.getY(), 0);
			tex.TEX.bind();
			Render.setColor(colour);
			glBegin(GL_QUAD_STRIP);
			for(int i = 0; i< joints.length; i++){
				float joint = joints[i];
				float width = widths[i];
				
				Vertex2f UV1 = UVs[i*2];
				glTexCoord2f(UV1.getX(),UV1.getY());
				Vertex2f v1 = new Vertex2f(MathUtil.getXSpeed(joint, width), MathUtil.getYSpeed(joint, width));
				v1 = VectorMath.add(v1, nodes[i]);
				glVertex2f(v1.getX(), v1.getY());
				
				Vertex2f UV2 = UVs[i*2+1];
				glTexCoord2f(UV2.getX(),UV2.getY());
				Vertex2f v2 = new Vertex2f(MathUtil.getXSpeed(joint, -width), MathUtil.getYSpeed(joint, -width));
				v2 = VectorMath.add(v2, nodes[i]);
				glVertex2f(v2.getX(), v2.getY());
			}
			glEnd();
		}
		glPopMatrix();
	}

}
