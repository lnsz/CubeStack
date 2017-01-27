package cubestack;

import java.io.Serializable;

import processing.core.*;

@SuppressWarnings("serial")
public class Cube implements Serializable {
	//I didn't comment the individual cube classes because they are all very similar
	private PApplet game; // See camera
	private PVector position; // Current position
	private float size; // Size of cube
	private boolean state; // Not being used anymore
	private boolean selection = false; // Selected cubes show a white border
	private boolean st; // Used for checking if there is a border
	//Only the skybox currently has no border
	//Following PImages are the textures for the sides
	private PImage top; 
	private PImage bottom;
	private PImage left;
	private PImage right;
	private PImage front;
	private PImage back;
	private float[] edges; //Points of the cube
	private int id; //Cube ID for inventory
	//The following are the vector equations of the cube
	//[0] is the position vector, [1] and [2] are the direction vectors
	private PVector[] topEquation; 
	private PVector[] bottomEquation;
	private PVector[] leftEquation;
	private PVector[] rightEquation;
	private PVector[] frontEquation;
	private PVector[] backEquation;
	//Lines normal to the planes. Used for calculating if player is looking at cube
	private PVector topNormal;
	private PVector bottomNormal;
	private PVector leftNormal;
	private PVector rightNormal;
	private PVector frontNormal;
	private PVector backNormal;

	/**
	 * Initializes all variables
	 * @param a
	 * @param x
	 * @param y
	 * @param z
	 * @param s
	 * @param str
	 * @param stat
	 */
	public Cube(PApplet a, float x, float y, float z, float s, boolean str,
			boolean stat) {
		game = a;
		position = new PVector(x, y, z);
		size = s;
		st = str;
		state = stat;
		edges = new float[] { (position.x + size), (position.x - size),
				(position.y + size), (position.y - size), (position.z + size),
				(position.z - size), };

		topEquation = new PVector[] {
				new PVector(edges[0], edges[3], edges[5]),
				new PVector(edges[1] - edges[0], edges[3] - edges[3], edges[5]
						- edges[5]),
				new PVector(edges[1] - edges[0], edges[2] - edges[3], edges[5]
						- edges[5]) };
		bottomEquation = new PVector[] {
				new PVector(edges[1], edges[3], edges[4]),
				new PVector(edges[0] - edges[1], edges[3] - edges[3], edges[4]
						- edges[4]),
				new PVector(edges[0] - edges[1], edges[2] - edges[3], edges[4]
						- edges[4]) };
		leftEquation = new PVector[] {
				new PVector(edges[1], edges[3], edges[4]),
				new PVector(edges[0] - edges[1], edges[3] - edges[3], edges[4]
						- edges[4]),
				new PVector(edges[0] - edges[1], edges[3] - edges[3], edges[5]
						- edges[4]) };
		rightEquation = new PVector[] {
				new PVector(edges[1], edges[2], edges[4]),
				new PVector(edges[0] - edges[1], edges[2] - edges[2], edges[4]
						- edges[4]),
				new PVector(edges[0] - edges[1], edges[2] - edges[2], edges[5]
						- edges[4]) };
		frontEquation = new PVector[] {
				new PVector(edges[0], edges[3], edges[4]),
				new PVector(edges[0] - edges[0], edges[3] - edges[3], edges[5]
						- edges[4]),
				new PVector(edges[0] - edges[0], edges[2] - edges[3], edges[5]
						- edges[4]) };
		backEquation = new PVector[] {
				new PVector(edges[1], edges[3], edges[5]),
				new PVector(edges[1] - edges[1], edges[3] - edges[3], edges[4]
						- edges[5]),
				new PVector(edges[1] - edges[1], edges[2] - edges[3], edges[4]
						- edges[5]) };
		topNormal = new PVector(topEquation[1].y * topEquation[2].z
				- topEquation[2].y * topEquation[1].z, topEquation[1].z
				* topEquation[2].x - topEquation[2].z * topEquation[1].x,
				topEquation[1].x * topEquation[2].y - topEquation[2].x
						* topEquation[1].y);
		bottomNormal = new PVector(bottomEquation[1].y * bottomEquation[2].z
				- bottomEquation[2].y * bottomEquation[1].z,
				bottomEquation[1].z * bottomEquation[2].x - bottomEquation[2].z
						* bottomEquation[1].x, bottomEquation[1].x
						* bottomEquation[2].y - bottomEquation[2].x
						* bottomEquation[1].y);
		leftNormal = new PVector(leftEquation[1].y * leftEquation[2].z
				- leftEquation[2].y * leftEquation[1].z, leftEquation[1].z
				* leftEquation[2].x - leftEquation[2].z * leftEquation[1].x,
				leftEquation[1].x * leftEquation[2].y - leftEquation[2].x
						* leftEquation[1].y);
		rightNormal = new PVector(rightEquation[1].y * rightEquation[2].z
				- rightEquation[2].y * rightEquation[1].z, rightEquation[1].z
				* rightEquation[2].x - rightEquation[2].z * rightEquation[1].x,
				rightEquation[1].x * rightEquation[2].y - rightEquation[2].x
						* rightEquation[1].y);
		frontNormal = new PVector(frontEquation[1].y * frontEquation[2].z
				- frontEquation[2].y * frontEquation[1].z, frontEquation[1].z
				* frontEquation[2].x - frontEquation[2].z * frontEquation[1].x,
				frontEquation[1].x * frontEquation[2].y - frontEquation[2].x
						* frontEquation[1].y);
		backNormal = new PVector(backEquation[1].y * backEquation[2].z
				- backEquation[2].y * backEquation[1].z, backEquation[1].z
				* backEquation[2].x - backEquation[2].z * backEquation[1].x,
				backEquation[1].x * backEquation[2].y - backEquation[2].x
						* backEquation[1].y);
	}
	
