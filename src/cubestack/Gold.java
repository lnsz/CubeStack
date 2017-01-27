package cubestack;

import java.io.Serializable;

import processing.core.PApplet;

@SuppressWarnings("serial")
public class Gold extends Cube implements Serializable{
	public Gold(PApplet a, float x, float y, float z, float s, boolean str, boolean stat) {
		super(a, x, y, z, s, str, stat);
		setTexture(CubeStack.gold,"top");
		setTexture(CubeStack.gold,"bottom");
		setTexture(CubeStack.gold,"left");
		setTexture(CubeStack.gold,"right");
		setTexture(CubeStack.gold,"front");
		setTexture(CubeStack.gold,"back");
		setID(4);
	}
}
