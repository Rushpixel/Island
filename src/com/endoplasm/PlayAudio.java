package com.endoplasm;

import paulscode.sound.SoundSystemConfig;

public class PlayAudio {

	public static String playTSound(AudioAsset audio, float volume, boolean toLoop, Vertex3f pos, Vertex3f vel, int aModel, float distOrRoll) {
		if (!Audio.isAudioWorking) return null;
		if (audio.PATH == null) return null;
		if (pos == null) pos = new Vertex3f(0, 0, 0);
		if (vel == null) vel = new Vertex3f(0, 0, 0);
		String source = Audio.soundsystem.quickPlay(false, audio.PATH, toLoop, pos.getX(), pos.getY(), pos.getZ(), aModel, distOrRoll);
		Audio.soundsystem.setVolume(source, volume);
		Audio.soundsystem.setVelocity(source, pos.getX(), pos.getY(), pos.getZ());
		return source;
	}

	public static String playSound(AudioAsset audio, float volume, boolean toLoop) {
		if (!Audio.isAudioWorking) return null;
		if (audio.PATH == null) return null;
		String source = Audio.soundsystem.quickStream(false, audio.PATH, toLoop, 0, 0, 0, SoundSystemConfig.ATTENUATION_NONE, 0);
		Audio.soundsystem.setVolume(source, volume);
		return source;
	}
	
	public static String playTSound(AudioAsset audio, float volume, boolean toLoop, Vertex3f pos, Vertex3f vel) {
		return playTSound(audio, volume, toLoop, pos, vel, Audio.aModel, Audio.afactor);
	}
	
	public static void setListener(Vertex3f pos, Vertex3f vel, Vertex3f look, Vertex3f up){
		if(pos != null) {
			Audio.lPos = pos;
		}
		if(vel != null) {
			Audio.lVel = vel;
		}
		if(look != null) {
			Audio.lLook = look;
		}
		if(up != null) {
			Audio.lUp = up;
		}
		Audio.setListener();
	}

}
