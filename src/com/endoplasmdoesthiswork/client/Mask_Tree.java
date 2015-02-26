package com.endoplasmdoesthiswork.client;

import java.util.Random;

import com.endoplasm.Render2d;
import com.endoplasm.Vertex2f;
import com.endoplasmdoesthiswork.Game;
import com.endoplasmdoesthiswork.Limb;
import com.endoplasmdoesthiswork.StateNode;

public class Mask_Tree extends StateNode {
	
	public Vertex2f pos;
	public long seed;
	
	// render info
	public int numLeaves;
	public Vertex2f[] baseRotSize; //Vertex2f(rotation of leaf, size of leaf)
	public Limb[] limbs;
	
	public Mask_Tree(StateNode parent, String name, long seed, Vertex2f pos){
		super(parent, name);
		this.pos = pos;
		this.seed = seed;
		generate();
	}
	
	public void generate(){
		Random r = new Random(seed);
		
		//generate baseRotSize
		numLeaves = r.nextInt(10) + 5;
		baseRotSize = new Vertex2f[numLeaves];
		float rot = r.nextFloat() * 360f;
		for(int i = 0; i < numLeaves; i++){
			rot += r.nextFloat() * (720 / numLeaves);
			rot %= 360;
			float size = r.nextFloat() * .5f + 0.75f;
			baseRotSize[i] = new Vertex2f(rot,size);
		}
		
		//generate limbs
		limbs = new Limb[numLeaves];
		for(int i = 0; i < numLeaves; i++){
			float[] widths = new float[]{8*baseRotSize[i].getY(), 8*baseRotSize[i].getY(), 8*baseRotSize[i].getY(), 8*baseRotSize[i].getY()};
			float[] lengths = new float[]{(64/3f)*baseRotSize[i].getY(), (64/3f)*baseRotSize[i].getY(), (64/3f)*baseRotSize[i].getY()};
			float[] angles = new float[]{baseRotSize[i].getX(), baseRotSize[i].getX(), baseRotSize[i].getX()};
			Vertex2f[] UVs = new Vertex2f[]{new Vertex2f(0, 0), new Vertex2f(0, .25f), new Vertex2f((1f/3f), 0), new Vertex2f((1f/3f), 0.25f), new Vertex2f((2f/3f), 0), new Vertex2f((2f/3f), .25f), new Vertex2f(1, 0), new Vertex2f(1, .25f)};
			limbs[i] = new Limb(pos, 4, widths, lengths, angles, UVs, Game.assets.PALM_FROND, new float[]{1,1,1,1});
		}
	}
	
	@Override
	public void render(){
		for(Limb leaf: limbs){
			leaf.render();
		}
	}

}
