package gameobjects.staticobjects;

import gameWorld.HitBox;
import gameWorld.rooms.Room;
import gameobjects.GameObject;
import libraries.Vector2;
import resources.ImagePaths;
import resources.RoomInfos;

/**
 * A Poop is a DestroyableStaticObject, that can both be destroyed by Tears and Bombs.
 * 
 * <p>
 * To check what the variable from Poop are please refer to {@link StaticObject} and {@link GameObject}.
 */
public class Poop extends DestroyableStaticObject {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new Poop, whose room is specified by the {@link Room} argument.
	 * The position is calculated using the Tiles size and the posx and posy argument.
	 * 
	 * @param posx the position on the x axes
	 * @param posy the position on the y axes
	 * @param room the room the Poop will be in
	 */
	public Poop(int posx, int posy, Room room) {
		super(new Vector2(posx * RoomInfos.TILE_WIDTH + RoomInfos.HALF_TILE_SIZE.getX(), 
				posy * RoomInfos.TILE_HEIGHT + RoomInfos.HALF_TILE_SIZE.getY()),
				RoomInfos.TILE_SIZE, ImagePaths.POOP_0, room, 2);
		setHitBox(new HitBox(new Vector2(RoomInfos.TILE_SIZE.scalarMultiplication(0.7)), this));
	}

	/**
	 * Sets the new durability of the Poop, and changes the image according to the durability.
	 * @param durability the new durability
	 */
	@Override
	public void setDurability(int durability) {
		super.setDurability(durability);
		switch (durability) {
			case 1 : setImagePath(ImagePaths.POOP_1); break;
			case 0 : setImagePath(ImagePaths.POOP_2); break;
			default: break;
		}
	}
}
