package com.endoplasm;

public class ModelAsset extends AssetNode {

	public String PATH;
	public Model MODEL;
	
	public ModelAsset(AssetNode PARENT, String PATH) {
		super(PARENT);
		this.PATH = PATH;
		this.NAME = PATH;
	}

	@Override
	public void load() {
		MODEL = ModelLoader.load(PATH);

	}

	@Override
	public void unload() {
		MODEL = null;
	}

}
