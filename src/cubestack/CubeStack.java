package cubestack;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;

@SuppressWarnings("serial")
public class CubeStack extends PApplet {
	//If you run into a serious bug, email me at lucas.ls97@gmail.com
	private Player player; // Player object
	private PFont f; // Font for the menu
	private PFont title; // Font for the menu
	private Camera camera; // Camera object
	private String cubeChecking; // Used for checking the side of the cube that
	// the player is looking at
	private int renderDistance; // Radius from where cubes are drawn
	private int loopDistance; // Distance of the loop that checks if cubes are
	// drawn
	private int xCubes; //# of cubes in the x direction
	private int yCubes; //# of cubes in the y direction
	private int zCubes; //# of cubes in the z direction
	private int cubeSize; //Side of the cube
	private int size; //Side of the map
	private boolean noClip; //When noclip is true, there is no collision
	private boolean playerMove; //USed for checking in player is moving
	private boolean collisionX; //Used for checking if there is collision
	private boolean collisionY; //''
	private boolean collisionZ; //''
	private float currentD; //Current distance to closest cube player is looking at
	private int currentX, currentY, currentZ; //Current closest cube
	private boolean render; // distance based rendering
	private boolean advRender; // surrounding based rendering, makes the game a
								// lot faster
	private boolean toggleInfo; //Toggles for info on the screen
	private boolean shooting; //Shooting mode
	private int renderType; //USed for toggling render types 
	//Following variables are used for looping through only the cubes around the player
	private int d1x; 
	private int d2x;
	private int d1y;
	private int d2y;
	private int d1z;
	private int d2z;
	private String closestSide; //Closest side of cube to player
	private float gravity; //Force of gravity 
	private ArrayList3D cubes; //Where all the cubes are stored
	private Cube skybox; //Skybox cube
	private ArrayList<Bullet> bullets; //Where the bullets are stored
	private boolean lights; //Toggles lights
	private boolean menu = true; //Checks if is in menu 
	public boolean loading = false; //Checks if is loading
	private int menuMode = 0; //Places in the menu
	public int buttonHeight; //Button dimensions
	public int buttonWidth;
	public int logoWidth;
	public int logoHeight;
	@SuppressWarnings("rawtypes")
	private ArrayList<ArrayList> save; ///USed for saving
	//The following PImages are the textures
	public static PImage grass;
	public static PImage stone;
	public static PImage dirt;
	public static PImage iron;
	public static PImage gold;
	public static PImage diamond;
	public static PImage bedrock;
	public static PImage sky;
	public static PImage logo;
	
	public void settings() {
		size(displayWidth, displayHeight, "processing.opengl.PGraphics3D");
	}

	@SuppressWarnings("rawtypes")
	/**
	 * Initializes all the variables
	 */
	public void setup() {
		textureMode(NORMAL);
		noCursor();
		noStroke();
		background(0);
		stroke(255);
		f = createFont("Lucida Console", 20, true);
		title = createFont("Lucida Console", 60, true);
		textFont(f, 20);
		fill(255);
		grass = loadImage("Assets/grass.jpg");
		stone = loadImage("Assets/stone.jpg");
		dirt = loadImage("Assets/dirt.jpg");
		iron = loadImage("Assets/iron.jpg");
		gold = loadImage("Assets/gold.jpg");
		diamond = loadImage("Assets/diamond.jpg");
		bedrock = loadImage("Assets/bedrock.jpg");
		sky = loadImage("Assets/sky.jpg");
		logo = loadImage("Assets/logo1.jpg");
		cubeSize = 50;
		renderDistance = 700;
		loopDistance = renderDistance / cubeSize + 2;
		xCubes = 20;
		yCubes = 20;
		zCubes = 10;
		// if size is too big, saving and loading will take a long time
		size = 100;
		shooting = false;
		playerMove = true;
		lights = true; //Lights increase lag
		gravity = (float) 0.5;
		noClip = false;
		renderType = 0;
		render = true;
		advRender = true;
		toggleInfo = true;
		buttonHeight = 40;
		buttonWidth = 150;
		logoWidth = 200;
		logoHeight = 200;
		xCubes = xCubes / 2;
		yCubes = yCubes / 2;
		zCubes = zCubes / 2;

		if (loopDistance > size) {
			loopDistance = size;
		}
		currentX = 0;
		currentY = 0;
		currentZ = 0;
		save = new ArrayList<ArrayList>();
		player = new Player(this, size / 2 * cubeSize, size / 2 * cubeSize,
				size / 2 * cubeSize - zCubes * cubeSize - 80);
		camera = new Camera(this);
		// fov is to prevent clipping the blocks. The values were taken from
		// here: https://processing.org/reference/perspective_.html and
		// here:http://stackoverflow.com/questions/16544258/render-clipping-of-near-polygon-objects-in-processing
		float fov = (float) (PI / 3.0);
		float cameraZ = (float) ((height / 2.0) / tan((float) (fov / 2.0)));
		perspective(fov, (float) width / (float) height, (float) (0.01),
				(float) (cameraZ * 10.0));
		cubes = new ArrayList3D(this, xCubes, yCubes, zCubes,
				player.getPHeight(), size, cubeSize);
		skybox = new Sky(this, 0, 0, 0, renderDistance + 25, false, true);
		bullets = new ArrayList<Bullet>();
	}
	/**
	 * Generates random number
	 * @param min
	 * @param max
	 * @return
	 */
	public static int random(int min, int max) {
		Random r = new Random();
		return (min + r.nextInt(max - min + 1));
	}

