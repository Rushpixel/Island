package com.endoplasmdoesthiswork;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;

public class NetworkUtil {

	public static int Send(DatagramChannel c, InetSocketAddress address, ByteBuffer b) {
		try {
			return c.send(b, address);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static int Send(DatagramChannel c, InetSocketAddress address, String s) {
		byte[] bs = s.getBytes();
		ByteBuffer b = ByteBuffer.allocate(bs.length);
		b.put(bs);
		b.flip();
		return Send(c, address, b);
	}

	public static Message[] Recieve(DatagramChannel c, int capacity) {
		try {
			ArrayList<Message> messagelist = new ArrayList<Message>();

			ByteBuffer b = ByteBuffer.allocate(capacity);
			SocketAddress lastIN = null;
			while ((lastIN = c.receive(b)) != null) {
				String in = new String(b.array()).trim();
				String s[] = in.split("%");
				//System.out.println("Recieved UDP " + in);
				if (s.length > 1) {
					for (int i = 1; i < s.length; i++) {
						messagelist.add(new Message(s[0] + "%" + s[i], (InetSocketAddress) lastIN));
					}
				} else messagelist.add(new Message(s[0], (InetSocketAddress) lastIN));
				b.clear();
			}
			Message[] m = new Message[messagelist.size()];
			messagelist.toArray(m);
			return m;
		} catch (IOException e) {
			e.printStackTrace();
			return new Message[0];
		}
	}

}
