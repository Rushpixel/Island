package com.endoplasmdoesthiswork.server;

import java.net.InetSocketAddress;

import com.endoplasmdoesthiswork.StateNode;

public class PlayerList extends StateNode {

	public PlayerList(StateNode parent) {
		super(parent, "P");
	}
	
	
	public ServerPlayer lastGiven = null;
	public ServerPlayer getPlayer(InetSocketAddress address){
		if(lastGiven != null) if(address == lastGiven.address){
			return lastGiven;
		}
		for(StateNode sn: children){
			if(sn instanceof ServerPlayer){
				ServerPlayer sp = (ServerPlayer) sn;
				if(sp.address == address) return sp;
			}
		}
		return null;
	}

	public String addPlayer(String username, InetSocketAddress address) {
		if (getChild(username) != null) {
			return "Username " + username + " is already in use";
		} else if(children.size() >= Server.maxPlayer){
			return "Server full";
		} else {
			System.out.println(Server.maxPlayer);
			ServerPlayer p = new ServerPlayer(this, username, address);
			children.add(p);
			System.out.println("[SERVER] Player " + username + " joined");
			return "";
		}
	}

}