	/**
	 * Getter method for state
	 * @return
	 */
	public boolean getState() {
		return (state);
	}
	
	/**
	 * Setter method for textures
	 * @param img
	 * @param side
	 */
	public void setTexture(PImage img, String side) {
		if (side.equals("top")) {
			top = img;
		}
		if (side.equals("bottom")) {
			bottom = img;
		}
		if (side.equals("left")) {
			left = img;
		}
		if (side.equals("right")) {
			right = img;
		}
		if (side.equals("front")) {
			front = img;
		}
		if (side.equals("back")) {
			back = img;
		}
	}
	
	/**
	 * Checks if there is a cube surrounding this cube
	 * to see if it should be renderable
	 * @param up
	 * @param down
	 * @param left
	 * @param right
	 * @param forward
	 * @param back
	 * @return
	 */
	public boolean checkRender(boolean up, boolean down, boolean left,
			boolean right, boolean forward, boolean back) {
		if (up == true && down == true && left == true && right == true
				&& forward == true && back == true) {
			return (false);
		} else {
			return (true);
		}
	}

	/**
	 * Not used
	 * @param s
	 * @param i
	 * @param noClip
	 */
	public void setState(boolean s, Inventory i, boolean noClip) {
		state = s;
		if (state == true && noClip == false) {
			i.removeItems(id);
		} else if (state == false && noClip == false) {
			i.addItems(id);
		}
	}

	/**
	 * Getter method for selection
	 * @return
	 */
	public boolean getSelection() {
		return (selection);
	}

	/**
	 * Setter method for selection
	 * @param a
	 */
	public void setSelection(boolean a) {
		selection = a;
	}
	
	/**
	 * Getter method for size
	 * @return
	 */
	public float getSize() {
		return (size);
	}
	
	/**
	 * Getter method for position
	 * @return
	 */
	public PVector getPosition() {
		return (position);
	}

	/**
	 * Moves a cube, currently only used by skybox but i had plans to
	 * make other blocks movable
	 * @param x
	 * @param y
	 * @param z
	 */
	public void move(float x, float y, float z) {
		position.x = x;
		position.y = y;
		position.z = z;
		edges[0] = (position.x + size);
		edges[1] = (position.x - size);
		edges[2] = (position.y + size);
		edges[3] = (position.y - size);
		edges[4] = (position.z + size);
		edges[5] = (position.z - size);

	}
	
	/**
	 * Getter method for edges
	 * @return
	 */
	public float[] getEdges() {
		return (edges);
	}
	/**
	 * Setter method for id
	 * @param i
	 */
	public void setID(int i) {
		id = i;
	}
	
	/**
	 * Getter method for id
	 * @return
	 */
	public int getID() {
		return (id);
	}
	
	/**
	 * Checks i f a point is inside a cube
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public boolean isInsideOf(float x, float y, float z) {
		if (x <= edges[0] && x >= edges[1] && y <= edges[2] && y >= edges[3]
				&& z <= edges[4] && z >= edges[5]) {
			return (true);
		} else {
			return (false);
		}
	}
	
	/**
	 * Checks the side of the cube a point is closest to
	 * @param x
	 * @param y
	 * @param z
	 * @param bulletType
	 * @return
	 */
	public String isClosestTo(float x, float y, float z, boolean bulletType) {
		if (this.isInsideOf(x, y, z) == true) { // If bullet is inside cube
			if (bulletType == true) { // If it is a removing shot
				return ("removeCube");

			} else if (bulletType == false) { // if it is a building shot
				if (Math.abs(x - position.x) > Math.abs(y - position.y)
				// if it is closer to x sides
						&& Math.abs(x - position.x) > Math.abs(z - position.z)) {
					if (x <= edges[0] && x >= position.x) { // if it is closer
															// to forward side
						return ("placeFront");
					} else if (x >= edges[1] && x <= position.x) { // if it is
																	// closer to
																	// back side
						return ("placeBack");
					}
				} else if (Math.abs(y - position.y) > Math.abs(x - position.x)
						&& Math.abs(y - position.y) > Math.abs(z - position.z)) {
					// if is is closer to y side
					if (y <= edges[2] && y >= position.y) { // if it is closer
															// to right side
						return ("placeLeft");
					} else if (y >= edges[3] && y <= position.y) {// if it is
																	// closer to
																	// left side
						return ("placeRight");
					}
				} else if (Math.abs(z - position.z) > Math.abs(x - position.x)
						&& Math.abs(z - position.z) > Math.abs(y - position.y)) {
					// if it is closer to z side
					if (z <= edges[4] && z >= position.z) { // if it is closer
															// to top side
						return ("placeUp");
					} else if (z >= edges[5] && z <= position.z) {// if it is
																	// closer to
																	// bottom
						return ("placeDown");
					}
				}
			}
		}
		return ("noAction");
	}
	
