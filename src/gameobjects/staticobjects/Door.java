package gameobjects.staticobjects;

import gameWorld.HitBox;
import gameWorld.rooms.Room;
import gameobjects.GameObject;
import libraries.Vector2;
import resources.RoomInfos;

/**
 * A Door is a StaticObject used to go from a Room to another 
 * upon collision by the player with a opened one.
 * <p>
 * It uses a couple unique variable, such as a boolean closed, 
 * used to know if a door is opened or closed. 
 * A String position, to know what orientation the door is facing.
 * <p>
 * There's also two String variable that indicate what image to use when closed or opened.
 * <p>
 * To check what the other variable from Door are please refer to {@link StaticObject} and {@link GameObject}.
 */
public class Door extends StaticObject {

	private static final long serialVersionUID = 1L;

	private boolean closed;
	private String closedImagePath;
	private String oppenedImagePath;
	private String orientation;
	
	/**
	 * Constructs a new Door, whose position and size are specified by the {@link Vector2} argument, 
	 * whose room is specified by the {@link Room} argument.
	 * 
	 * @param orientation the orientation the new Door will be facing
	 * @param room the room the new Door is in
	 * @param closed the state of the new Door
	 * @param openedImagePath the image of the new Door when opened
	 * @param closedImagePath the image of the new Door when closed
	 */
	public Door(String orientation, Room room, boolean closed , String openedImagePath, String closedImagePath) {
		super(new Vector2(), new Vector2(120*0.0014,81*0.0014), openedImagePath, room);
		double y = 81*0.0006;
		Vector2 h = new Vector2(RoomInfos.TILE_WIDTH - 0.005, y - 0.02);
		this.orientation = orientation;
		if (orientation.equals("WEST")) {
			this.setDegree(90);
			this.setPosition(new Vector2(0.06, ( RoomInfos.TILE_HEIGHT * RoomInfos.NB_TILES ) / 2));
			h = new Vector2(y,RoomInfos.TILE_HEIGHT - 0.005);
		}
		else if (orientation.equals("EST")) {
			this.setDegree(-90);
			this.setPosition(new Vector2(1- 0.06, ( RoomInfos.TILE_HEIGHT * RoomInfos.NB_TILES ) / 2));
			h = new Vector2(y,RoomInfos.TILE_HEIGHT - 0.005);
		}
		else if (orientation.equals("NORTH")) {
			this.setPosition(new Vector2(( RoomInfos.TILE_WIDTH * RoomInfos.NB_TILES ) / 2, 1 - RoomInfos.TILE_HEIGHT - 0.06));
		}
		else if (orientation.equals("SOUTH")) {
			this.setDegree(180);
			this.setPosition(new Vector2(( RoomInfos.TILE_WIDTH * RoomInfos.NB_TILES ) / 2, 0.06));
			h = new Vector2(RoomInfos.TILE_WIDTH - 0.005, y - 0.02);
		}
		setHitBox(new HitBox(h,this));
		this.closed = closed;
		if (closed) setImagePath(closedImagePath);
		this.closedImagePath = closedImagePath;
		this.oppenedImagePath = openedImagePath;
	}
	
	/*
	 * functions
	 */

	/**
	 * Opens the door by setting the image to the opened one and the state closed to false.
	 */
	public void openDoor() {
		setImagePath(oppenedImagePath);
		closed = false;
	}
	
	/**
	 * Closes the door by setting the image to the closed one and the state closed to true.
	 */
	public void closeDoor() {
		setImagePath(closedImagePath);
		closed = true;
	}
	
	/*
	 * getter and setter
	 */
	
	/**
	 * Returns the closed state of the door, whether it's opened or not.
	 * @return {@code true} if the door is closed, {@code false} if the door is opened.
	 */
	public boolean getClosed() {
		return closed;
	}
	
	/**
	 * Returns the orientation the door is facing. 
	 * It can ever be "EAST", "WEST", "NORTH" or "SOUTH.
	 * @return the orientation the door is facing.
	 */
	public String getOrientation() {
		return orientation;
	}
}
