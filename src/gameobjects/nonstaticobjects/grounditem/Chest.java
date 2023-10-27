package gameobjects.nonstaticobjects.grounditem;

import gameWorld.rooms.Room;
import gameobjects.GameObject;
import gameobjects.nonstaticobjects.NonStaticObject;
import libraries.Vector2;
import resources.ImagePaths;

/**
 * A Chest is a GroundItem, but with some special abilities.
 * <p>
 * A key can open a chest, so if a player collides with a chest and presses the interact key, it will open it.
 * <p>
 * To check what the variable from Chest are please refer to {@link GroundItem} , {@link NonStaticObject} and {@link GameObject}.
 * 
 */
public class Chest extends GroundItem {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new Chest, whose position is specified by the {@link Vector2} argument,
	 * and whose room is specified by the {@link Room} argument.
	 * 
	 * @param position the Chest position
	 * @param room the room the new Chest will be in
	 */
	public Chest(Vector2 position, Room room) {
		super(position, new Vector2(0.03 * 2,0.023 * 2), ImagePaths.CHEST, room, null, false);
	}

}
