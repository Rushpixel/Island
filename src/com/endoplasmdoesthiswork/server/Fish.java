package com.endoplasmdoesthiswork.server;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Random;

import com.endoplasm.MathUtil;
import com.endoplasm.VectorMath;
import com.endoplasm.Vertex2f;
import com.endoplasmdoesthiswork.StateNode;

public class Fish extends StateNode {

	public Vertex2f pos;
	public Vertex2f vel;
	
	public boolean hasSent = false;
	public boolean sendPos = true;
	Random r = new Random();

	// arraylist containing the last data to be sent to a player. Each item is a player in form <InetSocketAddress>#X,Y,Xspeed,Yspeed
	public ArrayList<String> Appendix = new ArrayList<String>();
	public String currentState;

	public Fish(StateNode parent, String name, float x, float y) {
		super(parent, name);
		pos = new Vertex2f(x, y);
		vel = new Vertex2f(1, 0);
	}

	@Override
	public String getTransmission(InetSocketAddress to) {
		ServerPlayer sp = Server.home.players.getPlayer(to);
		if(true){ // needs to send to this player
			String state = getStateForPlayer(sp);
			if(state.equals("")){
				Appendix.add(to.toString() + "#0,0,0,0");
				return "F@NF:" + name;
			} else{
				String[] list = state.split("#");
				String[] fs = list[1].split(",");
				float x = Float.parseFloat(fs[0]);
				float y = Float.parseFloat(fs[1]);
				float vx = Float.parseFloat(fs[2]);
				float vy = Float.parseFloat(fs[3]);
				if(MathUtil.distance(x, y, pos.getX(), pos.getY()) > 30 || MathUtil.distance(vx, vy, vel.getX(), vel.getY()) > 0.2){
					Appendix.set(Appendix.lastIndexOf(state), to.toString() + "#" + StatePV());
					return "F@" + name + "@PV:" + StatePV();
				}
			}
		}
		return "";
	}

	@Override
	public void update() {
		if(r.nextInt(50) == 0){
			vel.setX(r.nextFloat() * 2 - 1f);
			vel.setY(r.nextFloat() * 2 - 1f);
			sendPos = true;
		}
		if(pos.getX() > 600){
			pos.setX(600);
			vel.setX(-vel.getX());
		}
		if(pos.getX() < 0){
			pos.setX(0);
			vel.setX(-vel.getX());
		}
		if(pos.getY() > 600){
			pos.setY(600);
			vel.setY(-vel.getY());
		}
		if(pos.getY() < 0){
			pos.setY(0);
			vel.setY(-vel.getY());
		}
		pos = VectorMath.add(pos, vel);
		updateCurrentState();
	}

	public void updateCurrentState(){
		currentState = StatePV();
	}
	
	public String StatePV(){
		return MathUtil.reduce(pos.getX(), 2) + "," + MathUtil.reduce(pos.getY(), 2) + "," + MathUtil.reduce(vel.getX(), 3) + "," + MathUtil.reduce(vel.getY(), 3);
	}

	public String getStateForPlayer(ServerPlayer p){
		for(String s: Appendix){
			String a = p.address.toString();
			String[] list = s.split("#");
			if(a.equals(list[0])) return s;
		}
		return "";
	}
	
}
