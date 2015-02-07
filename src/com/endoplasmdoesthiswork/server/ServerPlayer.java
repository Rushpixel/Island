package com.endoplasmdoesthiswork.server;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import com.endoplasmdoesthiswork.StateNode;

public class ServerPlayer extends StateNode{
	
	public InetSocketAddress address;

	public ServerPlayer(StateNode parent, String name, InetSocketAddress address) {
		super(parent, name);
		this.address = address;
	}

}
