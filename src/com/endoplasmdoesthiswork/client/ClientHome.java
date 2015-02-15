package com.endoplasmdoesthiswork.client;

import java.net.InetSocketAddress;

import com.endoplasm.Vertex2f;
import com.endoplasmdoesthiswork.Island;
import com.endoplasmdoesthiswork.StateNode;

public class ClientHome extends StateNode{
	
	public Island island;
	public Ocean ocean;
	public ClientFishList fishes;
	public ClientPlayerList players;
	public ClientPlayer playerhome;
	
	public ClientHome(){
		super(null, "home");
		island = new Island(this);
		island.Generate(0);
		children.add(island);
		ocean = new Ocean(this);
		children.add(ocean);
		fishes = new ClientFishList(this);
		children.add(fishes);
		players = new ClientPlayerList(this);
		children.add(players);
		playerhome = new ClientPlayer(this, "PH");
		children.add(playerhome);
	}
	
	public void update(){
	}
	
	public void render(){
	}

}