	/**
	 * Checks if keys are being pressed
	 */
	public void keyPressed() {
		if (keyCode == 'A') {
			player.input(0, true);
		}
		if (keyCode == 'D') {
			player.input(1, true);
		}
		if (keyCode == 'W') {
			player.input(2, true);
		}
		if (keyCode == 'S') {
			player.input(3, true);
		}
		if (keyCode == 'N') {
			if (noClip == true) {
				noClip = false;
			} else if (noClip == false) {
				noClip = true;
			}

		}
		if (keyCode == ' ' && noClip == true) {
			player.input(4, true);
		}

		if (keyCode == CONTROL && noClip == true) {
			player.input(5, true);
		}
		if (keyCode == ' ' && noClip == false) {
			player.input(6, true);
		}
		if (keyCode == '1') {
			player.sItem(0);
		}
		if (keyCode == '2') {
			player.sItem(1);
		}
		if (keyCode == '3') {
			player.sItem(2);
		}
		if (keyCode == '4') {
			player.sItem(3);
		}
		if (keyCode == '5') {
			player.sItem(4);
		}
		if (keyCode == '6') {
			player.sItem(5);
		}
		if (keyCode == '7') {
			player.sItem(6);
		}
		if (keyCode == 'M') { // toggle render modes
			renderType++;
			if (renderType == 4) {
				renderType = 0;
			}
		}
		if (keyCode == 'B') {
			if (toggleInfo == true) {
				toggleInfo = false;
			} else {
				toggleInfo = true;
			}
		}
		if (keyCode == 'K') {
			if (shooting == true) {
				shooting = false;
			} else {
				shooting = true;
			}
		}
		if (keyCode == 'L') {
			if (lights == true) {
				lights = false;
			} else {
				lights = true;
			}
		}

	}

	/**
	 * checks if keys are being released
	 */
	public void keyReleased() {
		if (keyCode == 'A') {
			player.input(0, false);
		}
		if (keyCode == 'D') {
			player.input(1, false);
		}
		if (keyCode == 'W') {
			player.input(2, false);
		}
		if (keyCode == 'S') {
			player.input(3, false);
		}
		if (keyCode == ' ') {
			player.input(4, false);
		}
		if (keyCode == CONTROL) {
			player.input(5, false);
		}
		if (keyCode == ' ' && noClip == false) {
			player.input(6, false);
		}
		if (keyCode == 'P') {
			camera();
			textAlign(CENTER);
			hint(DISABLE_DEPTH_TEST);
			text("Loading...", width / 2, height / 2 - 30);
			hint(ENABLE_DEPTH_TEST);
			loading = true;
		}

	}

