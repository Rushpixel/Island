package com.endoplasm;

public abstract class AssetNodeGroup extends AssetNode{

	public AssetNodeGroup(AssetNode PARENT) {
		super(PARENT);
	}
	
	@Override
	public void load() {
		for(AssetNode Node: INDEX){
			Node.load();
		}
	}

	@Override
	public void unload() {
		for(AssetNode Node: INDEX){
			Node.unload();
		}
	}

}
