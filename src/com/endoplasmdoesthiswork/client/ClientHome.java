package com.endoplasmdoesthiswork.client;

import java.net.InetSocketAddress;

import com.endoplasm.Vertex2f;
import com.endoplasmdoesthiswork.StateNode;

public class ClientHome extends StateNode{
	
	public ClientFishList fishes;
	public ClientPlayerList players;
	public ClientPlayer playerhome;
	
	public ClientHome(){
		super(null, "home");
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