	/**
	 * Checks if mouse is being pressed
	 */
	public void mousePressed() {
		//If in menu, checks if mouse is on the buttons
		if (menu == true) {
			if (menuMode == 0) { //If on main screen
				if (button(mouseX, mouseY, displayWidth / 2 - buttonWidth / 2,
						displayHeight / 2 - buttonHeight / 2, buttonWidth,
						buttonHeight)) {
					menu = false; //Starts game
				}
				if (button(mouseX, mouseY, displayWidth / 2 - buttonWidth / 2,
						displayHeight / 2 - buttonHeight / 2 + 100,
						buttonWidth, buttonHeight)) {
					save = loadGame(); //Loads save and starts game
					load();
					save.clear();
					menu = false;
				}
				if (button(mouseX, mouseY, displayWidth / 2 - buttonWidth / 2,
						displayHeight / 2 - buttonHeight / 2 + 200,
						buttonWidth, buttonHeight)) {
					menuMode = 2; //Enters new screen
				}
				if (button(mouseX, mouseY, displayWidth / 2 - buttonWidth / 2,
						displayHeight / 2 - buttonHeight / 2 + 300,
						buttonWidth, buttonHeight)) {
					menuMode = 3; //Enders new screen
				}
			} else { //IF not on main screen
				if (button(mouseX, mouseY, displayWidth / 2 - buttonWidth / 2,
						displayHeight / 2 - buttonHeight / 2, buttonWidth,
						buttonHeight)) {
					menuMode = 0; //back to main screen
				}
			}

		} else { //If in game
			//Shooting mode creates either a destroying or placing bullet
			if (mouseButton == LEFT && shooting == true) {
				bullets.add(new Bullet(player.getPosition().x, player
						.getPosition().y, player.getPosition().z, mouseX,
						mouseY, this, camera.getCenterX(), camera.getCenterY(),
						camera.getCenterZ(), true));
			}
			if (mouseButton == RIGHT && shooting == true) {
				bullets.add(new Bullet(player.getPosition().x, player
						.getPosition().y, player.getPosition().z, mouseX,
						mouseY, this, camera.getCenterX(), camera.getCenterY(),
						camera.getCenterZ(), false));
			}
			//Non shooting mode creates or destroys block
			if (mouseButton == LEFT && shooting == false) {
				player.pickUp(cubes.getCube(currentX, currentY, currentZ).getID());
				cubes.removeCube(currentX, currentY, currentZ);
			}
			if (mouseButton == RIGHT && shooting == false) {
				if (cubes.getCube(currentX, currentY, currentZ) != null) {
				//When placing blocks, you need to check the side of the cube that is being looked at
					//By finding closest side and placing the block there
					findShortest(
							intersect(
									cubes.getCube(currentX, currentY, currentZ)
											.getEquation("top"),
									cubes.getCube(currentX, currentY, currentZ)
											.getNormal("top"),
									cubes.getCube(currentX, currentY, currentZ)
											.getEdges(), camera.getEquation()),
							intersect(
									cubes.getCube(currentX, currentY, currentZ)
											.getEquation("bottom"),
									cubes.getCube(currentX, currentY, currentZ)
											.getNormal("bottom"),
									cubes.getCube(currentX, currentY, currentZ)
											.getEdges(), camera.getEquation()),
							intersect(
									cubes.getCube(currentX, currentY, currentZ)
											.getEquation("left"),
									cubes.getCube(currentX, currentY, currentZ)
											.getNormal("left"),
									cubes.getCube(currentX, currentY, currentZ)
											.getEdges(), camera.getEquation()),
							intersect(
									cubes.getCube(currentX, currentY, currentZ)
											.getEquation("right"),
									cubes.getCube(currentX, currentY, currentZ)
											.getNormal("right"),
									cubes.getCube(currentX, currentY, currentZ)
											.getEdges(), camera.getEquation()),
							intersect(
									cubes.getCube(currentX, currentY, currentZ)
											.getEquation("front"),
									cubes.getCube(currentX, currentY, currentZ)
											.getNormal("front"),
									cubes.getCube(currentX, currentY, currentZ)
											.getEdges(), camera.getEquation()),
							intersect(
									cubes.getCube(currentX, currentY, currentZ)
											.getEquation("back"),
									cubes.getCube(currentX, currentY, currentZ)
											.getNormal("back"),
									cubes.getCube(currentX, currentY, currentZ)
											.getEdges(), camera.getEquation()));
					if (closestSide.equals("top")) {
						cubes.addCube(currentX, currentY, currentZ - 1, cubes
								.getCube(currentX, currentY, currentZ)
								.getPosition().x,
								cubes.getCube(currentX, currentY, currentZ)
										.getPosition().y,
								cubes.getCube(currentX, currentY, currentZ)
										.getPosition().z - cubeSize, player
										.gSelected());
					} else if (closestSide.equals("bottom")) {
						cubes.addCube(currentX, currentY, currentZ + 1, cubes
								.getCube(currentX, currentY, currentZ)
								.getPosition().x,
								cubes.getCube(currentX, currentY, currentZ)
										.getPosition().y,
								cubes.getCube(currentX, currentY, currentZ)
										.getPosition().z + cubeSize, player
										.gSelected());

					} else if (closestSide.equals("left")) {
						cubes.addCube(currentX, currentY - 1, currentZ, cubes
								.getCube(currentX, currentY, currentZ)
								.getPosition().x,
								cubes.getCube(currentX, currentY, currentZ)
										.getPosition().y - cubeSize, cubes
										.getCube(currentX, currentY, currentZ)
										.getPosition().z, player.gSelected());

					} else if (closestSide.equals("right")) {
						cubes.addCube(currentX, currentY + 1, currentZ, cubes
								.getCube(currentX, currentY, currentZ)
								.getPosition().x,
								cubes.getCube(currentX, currentY, currentZ)
										.getPosition().y + cubeSize, cubes
										.getCube(currentX, currentY, currentZ)
										.getPosition().z, player.gSelected());

					} else if (closestSide.equals("front")) {
						cubes.addCube(currentX + 1, currentY, currentZ, cubes
								.getCube(currentX, currentY, currentZ)
								.getPosition().x
								+ cubeSize,
								cubes.getCube(currentX, currentY, currentZ)
										.getPosition().y,
								cubes.getCube(currentX, currentY, currentZ)
										.getPosition().z, player.gSelected());

					} else if (closestSide.equals("back")) {
						cubes.addCube(currentX - 1, currentY, currentZ, cubes
								.getCube(currentX, currentY, currentZ)
								.getPosition().x
								- cubeSize,
								cubes.getCube(currentX, currentY, currentZ)
										.getPosition().y,
								cubes.getCube(currentX, currentY, currentZ)
										.getPosition().z, player.gSelected());

					}
				}
			}
		}
	}
	/**
	 * Checks if something is within a button
	 * @param x
	 * @param y
	 * @param x1
	 * @param y1
	 * @param wX
	 * @param wY
	 * @return if is within button
	 */
	public boolean button(float x, float y, float x1, float y1, float wX,
			float wY) {
		if (x >= x1 && x <= x1 + wX && y >= y1 && y <= y1 + wY) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Checks if something is colliding with a cube
	 * @param a
	 * @param x
	 * @param y
	 * @param z
	 * @param xV
	 * @param yV
	 * @param zV
	 * @return if is colliding
	 */
	public boolean checkCollision(Cube a, float x, float y, float z, float xV,
			float yV, float zV) {
		if (a.isInsideOf(x, y, z + zV + player.getPHeight())
				|| a.isInsideOf(x, y, z + zV - player.getHS())) {
			collisionZ = true;
		}
		if (a.isInsideOf(x, y + yV, z + player.getPHeight())
				|| a.isInsideOf(x, y + yV, z - player.getHS())) {
			collisionY = true;
		}
		if (a.isInsideOf(x + xV, y, z + player.getPHeight())
				|| a.isInsideOf(x + xV, y, z - player.getHS())) {
			collisionX = true;
		}
		if (collisionX == true || collisionY == true || collisionZ == true) {
			playerMove = false;
			return (true);
		} else {
			return (false);
		}
	}

	/**
	 * Toggling render types
	 * @param type
	 */
	public void setRender(int type) {
		if (type == 0) {
			render = true;
			advRender = true;
		} else if (type == 1) {
			render = true;
			advRender = false;
		} else if (type == 2) {
			render = false;
			advRender = true;
		} else if (type == 3) {
			render = false;
			advRender = false;
		}
	}
	
	/**
	 * Checks if the line created by the position wher the player is looking
	 * intersects with a cube
	 * @param equation
	 * @param normal
	 * @param edges
	 * @param cam
	 * @return The distance of the point of intersection
	 */
	public float intersect(PVector[] equation, PVector normal, float[] edges,
			PVector[] cam) {
		float t;
		float d;
		PVector intersection = null;
		float distance = 0;
		// if the lines are not parallel
		if ((normal.x * cam[1].x + normal.y * cam[1].y + normal.z * cam[1].z) != 0) {
			//Finds d value for scalar equation of plane
			d = -(normal.x * equation[0].x + normal.y * equation[0].y + normal.z
					* equation[0].z);
			//Finds t for the parametric equation
			t = (-normal.x * cam[0].x - normal.y * cam[0].y - normal.z
					* cam[0].z - d)
					/ (normal.x * cam[1].x + normal.y * cam[1].y + normal.z
							* cam[1].z);
			//Uses t to calculate point of intersection
			intersection = new PVector(cam[0].x + cam[1].x * t, cam[0].y
					+ cam[1].y * t, cam[0].z + cam[1].z * t);
			// Checks if the point of intersection is within the cube
			// and if the cube is in front of the player
			if (intersection.x <= edges[0]
					&& intersection.x >= edges[1]
					&& intersection.y <= edges[2]
					&& intersection.y >= edges[3]
					&& intersection.z <= edges[4]
					&& intersection.z >= edges[5]
					&& (Math.sqrt(Math.pow(cam[0].x - intersection.x, 2)
							+ Math.pow(cam[0].y - intersection.y, 2)
							+ Math.pow(cam[0].z - intersection.z, 2))) > (Math
								.sqrt(Math.pow(cam[2].x - intersection.x, 2)
										+ Math.pow(cam[2].y - intersection.y, 2)
										+ Math.pow(cam[2].z - intersection.z, 2)))) {
				distance = (float) Math.sqrt(Math.pow(
						cam[0].x - intersection.x, 2)
						+ Math.pow(cam[0].y - intersection.y, 2)
						+ Math.pow(cam[0].z - intersection.z, 2));
			} else {
				//if doesnt intersect, the distance is very big so it's not used
				distance = 1000000;
			}
		} else {
			distance = 1000000;
		}
		return (distance);
	}

	/**
	 * Finds to the player
	 * @param top
	 * @param bottom
	 * @param left
	 * @param right
	 * @param front
	 * @param back
	 * @return
	 */
	public float findShortest(float top, float bottom, float left, float right,
			float front, float back) {
		if (left <= bottom && left <= top && left <= right && left <= back
				&& left <= front) {
			closestSide = "left";
			return left;
		} else if (right <= bottom && right <= left && right <= top
				&& right <= back && right <= front) {
			closestSide = "right";
			return right;
		} else if (back <= bottom && back <= left && back <= right
				&& back <= top && back <= front) {
			closestSide = "back";
			return back;
		} else if (front <= bottom && front <= left && front <= right
				&& front <= back && front <= top) {
			closestSide = "front";
			return front;
		} else if (top <= bottom && top <= left && top <= right && top <= back
				&& top <= front) {
			closestSide = "top";
			return top;
		} else if (bottom <= top && bottom <= left && bottom <= right
				&& bottom <= back && bottom <= front) {
			closestSide = "bottom";
			return bottom;
		}
		return 0;
	}
	
	/**
	 * Saves game. Code is basically the same as my other game
	 * @param cubes
	 * @param player
	 */
	@SuppressWarnings("rawtypes")
	public void saveGame(ArrayList3D cubes, Player player) {
		ArrayList<ArrayList> saveGame = new ArrayList<ArrayList>();
		ArrayList<Float> playerPosition = new ArrayList<Float>();
		playerPosition.add(player.getPosition().x);
		playerPosition.add(player.getPosition().y);
		playerPosition.add(player.getPosition().z);
		ArrayList<ArrayList<ArrayList<Integer>>> cubesID = new ArrayList<ArrayList<ArrayList<Integer>>>();
		ArrayList<Boolean> gameSettings = new ArrayList<Boolean>();
		gameSettings.add(noClip);
		gameSettings.add(lights);
		gameSettings.add(shooting);
		gameSettings.add(render);
		gameSettings.add(advRender);
		gameSettings.add(toggleInfo);
		for (int x = 0; x < cubes.xSize(); x++) {
			cubesID.add(new ArrayList<ArrayList<Integer>>());
			for (int y = 0; y < cubes.xSize(); y++) {
				cubesID.get(x).add(new ArrayList<Integer>());
				for (int z = 0; z < cubes.xSize(); z++) {
					if (cubes.getCube(x, y, z) != null) {
						cubesID.get(x).get(y)
								.add(z, cubes.getCube(x, y, z).getID());
					} else {
						cubesID.get(x).get(y).add(z, null);
					}
				}
			}
		}

		saveGame.add(playerPosition);
		saveGame.add(cubesID);
		saveGame.add(gameSettings);
		try {
			// Attempting to open the .ser file may crash your computer
			FileOutputStream fileOut = new FileOutputStream("saveGame.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(saveGame);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	/**
	 * Loads game. Code is basically the same as my other game
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList<ArrayList> loadGame() {
		ArrayList<ArrayList> saveFile = new ArrayList<ArrayList>();
		try {
			FileInputStream fileIn = new FileInputStream("saveGame.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			saveFile = (ArrayList<ArrayList>) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
		}
		return (saveFile);
	}

	/**
	 * Sets the values from the file to values in game
	 */
	@SuppressWarnings("unchecked")
	public void load() {
		stroke(0);
		textAlign(LEFT);
		text("Loading...", 5, displayHeight - 5);
		player.setPosition((Float) save.get(0).get(0),
				(Float) save.get(0).get(1), (Float) save.get(0).get(2));
		noClip = (Boolean) save.get(2).get(0);
		lights = (Boolean) save.get(2).get(1);
		shooting = (Boolean) save.get(2).get(2);
		render = (Boolean) save.get(2).get(3);
		advRender = (Boolean) save.get(2).get(4);
		toggleInfo = (Boolean) save.get(2).get(5);
		for (int x = 0; x < cubes.xSize(); x++) {
			for (int y = 0; y < cubes.xSize(); y++) {
				for (int z = 0; z < cubes.xSize(); z++) {
					if (((ArrayList<ArrayList<Integer>>) save.get(1).get(x))
							.get(y).get(z) != null) {
						cubes.addCube(x, y, z, (float) x * cubeSize, (float) y
								* cubeSize,
								(float) z * cubeSize + player.getPHeight(),
								((ArrayList<ArrayList<Integer>>) save.get(1)
										.get(x)).get(y).get(z));
					} else {
						if (cubes.getCube(x, y, z) != null) {
							cubes.removeCube(x, y, z);
						}
					}
				}
			}
		}
	}

	/**
	 * Main loop where everything is drawn
	 */
	public void draw() {
		//Handles the menu
		if (menu == true) {
			textAlign(CENTER);
			cursor();
			fill(0);
			stroke(0);
			noFill();
			background(0, 136, 35);
			//Draws different buttons depending on the menuMode
			if (menuMode == 0) {
				textFont(title, 60);
				text("CubeStack", displayWidth / 2, 200);
				textFont(f, 20);
				image(logo, displayWidth / 2 - logoWidth / 2, 250, logoWidth, logoHeight);
				if (button(mouseX, mouseY, displayWidth / 2 - buttonWidth / 2,
						displayHeight / 2 - buttonHeight / 2, buttonWidth,
						buttonHeight)) {
					stroke(255);
				}
				rect(displayWidth / 2 - buttonWidth / 2, displayHeight / 2
						- buttonHeight / 2, buttonWidth, buttonHeight);
				stroke(0);
				if (button(mouseX, mouseY, displayWidth / 2 - buttonWidth / 2,
						displayHeight / 2 + 100 - buttonHeight / 2,
						buttonWidth, buttonHeight)) {
					stroke(255);
				}
				rect(displayWidth / 2 - buttonWidth / 2, displayHeight / 2
						+ 100 - buttonHeight / 2, buttonWidth, buttonHeight);
				stroke(0);
				if (button(mouseX, mouseY, displayWidth / 2 - buttonWidth / 2,
						displayHeight / 2 + 200 - buttonHeight / 2,
						buttonWidth, buttonHeight)) {
					stroke(255);
				}
				rect(displayWidth / 2 - buttonWidth / 2, displayHeight / 2
						+ 200 - buttonHeight / 2, buttonWidth, buttonHeight);
				stroke(0);
				if (button(mouseX, mouseY, displayWidth / 2 - buttonWidth / 2,
						displayHeight / 2 + 300 - buttonHeight / 2,
						buttonWidth, buttonHeight)) {
					stroke(255);
				}
				rect(displayWidth / 2 - buttonWidth / 2, displayHeight / 2
						+ 300 - buttonHeight / 2, buttonWidth, buttonHeight);
				stroke(0);
				text("Play", displayWidth / 2, displayHeight / 2);
				text("Load Game", displayWidth / 2, displayHeight / 2 + 100);
				//I did not have time to add adjustable settings in game
				//If you want to try new settings, you can change them in setup
				text("Settings", displayWidth / 2, displayHeight / 2 + 200);
				text("How to Play", displayWidth / 2, displayHeight / 2 + 300);
			} else if (menuMode == 1) {
				text("Load Game", displayWidth / 2, 200);
				if (button(mouseX, mouseY, displayWidth / 2 - buttonWidth / 2,
						displayHeight / 2 - buttonHeight / 2, buttonWidth,
						buttonHeight)) {
					stroke(255);
				}
				rect(displayWidth / 2 - buttonWidth / 2, displayHeight / 2
						- buttonHeight / 2, buttonWidth, buttonHeight);
				stroke(0);
				text("Back", displayWidth / 2, displayHeight / 2);
			} else if (menuMode == 2) {
				text("Settings", displayWidth / 2, 200);
				text("Haven't added this yet", displayWidth / 2, 250);
				text("You can change some settings by changing the values on setup() or CubeStack",
						displayWidth / 2, 270);
				if (button(mouseX, mouseY, displayWidth / 2 - buttonWidth / 2,
						displayHeight / 2 - buttonHeight / 2, buttonWidth,
						buttonHeight)) {
					stroke(255);
				}
				rect(displayWidth / 2 - buttonWidth / 2, displayHeight / 2
						- buttonHeight / 2, buttonWidth, buttonHeight);
				stroke(0);
				text("Back", displayWidth / 2, displayHeight / 2);
			} else if (menuMode == 3) {
				text("How to Play", displayWidth / 2, 200);
				text("WASD to move, SPACE to jump", displayWidth / 2, 250);
				text("M to switch render mode, N to toggle noClip",
						displayWidth / 2, 270);
				text("L to toggle lights, K to toggle shooting mode",
						displayWidth / 2, 290);
				text("B to hide information", displayWidth / 2, 310);
				if (button(mouseX, mouseY, displayWidth / 2 - buttonWidth / 2,
						displayHeight / 2 - buttonHeight / 2, buttonWidth,
						buttonHeight)) {
					stroke(255);
				}
				rect(displayWidth / 2 - buttonWidth / 2, displayHeight / 2
						- buttonHeight / 2, buttonWidth, buttonHeight);
				stroke(0);
				text("Back", displayWidth / 2, displayHeight / 2);
			}

		}
		//When not in menu
		if (menu == false) {
			if (loading) { //Saves the game and displays loading screen if player
				//saves game
				saveGame(cubes, player);
				loading = false;
			}
			//Resets some variables
			background(0);
			noCursor();
			textAlign(LEFT);
			setRender(renderType);
			player.keyPressed();
			//Changes player velocity
			player.changeVelocity(camera.getRotationAngle(),
					camera.getElevationAngle(), camera.getCenterX(),
					camera.getCenterY(), camera.getCenterZ(), noClip, gravity);
			camera.updateLights(lights);
			camera.setPosition(player.getPosition().x, player.getPosition().y,
					player.getPosition().z);
			pushMatrix();
			//Camera movement
			camera.updateCamera();
			camera.updateEquation();
			// Rendering
			// Only renders cubes within the render distance that are not on the
			// outer edges of the map
			// Only renders cubes that do not have adjacent cubes
			// Only renders cubes that have not been destroyed
			//Skybox moves with the player
			skybox.move(player.getPosition().x, player.getPosition().y,
					player.getPosition().z);
			skybox.draw();
			//Following code is repeated for every loop but it is for only looiping through
			//values around the player
			d1x = (int) (player.getPosition().x / cubeSize);
			d2x = (int) (cubes.xSize() - (player.getPosition().x / cubeSize));
			if (d1x > loopDistance) {
				d1x = loopDistance;
			}
			if (d2x > loopDistance) {
				d2x = loopDistance;
			}
			for (int x = ((int) (player.getPosition().x / cubeSize) - d1x); x < (int) (player
					.getPosition().x / cubeSize) + d2x; x++) {

				d1y = (int) (player.getPosition().y / cubeSize);
				d2y = (int) (cubes.ySize(x) - (player.getPosition().y / cubeSize));
				if (d1y > loopDistance) {
					d1y = loopDistance;
				}
				if (d2y > loopDistance) {
					d2y = loopDistance;
				}
				for (int y = (int) (player.getPosition().y / cubeSize - d1y); y < (int) (player
						.getPosition().y / cubeSize + d2y); y++) {

					d1z = (int) (player.getPosition().z / cubeSize);
					d2z = (int) (cubes.zSize(x, y) - (player.getPosition().z / cubeSize));
					if (d1z > loopDistance) {
						d1z = loopDistance;
					}
					if (d2z > loopDistance) {
						d2z = loopDistance;
					}
					for (int z = (int) (player.getPosition().z / cubeSize - d1z); z < (int) (player
							.getPosition().z / cubeSize + d2z); z++) {
						// if cube is not destroyed
						if (cubes.getCube(x, y, z) != null) {
							//checks if player is hitting a cube
							if (noClip == false) {
								checkCollision(cubes.getCube(x, y, z),
										player.getPosition().x,
										player.getPosition().y,
										player.getPosition().z,
										player.getVelocity().x,
										player.getVelocity().y,
										player.getVelocity().z);
							}
							// check if distance between cube and player is
							// bigger
							// than render distance
							if (render == false
									|| cubes.getCube(x, y, z).distanceToPlayer(
											player.getPosition().x,
											player.getPosition().y,
											player.getPosition().z) < renderDistance) {
								// check if the cube is exposed
								if (advRender == false
										|| !(cubes.checkRender(x + 1, y, z)
												&& cubes.checkRender(x - 1, y,
														z)
												&& cubes.checkRender(x, y + 1,
														z)
												&& cubes.checkRender(x, y - 1,
														z)
												&& cubes.checkRender(x, y,
														z + 1) && cubes
													.checkRender(x, y, z - 1))) {
									//Finds closest cube that player is looking at
									if (findShortest(
											intersect(cubes.getCube(x, y, z)
													.getEquation("top"), cubes
													.getCube(x, y, z)
													.getNormal("top"), cubes
													.getCube(x, y, z)
													.getEdges(),
													camera.getEquation()),
											intersect(
													cubes.getCube(x, y, z)
															.getEquation(
																	"bottom"),
													cubes.getCube(x, y, z)
															.getNormal("bottom"),
													cubes.getCube(x, y, z)
															.getEdges(), camera
															.getEquation()),
											intersect(cubes.getCube(x, y, z)
													.getEquation("left"), cubes
													.getCube(x, y, z)
													.getNormal("left"), cubes
													.getCube(x, y, z)
													.getEdges(), camera
													.getEquation()),
											intersect(
													cubes.getCube(x, y, z)
															.getEquation(
																	"right"),
													cubes.getCube(x, y, z)
															.getNormal("right"),
													cubes.getCube(x, y, z)
															.getEdges(), camera
															.getEquation()),
											intersect(
													cubes.getCube(x, y, z)
															.getEquation(
																	"front"),
													cubes.getCube(x, y, z)
															.getNormal("front"),
													cubes.getCube(x, y, z)
															.getEdges(), camera
															.getEquation()),
											intersect(cubes.getCube(x, y, z)
													.getEquation("back"), cubes
													.getCube(x, y, z)
													.getNormal("back"), cubes
													.getCube(x, y, z)
													.getEdges(), camera
													.getEquation())) < currentD) {
										currentD = findShortest(
												intersect(
														cubes.getCube(x, y, z)
																.getEquation(
																		"top"),
														cubes.getCube(x, y, z)
																.getNormal(
																		"top"),
														cubes.getCube(x, y, z)
																.getEdges(),
														camera.getEquation()),
												intersect(
														cubes.getCube(x, y, z)
																.getEquation(
																		"bottom"),
														cubes.getCube(x, y, z)
																.getNormal(
																		"bottom"),
														cubes.getCube(x, y, z)
																.getEdges(),
														camera.getEquation()),
												intersect(
														cubes.getCube(x, y, z)
																.getEquation(
																		"left"),
														cubes.getCube(x, y, z)
																.getNormal(
																		"left"),
														cubes.getCube(x, y, z)
																.getEdges(),
														camera.getEquation()),
												intersect(
														cubes.getCube(x, y, z)
																.getEquation(
																		"right"),
														cubes.getCube(x, y, z)
																.getNormal(
																		"right"),
														cubes.getCube(x, y, z)
																.getEdges(),
														camera.getEquation()),
												intersect(
														cubes.getCube(x, y, z)
																.getEquation(
																		"front"),
														cubes.getCube(x, y, z)
																.getNormal(
																		"front"),
														cubes.getCube(x, y, z)
																.getEdges(),
														camera.getEquation()),
												intersect(
														cubes.getCube(x, y, z)
																.getEquation(
																		"back"),
														cubes.getCube(x, y, z)
																.getNormal(
																		"back"),
														cubes.getCube(x, y, z)
																.getEdges(),
														camera.getEquation()));
														//previously selected cube
														//is unselected
										if (cubes.getCube(currentX, currentY,
												currentZ) != null) {
											cubes.getCube(currentX, currentY,
													currentZ).setSelection(
													false);
											cubes.getCube(currentX, currentY,
													currentZ).draw();
										}
										//That cube  is selected
										cubes.getCube(x, y, z).setSelection(
												true);
										currentX = x;
										currentY = y;
										currentZ = z;
									}
									//Draws the cube
									cubes.getCube(x, y, z).draw();
									//Unselects previoulsy selected cubes again
									if (cubes.getCube(currentX, currentY,
											currentZ) != null) {
										cubes.getCube(currentX, currentY,
												currentZ).setSelection(false);
									}
								}
							}
							//For all bullets
							for (int i = 0; i < bullets.size(); i++) {
								// check if it is inside a cube
								if (cubes.getCube(x, y, z) != null) {
									cubeChecking = cubes
											.getCube(x, y, z)
											.isClosestTo(
													bullets.get(i)
															.getPosition().x,
													bullets.get(i)
															.getPosition().y,
													bullets.get(i)
															.getPosition().z,
													bullets.get(i).getDestroy());
									if (!cubeChecking.equals("noAction")) {
										bullets.remove(i);
										if (cubeChecking.equals("removeCube")) {
											cubes.removeCube(x, y, z);
										} else if (cubeChecking
												.equals("placeFront")) {
											cubes.addCube(x + 1, y, z, cubes
													.getCube(x, y, z)
													.getPosition().x
													+ cubeSize,
													cubes.getCube(x, y, z)
															.getPosition().y,
													cubes.getCube(x, y, z)
															.getPosition().z,
													player.gSelected());
										} else if (cubeChecking
												.equals("placeBack")) {
											cubes.addCube(x - 1, y, z, cubes
													.getCube(x, y, z)
													.getPosition().x
													- cubeSize,
													cubes.getCube(x, y, z)
															.getPosition().y,
													cubes.getCube(x, y, z)
															.getPosition().z,
													player.gSelected());
										} else if (cubeChecking
												.equals("placeLeft")) {
											cubes.addCube(x, y + 1, z, cubes
													.getCube(x, y, z)
													.getPosition().x, cubes
													.getCube(x, y, z)
													.getPosition().y
													+ cubeSize,
													cubes.getCube(x, y, z)
															.getPosition().z,
													player.gSelected());
										} else if (cubeChecking
												.equals("placeRight")) {
											cubes.addCube(x, y - 1, z, cubes
													.getCube(x, y, z)
													.getPosition().x, cubes
													.getCube(x, y, z)
													.getPosition().y
													- cubeSize,
													cubes.getCube(x, y, z)
															.getPosition().z,
													player.gSelected());
										} else if (cubeChecking
												.equals("placeUp")) {
											cubes.addCube(x, y, z + 1, cubes
													.getCube(x, y, z)
													.getPosition().x, cubes
													.getCube(x, y, z)
													.getPosition().y, cubes
													.getCube(x, y, z)
													.getPosition().z
													+ cubeSize, player
													.gSelected());
										} else if (cubeChecking
												.equals("placeDown")) {
											cubes.addCube(x, y, z - 1, cubes
													.getCube(x, y, z)
													.getPosition().x, cubes
													.getCube(x, y, z)
													.getPosition().y, cubes
													.getCube(x, y, z)
													.getPosition().z
													- cubeSize, player
													.gSelected());
										}
									}
								}
							}
						}
					}
				}
			}
			//Adjests player velocity if player is touching a cube
			if (playerMove == false) {
				playerMove = true;
				if (collisionX == true) {
					player.setVelocity(0, player.getVelocity().y,
							player.getVelocity().z);
				}
				if (collisionY == true) {
					player.setVelocity(player.getVelocity().x, 0,
							player.getVelocity().z);
				}
				if (collisionZ == true) {
					player.setVelocity(player.getVelocity().x,
							player.getVelocity().y, 0);
				}
			}
			//Resets collision
			playerMove = true;
			collisionX = false;
			collisionY = false;
			collisionZ = false;
			//Moves player
			player.move();
			for (int i = 0; i < bullets.size(); i++) {
				bullets.get(i).move();
				bullets.get(i).draw();
				if (bullets.get(i).getDistanceTravelled() > bullets.get(i)
						.getRange()) {
					bullets.remove(i);
				}
			}
			popMatrix();
			//Draws all the info on screen
			player.inventoryDraw();
			if (toggleInfo) {
				textAlign(LEFT);
				hint(DISABLE_DEPTH_TEST); // Used to show text on top of 3d
											// shapes
				text("FPS: " + frameRate, 5, 20);
				text("X: " + (player.getPosition().x - size / 2 * cubeSize), 5,
						40);
				text("Y: " + (player.getPosition().y - size / 2 * cubeSize), 5,
						60);
				text("Z: " + (player.getPosition().z - size / 2 * cubeSize), 5,
						80);
				text("vX: " + player.getVelocity().x, 5, 100);
				text("vY: " + player.getVelocity().y, 5, 120);
				text("vZ: " + player.getVelocity().z, 5, 140);
				text("Center X: " + camera.getCenterX(), 5, 180);
				text("Center Y: " + camera.getCenterY(), 5, 200);
				text("Center Z: " + camera.getCenterZ(), 5, 220);
				text("Rotation Angle: " + camera.getRotationAngle()
						* (float) 57.2957795, 5, 240);
				text("Elevation Angle: " + camera.getElevationAngle()
						* (float) 57.2957795, 5, 260);
				text("Noclip: " + noClip, 5, 280);
				text("Shooting Mode: " + shooting, 5, 300);
				text("Render: " + render, 5, 320);
				text("Advanced Render: " + advRender, 5, 340);
				text("Lights: " + lights, 5, 360);
				hint(ENABLE_DEPTH_TEST);
			}
			hint(DISABLE_DEPTH_TEST);
			stroke(255);
			noFill();
			//Draws the targeting circle
			ellipse(width / 2 - 5, height / 2 - 5, 10, 10);
			hint(ENABLE_DEPTH_TEST);
			//If mouse is close to leaving the screen
			//Brings the mouse back to the other side
			if (mouseX > width - 5) {
				try {
					Robot bot = new Robot();
					bot.mouseMove(5, (int) MouseInfo.getPointerInfo()
							.getLocation().getY());
				} catch (AWTException e) {
					e.printStackTrace();
				}
			} else if (mouseX < 5) {
				try {
					Robot bot = new Robot();
					bot.mouseMove(width - 5, (int) MouseInfo.getPointerInfo()
							.getLocation().getY());
				} catch (AWTException e) {
					e.printStackTrace();
				}
			}
			//Resets current closest cube
			currentD = 900000;
		}
	}
	//This is here just so the game starts fullscreen
	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "cubestack.CubeStack" });
	}
}
