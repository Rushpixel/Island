package com.endoplasm;

public class SystemAssets {
	
	public Mask mask;;
	
	public SystemAssets(){
		mask = new Mask(null);
	}
	
	public void load(){
		mask.load();
	}
	
	public void unload(){
		mask.unload();
	}
	
	/**
	 * call this method to change the GUI mask
	 * @param mask the new Mask.
	 */
	public void switchMask(Mask mask){
		unload();
		this.mask = mask;
		load();
	}

}
