package com.endoplasm;

import org.newdawn.slick.opengl.Texture;

public class TextureAsset extends AssetNode{
	
	public Texture TEX = null;
	public String PATH;

	public TextureAsset(AssetNode PARENT, String PATH) {
		super(PARENT);
		this.NAME = PATH;
		this.PATH = PATH;
		INDEX = new AssetNode[0];
	}

	/**
	 * load the Texture into OPENGL via SpriteLoader and the Slick Texture class
	 */
	@Override
	public void load() {
		TEX = SpriteLoader.loadTexture(PATH);
	}

	/**
	 * Removes texture from openGL, and sets TEX to null
	 */
	@Override
	public void unload() {
		SpriteLoader.unloadTexture(TEX);
		TEX = null;
	}

}
