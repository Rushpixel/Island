package com.endoplasmdoesthiswork;

import com.endoplasm.AssetNode;
import com.endoplasm.AssetNodeGroup;
import com.endoplasm.TextureAsset;

public class Assets extends AssetNodeGroup{
	
	public TextureAsset BODY_BASE0 = new TextureAsset(this, "/Resources/Textures/body_base0");
	public TextureAsset HEAD_BASE0 = new TextureAsset(this, "/Resources/Textures/head_base0");

	public Assets() {
		super(null);
		INDEX = new AssetNode[]{
			BODY_BASE0,
			HEAD_BASE0,
		};
	}
	
	

}
