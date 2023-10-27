package gameobjects.staticobjects;

import gameWorld.rooms.Room;
import gameobjects.GameObject;
import libraries.Vector2;

/**
 * A StaticObject is a GameObject that as the special ability of being able to block or damage 
 * the other objects that collides with it.
 * <p>
 * It has two unique variable, the first one is a boolean trap, used to check if the StaticObject is a trap or not. 
 * The other one is a integer damage, that indicates how much health it will take away upon impact.
 * <p>
 * To check what the other variable from NonStaticObject are please refer to {@link GameObject}.
 */                                                                                                                                 
public class StaticObject extends GameObject{

	private static final long serialVersionUID = 1L;
	
	private int damage = 0;
	private boolean trap = false;

	/**
	 * Constructs a new StaticObject, whose position and size are specified by the {@link Vector2} argument, 
	 * whose room is specified by the {@link Room} argument.
	 * <p>
	 * This StaticObject is not a trap and will not inflict any damage to object when they collide with it.
	 * 
	 * @param position the position of the new StaticObject
	 * @param size the size of the new StaticObject
	 * @param imagePath the image of the new StaticObject
	 * @param room the room the new StaticObject will be in
	 */
	public StaticObject(Vector2 position, Vector2 size, String imagePath, Room room) {
		super(position, size, imagePath, room);
	}

	/**
	 * Constructs a new StaticObject, whose position and size are specified by the {@link Vector2} argument, 
	 * whose room is specified by the {@link Room} argument.
	 * <p>
	 * This StaticObject is a trap and will inflict damage to object when they collide with it.
	 * 
	 * @param position the position of the new StaticObject
	 * @param size the size of the new StaticObject
	 * @param imagePath the image of the new StaticObject
	 * @param room the room the new StaticObject will be in
	 * @param damage the damage the new StaticObject will inflict to other objects upon collision
	 */
	public StaticObject(Vector2 position, Vector2 size, String imagePath, Room room, int damage) {
		super(position, size, imagePath, room);
		this.damage = damage;
		this.trap = true;
	}
	
	/**
	 * Returns the damage inflicted by the StaticObject upon collision.
	 * @return the damage inflicted by the StaticObject upon collision.
	 */
	public int getDamage() {
		return damage;
	}
	
	/**
	 * Returns the trap state of the StaticObject, whether it's a trap or not.
	 * @return {@code true} if it's a trap, {@code false} if it's not.
	 */
	public boolean getTrap() {
		return trap;
	}

}
