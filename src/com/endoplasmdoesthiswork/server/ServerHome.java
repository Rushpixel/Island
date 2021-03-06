package com.endoplasmdoesthiswork.server;

import java.net.InetSocketAddress;

import com.endoplasmdoesthiswork.Island;
import com.endoplasmdoesthiswork.StateNode;

public class ServerHome extends StateNode{
	
	public Island island;
	public ServerPlayerList players;
	public FishList fish;

	public ServerHome() {
		super(null, "home");
		island = new Island(this);
		children.add(island);
		players = new ServerPlayerList(this);
		children.add(players);
		fish = new FishList(this, "f");
		children.add(fish);
	}

}
