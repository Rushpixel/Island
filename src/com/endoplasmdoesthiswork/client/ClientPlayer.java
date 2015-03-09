package com.endoplasmdoesthiswork.client;

import java.net.InetSocketAddress;

import org.lwjgl.input.Keyboard;

import com.endoplasm.MathUtil;
import com.endoplasm.VectorMath;
import com.endoplasm.Vertex2f;
import com.endoplasmdoesthiswork.Camera2f;
import com.endoplasmdoesthiswork.StateNode;

public class ClientPlayer extends StateNode{
	
	public float Appendix_headRot = 0;
	public Vertex2f Appendix_Step = new Vertex2f(0,0);
	
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
	
	@Override
	public String getTransmission(InetSocketAddress to) {
		if(Math.abs(MathUtil.getAngleBetween(Appendix_headRot, mask.headAngle)) > 10){
			Appendix_headRot = mask.headAngle;
			return "P@" + Client.PLAYER_NAME + "@ROT:" + MathUtil.reduce(mask.headAngle, 2);
		}
		if(Appendix_Step.getX() != mask.step.getX() || Appendix_Step.getY() != mask.step.getY()){
			Appendix_Step.setX(mask.step.getX());
			Appendix_Step.setY(mask.step.getY());
			
			return "P@" + Client.PLAYER_NAME + "@STEP:" + MathUtil.reduce(Appendix_Step.getX(), 1) + "," + MathUtil.reduce(Appendix_Step.getY(), 1) + "," + MathUtil.reduce(Appendix_Step.getX(), 1) + "," + MathUtil.reduce(mask.pos.getY(), 1);
		}
		return "";
	}
	
	public void update(){
		mask.headAngle = MathUtil.direction(mask.pos.getX(), mask.pos.getY(), Camera2f.mousePos.getX(), Camera2f.mousePos.getY()) + 180;
		if(Keyboard.isKeyDown(Keyboard.KEY_W) && MathUtil.distance(mask.pos.getX(), mask.pos.getY(), mask.step.getX(), mask.step.getY()) < 6){
			mask.step = VectorMath.add(mask.pos, new Vertex2f(MathUtil.getXSpeed(mask.headAngle, baseStepSpeed), MathUtil.getYSpeed(mask.headAngle, baseStepSpeed)));
		}
	}

}
