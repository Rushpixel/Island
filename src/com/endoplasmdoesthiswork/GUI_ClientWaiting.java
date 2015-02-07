package com.endoplasmdoesthiswork;

import org.lwjgl.input.Cursor;

import com.endoplasm.Endogen;
import com.endoplasm.GUI;
import com.endoplasm.GUIElement;
import com.endoplasm.GUIRect;
import com.endoplasm.Text;
import com.endoplasm.Vertex2f;
import com.endoplasmdoesthiswork.client.Client;

public class GUI_ClientWaiting extends GUIRect{
	
	public String base = "Waiting for Host to Reply";
	public String Waiting = base;
	public int timer = 0;
	public int dots = 3;

	public GUI_ClientWaiting(GUIElement parent) {
		super(parent, new Vertex2f(0, 20), new Vertex2f(300, 300));
	}


	@Override
	public Cursor getCursor() {
		return Endogen.SystemAssets.mask.CURSORCLICKABLE;
	}

	@Override
	public void render() {
		Text.renderTextFromString(Waiting, 5, -98, 16, 24, 50, 2, Endogen.SystemAssets.mask.FONT1, new float[] { 1f, 1f, 1f, 1 });
	}

	@Override
	public void update() {
		pos.setX(Endogen.WIDTH / 2 - 150);
		pos.setY(Endogen.HEIGHT / 2 + 150);
		if(timer >= 60) {
			timer = 0;
			dots = (dots + 1) % 6;
			Waiting = base;
			for(int i = 0; i < Math.abs(dots - 3); i++) Waiting = Waiting + ".";
		}
		timer++;
		
		Client.getPackets();
		if(Client.connected){
			GUI.field.children.remove(this);
		}
	}

}
