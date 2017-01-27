package cubestack;

import java.io.Serializable;

import processing.core.PApplet;

@SuppressWarnings("serial")
public class Iron extends Cube implements Serializable{
	public Iron(PApplet a, float x, float y, float z, float s, boolean str, boolean stat) {
		super(a, x, y, z, s, str, stat);
		setTexture(CubeStack.iron,"top");
		setTexture(CubeStack.iron,"bottom");
		setTexture(CubeStack.iron,"left");
		setTexture(CubeStack.iron,"right");
		setTexture(CubeStack.iron,"front");
		setTexture(CubeStack.iron,"back");
		setID(3);
	}
}
