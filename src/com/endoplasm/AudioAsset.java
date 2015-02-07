package com.endoplasm;

public class AudioAsset extends AssetNode {
	
	protected String PATH;

	public AudioAsset(AssetNode PARENT, String PATH) {
		super(PARENT);
		this.PATH = PATH;
		INDEX = new AssetNode[0];
	}

	@Override
	public void load() {
		if(Audio.isAudioWorking){
			Audio.soundsystem.loadSound(PATH);
		} else{
			System.err.println("Attempted to load audio, but SoundSystem is not working, or has not been initialised yet");
		}
	}

	@Override
	public void unload() {
		if(Audio.isAudioWorking){
			Audio.soundsystem.unloadSound(PATH);
		} else{
			System.err.println("Attempted to unload audio, but SoundSystem is not working, or has been removed");
		}
	}

}
