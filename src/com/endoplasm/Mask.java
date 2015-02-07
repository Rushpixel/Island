package com.endoplasm;

import org.lwjgl.input.Cursor;
import org.newdawn.slick.opengl.CursorLoader;

public class Mask extends AssetNodeGroup {
	
	public TextureAsset WINDOWFRAME = new TextureAsset(this, "/Resources/Textures/window_frame");
	public TextureAsset BLANK = new TextureAsset(this, "/Resources/Textures/blank");
	
	public Cursor CURSORDEFAULT;
	public Cursor CURSORCLICK;
	public Cursor CURSORCLICKABLE;
	public Cursor CURSORMOVE;
	public Cursor CURSORMOVEABLE;
	public Cursor CURSORINVISIBLE;
	
	public Mask(AssetNode PARENT) {
		super(PARENT);
		INDEX = new AssetNode[] {FONT1, WINDOWFRAME, BLANK };
		try{
		CURSORDEFAULT = CursorLoader.get().getCursor("Resources/Textures/cursor.png", 1, 1);
		CURSORCLICK = CursorLoader.get().getCursor("Resources/Textures/cursor_click.png", 1, 1);
		CURSORCLICKABLE = CursorLoader.get().getCursor("Resources/Textures/cursor_clickable.png", 1, 1);
		CURSORMOVE = CursorLoader.get().getCursor("Resources/Textures/cursor_move.png", 8, 8);
		CURSORMOVEABLE = CursorLoader.get().getCursor("Resources/Textures/cursor_moveable.png", 8, 8);
		CURSORINVISIBLE = CursorLoader.get().getCursor("Resources/Textures/cursor_invisible.png", 1, 1);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// the spacing data for the default font
	public final float[] FONT1SPACING = new float[] { 1, // 0
			0.75f, // 1
			1, // 2
			1, // 3
			1, // 4
			1, // 5
			1, // 6
			1, // 7
			1, // 8
			1, // 9
			1, // \\
			.75f, // -
			1, // +
			1, // /
			1, // *
			1, // =
			1, // %
			1, // ^
			1, // <
			1, // >
			1, // "
			.5f, // \'
			1, // (
			.75f, // )
			1, // {
			1, // }
			1, // [
			-.75f, // ]
			.5f, // .
			.5f, // ,
			.75f, // !
			1, // ?
			1, // #
			.5f, // :
			.5f, // ;
			1, // $
			1, // @
			1, // ~
			1, // a
			1, // b
			1, // c
			1, // d
			1, // e
			1, // f
			1, // g
			1, // h
			.5f, // i
			.75f, // j
			1, // k
			.75f, // l
			1, // m
			1, // n
			1, // o
			1, // p
			1, // q
			.75f, // r
			1, // s
			.75f, // t
			1, // u
			1, // v
			1, // w
			1, // x
			1, // y
			1, // z
			1, // A
			1, // B
			1, // C
			1, // D
			1, // E
			1, // F
			1, // G
			1, // H
			.5f, // I
			1, // J
			1, // K
			1, // L
			1, // M
			1, // N
			1, // O
			1, // P
			1, // Q
			1, // R
			1, // S
			1, // T
			1, // U
			1, // V
			1, // W
			1, // X
			1, // Y
			1, // Z
			1 //
	};
	
	public FontAsset FONT1 = new FontAsset(this, "/Resources/Textures/font1", 4, 6, 16, FONT1SPACING);

}