	/**
	 * Draws a cube
	 */
	public void draw() {
		game.strokeWeight(1);
		game.noFill();
		if (st == false) { //Only used by skybox
			game.noStroke();
		} else { //Other cubes are white if selected, black if not
			if (selection == false) {
				game.stroke(0);
			} else if (selection == true) {
				game.stroke(255);
			}
		}
		//Cube consists of 6 planes with different textures
		game.beginShape(PConstants.QUADS); // top face
		game.texture(top);
		game.vertex(edges[0], edges[3], edges[5], 0, 0);
		game.vertex(edges[1], edges[3], edges[5], 1, 0);
		game.vertex(edges[1], edges[2], edges[5], 1, 1);
		game.vertex(edges[0], edges[2], edges[5], 0, 1);
		game.endShape();

		game.beginShape(PConstants.QUADS);
		game.texture(bottom);
		game.vertex(edges[1], edges[3], edges[4], 0, 0);
		game.vertex(edges[0], edges[3], edges[4], 1, 0);
		game.vertex(edges[0], edges[2], edges[4], 1, 1);
		game.vertex(edges[1], edges[2], edges[4], 0, 1);
		game.endShape();

		game.beginShape(PConstants.QUADS);
		game.texture(right);
		game.vertex(edges[1], edges[2], edges[4], 0, 0);
		game.vertex(edges[0], edges[2], edges[4], 1, 0);
		game.vertex(edges[0], edges[2], edges[5], 1, 1);
		game.vertex(edges[1], edges[2], edges[5], 0, 1);
		game.endShape();

		game.beginShape(PConstants.QUADS);
		game.texture(left);
		game.vertex(edges[1], edges[3], edges[4], 0, 0);
		game.vertex(edges[0], edges[3], edges[4], 1, 0);
		game.vertex(edges[0], edges[3], edges[5], 1, 1);
		game.vertex(edges[1], edges[3], edges[5], 0, 1);
		game.endShape();

		game.beginShape(PConstants.QUADS);
		game.texture(front);
		game.vertex(edges[0], edges[3], edges[4], 0, 0);
		game.vertex(edges[0], edges[3], edges[5], 1, 0);
		game.vertex(edges[0], edges[2], edges[5], 1, 1);
		game.vertex(edges[0], edges[2], edges[4], 0, 1);
		game.endShape();

		game.beginShape(PConstants.QUADS);
		game.texture(back);
		game.vertex(edges[1], edges[3], edges[5], 0, 0);
		game.vertex(edges[1], edges[3], edges[4], 1, 0);
		game.vertex(edges[1], edges[2], edges[4], 1, 1);
		game.vertex(edges[1], edges[2], edges[5], 0, 1);
		game.endShape();
	}
	
	/**
	 * Distance of cube to player
	 * Used for rendering
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public float distanceToPlayer(float x, float y, float z) {
		float distance;
		distance = (float) (Math.sqrt(Math.pow(x - position.x, 2)
				+ Math.pow(y - position.y, 2) + Math.pow(z - position.z, 2)));
		return (distance);
	}

	/**
	 * Getter method for normal
	 * @param side
	 * @return
	 */
	public PVector getNormal(String side) {
		if (side.equals("top")) {
			return (topNormal);
		} else if (side.equals("bottom")) {
			return (bottomNormal);
		} else if (side.equals("left")) {
			return (leftNormal);
		} else if (side.equals("right")) {
			return (rightNormal);
		} else if (side.equals("front")) {
			return (frontNormal);
		} else if (side.equals("back")) {
			return (backNormal);
		}
		return (null);
	}

	/**
	 * Getter method for equation
	 * @param side
	 * @return
	 */
	public PVector[] getEquation(String side) {
		if (side.equals("top")) {
			return (topEquation);
		} else if (side.equals("bottom")) {
			return (bottomEquation);
		} else if (side.equals("left")) {
			return (leftEquation);
		} else if (side.equals("right")) {
			return (rightEquation);
		} else if (side.equals("front")) {
			return (frontEquation);
		} else if (side.equals("back")) {
			return (backEquation);
		}
		return (null);
	}
}
