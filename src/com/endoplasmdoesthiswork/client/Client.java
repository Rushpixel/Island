package com.endoplasmdoesthiswork.client;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import com.endoplasmdoesthiswork.Game;
import com.endoplasmdoesthiswork.Message;
import com.endoplasmdoesthiswork.NetworkUtil;

public class Client {

	public static String HOSTIP;
	public static int HOSTPORT;
	public static String PLAYER_NAME;
	public static InetSocketAddress HOST;
	public static DatagramChannel channel;
	public static String lastPacket = "No packets recieved";
	public static boolean connected = false;

	public Client(String HOSTIP, int HOSTPORT, String PLAYER_NAME) {
		Client.HOSTIP = HOSTIP;
		Client.HOSTPORT = HOSTPORT;
		Client.PLAYER_NAME = PLAYER_NAME;
	}

	public String Begin() {
		String error = StartConnection();
		if(error.equals("")){
			error = downloadMap();
		}
		return error;
	}

	private String StartConnection() {
		System.out.println("Trying to connect to server at " + HOSTIP + ":" + HOSTPORT);
		try {
			HOST = new InetSocketAddress(HOSTIP, HOSTPORT);
			channel = DatagramChannel.open();
			channel.socket().bind(null);
			channel.configureBlocking(false);

			NetworkUtil.Send(channel, HOST, "HELLO%" + PLAYER_NAME);
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return ("The following exception occured: \n" + e.getMessage());
		}
	}

	private String downloadMap() {
		
		return "";
	}
	
	public static void getPackets(){
		Message[] incoming = NetworkUtil.Recieve(channel, 400);
		if (incoming.length > 0) lastPacket = incoming[0].HEADER + "%" + incoming[0].DATA;
		for (Message m : incoming) {
			//System.out.println("[CLIENT] " + m.HEADER + "%" + m.DATA);
			switch (m.HEADER) {
			case "S": {
				try{
				String[] loccom = m.DATA.split(":")[0].split("@");
				String args = m.DATA.split(":")[1];
				Game.clientgraph.Recieve(loccom, args, m.from);
				} catch(Exception e){
					System.err.println("Bad StateNode Packet with DATA: " + m.DATA);
					e.printStackTrace();
				}
				break;
			}
			case "PERMISSION":{
				connected = true;
			}
			}
		}
	}

}
