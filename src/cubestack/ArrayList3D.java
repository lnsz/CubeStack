package cubestack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;

@SuppressWarnings("serial")
public class ArrayList3D implements Serializable {
	// I wanted this to simplify 3d arraylists but it ended up just being where
	// all the cubes are stored

	// Arraylist of arralists of arraylists of cubes. 3d arraylist containing
	// all cubes
	ArrayList<ArrayList<ArrayList<Cube>>> cubes = new ArrayList<ArrayList<ArrayList<Cube>>>();
	private int rand; // Random value
	private int size; // Size of arraylist
	private PApplet game; // See camera
	private int cubeSize; // size of cube

	/**
	 * Adds all the cubes to the arraylist
	 * 
	 * @param a
	 * @param initialX
	 * @param initialY
	 * @param initialZ
	 * @param pHeight
	 * @param s
	 * @param cS
	 */
	public ArrayList3D(PApplet a, int initialX, int initialY, int initialZ,
			float pHeight, int s, int cS) {
		game = a;
		size = s;
		cubeSize = cS;
		for (int x = 0; x < size; x++) {
			cubes.add(new ArrayList<ArrayList<Cube>>());
			for (int y = 0; y < size; y++) {
				cubes.get(x).add(new ArrayList<Cube>());
				for (int z = 0; z < size; z++) {

					if (x >= size / 2 - initialX && x <= size / 2 + initialX
							&& y >= size / 2 - initialY
							&& y <= size / 2 + initialY
							&& z >= size / 2 - initialZ
							&& z <= size / 2 + initialZ) {
						if (z == size / 2 - initialZ) {// grass layer
							cubes.get(x)
									.get(y)
									.add(new Grass(a, x * cubeSize, y
											* cubeSize, z * cubeSize + pHeight,
											cubeSize / 2, true, true));
						} else if (z == size / 2 + initialZ) {
							cubes.get(x)
									.get(y)
									.add(new Bedrock(a, x * cubeSize, y
											* cubeSize, z * cubeSize + pHeight,
											cubeSize / 2, true, true));
						} else if (z > size / 2 + initialZ - 2
								* ((2 * initialZ) / 3.0)) {// stone
							rand = random(1, 100); // layer
							if (rand == 1) {// 1 in 100 chance of stone being
											// diamond
								cubes.get(x)
										.get(y)
										.add(new Diamond(a, x * cubeSize, y
												* cubeSize, z * cubeSize
												+ pHeight, cubeSize / 2, true,
												true));
							} else if (rand <= 4) {// 1 in 20 chance of stone
													// being
													// gold
								cubes.get(x)
										.get(y)
										.add(new Gold(a, x * cubeSize, y
												* cubeSize, z * cubeSize
												+ pHeight, cubeSize / 2, true,
												true));
							} else if (rand <= 15) {// 1 in 10 chance of stone
													// being
								// iron
								cubes.get(x)
										.get(y)
										.add(new Iron(a, x * cubeSize, y
												* cubeSize, z * cubeSize
												+ pHeight, cubeSize / 2, true,
												true));
							} else {
								cubes.get(x)
										.get(y)
										.add(new Stone(a, x * cubeSize, y
												* cubeSize, z * cubeSize
												+ pHeight, cubeSize / 2, true,
												true));
							}
						} else {// dirt layer
							cubes.get(x)
									.get(y)
									.add(new Dirt(a, x * cubeSize,
											y * cubeSize, z * cubeSize
													+ pHeight, cubeSize / 2,
											true, true));
						}
					} else {
						cubes.get(x).get(y).add(null);
					}
				}
			}
		}
	}

