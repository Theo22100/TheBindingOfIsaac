package gameobjects;

import java.awt.Color;
import java.io.Serializable;

import org.eclipse.jdt.annotation.Nullable;

import gameWorld.HitBox;
import gameWorld.rooms.Room;
import gameobjects.nonstaticobjects.Hero;
import gameobjects.nonstaticobjects.NonStaticObject;
import gameobjects.nonstaticobjects.grounditem.GroundItem;
import gameobjects.nonstaticobjects.monsters.Fly;
import gameobjects.nonstaticobjects.monsters.Monster;
import gameobjects.staticobjects.Door;
import gameobjects.staticobjects.StaticObject;
import gameobjects.staticobjects.Wall;
import libraries.StdDraw;
import libraries.Vector2;
import resources.RoomInfos;

/**
 * A GameObject is the class used to create any GameObject, 
 * it uses a lot of parameter, such as a Vector2 position that refers to the position in the window. 
 * <p> 
 * Another Vector2 size that is how big the GameObject will appear. Linked to that is the HitBox that by default 
 * will be the same size as the size but can be changed. Check {@link HitBox} to see how it's created.
 * <p>
 * Then there's the String imagePath, that's what image is going to be used to represent the GameObject. 
 * And an integer degree, that is the rotation applied to the image of the GameObject. 
 * The Room room is next variable on the list, that stores what Room the GameObject will be in.
 * <p>
 * Lastly there's the Color color, and integer transparency, used when there's a need to 
 * apply a transparent color on top of GameObject.
 * 
 * Usually a gameObject is created with child class and not by itself.
 * 
 * @see Room HitBox
 */
public class GameObject implements Serializable {
//	to be able to load it from a file
	private static final long serialVersionUID = 1L;
	
	private Vector2 position;
	private Vector2 size;
	private String imagePath;
	private HitBox hitBox;
	private Room room;
	private int degree;
	private Color color = null;
	private int transparency = 180;
	
/*
 * constructor
 */
	
	/**
	 * Constructs a new GameObject, whose position and size are specified by the {@link Vector2} argument,
	 * whose room is specified by the {@link Room} argument.
	 * 
	 * Note that the room is the only variable that is nullable, and is only null when creating the Hero.
	 * 
	 * @throws IllegalArgumentException unless both {@code position} and
	 *                                  {@code size} aren't null
	 * @param position the position of the new GameObject
	 * @param size the size of the new GameObject
	 * @param imagePath the imagePath of the new GameObject
	 * @param room the room the new GameObject will be in
	 */
	public GameObject(Vector2 position, Vector2 size, String imagePath, @Nullable Room room) {
		if (size == null || size.equals(new Vector2()) ) throw new IllegalArgumentException("the size cant be null");
		if (position == null ) throw new IllegalArgumentException("the position cant be null");
		if (imagePath == null || imagePath.equals("") ) throw new IllegalArgumentException("the imagePath is not set");
		
		this.position = position;
		this.size = size;
		this.imagePath = imagePath;
		this.hitBox = new HitBox(size, this);
		this.room = room;
	}
	
/*
 * 	functions
 */
		
	/**
	 * Draws the gameObject based on it's position, imagePath, size and degree.
	 * <p>
	 * If a color is specified then it uses {@link StdDraw#picture(double, double, String, double, double, double, Color, int)} 
	 * to draw a transparent filter of the color on top of the GameObject.
	 */
    public void drawGameObject()
	{
    	if (color == null)
    		StdDraw.picture(getPosition().getX(), getPosition().getY(), imagePath, getSize().getX(), getSize().getY(),
				degree);
    	else 
    		StdDraw.picture(getPosition().getX(), getPosition().getY(), imagePath, getSize().getX(), getSize().getY(),
				degree,color, transparency);
	}
	
	/**
	 * Checks if the GameObject hitbox is colliding with another GameObject.
	 * 
	 * @param object the GameObject you want to check collision with
	 * @return {@code true} if the hitboxs are colliding, {@code false} otherwise.
	 */
	public boolean collide(GameObject object) {
		return this.hitBox.collide(object.hitBox);
	}
	
	/**
	 * Checks if the GameObject hitbox is colliding with another GameObject, at the specified coordinates.
	 * 
	 * @param position the position of the GameObject you want to test
	 * @param object the GameObject you want to check collision with
	 * @return {@code true} if the hitboxs are colliding, {@code false} otherwise.
	 */
	public boolean collideWithPosition(Vector2 position, GameObject object) {
		return object.getHitBox().collide(position, this.getHitBox());
	}
	
