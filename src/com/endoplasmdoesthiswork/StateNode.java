package com.endoplasmdoesthiswork;

import java.net.InetSocketAddress;
import java.util.ArrayList;

import com.endoplasmdoesthiswork.server.ServerPlayer;

public abstract class StateNode {

	public ArrayList<StateNode> children = new ArrayList<StateNode>();
	public StateNode parent;
	public String name = "";

	public boolean isListening = true;

	public StateNode(StateNode parent, String name) {
		this.parent = parent;
		this.name = name;
	}

	/**
	 * 
	 * @param from
	 *            the InetAddress the command was received from
	 * @param command
	 *            the name of the command
	 * @param args
	 *            the arguments of the command
	 * @return Whether the command was valid
	 */

	public void Recieve(String[] loccom, String arg, InetSocketAddress from) {
		if (loccom.length > 1) { // check if there are locations before the command
			String[] nextloccom = new String[loccom.length - 1];
			for(int i = 0; i < nextloccom.length; i++) nextloccom[i] = loccom[i + 1];
			getChild(loccom[0]).Recieve(nextloccom, arg, from);
		} else if (loccom.length == 1) { // check if there are no locations, but there is a command command
			set(loccom[0], arg, from);
		} else {
			System.err.println("Bad loccom with args " + arg);
		}
	}
	
	public void set(String command, String arg, InetSocketAddress from){
		System.err.println("StateNode " + name + " does not have any commands, and yet command " + command + "was called with arg " + arg);
	}

	public String Transmit(InetSocketAddress to) {
		String transmission = getTransmission(to);
		if(transmission != "") transmission = transmission + "%";
		for(StateNode sn: children){
			transmission = transmission + sn.Transmit(to);
		}
		return transmission;
	}

	public String getTransmission(InetSocketAddress to) {
		return "";
	}

	public StateNode getChild(String name) {
		for (StateNode sn : children) {
			if (sn.name.equals(name)) return sn;
		}
		System.err.println("Parent " + this.name + ", " + this.getClass().getName() + " cannot find child with name " + name + "\nchildren.size() = " + children.size() + "\nHas children");
		for(StateNode child: children){
			System.out.print(child.name + ", ");
		}
		System.out.println();
		return null;
	}

	public void update() {
	}

	public void render() {
	}

	public static void updateFrom(StateNode top) {
		top.update();
		for (int i = 0; i < top.children.size(); i++) {
			updateFrom(top.children.get(i));
		}
	}

	public static void renderFrom(StateNode top) {
		top.render();
		for (int i = 0; i < top.children.size(); i++) {
			renderFrom(top.children.get(i));
		}
	}

}
