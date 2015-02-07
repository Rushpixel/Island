package com.endoplasmdoesthiswork.client;

import java.net.InetSocketAddress;

import com.endoplasmdoesthiswork.StateNode;

public class ClientFishList extends StateNode {

	public ClientFishList(StateNode parent) {
		super(parent, "F");
	}

	@Override
	public void set(String command, String args, InetSocketAddress from) {
		if(command.equals("NF")){
			children.add(new ClientFish(this, args));
			System.out.println("creating fish with name " + args);
		}
	}

}
