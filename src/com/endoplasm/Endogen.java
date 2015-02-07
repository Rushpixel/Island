package com.endoplasm;

import static org.lwjgl.opengl.EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.GL_FRAMEBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.GL_RENDERBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindFramebufferEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindRenderbufferEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glFramebufferRenderbufferEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glFramebufferTexture2DEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glGenFramebuffersEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glGenRenderbuffersEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glRenderbufferStorageEXT;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;

public class Endogen {

	public static int WIDTH = 512;
	public static int HEIGHT = 288;
	private static boolean matchDisplay = false;
	public static boolean needsResize = true;

	public static Endogen endogen;
	public static ExceptionHandler exceptionhandler;

	public static GInit game;
	public static Manager Manager = new Manager();
	public static Camera camera;
	public static SystemAssets SystemAssets;
	public static Audio Audio;
	public static GUI GUI;
	public static GameMouse mouse;

	public static boolean buffersInit = false;
	public static int colorTextureID;
	public static int framebufferID;
	public static int depthRenderBufferID;

	public final static int UPS = 60;
	public static int lastFPS = 0;
	public static int lastUPS = 0;

	/**
	 * In the constructor, the System is init, the game is init, then the
	 * gameloop begins.
	 * 
	 * @param game
	 *            a class that extends GInit, which will handle the game.
	 */
	public Endogen(GInit game) {
		endogen = this;
		Endogen.game = game;

		System.out.println("Setting up ExceptionHandler");
		// Debug init
		initExceptionHandler();
	}

	public void Begin() {

		// System init
		printSystemHeader();
		createDisplay();
		initOpenGL();
		initFBO();
		initSystemAssets();
		initGUI();
		initAudio();

		// Game
		printGInitHeader();
		game.init();
		Manager.GameLoop();
		end();
	}

	private void initExceptionHandler() {
		exceptionhandler = new ExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(exceptionhandler);
	}

	/**
	 * This method is used to init the LWJGL Display
	 */
	private void createDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(512 * 3, 288 * 3));
			Display.setVSyncEnabled(false);
			Display.setResizable(true);
			Display.setTitle("RPG");
			Display.create(new PixelFormat(0, 8, 0, 4));
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * This method inits OpenGL via LWJGL
	 */
	private void initOpenGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glMatrixMode(GL_MODELVIEW);

		glClearColor(0, 0, 0, 0);

		glDisable(GL_DEPTH_TEST);

		glEnable(GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		// init camera
		camera = new Camera();

		// TODO add shader init stuff here
	}

	void initFBO() {
		if (!GLContext.getCapabilities().GL_EXT_framebuffer_object) {
			exceptionhandler.ErrorMessage("This Computer does not support OpenGL FBOs");
		} else {

			System.out.println("FBO is supported");

			// init our fbo
			if (!buffersInit) {
				framebufferID = glGenFramebuffersEXT();
				colorTextureID = glGenTextures();
				depthRenderBufferID = glGenRenderbuffersEXT();
				buffersInit = true;
			}

			System.out.println("Frame buffer ID = " + framebufferID);

			glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, framebufferID);

			// initialize color texture
			glBindTexture(GL_TEXTURE_2D, colorTextureID);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, WIDTH, HEIGHT, 0, GL_RGBA, GL_INT, (java.nio.ByteBuffer) null);
			glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL_COLOR_ATTACHMENT0_EXT, GL_TEXTURE_2D, colorTextureID, 0);

			// initialize depth renderbuffer
			glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, depthRenderBufferID);
			glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL14.GL_DEPTH_COMPONENT24, WIDTH, HEIGHT);
			glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT, GL_DEPTH_ATTACHMENT_EXT, GL_RENDERBUFFER_EXT, depthRenderBufferID);

			glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
		}
	}

	private void initSystemAssets() {
		SystemAssets = new SystemAssets();
		SystemAssets.load();
	}

	private void initGUI() {
		GUI = new GUI();
		mouse = new GameMouse();
	}

	private void initAudio() {
		Audio = new Audio();
	}

	/**
	 * Print RAM info to console
	 */
	public static void printRAM() {
		int kb = 1024;
		// Getting the runtime reference from system
		Runtime runtime = Runtime.getRuntime();
		System.out.println();
		System.out.println("HEAP DATA for JVM");
		// Print used memory
		System.out.println("Used Memory: " + (runtime.totalMemory() - runtime.freeMemory()) / kb + "KB");
		// Print free memory
		System.out.println("Free Memory: " + runtime.freeMemory() / kb + "KB");
		// Print total available memory
		System.out.println("Total Memory: " + runtime.totalMemory() / kb + "KB");
		// Print Maximum available memory
		System.out.println("Max Memory: " + runtime.maxMemory() / kb + "KB");

		System.out.println();
	}

	private void printSystemHeader() {
		System.out.println("************** Started loading Endogen System **************");
		System.out.println("Endogen copyright to Endoplasm Gaming 2014");
		printRAM();
	}

	private void printGInitHeader() {
		System.out.println();
		System.out.println("********************** Calling GInit **********************");
		System.out.println();
	}

	/**
	 * Kills and cleans up System
	 */
	public static void end() {
		game.cleanup();
		com.endoplasm.Audio.soundsystem.cleanup();
		Display.destroy();
		System.exit(0);
	}

	public static boolean isMatchDisplay() {
		return matchDisplay;
	}

	public static void setMatchDisplay(boolean matchDisplay) {
		Endogen.needsResize = true;
		Endogen.matchDisplay = matchDisplay;
		if (matchDisplay) System.out.println("Endogen changed to matchDisplay mode, the WIDTH and HEIGHT attributes will now be set to the Dispays size");
		if (!matchDisplay) System.out.println("Endogen changed to dontMatchDisplay mode, the WIDTH and HEIGHT attributes need to be set manually if you want them to change " + "/nDon't forget to set needsResize to true once you've done this.");
	}

}
