package com.endoplasmdoesthiswork.server;

import java.net.InetSocketAddress;

import com.endoplasmdoesthiswork.StateNode;

public class ServerHome extends StateNode{
	
	public PlayerList players;
	public FishList fish;

	public ServerHome() {
		super(null, "home");
		players = new PlayerList(this);
		children.add(players);
		fish = new FishList(this, "f");
		children.add(fish);
	}

}
