package com.endoplasmdoesthiswork.server;

import java.net.InetSocketAddress;

import com.endoplasmdoesthiswork.StateNode;

public class FishList extends StateNode{

	public FishList(StateNode parent, String name) {
		super(parent, name);
		children.add(new Fish(this, "0", 0, 20));
		children.add(new Fish(this, "1", 30, 200));
		children.add(new Fish(this, "2", 50, 200));
		children.add(new Fish(this, "3", 70, 200));
		children.add(new Fish(this, "4", 90, 200));
		children.add(new Fish(this, "5", 110, 200));
	}

}
