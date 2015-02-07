package com.endoplasm;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

public class Text {
	
	public static final int NUMCHARS = 91;

	/**
	 * Converts a String into an array of ints, for text encoding
	 * @param text The String to convert
	 * @return An int array representation of the text
	 */
	public static int[] StringtoInt(String text) {
		//if text == null return a blank space;
		if(text == null) return new int[]{90};
		
		char[] chars = text.toCharArray();
		int[] ints = new int[chars.length];

		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			switch(c){
			case '0': ints[i] = 0; break;
			case '1': ints[i] = 1; break;
			case '2': ints[i] = 2; break;
			case '3': ints[i] = 3; break;
			case '4': ints[i] = 4; break;
			case '5': ints[i] = 5; break;
			case '6': ints[i] = 6; break;
			case '7': ints[i] = 7; break;
			case '8': ints[i] = 8; break;
			case '9': ints[i] = 9; break;
			case '\\': ints[i] = 10; break;
			case '-': ints[i] = 11; break;
			case '+': ints[i] = 12; break;
			case '/': ints[i] = 13; break;
			case '*': ints[i] = 14; break;
			case '=': ints[i] = 15; break;
			case '%': ints[i] = 16; break;
			case '^': ints[i] = 17; break;
			case '<': ints[i] = 18; break;
			case '>': ints[i] = 19; break;
			case '"': ints[i] = 20; break;
			case '\'': ints[i] = 21; break;
			case '(': ints[i] = 22; break;
			case ')': ints[i] = 23; break;
			case '{': ints[i] = 24; break;
			case '}': ints[i] = 25; break;
			case '[': ints[i] = 26; break;
			case ']': ints[i] = 27; break;
			case '.': ints[i] = 28; break;
			case ',': ints[i] = 29; break;
			case '!': ints[i] = 30; break;
			case '?': ints[i] = 31; break;
			case '#': ints[i] = 32; break;
			case ':': ints[i] = 33; break;
			case ';': ints[i] = 34; break;
			case '$': ints[i] = 35; break;
			case '@': ints[i] = 36; break;
			case '~': ints[i] = 37; break;
			case 'a': ints[i] = 38; break;
			case 'b': ints[i] = 39; break;
			case 'c': ints[i] = 40; break;
			case 'd': ints[i] = 41; break;
			case 'e': ints[i] = 42; break;
			case 'f': ints[i] = 43; break;
			case 'g': ints[i] = 44; break;
			case 'h': ints[i] = 45; break;
			case 'i': ints[i] = 46; break;
			case 'j': ints[i] = 47; break;
			case 'k': ints[i] = 48; break;
			case 'l': ints[i] = 49; break;
			case 'm': ints[i] = 50; break;
			case 'n': ints[i] = 51; break;
			case 'o': ints[i] = 52; break;
			case 'p': ints[i] = 53; break;
			case 'q': ints[i] = 54; break;
			case 'r': ints[i] = 55; break;
			case 's': ints[i] = 56; break;
			case 't': ints[i] = 57; break;
			case 'u': ints[i] = 58; break;
			case 'v': ints[i] = 59; break;
			case 'w': ints[i] = 60; break;
			case 'x': ints[i] = 61; break;
			case 'y': ints[i] = 62; break;
			case 'z': ints[i] = 63; break;
			case 'A': ints[i] = 64; break;
			case 'B': ints[i] = 65; break;
			case 'C': ints[i] = 66; break;
			case 'D': ints[i] = 67; break;
			case 'E': ints[i] = 68; break;
			case 'F': ints[i] = 69; break;
			case 'G': ints[i] = 70; break;
			case 'H': ints[i] = 71; break;
			case 'I': ints[i] = 72; break;
			case 'J': ints[i] = 73; break;
			case 'K': ints[i] = 74; break;
			case 'L': ints[i] = 75; break;
			case 'M': ints[i] = 76; break;
			case 'N': ints[i] = 77; break;
			case 'O': ints[i] = 78; break;
			case 'P': ints[i] = 79; break;
			case 'Q': ints[i] = 80; break;
			case 'R': ints[i] = 81; break;
			case 'S': ints[i] = 82; break;
			case 'T': ints[i] = 83; break;
			case 'U': ints[i] = 84; break;
			case 'V': ints[i] = 85; break;
			case 'W': ints[i] = 86; break;
			case 'X': ints[i] = 87; break;
			case 'Y': ints[i] = 88; break;
			case 'Z': ints[i] = 89; break;
			case ' ': ints[i] = 90; break;
			//default "?"
			default : ints[i] = 31;
			}
		}
		return ints;
	}
	
	/**
	 * Converts a thread into an array of Strings, each shorter then numperline
	 * @param full The full String to be chopped up
	 * @param numperline The Maximum number of characters per line
	 * @return an Array of strings storing all the characters of full, broken into peaces smaller then numperline
	 */
	public static String[] getLines(String full, int numperline){
		ArrayList<String> tl = new ArrayList<String>();
		int counter = 0;
		int lastEncountedGap = -1;
		boolean incountedGap = false;
		int lastB = 0;
		for(int i = 0; i < full.length(); i++){
			if(full.charAt(i) == ' '){
				lastEncountedGap = i;
				incountedGap = true;
			}
			if(counter >= numperline || full.charAt(i) == '\n'){
				int a = lastB;
				int b = incountedGap ? lastEncountedGap + 1: i;
				if(full.charAt(i) == '\n') b++;
				//System.out.println("a is " + a + ", b is " + b + ", Total is " + full.length());
				tl.add(full.substring(a, b));
				lastB = b;
				i = b;
				lastEncountedGap = b;
				incountedGap = false;
				counter = 0;
				tl.set(tl.size()-1, tl.get(tl.size()-1).replace('\n', ' '));
			}
			counter++;
		}
		tl.add(full.substring(lastB, full.length()));
		tl.set(tl.size()-1, tl.get(tl.size()-1).replace('\n', ' '));
		
		String[] lines = new String[tl.size()];
		tl.toArray(lines);
		return lines;
	}
	
	/**
	 * Renders text from a string, automatically breaks the String into lines, and applies a font and a colour.
	 * @param string The String to be rendered
	 * @param x The x location of the top left corner of the first letter
	 * @param y The y location of the top left corner of the first letter
	 * @param width The width of every character, note that this is scaled for individual charRs widthFactor
	 * @param height The Height of every character
	 * @param numperline The maximum number of characters per line
	 * @param lineGap How much space between lines of text
	 * @param font The font AssetObject to be used, default fonts can be found in SystemAssets.Mask
	 * @param colour An array of 4 floats to be used to colour the array, in RGBA style.
	 */
	public static void renderTextFromString(String string, float x, float y, float width, float height, int numperline, float lineGap, FontAsset font, float[] colour){
		if(string == null) return;
		if(width == 0) return;
		if(height == 0) return;
		if(numperline <= 0) numperline = string.length() + 1;
		if(font == null || font.TEXASSET == null || font.CHARS == null){
			System.err.println("Bad font used when rendering string " + string);
		}
		if(colour == null) colour = new float[]{1, 1, 1, 1};
		String[] lines = getLines(string, numperline);
		
		glPushMatrix();
		glColor4f(colour[0], colour[1], colour[2], colour[3]);
		font.TEXASSET.TEX.bind();
		glTranslatef(x, y, 0);
		for(String line: lines){
			glPushMatrix();
			int[] ints = StringtoInt(line);
			for(int c: ints){
				renderChar(c, width, height, font);
				glTranslatef(width * font.SPACING[c], 0, 0);
			}
			glPopMatrix();
			glTranslatef(0, -height - lineGap, 0);
		}
		glPopMatrix();
		
	}
	
	private static void renderChar(int i, float width, float height, FontAsset font){
		CharR cr = font.CHARS[i];
		glBegin(GL_QUADS);
		{
			glTexCoord2f(cr.u1, cr.v1);
			glVertex2f(0, 0);
			glTexCoord2f(cr.u2, cr.v1);
			glVertex2f(width, 0);
			glTexCoord2f(cr.u2, cr.v2);
			glVertex2f(width, -height);
			glTexCoord2f(cr.u1, cr.v2);
			glVertex2f(0, -height);
		}
		glEnd();
	}
}
