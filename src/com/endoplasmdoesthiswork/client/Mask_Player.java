package com.endoplasmdoesthiswork.client;

import java.net.InetSocketAddress;

import org.lwjgl.input.Keyboard;

import com.endoplasm.Endogen;
import com.endoplasm.MathUtil;
import com.endoplasm.Render2d;
import com.endoplasm.VectorMath;
import com.endoplasm.Vertex2f;
import com.endoplasmdoesthiswork.Camera2f;
import com.endoplasmdoesthiswork.Game;
import com.endoplasmdoesthiswork.StateNode;

public class Mask_Player extends StateNode {

	public Vertex2f pos;
	public Vertex2f rpos;
	public Vertex2f vel;
	public Vertex2f step;
	public float friction = 0.8f;
	public float lastMoveAngle = 0;
	public float headAngle = 0;
	public float rHeadAngle = 0;
	public float bodAngle = 0;

	public Mask_Player(StateNode parent, String name) {
		super(parent, name);
		this.pos = new Vertex2f(0, 0);
		this.rpos = new Vertex2f(0,0);
		step = pos;
		vel = new Vertex2f(0, 0);
	}

	@Override
	public void set(String command, String args, InetSocketAddress from) {
		switch (command) {
		case "ROT": {
			headAngle = Float.parseFloat(args);
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

	public void update() { 
		float dir = MathUtil.direction(pos.getX(), pos.getY(), step.getX(), step.getY());
		float dis = MathUtil.distance(step.getX(), step.getY(), pos.getX(), pos.getY()) * .05f;
		Vertex2f dv = new Vertex2f(MathUtil.getXSpeed(dir, dis), MathUtil.getYSpeed(dir, dis));
		vel = VectorMath.subtract(vel, dv);
		vel = VectorMath.multiply(vel, new Vertex2f(friction, friction));
		pos = VectorMath.add(pos, vel);

		rHeadAngle += MathUtil.getAngleBetween(headAngle, rHeadAngle) * 0.4f;
		float da = MathUtil.getAngleBetween(rHeadAngle, bodAngle);
		if (MathUtil.distance(0, 0, vel.getX(), vel.getY()) > 0.3) {
			bodAngle += da * 0.1f;
		}
		if (Math.abs(da) > 60) {
			bodAngle = da > 0 ? rHeadAngle - 60 : rHeadAngle + 60;
		}
	}

	public void render() {
		Render2d.squareRot(pos.getX(), pos.getY(), -16, -16, 16, 16, bodAngle - 90, new float[] { 1, 1, 1, 1 }, Game.assets.BODY_BASE0);
		Render2d.squareRot(pos.getX(), pos.getY(), -16, -16, 16, 16, headAngle - 90, new float[] { 1, 1, 1, 1 }, Game.assets.HEAD_BASE0);
		Render2d.square(step.getX(), step.getY(), 2, 2, new float[] { 1, 1, 1, 1 }, Endogen.SystemAssets.mask.BLANK);

	}

}
