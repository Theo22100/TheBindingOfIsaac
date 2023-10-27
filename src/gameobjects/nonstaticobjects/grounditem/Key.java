package gameobjects.nonstaticobjects.grounditem;

import gameWorld.rooms.Room;
import gameobjects.GameObject;
import gameobjects.Item;
import gameobjects.nonstaticobjects.NonStaticObject;
import libraries.Vector2;
import resources.ImagePaths;

/**
 * 
 * A Key is a GroundItem, but with some special abilities.
 * <p>
 * A key can open a chest, so if a player collides with a chest and presses the interact key, it will open it.
 * <p>
 * To check what the variable from Key are please refer to {@link GroundItem} , {@link NonStaticObject} and {@link GameObject}.
 * 
 */
public class Key extends GroundItem{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new key, whose position is specified by the {@link Vector2} argument,
	 * and whose room is specified by the {@link Room} argument.
	 * 
	 * @param position the key position
	 * @param room the room the new key will be in
	 */
	public Key(Vector2 position, Room room) {
		super(position, new Vector2(0.028,0.04), ImagePaths.KEY, room, new Item(ImagePaths.KEY, 1, "key", null, false), true);
	}
}
