package com.endoplasmdoesthiswork.server;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import org.lwjgl.opengl.Display;

import com.endoplasm.Endogen;
import com.endoplasm.GUI;
import com.endoplasmdoesthiswork.Game;
import com.endoplasmdoesthiswork.Message;
import com.endoplasmdoesthiswork.NetworkUtil;
import com.endoplasmdoesthiswork.StateNode;

public class Server implements Runnable {

	public static String IP;
	public static int PORT;

	DatagramChannel channel;

	public static String lastPacket = "No packets recieved yet.";
	public static int UPS = 0;

	public static boolean isServerRunning = false;

	public static ServerHome home;
	public static int maxPlayer;

	public Server(int port, int maxPlayer) {
		Server.maxPlayer = maxPlayer;
		try {
			IP = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			Game.hostgamedets.Error = "Couldn't find local IP";
			e.printStackTrace();
		}
		PORT = port;
	}

	public void run() {
		if (isServerRunning) {
			System.out.println("Failed to create new server, only one server can be running at a time.");
			return;
		}
		home = new ServerHome();
		System.out.println("Tried to start server at " + IP + ":" + PORT);
		createSocket();
		isServerRunning = true;

		home.island.Generate(0);
		serverLoop();
		isServerRunning = false;
	}

	private void createSocket() {
		try {
			channel = DatagramChannel.open();
			InetSocketAddress locIP = new InetSocketAddress(IP, PORT);
			channel.socket().bind(locIP);
			channel.configureBlocking(false);
		} catch (SocketException se) {
			Game.hostgamedets.Error = "Invalid IP \nThis needs to be this computers IP, and the port has to be open. Look at your router settings or Google hosting online games.";
			se.printStackTrace();
			isServerRunning = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void serverLoop() {
		lastTimeCheck = System.nanoTime();
		lastTimeSync = System.nanoTime();
		while (isServerRunning) {
			checkTime();
			if (timeSinceLastUpdate > utime) {
				update();
				ucounter++;
				timeSinceLastUpdate -= utime;
			}
		}
	}

	public long timeSinceLastUpdate = 0;
	public long lastTimeSync = 0;
	public long lastTimeCheck = 0;
	public int ucounter = 0;
	public static final long utime = 1000000000 / Endogen.UPS;

	public void checkTime() {
		long time = System.nanoTime();
		long timeDelta = time - lastTimeCheck;
		timeSinceLastUpdate += timeDelta;
		lastTimeSync += timeDelta;
		if (lastTimeSync > 1000000000) {
			UPS = ucounter;
			ucounter = 0;
			lastTimeSync = 0;
		}
		lastTimeCheck = time;
	}

	private void getPackets() {
		Message[] incoming = NetworkUtil.Recieve(channel, 400);
		if (incoming.length > 0) lastPacket = incoming[0].HEADER + "%" + incoming[0].DATA;
		for (Message m : incoming) {
			switch (m.HEADER) {
			case "HELLO":
				String arg = home.players.addPlayer(m.DATA, m.from);
				if (arg.equals("")) {
					NetworkUtil.Send(channel, m.from, "PERMISSION%" + home.island.seed + "%");
				}
				break;
			}
		}
	}

	private void sendPackets() {
		for (StateNode sn : home.players.children) {
			if (sn instanceof ServerPlayer) {
				String send = home.Transmit(((ServerPlayer) sn).address);
				if (!send.equals("")) {
					// System.out.println("About to send command " + send);
					NetworkUtil.Send(channel, ((ServerPlayer) sn).address, "S%" + send);
				}

			}
		}
	}

	public void update() {
		getPackets();
		StateNode.updateFrom(home);
		sendPackets();
	}

}
