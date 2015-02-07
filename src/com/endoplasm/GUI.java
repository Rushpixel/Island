package com.endoplasm;

import org.lwjgl.opengl.GL11;

public class GUI {
	
	/**
	 * The GUIFeild feild, this system owned GUIElement is the suggested parent of all other elements on the GUI tree.
	 */
	public static GUIField field;
	/**
	 * The GUIElement that the cursor is hovering over. Calculated before GInit.update.
	 */
	public static GUIElement ActiveElement;
	
	public GUI(){
		field = new GUIField(null);
		ActiveElement = field;
	}
	
	public void update(){
		calcActiveElement();
		updateTree(field);
	}
	
	public void calcActiveElement(){
		ActiveElement = null;
		GUIElement cActive = field;
		Vertex2f parentOffset = new Vertex2f(0, 0);
		while(ActiveElement == null){
			GUIElement tActive = null;
			for(GUIElement ge: cActive.children){
				if(ge.doesContain(parentOffset)) tActive = ge;
			}
			if(tActive != null){
				cActive = tActive;
				parentOffset.addX(cActive.pos.getX());
				parentOffset.addY(cActive.pos.getY());
			} else{
				ActiveElement = cActive;
			}
		}
	}
	
	public void updateTree(GUIElement top){
		top.update();
		for(int i = 0; i < top.children.size(); i++){
			updateTree(top.children.get(i));
		}
	}
	
	public void render(){
		GL11.glPushMatrix();
		renderTree(field);
		GL11.glPopMatrix();
	}
	
	public void renderTree(GUIElement top){
		GL11.glTranslatef(top.getPos().getX(), top.getPos().getY(), 0);
		top.render();
		for(GUIElement ge: top.children){
			renderTree(ge);
		}
		GL11.glTranslatef(-top.getPos().getX(), -top.getPos().getY(), 0);
	}

}
