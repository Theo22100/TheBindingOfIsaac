package gameobjects.staticobjects;

import gameWorld.HitBox;
import gameWorld.rooms.Room;
import gameobjects.GameObject;
import libraries.Vector2;
import libraries.addedByUs.Utils;
import resources.ImagePaths;
import resources.RoomInfos;

/**
 * A Wall is a StaticObject used to delimit the room. 
 * It blocks anything that tries to go through it.
 *<p>
 * To check what the other variable from Door are please refer to {@link StaticObject} and {@link GameObject}.
 */
public class Wall extends StaticObject{

	private static final long serialVersionUID = 1L;
	
	private HitBox secondHitBox;
	
	/**
	 * Constructs a new Wall, whose room is specified by the {@link Room} argument.
	 * 
	 * @param indexX the position of the new Wall in the x axes
	 * @param indexY the position of the new Wall in the y axes
	 * @param room the room the new Wall will be in
	 */
	public Wall(int indexX, int indexY, Room room) {
		super(new Vector2(), RoomInfos.TILE_SIZE, ImagePaths.WALL, room);
		
		this.setImagePath(getWallType(indexX, indexY));
		this.setDegree(getWallOrientation(indexX, indexY));
		this.setPosition(Utils.positionFromTileIndex(indexX, indexY));
		
		this.setHitBox(new HitBox( getWallHitBox(getImagePath(), getDegree()) , this));
		if (isInmiddleAxes(indexX, indexY)) {
			for (Door d: room.getListDoor()) {
				if (!this.collide(d)) {
					this.setHitBox(new HitBox( getWallHitBox(getImagePath(), getDegree()) , this));
				}
				else {
					this.setHitBox(null); 
					break;
				}
			}
		}
	}
	
	/**
	 * Checks if the coordinates are on a middleAxes of a wall.
	 * 
	 * @param indexX the x position on the X axes
	 * @param indexY the y position on the Y axes
	 * @return {@code true} if it's in the middle axes, {@code false} otherwise.
	 */
	private boolean isInmiddleAxes(int indexX, int indexY) {
		if ((indexX ==( RoomInfos.NB_TILES - 1 )/ 2 || indexY ==(RoomInfos.NB_TILES - 1) / 2)
		) return true;
		return false;
	}

	/**
	 * Returns the Vector hitbox size used for firstHitbox of the wall, base on what side the wall is. 
	 * At the same time it also sets the secondHitBox used for walls that are on the diagonal.
	 * 
	 * @param ImagePath the Wall image
	 * @param degree the orientation of the Wall
	 * @return A vector that refers to the wall hitbox size.
	 */
	private Vector2 getWallHitBox(String ImagePath, int degree) {
		if (ImagePath == ImagePaths.WALL) {
			if (degree == 0 || degree == 180) return new Vector2(RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT * 0.7);
			else return  new Vector2(RoomInfos.TILE_WIDTH * 0.7, RoomInfos.TILE_HEIGHT);
		}
		else {
			secondHitBox = new HitBox(new Vector2(RoomInfos.TILE_WIDTH * 0.7, RoomInfos.TILE_HEIGHT), this);
			return new Vector2(RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT * 0.7);
		}
	}

	/**
	 * Returns the wall type, either CORNER_WALL or WALL, based on the coordinates inputed.
	 * 
	 * @param indexX the x position on the x axes
	 * @param indexY the y position on the y axes
	 * @return the imagePath corresponding to the wall type.
	 */
	private String getWallType(int indexX, int indexY) {
		if ((indexX == 0 && indexY == 0) || (indexX == 0 && indexY == RoomInfos.NB_TILES - 1) || (indexY == 0 && indexX == RoomInfos.NB_TILES - 1) 
				|| (indexY == RoomInfos.NB_TILES - 1 && indexX == RoomInfos.NB_TILES -1) ) {
				return ImagePaths.CORNER_WALL;
			}
		return ImagePaths.WALL;
	}

	/**
	 * Returns the wall orientation in degrees, based on the coordinates inputed.
	 * 
	 * @param indexX the x position on the x axes
	 * @param indexY the y position on the y axes
	 * @return the wall orientation either 0, 90, 180 or 270.
	 */
	private int getWallOrientation(int indexX, int indexY) {		
		if (indexX == RoomInfos.NB_TILES - 1) {
			if (indexY == 0) return 180;
			return 270;
		}
		if (indexX == 0) {
			if (indexY == RoomInfos.NB_TILES - 1) return 0;
			return 90;
		}
		if (indexY == RoomInfos.NB_TILES - 1) return 0;
		return 180;
	}
	
	/**
	 * Checks if the wall hitboxes are within the other object hitbox.
	 * 
	 * @param object the GameObject you want to check collision with
	 * @return {@code true} if the hitboxes are colliding, {@code false} otherwise.
	 */
	@Override
	public boolean collide(GameObject object) {
		boolean tmp = false;
		if (this.getHitBox() != null) tmp = super.collide(object);
		if (this.getSecondHitBox() != null) {
			if (tmp == false) tmp = this.secondHitBox.collide(object.getHitBox());
		}
		return tmp;
	}
	
	/**
	 * Checks if the wall hitboxes are within the other object hitbox at the given position.
	 * 
	 * @param object the GameObject you want to check collision with
	 * @return {@code true} if the hitboxes are colliding, {@code false} otherwise.
	 */
	@Override
	public boolean collideWithPosition(Vector2 position, GameObject object) {
		boolean tmp = false;
		if (this.getHitBox() != null) tmp = object.collideWithPosition(position, this);
		if (this.getSecondHitBox() != null) {
			if (tmp == false) tmp = secondHitBox.collide(position, object.getHitBox());
		}
		return tmp;
	}
	
	
	/*
	 * getter and setter
	 */
	
	/**
	 * Returns the second Hitbox of the wall.
	 * @return the second Hitbox of the wall.
	 */
	public HitBox getSecondHitBox() {
		return secondHitBox;
	}
}
