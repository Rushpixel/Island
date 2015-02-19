package com.endoplasmdoesthiswork;

import java.util.ArrayList;
import java.util.Random;

import com.endoplasm.Endogen;
import com.endoplasm.MathUtil;
import com.endoplasm.RectangleMath;
import com.endoplasm.Render2d;
import com.endoplasm.Vertex2f;

public class Island extends StateNode {
	private Random r;
	public int numBegin = 7;
	public int numProccesses = 4;
	public float minSliceSize = 100;
	public float maxSliceSize = 800;
	public float minBeachSize = 50;
	public int chunkDivide = 4;

	public float sidelengths[];
	public float beachlengths[];
	public Triangle3f[] sand;
	public Triangle3f[] grass;

	public Island(StateNode parent) {
		super(parent, "I");
	}

	public void Generate(long seed) {
		r = new Random();

		// generate random sidelengths
		sidelengths = new float[numBegin];
		for (int i = 0; i < numBegin; i++) {
			sidelengths[i] = r.nextFloat() * (maxSliceSize - minSliceSize) + minSliceSize;
		}

		// generate beachlengths from sideslengths
		beachlengths = new float[numBegin];
		for (int i = 0; i < numBegin; i++) {
			float largest = sidelengths[i == 0 ? sidelengths.length - 1 : i - 1] + sidelengths[(i + 1) % sidelengths.length] + sidelengths[i];
			largest /= 3;
			if (largest < sidelengths[i] + minBeachSize) {
				largest = sidelengths[i] + minBeachSize;
				// System.out.println("i " + i + " is equal to sidelengths[i] + minbeachsize");
			}
			beachlengths[i] = largest;
		}

		// tween sidelengths
		sidelengths = tween(sidelengths, numProccesses, r);

		// tween beachlengths
		beachlengths = tween(beachlengths, numProccesses, r);

		// smooth sidelengths
		sidelengths = smooth(sidelengths, 1);

		// smooth beachlengths
		beachlengths = smooth(beachlengths, 3);

		// make sure tweened beachlength is always larger then sidelengths
		for (int i = 0; i < beachlengths.length; i++) {
			if (beachlengths[i] < sidelengths[i] + minBeachSize) beachlengths[i] = sidelengths[i] + minBeachSize;
		}

		// generate grass from sidelengths
		float sliceAngle = 360f / sidelengths.length;
		grass = new Triangle3f[sidelengths.length];
		for (int i = 0; i < sidelengths.length; i++) {
			int nextSl = ((i + 1) % sidelengths.length);
			Vertex2f c1 = new Vertex2f(0, 0);
			float a1 = sliceAngle * i;
			Vertex2f c2 = new Vertex2f(MathUtil.getXSpeed(a1, sidelengths[i]), MathUtil.getYSpeed(a1, sidelengths[i]));
			float a2 = sliceAngle * nextSl;
			Vertex2f c3 = new Vertex2f(MathUtil.getXSpeed(a2, sidelengths[nextSl]), MathUtil.getYSpeed(a2, sidelengths[nextSl]));
			grass[i] = new Triangle3f(c1, c2, c3);
			// System.out.println("a1 = " + a1 + ", a2 = " + a2);
		}

		// generate sand from beachlengths
		sand = new Triangle3f[beachlengths.length * 2];
		for (int i = 0; i < sidelengths.length; i++) {
			{
				int nextSl = ((i + 1) % beachlengths.length);
				float a1 = sliceAngle * i;
				float a2 = sliceAngle * nextSl;
				Vertex2f c1 = new Vertex2f(MathUtil.getXSpeed(a1, sidelengths[i]), MathUtil.getYSpeed(a1, sidelengths[i]));
				Vertex2f c2 = new Vertex2f(MathUtil.getXSpeed(a1, beachlengths[i] * 2), MathUtil.getYSpeed(a1, beachlengths[i] * 2));
				Vertex2f c3 = new Vertex2f(MathUtil.getXSpeed(a2, beachlengths[nextSl] * 2), MathUtil.getYSpeed(a2, beachlengths[nextSl] * 2));
				sand[i * 2] = new Triangle3f(c1, c2, c3);
			}
			{
				int nextSl = ((i + 1) % beachlengths.length);
				float a1 = sliceAngle * i;
				float a2 = sliceAngle * nextSl;
				Vertex2f c1 = new Vertex2f(MathUtil.getXSpeed(a2, beachlengths[nextSl] * 2), MathUtil.getYSpeed(a2, beachlengths[nextSl] * 2));
				Vertex2f c2 = new Vertex2f(MathUtil.getXSpeed(a1, sidelengths[i]), MathUtil.getYSpeed(a1, sidelengths[i]));
				Vertex2f c3 = new Vertex2f(MathUtil.getXSpeed(a2, sidelengths[nextSl]), MathUtil.getYSpeed(a2, sidelengths[nextSl]));
				sand[i * 2 + 1] = new Triangle3f(c1, c2, c3);
			}
		}

		// breakup grass to chunks
		grass = breakup(grass, chunkDivide);

		// breakup sand to chunks
		sand = breakup(sand, chunkDivide / 2);

		System.out.println("Finished generation with " + grass.length + " chunks of grass, and " + sand.length + " chunks of sand");
	}

