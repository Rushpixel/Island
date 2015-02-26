package com.endoplasmdoesthiswork;

import org.lwjgl.opengl.GL11;

import com.endoplasm.Endogen;
import com.endoplasm.GInit;
import com.endoplasm.GUI;
import com.endoplasm.Render2d;
import com.endoplasm.Text;
import com.endoplasmdoesthiswork.client.Client;
import com.endoplasmdoesthiswork.client.ClientHome;
import com.endoplasmdoesthiswork.server.Server;

public class Game extends GInit {

	public static Assets assets = new Assets();
	public static Camera2f camera = new Camera2f();

	public static GUI_MainMenu mainmenu;
	public static GUI_HostGameDetails hostgamedets;
	public static GUI_JoinGame joingame;
	public static GUI_ClientWaiting clientwaiting;
	public static GUI_ServerTracker servertracker;

	public static ClientHome clientgraph;

	@Override
	public void init() {
		Endogen.setMatchDisplay(true);
		assets.load();
		mainmenu = new GUI_MainMenu(GUI.field);
		hostgamedets = new GUI_HostGameDetails(GUI.field);
		servertracker = new GUI_ServerTracker(GUI.field);
		joingame = new GUI_JoinGame(GUI.field);
		clientwaiting = new GUI_ClientWaiting(GUI.field);
		GUI.field.children.add(mainmenu);
	}

	@Override
	public void cleanup() {
		Server.isServerRunning = false;
		assets.unload();
	}

	@Override
	public void update() {
		if (clientgraph != null && Client.connected) {
			camera.update();
			Client.getPackets();
			StateNode.updateFrom(clientgraph);
		}
	}

	@Override
	public void render3D() {
		// not gonna even use this
	}

	@Override
	public void render2D() {
		if (clientgraph != null && Client.connected) if (clientgraph.island.finishedGeneration) {
			GL11.glPushMatrix();
			camera.centre = clientgraph.playerhome.mask.pos;
			camera.setCamera();
			StateNode.renderFrom(clientgraph);
			GL11.glPopMatrix();
		}
		Text.renderTextFromString("FPS " + Endogen.lastFPS + " UPS " + Endogen.lastUPS, 2, Endogen.HEIGHT - 2, 8, 12, -1, 0, Endogen.SystemAssets.mask.FONT1, new float[] { 1, 1, 1, 1 });
	}

}
