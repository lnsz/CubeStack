package cubestack;

import java.io.Serializable;

import processing.core.PApplet;

@SuppressWarnings("serial")
public class Grass extends Cube implements Serializable{

	public Grass(PApplet a, float x, float y, float z, float s, boolean str, boolean stat) {
		super(a, x, y, z, s, str, stat);
		setTexture(CubeStack.grass,"top");
		setTexture(CubeStack.dirt,"bottom");
		setTexture(CubeStack.dirt,"left");
		setTexture(CubeStack.dirt,"right");
		setTexture(CubeStack.dirt,"front");
		setTexture(CubeStack.dirt,"back");
		setID(0);
	}
}
