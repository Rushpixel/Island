package com.endoplasm;

public class FontAsset extends AssetNode {

	public String PATH;
	public CharR[] CHARS;
	public float[] SPACING;

	public TextureAsset TEXASSET;

	private int charWidth;
	private int numColumn;
	private int charHeight;

	/**
	 * The Font Asset is used to store fonts for rendering text
	 * @param PARENT Parent Node
	 * @param PATH Path to the Font's PNG bitmap
	 * @param charWidth pixel Width of every character
	 * @param charHeight pixel Height of every character
	 * @param numColumn The number of characters per column in the bitmap
	 */
	public FontAsset(AssetNode PARENT, String PATH, int charWidth, int charHeight, int numColumn, float[] spacing) {
		super(PARENT);
		if(spacing == null) spacing = getFullSpacing();
		if(spacing.length < Text.NUMCHARS) {
			System.err.println("font given spacing array of invalid length,");
			System.err.println("length needed was" + Text.NUMCHARS + ", spacing given " + spacing.length);
			spacing = getFullSpacing();
		}
		
		TEXASSET = new TextureAsset(this, PATH);
		INDEX = new AssetNode[] { TEXASSET };
		
		NAME = PATH;
		this.PATH = PATH;
		this.charWidth = charWidth;
		this.charHeight = charHeight;
		this.numColumn = numColumn;
		SPACING = spacing;
	}

	@Override
	public void load() {
		// load Texture
		TEXASSET.load();
		
		//load charRs
		CHARS = new CharR[Text.NUMCHARS];
		float cw = (float)charWidth / (float)TEXASSET.TEX.getImageWidth();
		float ch = (float)charHeight / (float)TEXASSET.TEX.getImageHeight();
		for(int i = 0; i < Text.NUMCHARS; i++){
			float u1 = (i % numColumn) * cw;
			float v1 = (i / numColumn) * ch;
			float u2 = u1 + cw;
			float v2 = v1 + ch;
			CHARS[i] = new CharR(u1, v1, u2, v2, SPACING[i]);
		}
	}

	@Override
	public void unload() {
		//unload texture Asset
		TEXASSET.unload();
		
		//unload CharRs
		CHARS = null;
	}
	
	public static float[] getFullSpacing(){
		float[] spacing = new float[Text.NUMCHARS];
		for(int i = 0; i < spacing.length; i++){
			spacing[i] = 1;
		}
		return spacing;
	}

}
