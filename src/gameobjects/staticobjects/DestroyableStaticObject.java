package gameobjects.staticobjects;

import gameWorld.rooms.Room;
import gameobjects.GameObject;
import libraries.Vector2;

/**
 * A DestroyableStaticObject is a StaticObject that as the special ability of being destroyed by the players bomb.
 * <p>
 * It has a durability variable, that upon being equal to 0 will destroy the Object.
 * <p>
 * To check what the other variable from NonStaticObject are please refer to {@link GameObject}.
 */
public class DestroyableStaticObject extends StaticObject{
	
	private static final long serialVersionUID = 1L;

	private int durability;

	/**
	 * Constructs a new DestroyableStaticObject, whose position and size are specified by the {@link Vector2} argument,
	 * whose room is specified by the {@link Room} argument.
	 * <p>
	 * This StaticObject is not a trap and will not inflict any damage to object when they collide with it.
	 * 
	 * @param position the position of the new DestroyableStaticObject
	 * @param size the size of the new DestroyableStaticObject
	 * @param imagePath the image of the new DestroyableStaticObject
	 * @param room the room the new DestroyableStaticObject will be in
	 * @param durability the number of damage it will take the object to get destroyed
	 */
	public DestroyableStaticObject(Vector2 position, Vector2 size, String imagePath, Room room, int durability) {
		super(position, size, imagePath, room);
		this.durability = durability;
	}

	/**
	 * Constructs a new DestroyableStaticObject, whose position and size are specified by the {@link Vector2} argument,
	 * whose room is specified by the {@link Room} argument.
	 * <p>
	 * This StaticObject is a trap and will inflict any damage to object when they collide with it.
	 * 
	 * @param position the position of the new DestroyableStaticObject
	 * @param size the size of the new DestroyableStaticObject
	 * @param imagePath the image of the new DestroyableStaticObject
	 * @param room the room the new DestroyableStaticObject will be in
	 * @param damage the damage the new DestroyableStaticObject will inflict to other objects upon collision
	 * @param durability the number of damage it will take the object to get destroyed
	 */
	public DestroyableStaticObject(Vector2 position, Vector2 size, String imagePath, Room room, int damage, int durability) {
		super(position, size, imagePath, room, damage);
		this.durability = durability;
	}
	
	
	
	/*
	 * getter and setter
	 */
	
	/**
	 * Returns the durability of the DestroyableStaticObject.
	 * @return the durability of the DestroyableStaticObject.
	 */
	public int getDurability() {
		return durability;
	}
	
	/**
	 * Sets the new durability of the DestroyableStaticObject.
	 * @param durability the new durability of the DestroyableStaticObject
	 */
	public void setDurability(int durability) {
		this.durability = durability;
	}
}
