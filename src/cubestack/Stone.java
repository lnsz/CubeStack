package cubestack;

import java.io.Serializable;

import processing.core.PApplet;

@SuppressWarnings("serial")
public class Stone extends Cube implements Serializable{
	public Stone(PApplet a, float x, float y, float z, float s, boolean str, boolean stat) {
		super(a, x, y, z, s, str, stat);
		setTexture(CubeStack.stone,"top");
		setTexture(CubeStack.stone,"bottom");
		setTexture(CubeStack.stone,"left");
		setTexture(CubeStack.stone,"right");
		setTexture(CubeStack.stone,"front");
		setTexture(CubeStack.stone,"back");
		setID(2);
	}
}