	/**
	 * Checks if this GameObject is of the same instance as the other GameObject.
	 * 
	 * @param object the GameObject 
	 * @return {@code true} if the instances are the same, {@code false} otherwise.
	 */
	public boolean isSameInstance(GameObject object) {
		if (this instanceof Hero && object instanceof Hero) return true;
		if (this instanceof Monster && object instanceof Monster) return true;
		if (this instanceof Fly && object instanceof Fly) return true;
		return false;
	}
	
	/**
	 * Returns whether or not a position is legal. 
	 * Which is legal if the position is not colliding with any other object in the room.
	 * @param position the position for the object
	 * @return {@code true} if the position is legal, {@code false otherwise}.
	 */
	public boolean legalPosition(Vector2 position) {
		if (position.getX() > RoomInfos.TILE_WIDTH && position.getX() < 1 - RoomInfos.TILE_WIDTH) {
			if (position.getY() > RoomInfos.TILE_HEIGHT && position.getY() < 1 - 2 * RoomInfos.TILE_HEIGHT);
			else return false;
		}
		else return false;
		for (StaticObject so : getRoom().getListStaticObject()) {
			if (this.collideWithPosition(position, so) ) return false;
		}
		for (NonStaticObject nso: getRoom().getListNonStaticObject()) {
			if (this.collideWithPosition(position, nso)) return false;
		}
		for (GroundItem go: getRoom().getListGroundItem()) {
			if (this.collideWithPosition(position, go)) return false;
		}
		for (Wall w: getRoom().getListWall()) {
			if (w.collideWithPosition(position, this)) return false;
		}
		for (Door d: getRoom().getListDoor()) {
			if (this.collideWithPosition(position, d)) return false;
		}
		if (this.collideWithPosition(position, getRoom().getHero())) return false;
		return true;
	}
	
	
/*
 * getter and setter
 */
	
	/**
	 * Returns the GameObject position.
	 * @return the GameObject position.
	 */
	public Vector2 getPosition()
	{
		return position;
	}

	/**
	 * Sets the new position of the GameObject.
	 * @param position the new position for the GameObject
	 */
	public void setPosition(Vector2 position)
	{
		this.position = position;
	}

	/**
	 * Returns the GameObject size.
	 * @return the GameObject size.
	 */
	public Vector2 getSize()
	{
		return size;
	}
	
	/**
	 * Sets the new size of the GameObject.
	 * @param size the new size for the GameObject
	 */
	public void setSize(Vector2 size)
	{
		this.size = size;
	}
	
	/**
	 * Returns the GameObject imagePath.
	 * @return the GameObject imagePath.
	 */
	public String getImagePath()
	{
		return imagePath;
	}
	
	/**
	 * Sets the new imagePath of the GameObject.
	 * @param imagePath the new imagePath for the GameObject
	 */
	public void setImagePath(String imagePath)
	{
		this.imagePath = imagePath;
	}
	
	/**
	 * Sets the new HitBox of the GameObject.
	 * @param hitBox the new HitBox for the GameObject
	 */
	public void setHitBox(HitBox hitBox) {
		this.hitBox = hitBox;
	}
	
	/**
	 * Returns the GameObject HitBox.
	 * @return the GameObject HitBox.
	 */
	public HitBox getHitBox() {
		return this.hitBox;
	}
	
	/**
	 * Returns the GameObject Room.
	 * @return the GameObject Room.
	 */
	public Room getRoom() {
		return this.room;
	}
	
	/**
	 * Sets the new Room of the GameObject.
	 * @param room the new Room for the GameObject
	 */
	public void setRoom(Room room) {
		this.room = room;
	}
	
	/**
	 * Returns the GameObject orientation in degrees.
	 * @return the GameObject orientation in degrees.
	 */
	public int getDegree() {
		return this.degree;
	}
	
	/**
	 * Sets the new orientation of the GameObject in degrees.
	 * @param degree the new orientation in degrees for the GameObject
	 */
	public void setDegree(int degree) {
		this.degree = degree;
	}
	
	/**
	 * Sets the new Color filter of the GameObject.
	 * @param color the new Color filter for the GameObject
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Returns the GameObject Color filter.
	 * @return the GameObject Color filter.
	 */
	public Color getColor() {
		return this.color;
	}
	
	
	/**
	 * Sets the new transparency value of the GameObject.
	 * @param transparency the new transparency value for the GameObject
	 */
	public void setTransparency(int transparency) {
		this.transparency = transparency;
	}
	
	/**
	 * Returns the GameObject transparency value.
	 * @return the GameObject transparency value.
	 */
	public int getTransparency() {
		return this.transparency;
	}
}
