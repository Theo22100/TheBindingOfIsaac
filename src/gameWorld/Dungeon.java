package gameWorld;

import java.io.Serializable;
import java.util.ArrayList;

import gameobjects.nonstaticobjects.Hero;

/**
 * A Dungeon is a list of Floor that the Hero will be able to clear. 
 * The game is beaten when Boss from all Floor have been defeated.
 * 
 * @see Floor
 */
public class Dungeon implements Serializable {

	private static final long serialVersionUID = 1L;
	
	ArrayList<Floor> lFloor = new ArrayList<Floor>();
	private Floor currentFloor;
	
	/**
	 * Constructs a new Dungeon whose Hero is specified with the {@link Hero} argument.
	 * 
	 * @param numberFloor the number of Floor of the new Dungeon
	 * @param hero the Hero of the new Dungeon
	 * @throws IllegalArgumentException if the number of Floor is less or equal to 0
	 */
	public Dungeon(int numberFloor, Hero hero) {
		if (numberFloor <= 0) throw new IllegalArgumentException("the number of Floors must be greater than 0");
		for (int i = 0; i < numberFloor; i++) {
			lFloor.add(new Floor(5 + i*i + i, 4 + i, 4 + i, hero));
		}
		currentFloor = lFloor.get(0);
	}
	
	
	/**
	 * Returns the next Floor of the Dungeon.
	 * @return the next Floor of the Dungeon.
	 */
	public Floor getNextFloor() {
		int indexOfNextFloor = lFloor.indexOf(currentFloor) + 1;
		if (indexOfNextFloor >= lFloor.size()) return null;
		currentFloor = lFloor.get(indexOfNextFloor);
		return currentFloor;
	}
	
	
	/**
	 * getter and setter
	 */
	
	/**
	 * Returns the current Floor.
	 * @return the current Floor.
	 */
	public Floor getCurrentFloor() {
		return currentFloor;
	}
}