	/**
	 * Gets a cube in the arraylist
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public Cube getCube(int x, int y, int z) {
		try {
			return cubes.get(x).get(y).get(z);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return null;
	}

	/**
	 * Randomizer
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int random(int min, int max) {
		Random r = new Random();
		return (min + r.nextInt(max - min + 1));
	}

	/**
	 * Adds a cube to the arraylist
	 * 
	 * @param x1
	 * @param y1
	 * @param z1
	 * @param x
	 * @param y
	 * @param z
	 * @param type
	 */
	public void addCube(int x1, int y1, int z1, float x, float y, float z,
			int type) {
		// System.out.println("added cube to " + x1 + " " + y1 + " " + z1 +
		// " size of the array is now "
		// + cubes.get(x1).get(y1).size());
		if (x1 < 0) {
			x1 = 0;
		}
		if (y1 < 0) {
			y1 = 0;
		}
		if (z1 < 0) {
			z1 = 0;
		}
		// Different types of cube based on player selection
		if (type == 0) {
			cubes.get(x1)
					.get(y1)
					.set(z1, new Grass(game, x, y, z, cubeSize / 2, true, true));
		} else if (type == 1) {
			cubes.get(x1).get(y1)
					.set(z1, new Dirt(game, x, y, z, cubeSize / 2, true, true));
		} else if (type == 2) {
			cubes.get(x1)
					.get(y1)
					.set(z1, new Stone(game, x, y, z, cubeSize / 2, true, true));
		} else if (type == 3) {
			cubes.get(x1).get(y1)
					.set(z1, new Iron(game, x, y, z, cubeSize / 2, true, true));
		} else if (type == 4) {
			cubes.get(x1).get(y1)
					.set(z1, new Gold(game, x, y, z, cubeSize / 2, true, true));
		} else if (type == 5) {
			cubes.get(x1)
					.get(y1)
					.set(z1,
							new Diamond(game, x, y, z, cubeSize / 2, true, true));
		} else if (type == 6) {
			cubes.get(x1)
					.get(y1)
					.set(z1,
							new Bedrock(game, x, y, z, cubeSize / 2, true, true));
		}
	}

	/**
	 * Removes a cube by setting the element to null
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void removeCube(int x, int y, int z) {
		cubes.get(x).get(y).set(z, null);
	}

	/**
	 * Gets the size of the arraylist
	 * 
	 * @return
	 */
	public int xSize() {
		return cubes.size();
	}

	/**
	 * Gets the size of the arraylist at element x
	 * 
	 * @return
	 */
	public int ySize(int x) {
		return cubes.get(x).size();
	}

	/**
	 * Gets the size of the arraylist at element x at element y
	 * 
	 * @return
	 */
	public int zSize(int x, int y) {
		return cubes.get(x).get(y).size();
	}

	/**
	 * Checks if a cube is surrounded by other cubes
	 * @param x1
	 * @param y1
	 * @param z1
	 * @return
	 */
	public boolean isSurrounded(float x1, float y1, float z1) {
		boolean left = false;
		boolean right = false;
		boolean up = false;
		boolean down = false;
		boolean front = false;
		boolean back = false;
		for (int x = 0; x < xSize(); x++) {
			for (int y = 0; y < ySize(x); y++) {
				for (int z = 0; z < zSize(x, y); z++) {
					if (x1 + cubeSize == getCube(x, y, z).getPosition().x
							&& y1 == getCube(x, y, z).getPosition().y
							&& z1 == getCube(x, y, z).getPosition().z) {
						front = true;
					}
					if (x1 - cubeSize == getCube(x, y, z).getPosition().x
							&& y1 == getCube(x, y, z).getPosition().y
							&& z1 == getCube(x, y, z).getPosition().z) {
						back = true;
					}
					if (x1 == getCube(x, y, z).getPosition().x
							&& y1 + cubeSize == getCube(x, y, z).getPosition().y
							&& z1 == getCube(x, y, z).getPosition().z) {
						right = true;
					}
					if (x1 == getCube(x, y, z).getPosition().x
							&& y1 - cubeSize == getCube(x, y, z).getPosition().y
							&& z1 == getCube(x, y, z).getPosition().z) {
						left = true;
					}
					if (x1 == getCube(x, y, z).getPosition().x
							&& y1 == getCube(x, y, z).getPosition().y
							&& z1 + cubeSize == getCube(x, y, z).getPosition().z) {
						down = true;
					}
					if (x1 == getCube(x, y, z).getPosition().x
							&& y1 == getCube(x, y, z).getPosition().y
							&& z1 - cubeSize == getCube(x, y, z).getPosition().z) {
						up = true;
					}
				}
			}

		}
		if (front && back && left && right && down && up) {
			return (true);
		}
		return (false);
	}
	
	/**
	 * Checks if a cube is rendered
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public boolean checkRender(int x, int y, int z) {
		if (x >= xSize() && y >= ySize(x) && z >= zSize(x, y)
				|| getCube(x, y, z) == null) {
			return (false);
		} else {
			return (true);
		}
	}
	
}
