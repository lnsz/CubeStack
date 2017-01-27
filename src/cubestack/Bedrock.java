package cubestack;

import java.io.Serializable;

import processing.core.PApplet;

@SuppressWarnings("serial")
public class Bedrock extends Cube implements Serializable{
	public Bedrock(PApplet a, float x, float y, float z, float s, boolean str, boolean stat) {
		super(a, x, y, z, s, str, stat);
		setTexture(CubeStack.bedrock, "top");
		setTexture(CubeStack.bedrock, "bottom");
		setTexture(CubeStack.bedrock, "left");
		setTexture(CubeStack.bedrock, "right");
		setTexture(CubeStack.bedrock, "front");
		setTexture(CubeStack.bedrock, "back");
		setID(6);
	}
}
