package gameobjects.staticobjects;

import gameWorld.HitBox;
import gameWorld.rooms.Room;
import gameobjects.GameObject;
import libraries.Vector2;
import resources.ImagePaths;
import resources.RoomInfos;

/**
 * A rock is a StaticObject that is just used to block the player.
 * <p>
 * To check what the other variable from Rock are please refer to {@link StaticObject} and {@link GameObject}.
 */
public class Rock extends DestroyableStaticObject {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new Rock, whose room is specified by the {@link Room} argument.
	 * The position is calculated using the Tiles size and the posx and posy argument.
	 * 
	 * @param posx the position on the x axes
	 * @param posy the position on the y axes
	 * @param room the room the Rock will be in
	 */
	public Rock(int posx, int posy, Room room) {
		super(new Vector2(posx * RoomInfos.TILE_WIDTH + RoomInfos.HALF_TILE_SIZE.getX(), 
				posy * RoomInfos.TILE_HEIGHT + RoomInfos.HALF_TILE_SIZE.getY()),
				RoomInfos.TILE_SIZE, ImagePaths.ROCK, room, 2);
		setHitBox(new HitBox(new Vector2(RoomInfos.TILE_SIZE.scalarMultiplication(0.7)), this));
	}

}
