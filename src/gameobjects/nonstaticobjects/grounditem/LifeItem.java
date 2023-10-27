package gameobjects.nonstaticobjects.grounditem;

import gameWorld.rooms.Room;
import gameobjects.GameObject;
import gameobjects.nonstaticobjects.NonStaticObject;
import libraries.Vector2;
import resources.ImagePaths;

/**
 * A LifeItem is a GroundItem, it uses different variables such as 
 * a boolean half which specifies whether the heart is full or not.
 * <p>
 * The LifeItem also has a value which indicates how many health the hero will be getting back.
 * This value is equal to 2 by default, but if the boolean half is set to true, then the value will be set to 1.
 * <p>
 * 
 * To check what the variable from LifeItem are please refer to {@link GroundItem} , {@link NonStaticObject} and {@link GameObject}.
 * 
 */
public class LifeItem extends GroundItem {

	private static final long serialVersionUID = 1L;
	
	private int value;
	
	/**
	 * 
	 * Constructs a new LifeItem, whose position is specified by the {@link Vector2} argument,
	 * whose room is specified by the {@link Room} argument.
	 * <p>
	 * When half is set to true, then the heart will be halved.
	 * 
	 * @param position the new lifeItem position
	 * @param room the room the new lifeItem will be in
	 * @param half the boolean to check whether it's a full hearth or not
	 */
	public LifeItem(Vector2 position, Room room, boolean half) {
		super(position, new Vector2(0.016*2, 0.013*2), ImagePaths.HEART_PICKABLE, room, null, true);
		value = 2;
		if (half) {
			this.setSize(new Vector2(0.016*2, 0.014*2));
			this.setImagePath(ImagePaths.HALF_HEART_PICKABLE);
			value = 1;
		}
	}
	
	/*
	 * getter and setter
	 */
	
	/**
	 * Returns the value the hero will be healed
	 * @return the value the hero will be healed
	 */
	public int getValue() {
		return value;
	}
}
