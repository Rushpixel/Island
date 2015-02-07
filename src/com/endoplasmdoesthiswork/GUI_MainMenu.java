package com.endoplasmdoesthiswork;

import java.net.InetAddress;

import org.lwjgl.input.Cursor;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.endoplasm.Button;
import com.endoplasm.Endogen;
import com.endoplasm.GUI;
import com.endoplasm.GUIElement;
import com.endoplasm.GUIRect;
import com.endoplasm.Render2d;
import com.endoplasm.Vertex2f;
import com.endoplasmdoesthiswork.client.Client;
import com.endoplasmdoesthiswork.client.ClientHome;
import com.endoplasmdoesthiswork.server.Server;

public class GUI_MainMenu extends GUIRect{
	
	public Button Singleplayer;
	public Button Join;
	public Button Host;

	public GUI_MainMenu(GUIElement parent) {
		super(parent, new Vertex2f(0,20), new Vertex2f(300, 300));
		Singleplayer = new Button(this, new Vertex2f(5, -5), new Vertex2f(290, 40), "Single Player");
		Join = new Button(this, new Vertex2f(5, -50), new Vertex2f(290, 40), "Join Game");
		Host = new Button(this, new Vertex2f(5, -95), new Vertex2f(290, 40), "Host Game");
		children.add(Singleplayer);
		children.add(Join);
		children.add(Host);
	}

	@Override
	public Cursor getCursor() {
		return Endogen.SystemAssets.mask.CURSORDEFAULT;
	}

	@Override
	public void render() {
		Render2d.square(0, 0, dimensions.getX(), -dimensions.getY(), new float[]{0,0,1,0.3f}, Endogen.SystemAssets.mask.BLANK);
	}

	@Override
	public void update() {
		pos.setX(Endogen.WIDTH / 2 - 150);
		pos.setY(Endogen.HEIGHT / 2 + 150);
	
		if(Join.pressed) {
			System.out.println("Joining a game...");
			GUI.field.children.remove(this);
			GUI.field.children.add(Game.joingame);
		}
		
		if(Host.pressed) {
			System.out.println("Hosting a game...");
			GUI.field.children.remove(this);
			GUI.field.children.add(Game.hostgamedets);
		}
		
		if(Singleplayer.pressed) {
			try{
				// host
				Server server = new Server(65000, 1);
				Thread serverThread = new Thread(server);
				serverThread.start();
				//join
				Client client = new Client(InetAddress.getLocalHost().getHostAddress(), 65000, "Player");
				client.Begin();
				GUI.field.children.add(Game.clientwaiting);
				Game.clientgraph = new ClientHome();
				
				System.out.println("Creating singleplayer game...");
				GUI.field.children.remove(this);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}
