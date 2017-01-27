package cubestack;

import processing.core.*;

public class Camera {
	private float rotationAngle; //Rotation angle of the mouse
	private float elevationAngle; //Elevation angle of the mouse
	private float centerX = 0; //Point where the camera is looking at
	private float centerY = 0;
	private float centerZ = 0;
	private PVector position; //Position of the camera
	private PApplet game; //For using the PApplet methods
	private PVector[] equation; //Vector equation of line between 
	//camera position and the point where the camera is looking at
	private boolean lights; //Lights on/off

	/**
	 * Initializes the variables
	 * @param a
	 */
	public Camera(PApplet a) {
		game = a;
		position = new PVector(0, 0, 0);
		//equation [0] is the point vector
		//equation [1] is the direction vector, not currently being used
		//equation [2] is the direction vector based on current position
		equation = new PVector[3];
		lights = true;
	}

	/**
	 * Getter method for position
	 * @return
	 */
	public PVector getPosition() {
		return (position);
	}
	
	/**
	 * Setter method for position
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setPosition(float x, float y, float z) {
		position.x = x;
		position.y = y;
		position.z = z;
	}

	/**
	 * Getter method for rotation angle
	 * @return
	 */
	public float getRotationAngle() {
		return (rotationAngle);
	}
	
	/**
	 * Getter method for elevation angle
	 * @return
	 */
	public float getElevationAngle() {
		return (elevationAngle);
	}

	public float getCenterX() {
		return (centerX);
	}

	public float getCenterY() {
		return (centerY);
	}

	public float getCenterZ() {
		return (centerZ);
	}
	/**
	 * Updates the camera
	 */
	public void updateCamera() {
		rotationAngle = PApplet.map(game.width - game.mouseX, 0, game.width, 0,
				(float) (2 * Math.PI));
		elevationAngle = PApplet.map(game.mouseY, 0, game.height, (float) 0.1,
				(float) Math.PI);
		centerX = (float) (Math.cos(rotationAngle) * Math.sin(elevationAngle));
		centerY = (float) (Math.sin(rotationAngle) * Math.sin(elevationAngle));
		centerZ = (float) -Math.cos(elevationAngle);
		game.camera(position.x, position.y, position.z, position.x + centerX,
				position.y + centerY, position.z + centerZ, 0, 0, 1);
		if (lights == true) {
			game.lightFalloff((float) 0.7, 0, 0);
			game.spotLight(255, 255, 255, position.x, position.y, position.z,
					position.x + centerX, position.y + centerY, position.z
							+ centerZ, PConstants.PI * 2, 0);
		}
	}
	
	/**
	 * Updates equations with new positions
	 */
	public void updateEquation() {
		equation[0] = position;
		equation[1] = (new PVector(centerX, centerY, centerZ));
		equation[2] = (new PVector(centerX+position.x, centerY+position.y, centerZ+position.z));
	}
	
	/**
	 * Updates lights with current setting
	 * @param a
	 */
	public void updateLights(boolean a) {
		lights = a;
	}

	/**
	 * Getter method for the equation
	 * @return
	 */
	public PVector[] getEquation() {
		return equation;
	}

}
