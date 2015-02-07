package com.endoplasmdoesthiswork.client;

import java.net.InetSocketAddress;

import org.lwjgl.input.Keyboard;

import com.endoplasm.MathUtil;
import com.endoplasm.VectorMath;
import com.endoplasm.Vertex2f;
import com.endoplasmdoesthiswork.Camera2f;
import com.endoplasmdoesthiswork.StateNode;

public class ClientPlayer extends StateNode{
	
	public Mask_Player mask;
	
	public float baseStepSpeed = 15f;
	
	public ClientPlayer(StateNode parent, String name) {
		super(parent, name);
		mask = new Mask_Player(this, "M");
		children.add(mask);
	}

	@Override
	public void set(String command, String args, InetSocketAddress from) {
		
	}
	
	public void update(){
		mask.headAngle = MathUtil.direction(mask.pos.getX(), mask.pos.getY(), Camera2f.mousePos.getX(), Camera2f.mousePos.getY()) + 180;
		if(Keyboard.isKeyDown(Keyboard.KEY_W) && MathUtil.distance(mask.pos.getX(), mask.pos.getY(), mask.step.getX(), mask.step.getY()) < 6){
			mask.step = VectorMath.add(mask.pos, new Vertex2f(MathUtil.getXSpeed(mask.headAngle, baseStepSpeed), MathUtil.getYSpeed(mask.headAngle, baseStepSpeed)));
		}
	}

}
