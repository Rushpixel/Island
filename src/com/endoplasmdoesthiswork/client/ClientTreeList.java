package com.endoplasmdoesthiswork.client;

import com.endoplasm.Vertex2f;
import com.endoplasmdoesthiswork.StateNode;

public class ClientTreeList extends StateNode{

	public ClientTreeList(StateNode parent){
		super(parent, "T");
		newTree(new Vertex2f(0,0), 0l);
	}
	
	public void newTree(Vertex2f pos, long Seed){
		children.add(new Mask_Tree(this, children.size() + "", Seed, pos));
	}
	
}
