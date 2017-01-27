package cubestack;

import java.io.Serializable;

import processing.core.*;

@SuppressWarnings("serial")
public class Player extends PApplet implements Serializable {
	private PVector position, velocity; // Position and velocity
	private float friction; // Rate at which player slows down
	private float speed; // Amount that velocity increases by when moving
	private float initialSpeed; // Base speed
	private PApplet game; // See camera class
	private boolean left = false, right = false, forward = false, back = false,
			up = false, down = false, jump = false; // Is player moving in
													// direction
	private float jumpForce; // Amount that velocity increases when jumping
	private PVector pVelocity; // Previous velocity. Moved in the last loop
	private float playerHeight; // Player height
	private float headSize; // Size of player head
	private Inventory inventory; // Inventory

	/**
	 * Initializes all variables
	 * 
	 * @param a
	 * @param x
	 * @param y
	 * @param z
	 */
	public Player(PApplet a, int x, int y, int z) {
		game = a;
		speed = 2;
		initialSpeed = 2;
		position = new PVector(x, y, z);
		velocity = new PVector(0, 0, 0);
		pVelocity = new PVector(0, 0, 0);
		friction = (float) 0.8;
		jumpForce = 8;
		playerHeight = 70;
		headSize = 10;
		inventory = new Inventory(game, 6);
	}

	/**
	 * Checks input. Used because keyPressed only detects one key at a time
	 * 
	 * @param direction
	 * @param condition
	 */
	public void input(int direction, boolean condition) {
		if (direction == 0) {
			if (condition == true) {
				left = true;
			} else {
				left = false;
			}
		}
		if (direction == 1) {
			if (condition == true) {
				right = true;
			} else {
				right = false;
			}
		}
		if (direction == 2) {
			if (condition == true) {
				forward = true;
			} else {
				forward = false;
			}
		}
		if (direction == 3) {
			if (condition == true) {
				back = true;
			} else {
				back = false;
			}
		}
		if (direction == 4) {
			if (condition == true) {
				up = true;
			} else {
				up = false;
			}
		}
		if (direction == 5) {
			if (condition == true) {
				down = true;
			} else {
				down = false;
			}
		}
		if (direction == 6) {
			if (condition == true) {
				jump = true;
			} else {
				jump = false;
			}
		}

	}

	/**
	 * Getter for position
	 * 
	 * @return
	 */
	public PVector getPosition() {
		return (position);
	}

	/**
	 * Getter for previous velocity
	 * 
	 * @return
	 */
	public PVector getpVelocity() {
		return (pVelocity);
	}

	/**
	 * Getter for velocity
	 * 
	 * @return
	 */
	public PVector getVelocity() {
		return (velocity);
	}

	/**
	 * Getter for height
	 * 
	 * @return
	 */
	public float getPHeight() {
		return (playerHeight);
	}

	/**
	 * Getter for head size
	 * 
	 * @return
	 */
	public float getHS() {
		return (headSize);
	}

	/**
	 * Setter for velocity
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setVelocity(float x, float y, float z) {
		velocity.x = x;
		velocity.y = y;
		velocity.z = z;
	}

	/**
	 * Setter for position
	 * 
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
	 * Method for falling
	 * 
	 * @param gravity
	 */
	public void fall(float gravity) {
		velocity.z = velocity.z + gravity;
	}
	
	/**
	 * Method for jumping
	 */
	public void jump() {
		//Checks if player is not currently jumping
		if (velocity.z == 0 && pVelocity.z == velocity.z) {
			velocity.z = velocity.z - jumpForce;
		}
	}

	/**
	 * Changes velocity according to current keys being pressed
	 * @param rotationAngle
	 * @param elevationAngle
	 * @param centerX
	 * @param centerY
	 * @param centerZ
	 * @param noClip
	 * @param gravity
	 */
	public void changeVelocity(float rotationAngle, float elevationAngle,
			float centerX, float centerY, float centerZ, boolean noClip,
			float gravity) {
		pVelocity.z = velocity.z;
		//If two keys are being pressed, halves the speed
		if ((left == true && forward == true) || (left == true && back == true)
				|| (right == true && forward == true)
				|| (right == true && back == true)) {
			speed = initialSpeed / 2;
		} else {
			speed = initialSpeed;
		}

		if (left == true) {
			setVelocity(getVelocity().x + speed
					* ((cos(rotationAngle + PI / 2))), // x
					getVelocity().y + speed * ((sin(rotationAngle + PI / 2))), // y
					getVelocity().z); // z
		}
		if (right == true) {
			setVelocity(getVelocity().x + speed
					* ((cos(rotationAngle - PI / 2))), // x
					getVelocity().y + speed * ((sin(rotationAngle - PI / 2))), // y
					getVelocity().z); // z
		}
		if (forward == true) {
			setVelocity(getVelocity().x + speed * ((cos(rotationAngle))),
					getVelocity().y + speed * ((sin(rotationAngle))),
					getVelocity().z);
		}
		if (back == true) {
			setVelocity(getVelocity().x - speed * ((cos(rotationAngle))),
					getVelocity().y - speed * ((sin(rotationAngle))),
					getVelocity().z);
		}
		if (up == true) {
			setVelocity(getVelocity().x, getVelocity().y, getVelocity().z
					- speed);
		}
		if (down == true) {
			setVelocity(getVelocity().x, getVelocity().y, getVelocity().z
					+ speed);
		}

		velocity.x = (float) (velocity.x * friction);
		velocity.y = (float) (velocity.y * friction);
		//Only falls if noclip is off
		if (noClip == true) {
			velocity.z = (float) (velocity.z * friction);
		} else {
			fall(gravity);
		}
	}
	
	/**
	 * Moves based on velocity
	 */
	public void move() {
		if (jump == true) {
			jump();
		}
		position.x = position.x + velocity.x;
		position.y = position.y + velocity.y;
		position.z = position.z + velocity.z;
	}
	
	/**
	 * Draws the inventory
	 */
	public void inventoryDraw() {
		inventory.draw();
	}
	
	/**
	 * Adds items to inventory
	 * @param id
	 */
	public void pickUp(int id) {
		inventory.addItems(id);
	}

	/**
	 * Getter for inventory
	 * @return
	 */
	public Inventory getInventory() {
		return inventory;
	}
	
	/**
	 * Selecting items
	 * @param i
	 */
	public void sItem(int i) {
		inventory.selectItem(i);
	}
	
	/**
	 * Getter for currently selected item
	 * @return
	 */
	public int gSelected() {
		return (inventory.getSelected());
	}

}
