package com.endoplasm;

import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;

public class Button extends GUIRect {

	public String text;
	public boolean hovering = false;
	public boolean clicking = false;
	public boolean pressed = false;

	public Button(GUIElement parent, Vertex2f pos, Vertex2f dimensions, String text) {
		super(parent, pos, dimensions);
		this.text = text;
	}

	@Override
	public Cursor getCursor() {
		return Endogen.SystemAssets.mask.CURSORMOVEABLE;
	}

	@Override
	public void render() {
		if (hovering) {
			Render2d.square(0, 0, dimensions.getX(), -dimensions.getY(), new float[] { 1, 1, 1, 1 }, Endogen.SystemAssets.mask.BLANK);
			Text.renderTextFromString(text, 2, -2, dimensions.getX() / text.length(), dimensions.getY() - 4, -1, 0, Endogen.SystemAssets.mask.FONT1, new float[] { 0, 0, 0, 1 });
		} else {
			Render2d.square(0, 0, dimensions.getX(), -dimensions.getY(), new float[] { 0.9f, 0.9f, 0.9f, 1 }, Endogen.SystemAssets.mask.BLANK);
			Text.renderTextFromString(text, 2, -2, dimensions.getX() / text.length(), dimensions.getY() - 4, -1, 0, Endogen.SystemAssets.mask.FONT1, new float[] { 0, 0, 0, 1 });
		}
	}

	@Override
	public void update() {
		pressed = false;
		pressed = hovering && clicking && !Mouse.isButtonDown(0);
		hovering = GUI.ActiveElement == this;
		clicking = Mouse.isButtonDown(0);
	}

}
