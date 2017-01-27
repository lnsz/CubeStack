package cubestack;

import java.io.Serializable;

import processing.core.PApplet;

@SuppressWarnings("serial")
public class Diamond extends Cube implements Serializable{
	public Diamond(PApplet a, float x, float y, float z, float s, boolean str, boolean stat) {
		super(a, x, y, z, s, str, stat);
		setTexture(CubeStack.diamond,"top");
		setTexture(CubeStack.diamond,"bottom");
		setTexture(CubeStack.diamond,"left");
		setTexture(CubeStack.diamond,"right");
		setTexture(CubeStack.diamond,"front");
		setTexture(CubeStack.diamond,"back");
		setID(5);
	}
}
