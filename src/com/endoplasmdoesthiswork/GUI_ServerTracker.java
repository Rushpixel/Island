package com.endoplasmdoesthiswork;

import org.lwjgl.input.Cursor;

import com.endoplasm.Endogen;
import com.endoplasm.GUIElement;
import com.endoplasm.GUIRect;
import com.endoplasm.MathUtil;
import com.endoplasm.Text;
import com.endoplasm.Vertex2f;
import com.endoplasmdoesthiswork.server.Server;

public class GUI_ServerTracker extends GUIElement {

	public GUI_ServerTracker(GUIElement parent) {
		super(parent);
		pos = new Vertex2f(5, Endogen.HEIGHT - 20);
	}

	@Override
	public Cursor getCursor() {
		return null;
	}

	@Override
	public void render() {
		if (Server.isServerRunning) {
			float i = 0;
			Text.renderTextFromString("Server Details:", 2, i, 8, 12, -1, 0, Endogen.SystemAssets.mask.FONT1, new float[] { 1, 1, 1, 1 });
			i -= 16;
			Text.renderTextFromString("Hosting server at " + Server.IP + ":" + Server.PORT, 2, i, 8, 12, -1, 0, Endogen.SystemAssets.mask.FONT1, new float[] { 1, 1, 1, 1 });
			i -= 16;
			Text.renderTextFromString("Server UPS " + Server.UPS, 2, i, 8, 12, -1, 0, Endogen.SystemAssets.mask.FONT1, new float[] { 1, 1, 1, 1 });
			i -= 16;
			Text.renderTextFromString("Players " + Server.home.players.children.size() + "/" + Server.maxPlayer, 2, i, 8, 12, -1, 0, Endogen.SystemAssets.mask.FONT1, new float[] { 1, 1, 1, 1 });
			i -= 16;
			Text.renderTextFromString("" + Server.lastPacket, 2, i, 8, 12, -1, 0, Endogen.SystemAssets.mask.FONT1, new float[] { 1, 1, 1, 1 });
			i -= 16;
		}
	}

	@Override
	public void update() {
		pos = new Vertex2f(0, Endogen.HEIGHT - 20);

	}

	@Override
	public boolean doesContain(Vertex2f parentOffset) {
		return false;
	}

}
