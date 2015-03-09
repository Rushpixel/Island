package com.endoplasmdoesthiswork.server;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;

import com.endoplasm.Endogen;
import com.endoplasm.MathUtil;
import com.endoplasm.Vertex2f;
import com.endoplasmdoesthiswork.StateNode;

public class ServerPlayer extends StateNode {

	// arraylist containing the last data to be sent to a player. Each item is a player in form <InetSocketAddress>#ROT?headRot#STEP?step.x,step.y)
	public ArrayList<String> Appendix = new ArrayList<String>();
	public String currentState_HeadRot;

	public InetSocketAddress address;

	public Vertex2f step = new Vertex2f(0, 0);
	public Vertex2f pos = new Vertex2f(0, 0);
	public float headRot = 0;

	public ServerPlayer(StateNode parent, String name, InetSocketAddress address) {
		super(parent, name);
		this.address = address;
	}

	@Override
	public void set(String command, String args, InetSocketAddress from) {
		switch (command) {
		case "ROT": {
			headRot = Float.parseFloat(args);
		}
			break;
		case "STEP": {
			String[] XY = args.split(",");
			step = new Vertex2f(Float.parseFloat(XY[0]), Float.parseFloat(XY[1]));
			pos = new Vertex2f(Float.parseFloat(XY[2]), Float.parseFloat(XY[3]));
		}
			break;
		}
	}

	@Override
	public String getTransmission(InetSocketAddress to) {
		String returns = "";
		ServerPlayer sp = Server.home.players.getPlayer(to);
		if (sendToAddress(to)) { // needs to send to this player
			String state = getStateForPlayer(sp);
			try {
				if (state.equals("")) {
					Appendix.add(to.toString() + "#ROT?0#STEP?0,0");
					return "P@NP:" + name;
				} else {
					String[] list = state.split("#");

					// Rotation
					float lastHeadRot = Float.parseFloat(list[1].replace("ROT?", ""));
					if (Math.abs(MathUtil.getAngleBetween(lastHeadRot, headRot)) > 10) {
						list[1] = ("ROT?" + MathUtil.reduce(headRot, 2));
						returns = "P@" + name + "@ROT:" + MathUtil.reduce(headRot, 2);
					}
					// Step
					System.out.println(list[2]);
					float lastStepX = Float.parseFloat(list[2].replace("STEP?", "").split(",")[0]);
					float lastStepY = Float.parseFloat(list[2].replace("STEP?", "").split(",")[1]);
					if (MathUtil.distance(lastStepX, lastStepY, step.getX(), step.getY()) > 3 && returns.equals("")) {
						list[2] = ("STEP?" + step.getX() + "," + step.getY());
						returns = "P@" + name + "@STEP:" + MathUtil.reduce(step.getX(), 1) + "," + MathUtil.reduce(step.getY(), 1);
					}
					Appendix.set(Appendix.lastIndexOf(state), list[0] + "#" + list[1] + "#" + list[2]);

				}
			} catch (Exception e) {
				System.err.println("Appendix = " + state);
				e.printStackTrace();
				Endogen.exceptionhandler.ErrorMessage(e.getMessage(), true);
			}
		}
		return returns;
	}

	public boolean sendToAddress(InetSocketAddress to) {
		return !address.equals(to);
	}

	public String State() {
		return MathUtil.reduce(headRot, 4) + "";
	}

	public String getStateForPlayer(ServerPlayer p) {
		for (String s : Appendix) {
			String a = p.address.toString();
			String[] list = s.split("#");
			if (a.equals(list[0])) return s;
		}
		return "";
	}

}
