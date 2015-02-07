package com.endoplasmdoesthiswork.client;

import java.net.InetSocketAddress;

import com.endoplasm.Endogen;
import com.endoplasm.MathUtil;
import com.endoplasm.Render2d;
import com.endoplasm.VectorMath;
import com.endoplasm.Vertex2f;
import com.endoplasmdoesthiswork.StateNode;

public class ClientFish extends StateNode{
	
	public Vertex2f pos = new Vertex2f(0, 0);
	public Vertex2f rpos = new Vertex2f(0, 0);
	public Vertex2f vel = new Vertex2f(0, 0);

	public ClientFish(StateNode parent, String name) {
		super(parent, name);
	}

	@Override
	public void set(String command, String args, InetSocketAddress from) {
		switch(command){
		case "PV":
			String[] a = args.split(",");
			pos = new Vertex2f(Float.parseFloat(a[0]), Float.parseFloat(a[1]));
			vel = new Vertex2f(Float.parseFloat(a[2]), Float.parseFloat(a[3]));
			break;
		}
	}
	
	@Override
	public void update(){
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
		rpos = VectorMath.add(rpos, vel);
		rpos.addX((pos.getX() - rpos.getX()) * 0.5f);
		rpos.addY((pos.getY() - rpos.getY()) * 0.5f);
		if(MathUtil.distance(rpos.getX(), rpos.getY(), pos.getX(), pos.getY()) > 30){
			rpos.setX(pos.getX());
			rpos.setY(pos.getY());
		}
	}
	
	@Override
	public void render(){
		Render2d.square(rpos.getX(), rpos.getY(), 8, 8, new float[]{0, 1, 1, 1}, Endogen.SystemAssets.mask.BLANK);
	}

}
