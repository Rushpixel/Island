package com.endoplasm;

public abstract class GUIRect extends GUIElement{
	
	public Vertex2f dimensions;

	public GUIRect(GUIElement parent, Vertex2f pos, Vertex2f dimensions) {
		super(parent);
		this.pos = pos;
		this.dimensions = dimensions;
	}

	@Override
	public boolean doesContain(Vertex2f ParentOffset) {
		return RectangleMath.doesContain(VectorMath.subtract(Endogen.mouse.pos, ParentOffset),VectorMath.add(pos, new Vertex2f(0, -dimensions.getY())), VectorMath.add(pos, new Vertex2f(dimensions.getX(),0)));
	}

}
