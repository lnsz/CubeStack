package cubestack;

import java.io.Serializable;

import processing.core.PApplet;

@SuppressWarnings("serial")
public class Dirt extends Cube implements Serializable{
	public Dirt(PApplet a, float x, float y, float z, float s, boolean str, boolean stat) {
		super(a, x, y, z, s, str, stat);
		setTexture(CubeStack.dirt,"top");
		setTexture(CubeStack.dirt,"bottom");
		setTexture(CubeStack.dirt,"left");
		setTexture(CubeStack.dirt,"right");
		setTexture(CubeStack.dirt,"front");
		setTexture(CubeStack.dirt,"back");
		setID(1);
	}
}
