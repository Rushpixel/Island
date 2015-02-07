package com.endoplasm;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class SpriteLoader {

	public static Texture loadTexture(String key) {
		Texture texture = null;
		System.out.println("loading image " + key);
		try {
			InputStream is = SpriteLoader.class.getResourceAsStream(key + ".png");
			texture = TextureLoader.getTexture(".png", is);
			texture.setTextureFilter(GL_NEAREST);
		} catch (FileNotFoundException e) {
			Endogen.exceptionhandler.ErrorMessage("Texture key " + key + " was improperly named or does not exist");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("texture '" + key + "' loaded [" + texture.getImageWidth() + "," + texture.getImageHeight() + "]");
		return texture;
	}
	
	public static void unloadTexture(Texture tex){
		if(tex == null) return;
		glDeleteTextures(tex.getTextureID());
	}

	public static BufferedImage loadImage(File file) {
		try {

			return ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage loadImage(InputStream is) {
		try {

			return ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}