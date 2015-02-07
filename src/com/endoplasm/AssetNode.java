package com.endoplasm;

public abstract class AssetNode {
	
	public String NAME = "UnNamedAsset";
	
	/**
	 * The INDEX is a list of all children nodes
	 */
	public AssetNode INDEX[];
	
	/**
	 * The PARENT is the parent node to this object
	 */
	public AssetNode PARENT;
	
	public AssetNode(AssetNode PARENT){
		this.PARENT = PARENT;
	}
	
	/**
	 * The load is not the init, but is called when the assets below this node should be brought into the RAM.
	 */
	public abstract void load();
	
	/**
	 * This method should be called whenever all the Assets below this node should be unloaded from the RAM
	 * It should be noted this call shouldn't remove the AssetNodes, just their resources
	 */
	public abstract void unload();
}
