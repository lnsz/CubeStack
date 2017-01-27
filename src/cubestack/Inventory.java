package cubestack;

import java.io.Serializable;

import processing.core.PApplet;
import processing.core.PConstants;

@SuppressWarnings("serial")
public class Inventory implements Serializable{
	int[] items; //Items in the inventory
	PApplet game; //See camera
	int select; //Is item selected

	// 0=grass, 1=dirt, 2=stone, 3=iron, 4=gold, 5=diamond, 6=bedrock
	public Inventory(PApplet a, int size) {
		items = new int[size+1];
		game = a;
	}
	
	/**
	 * Getter for number of items
	 * @param index
	 * @return
	 */
	public int getNumber(int index) {
		return (items[index]);
	}
	
	/**
	 * Adds item of specific id
	 * @param id
	 */
	public void addItems(int id) {
		items[id]++;
	}
	
	/**
	 * Removes item of specific id
	 * @param id
	 */
	public void removeItems(int id) {
		items[id]--;
	}
	
	/**
	 * Draws the inventory info
	 */
	public void draw() {
		game.hint(PConstants.DISABLE_DEPTH_TEST);
		for (int i = 0; i < items.length; i++) {
			game.textAlign(PConstants.CENTER);
			if (select==i){
				game.fill(255,0,0);
			}else{
				game.fill(255);
			}
			if (i == 0) {
				game.text("GRASS", game.displayWidth/15*(1+i*2), game.height - 40);
			} else if (i == 1) {
				game.text("DIRT", game.displayWidth/14*(1+i*2), game.height - 40);
			} else if (i == 2) {
				game.text("STONE", game.displayWidth/14*(1+i*2), game.height - 40);
			} else if (i == 3) {
				game.text("IRON", game.displayWidth/14*(1+i*2), game.height - 40);
			} else if (i == 4) {
				game.text("GOLD", game.displayWidth/14*(1+i*2), game.height - 40);
			} else if (i == 5) {
				game.text("DIAMOND", game.displayWidth/14*(1+i*2), game.height - 40);
			}else if (i == 6) {
				game.text("BEDROCK",game.displayWidth/14*(1+i*2), game.height - 40);
			}
			game.text(items[i], game.displayWidth/14*(1+i*2), game.height - 20);
			game.fill(255);
		}
		game.hint(PConstants.ENABLE_DEPTH_TEST);
	}
	/**
	 * Selects an item
	 * @param item
	 */
	public void selectItem(int item){
		select=item;
	}
	/**
	 * Getter for selected item
	 * @return
	 */
	public int getSelected(){
		return(select);
	}

}
