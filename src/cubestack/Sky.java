package cubestack;

import java.io.Serializable;

import processing.core.PApplet;

@SuppressWarnings("serial")
public class Sky extends Cube implements Serializable{
	public Sky(PApplet a, float x, float y, float z, float s, boolean str, boolean stat) {
		super(a, x, y, z, s, str, stat);
		setTexture(CubeStack.sky,"top");
		setTexture(CubeStack.sky,"bottom");
		setTexture(CubeStack.sky,"left");
		setTexture(CubeStack.sky,"right");
		setTexture(CubeStack.sky,"front");
		setTexture(CubeStack.sky,"back");
	}

}
