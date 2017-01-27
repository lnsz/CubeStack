package cubestack;

import processing.core.*;

@SuppressWarnings("serial")
public class Bullet extends PApplet {
	//This is still in the game but the new targetting system is much better
	//You can try it by pressing k
	//There are probably bugs because i stopped working with it
	private PVector position;
	private int size;
	private float directionX, directionY, directionZ;
	private int speed;
	private int distanceTravelled;
	private int range;
	private boolean destroy;
	private PApplet game;

	public Bullet(float x, float y, float z, int targetX, int targetY,
			PApplet a, float centerX, float centerY, float centerZ, boolean d) {
		position = new PVector(x + centerX*15, y + centerY*15, z + centerZ*15);
		directionX = centerX;
		directionY = centerY;
		directionZ = centerZ;
		game = a;
		range= 150;
		distanceTravelled= 0;
		size= 1;
		destroy=d;
		if (destroy==false){
		speed = 5;
		}else{
			speed=15;
		}
	}

	public void move() {
		position.x = position.x + directionX * speed;
		position.y = position.y + directionY * speed;
		position.z = position.z + directionZ * speed;

	}
	public boolean getDestroy(){
		return (destroy);
	}
	public int getDistanceTravelled() {
		return (distanceTravelled);
	}

	public int getRange() {
		return (range);
	}

	public PVector getPosition() {
		return (position);
	}

	public int getS() {
		return (size);
	}
	public float getDirectionX(){
		return(directionX);
	}
	public float getDirectionY(){
		return(directionY);
	}
	public float getDirectionZ(){
		return(directionZ);
	}
	public void draw() {
		if (destroy==true){
			game.stroke(255);
		}
		else{
			game.stroke(0);
		}
		game.pushMatrix();
		game.translate(position.x, position.y, position.z);
		game.sphere(size);
		game.popMatrix();
		distanceTravelled++;

	}

}
