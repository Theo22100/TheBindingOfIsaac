package gameobjects.nonstaticobjects.grounditem;

import gameWorld.rooms.Room;
import gameWorld.rooms.ShopRoom;
import gameobjects.GameObject;
import gameobjects.Item;
import gameobjects.nonstaticobjects.NonStaticObject;
import libraries.Vector2;
import resources.ImagePaths;

/**
 * 
 * Money is a GroundItem, it uses different variables such as
 * an integer value to indicate the amount of money the hero will be getting by colliding with it.
 * <p>
 * Money is the currency in the game it's used to buy item from a {@link ShopRoom}.
 * <p>
 * 
 * To check what the variable from Money are please refer to {@link GroundItem} , {@link NonStaticObject} and {@link GameObject}.
 * 
 */
public class Money extends GroundItem {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new Money, whose position is specified by the {@link Vector2} argument,
	 * whose room is specified by the {@link Room} argument.
	 * <p>
	 * 
	 * @param position the new Money position
	 * @param room the room the new Money will be in
	 * @param value the amount of money given when the player collides with the new Money
	 */
	public Money(Vector2 position, Room room, int value) {
		super(position, new Vector2(0.018 * 2 ,0.013 * 2), ImagePaths.NICKEL, room, new Item(ImagePaths.COIN, value, "money", null, false), true);
		if (value > 2) this.setImagePath(ImagePaths.DIME);
		if (value > 6) this.setImagePath(ImagePaths.COIN);
	}

}
