package com.endoplasm;

import paulscode.sound.Library;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryJavaSound;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

public class Audio {
	
	public static boolean aLCompatible;
	public static boolean jSCompatible;
	
	public static SoundSystem soundsystem;
	
	public static boolean isAudioWorking = false;
	
	public static int aModel = SoundSystemConfig.ATTENUATION_ROLLOFF;
	public static float afactor = SoundSystemConfig.getDefaultRolloff();
	
	public static float rollOff = SoundSystemConfig.getDefaultRolloff() / 4;
	
	public static Vertex3f lPos = new Vertex3f(0, 0, 0);
	public static Vertex3f lVel = new Vertex3f(0, 0, 0);
	public static Vertex3f lLook = new Vertex3f(1, 0, 0);
	public static Vertex3f lUp = new Vertex3f(0, 0, 1);
	
	public Audio(){
		try {
			SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class); // LWJGL OpenAL [prioritise]
			SoundSystemConfig.addLibrary(LibraryJavaSound.class ); // Java Sound [backup]
			SoundSystemConfig.setCodec( "wav", CodecWav.class ); // .wav CODEC

			aLCompatible = SoundSystem.libraryCompatible( LibraryLWJGLOpenAL.class );
			jSCompatible = SoundSystem.libraryCompatible( LibraryJavaSound.class );
			
			if( aLCompatible )
			    soundsystem = new SoundSystem(LibraryLWJGLOpenAL.class);   // OpenAL
			else if( jSCompatible )
				soundsystem = new SoundSystem(LibraryJavaSound.class);  // Java Sound
			else
				soundsystem = new SoundSystem(Library.class);  // "No Sound, Silent Mode"
			
			isAudioWorking = true;
			
			setListener();

		}
		catch( SoundSystemException e )
		{
		    e.printStackTrace();
		}
	}
	
	/**
	 * Use this to set where to take sounds from
	 * @param Path is a resource package that contains all the games audio
	 */
	public static void setResourcePath(String Path){
		SoundSystemConfig.setSoundFilesPackage(Path);
	}
	
	public static void setListener(){
		Audio.soundsystem.setListenerPosition(lPos.getX(), lPos.getY(), lPos.getZ()); 
		Audio.soundsystem.setListenerVelocity(lVel.getX(), lVel.getY(), lVel.getZ()); 
		Audio.soundsystem.setListenerOrientation(lLook.getX(), lLook.getY(), lLook.getZ(), lUp.getX(), lUp.getY(), lUp.getZ());
	}

}
