package com.endoplasmdoesthiswork.client;

import com.endoplasm.Endogen;
import com.endoplasm.MathUtil;
import com.endoplasm.Render2d;
import com.endoplasmdoesthiswork.Game;
import com.endoplasmdoesthiswork.Island;
import com.endoplasmdoesthiswork.StateNode;

public class Ocean extends StateNode{
	
	public static float highPoint = .5f;
	public static float lowPoint = .4f;
	public static float currentPoint = 0.4f;
	public static boolean descending = false;
	public static float foamThickness = 5;
	
	public Ocean(StateNode parent) {
		super(parent, "O");
	}
	
	@Override
	public void update(){
		if(descending){
			currentPoint += (currentPoint - highPoint) * 0.01;
		} else{
			currentPoint -= (currentPoint - highPoint) * 0.01;
		}
		if(currentPoint > highPoint - .01f) descending = true;
		if(currentPoint < lowPoint + .01f) descending = false;
		foamThickness = (currentPoint - lowPoint) * 10 * 20;
	}
	
	@Override
	public void render(){
		Island island = Game.clientgraph.island;
		
		for(int i = 0; i < island.beachlengths.length; i++){
			int nextSl = ((i + 1) % island.beachlengths.length);
			float beachLength = island.beachlengths[i] * 2;
			float nextBLength = island.beachlengths[nextSl] * 2;
			float sliceAngle = 360f / island.sidelengths.length;
			float a1 = sliceAngle * i;
			float a2 = sliceAngle * nextSl;
			float waterLevel = beachLength * (1 - currentPoint);
			float nextWLevel = nextBLength * (1 - currentPoint);
			//water
			Render2d.uniTriangle(
					MathUtil.getXSpeed(a1, waterLevel), MathUtil.getYSpeed(a1, waterLevel),
					MathUtil.getXSpeed(a1, beachLength),MathUtil.getYSpeed(a1, beachLength),
					MathUtil.getXSpeed(a2, nextBLength),MathUtil.getYSpeed(a2, nextBLength),
					new float[]{ 0.1f, 0.7f, 0.8f, 1 }, Endogen.SystemAssets.mask.BLANK, 1);
			Render2d.uniTriangle(
					MathUtil.getXSpeed(a1, waterLevel), MathUtil.getYSpeed(a1, waterLevel),
					MathUtil.getXSpeed(a2, nextBLength),MathUtil.getYSpeed(a2, nextBLength),
					MathUtil.getXSpeed(a2, nextWLevel),MathUtil.getYSpeed(a2, nextWLevel),
					new float[]{ 0.1f, 0.7f, 0.8f, 1 }, Endogen.SystemAssets.mask.BLANK, 1);
			
			//foam
			float foamClose = waterLevel - foamThickness;
			float foamFar = waterLevel + foamThickness;
			float nexFClose = nextWLevel - foamThickness;
			float nexFFar = nextWLevel + foamThickness;
			Render2d.uniTriangle(
					MathUtil.getXSpeed(a1, foamClose), MathUtil.getYSpeed(a1, foamClose),
					MathUtil.getXSpeed(a1, foamFar),MathUtil.getYSpeed(a1, foamFar),
					MathUtil.getXSpeed(a2, nexFFar),MathUtil.getYSpeed(a2, nexFFar),
					new float[]{ 1, 1, 1, 1 }, Endogen.SystemAssets.mask.BLANK, 1);
			Render2d.uniTriangle(
					MathUtil.getXSpeed(a1, foamClose), MathUtil.getYSpeed(a1, foamClose),
					MathUtil.getXSpeed(a2, nexFClose),MathUtil.getYSpeed(a2, nexFClose),
					MathUtil.getXSpeed(a2, nexFFar),MathUtil.getYSpeed(a2, nexFFar),
					new float[]{ 1, 1, 1, 1 }, Endogen.SystemAssets.mask.BLANK, 1);
			
		}
	}

}