	public static float[] tween(float[] array, int numProccesses, Random r) {
		for (int ip = 0; ip < numProccesses; ip++) {
			float[] oldSides = array;
			array = new float[oldSides.length * 2];
			for (int i = 0; i < array.length; i++) {
				if (i % 2 == 0) {
					array[i] = oldSides[i / 2];
				}
			}
			for (int i = 0; i < array.length; i++) {
				if (i % 2 == 1) {
					int prevI = i == 0 ? array.length - 1 : i - 1;
					int nextI = (i + 1) % array.length;
					float range = Math.abs(array[nextI] - array[prevI]);
					array[i] = MathUtil.getMin(new float[] { array[prevI], array[nextI] }) + r.nextFloat() * range;
				}
			}
		}
		return array;
	}

	public static float[] smooth(float[] array, int numSmooth) {
		while (numSmooth > 0) {
			for (int i = 0; i < array.length; i++) {
				float average = array[i == 0 ? array.length - 1 : i - 1] + array[(i + 1) % array.length] + array[i];
				average /= 3;
				array[i] = average;
			}
			numSmooth--;
		}
		return array;
	}

	public static Triangle3f[] breakup(Triangle3f[] array, int numBreaks) {
		ArrayList<Triangle3f> stack = new ArrayList<Triangle3f>();

		for (int i = 0; i < array.length; i++) {
			// if(stack.size() > maxChunkDivide) break;
			Triangle3f t = array[i];
			float totalArea = RectangleMath.getArea(t.a, t.b) / 2;
			// System.out.println("At i " + i + "The stack size is " + stack.size() + " and the total area is " + totalArea);
			breakup(t, numBreaks, stack);
		}

		array = new Triangle3f[stack.size()];
		stack.toArray(array);
		return array;
	}

	public static void breakup(Triangle3f t, int numBreaks, ArrayList<Triangle3f> stack) {
		float totalArea = RectangleMath.getArea(t.a, t.b) / 2;
		// System.out.println("Broken into " + numBreaks + " peices");
		float[] ratios = new float[numBreaks + 1];
		for (int i = 0; i <= numBreaks; i++) {
			ratios[i] = (1f / numBreaks) * i;
		}
		Vertex2f[] vs = new Vertex2f[numBreaks * 2 + 1];
		float ABdir = MathUtil.direction(t.c1.getX(), t.c1.getY(), t.c2.getX(), t.c2.getY());
		float ABdist = MathUtil.distance(t.c1.getX(), t.c1.getY(), t.c2.getX(), t.c2.getY());
		float ACdir = MathUtil.direction(t.c1.getX(), t.c1.getY(), t.c3.getX(), t.c3.getY());
		float ACdist = MathUtil.distance(t.c1.getX(), t.c1.getY(), t.c3.getX(), t.c3.getY());
		vs[0] = t.c1;
		for (int i = 1; i < ratios.length; i++) {
			vs[i * 2 - 1] = new Vertex2f(t.c1.getX() - MathUtil.getXSpeed(ABdir, ABdist * ratios[i]), t.c1.getY() - MathUtil.getYSpeed(ABdir, ABdist * ratios[i]));
			vs[i * 2] = new Vertex2f(t.c1.getX() - MathUtil.getXSpeed(ACdir, ACdist * ratios[i]), t.c1.getY() - MathUtil.getYSpeed(ACdir, ACdist * ratios[i]));
		}
		// string vertices together
		stack.add(new Triangle3f(new Vertex2f(vs[0].getX(), vs[0].getY()), new Vertex2f(vs[1].getX(), vs[1].getY()), new Vertex2f(vs[2].getX(), vs[2].getY())));
		for (int i = 1; i < numBreaks; i++) {
			Vertex2f A = vs[i * 2 - 1];
			Vertex2f B = vs[i * 2];
			Vertex2f C = vs[i * 2 + 1];
			Vertex2f D = vs[i * 2 + 2];
			stack.add(new Triangle3f(new Vertex2f(A.getX(), A.getY()), new Vertex2f(B.getX(), B.getY()), new Vertex2f(C.getX(), C.getY())));
			stack.add(new Triangle3f(new Vertex2f(B.getX(), B.getY()), new Vertex2f(C.getX(), C.getY()), new Vertex2f(D.getX(), D.getY())));
		}
	}

	@Override
	public void render() {
		Render2d.uniSquare(Game.camera.centre.getX() - Game.camera.horSize / 2, Game.camera.centre.getY() - Game.camera.verSize / 2, Game.camera.horSize, Game.camera.verSize, new float[] { 0.05f, 0.15f, 0.2f, 1 }, Endogen.SystemAssets.mask.BLANK, 16);
		int count = 0;
		for (Triangle3f t : sand) {
			if (RectangleMath.doCollide(t.a, t.b, Game.camera.a, Game.camera.b)) {
				count++;
				Render2d.uniTriangle(t.c1.getX(), t.c1.getY(), t.c2.getX(), t.c2.getY(), t.c3.getX(), t.c3.getY(), new float[] { 1, 1, 1, 1 }, Game.assets.GROUND_SAND, 16);
			}
		}
		for (Triangle3f t : grass) {
			if (RectangleMath.doCollide(t.a, t.b, Game.camera.a, Game.camera.b)) {
				count++;
				Render2d.uniTriangle(t.c1.getX(), t.c1.getY(), t.c2.getX(), t.c2.getY(), t.c3.getX(), t.c3.getY(), new float[] { 1, 1, 1, 1 }, Game.assets.GROUND_GRASS, 16);
			}
		}
		// System.out.println(count);
	}

}
