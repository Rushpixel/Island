package com.endoplasmdoesthiswork.client;

import java.net.InetSocketAddress;

import com.endoplasmdoesthiswork.StateNode;

public class ClientPlayerList extends StateNode {

	public ClientPlayerList(StateNode parent) {
		super(parent, "P");
	}
	
	@Override
	public void set(String command, String args, InetSocketAddress from) {
		if(command.equals("NP")){
			children.add(new Mask_Player(this, args));
		}
	}

}
