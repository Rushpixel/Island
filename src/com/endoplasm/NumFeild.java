package com.endoplasm;

import org.lwjgl.input.Cursor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class NumFeild extends Button {

	public boolean listening = false;
	public boolean wasKeyPressed = false;
	public String VALUE = "";
	public float width = 0;
	public float height = 0;

	public boolean AllowFullStop = false;
	public boolean AllowLetters = false;

	public NumFeild(GUIElement parent, Vertex2f pos, Vertex2f dimensions, float width, float height, String text) {
		super(parent, pos, dimensions, text);
		this.width = width;
		this.height = height;
	}
	
	public NumFeild(GUIElement parent, Vertex2f pos, Vertex2f dimensions, float width, float height, String text, String Default) {
		super(parent, pos, dimensions, text);
		this.width = width;
		this.height = height;
		this.VALUE = Default;
	}

	@Override
	public void render() {
		if (hovering || listening) {
			Text.renderTextFromString(text + VALUE, 2, -2, width, height, -1, 0, Endogen.SystemAssets.mask.FONT1, new float[] { 1, 1, 1, 1 });
		} else {
			Text.renderTextFromString(text + VALUE, 2, -2, width, height, -1, 0, Endogen.SystemAssets.mask.FONT1, new float[] { 0.6f, 0.6f, 0.6f, 1 });
		}
	}

	@Override
	public void update() {
		super.update();

		listening = Mouse.isButtonDown(0) ? hovering : listening;

		if (listening) {

			if (Keyboard.isKeyDown(Keyboard.KEY_0)) {
				if (!wasKeyPressed) VALUE = VALUE + "0";
				wasKeyPressed = true;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
				if (!wasKeyPressed) VALUE = VALUE + "1";
				wasKeyPressed = true;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
				if (!wasKeyPressed) VALUE = VALUE + "2";
				wasKeyPressed = true;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_3)) {
				if (!wasKeyPressed) VALUE = VALUE + "3";
				wasKeyPressed = true;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_4)) {
				if (!wasKeyPressed) VALUE = VALUE + "4";
				wasKeyPressed = true;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_5)) {
				if (!wasKeyPressed) VALUE = VALUE + "5";
				wasKeyPressed = true;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_6)) {
				if (!wasKeyPressed) VALUE = VALUE + "6";
				wasKeyPressed = true;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_7)) {
				if (!wasKeyPressed) VALUE = VALUE + "7";
				wasKeyPressed = true;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_8)) {
				if (!wasKeyPressed) VALUE = VALUE + "8";
				wasKeyPressed = true;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_9)) {
				if (!wasKeyPressed) VALUE = VALUE + "9";
				wasKeyPressed = true;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
				if (!wasKeyPressed && VALUE.length() > 0) VALUE = VALUE.substring(0, VALUE.length() - 1);
				wasKeyPressed = true;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_PERIOD) && AllowFullStop) {
				if (!wasKeyPressed) VALUE = VALUE + ".";
				wasKeyPressed = true;
			} else {
				wasKeyPressed = false;
			}

		} else
			wasKeyPressed = false;

		dimensions = new Vertex2f((text + VALUE).length() * width, height);
	}

}
