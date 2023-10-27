package gameobjects.staticobjects;

import gameWorld.HitBox;
import gameWorld.rooms.Room;
import gameobjects.GameObject;
import libraries.Vector2;
import resources.ImagePaths;
import resources.RoomInfos;

/**
 * A Stair is a StaticObject that is just to send the player to the next floor.
 * <p>
 * To check what the other variable from Rock are please refer to {@link StaticObject} and {@link GameObject}.
 */
public class Stair extends StaticObject{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new Stair, whose room is specified by the {@link Room} argument. 
	 * The position is calculated using the Tiles size and the posx and posy argument.
	 * 
	 * @param posx the position on the x axes
	 * @param posy the position on the y axes
	 * @param room the room the Rock will be in
	 */
	public Stair(double posx, double posy, Room room) {
		super(new Vector2(posx * RoomInfos.TILE_WIDTH + RoomInfos.HALF_TILE_SIZE.getX(), 
				posy * RoomInfos.TILE_HEIGHT + RoomInfos.HALF_TILE_SIZE.getY()),
				RoomInfos.TILE_SIZE, ImagePaths.STAIRS, room);
		this.setHitBox(new HitBox(new Vector2(RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT * 0.6), this));
	}

}
