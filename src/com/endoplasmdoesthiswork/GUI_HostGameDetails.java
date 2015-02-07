package com.endoplasmdoesthiswork;

import org.lwjgl.input.Cursor;

import com.endoplasm.Button;
import com.endoplasm.Endogen;
import com.endoplasm.GUI;
import com.endoplasm.GUIElement;
import com.endoplasm.GUIRect;
import com.endoplasm.NumFeild;
import com.endoplasm.Text;
import com.endoplasm.Vertex2f;
import com.endoplasmdoesthiswork.server.Server;

public class GUI_HostGameDetails extends GUIRect {

	public NumFeild IPFeild;
	public NumFeild PortFeild;
	public NumFeild NumPlayersField;
	public NumFeild SeedFeild;
	public Button Host;
	public Button Cancel;
	public String Error = "";

	public GUI_HostGameDetails(GUIElement parent) {
		super(parent, new Vertex2f(0, 20), new Vertex2f(300, 300));
		IPFeild = new NumFeild(this, new Vertex2f(5, -5), new Vertex2f(0, 0), 8, 12, "IP: ", "10.1.1.6");
		IPFeild.AllowFullStop = true;
		PortFeild = new NumFeild(this, new Vertex2f(5, -20), new Vertex2f(0, 0), 8, 12, "Port:", "65000");
		NumPlayersField = new NumFeild(this, new Vertex2f(5, -35), new Vertex2f(0, 0), 8, 12, "Max players: ", "10");
		SeedFeild = new NumFeild(this, new Vertex2f(5, -50), new Vertex2f(0, 0), 8, 12, "Island seed: ");
		Host = new Button(this, new Vertex2f(5, -65), new Vertex2f(32, 16), "Host");
		Cancel = new Button(this, new Vertex2f(5, -81), new Vertex2f(40, 16), "Cancel");
		children.add(IPFeild);
		children.add(PortFeild);
		children.add(NumPlayersField);
		children.add(SeedFeild);
		children.add(Host);
		children.add(Cancel);
	}

	@Override
	public Cursor getCursor() {
		return Endogen.SystemAssets.mask.CURSORCLICKABLE;
	}

	@Override
	public void render() {
		Text.renderTextFromString(Error, 5, -98, 8, 12, 50, 2, Endogen.SystemAssets.mask.FONT1, new float[] { 0.7f, 0.1f, 0.1f, 1 });
	}

	@Override
	public void update() {
		pos.setX(Endogen.WIDTH / 2 - 150);
		pos.setY(Endogen.HEIGHT / 2 + 150);

		if (Cancel.pressed) {
			System.out.println("Canceling game.");
			GUI.field.children.remove(this);
			GUI.field.children.add(Game.mainmenu);
		}

		if (Host.pressed) {
			Error = "";
			System.out.println("Details entered");

			int port;
			try {
				port = Integer.parseInt(PortFeild.VALUE);
			} catch (Exception e) {
				Error = "Invalid PORT";
				return;
			}
			if (port < 1025 || port > 65534) {
				Error = "Invalid PORT \nPORT must be in range 1024-65535";
				return;
			}
			int nump = 0;
			try{
			nump = Integer.parseInt(NumPlayersField.VALUE);
			} catch(NumberFormatException e){
				Error = "Invalid value";
				return;
			}
			
			Server server = new Server(port, nump);
			Thread serverThread = new Thread(server);
			serverThread.start();
		}
		
		if (Server.isServerRunning) {
			GUI.field.children.remove(this);
			GUI.field.children.add(Game.mainmenu);
			GUI.field.children.add(Game.servertracker);
		}

	}

}
