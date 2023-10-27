package gameobjects.nonstaticobjects.grounditem;

import org.eclipse.jdt.annotation.Nullable;

import gameWorld.rooms.Room;
import gameobjects.GameObject;
import gameobjects.Item;
import gameobjects.nonstaticobjects.Hero;
import gameobjects.nonstaticobjects.NonStaticObject;
import gameobjects.nonstaticobjects.Tear;
import gameobjects.nonstaticobjects.monsters.Fly;
import libraries.Vector2;

/**
 *
 * A groundItem is a object that can be picked or moved by the hero, 
 * it uses different variables such as an Item item that represents the item given
 * to the hero if it collides with it, it's a nullable variable.
 * <p>
 * 
 * To check what the over variable from groundItem are please refer to {@link NonStaticObject} and {@link GameObject}.
 * 
 *<p>
 *<b> Creating a groundItem </b>
 *<p>
 * Here's an example to create a groundItem : 
 * <pre>
 * GroundItem item = new GroundItem(new Vector2(0.5, 0.5), new Vector2(0.1,0.1), "image/item0", currentRoom, null, true);
 * </pre>
 * 
 * @see Item Hero
 */
public class GroundItem extends NonStaticObject{
	private static final long serialVersionUID = 1L;
	
	private Item item;
	private boolean consumable;
	
	/**
	 * Constructs a new GroundItem, whose position and size are specified by the {@link Vector2} argument,
	 * whose room is specified by the {@link Room} argument, and whose item is specified by the {@link Item} argument.
	 * <p>
	 * 
	 * @throws IllegalArgumentException unless both {@code position} and
	 *                                  {@code size} aren't null
	 * 
	 * @param position the position of the new GroundItem
	 * @param size the size of the new GroundItem
	 * @param imagePath the image of the new GroundItem
	 * @param room the room the new GroundItem will be in
	 * @param item the item that the player will pick up by colliding with the new GroundItem
	 * @param consumable the boolean to check whether it's a consumable object
	 */
	public GroundItem(Vector2 position, Vector2 size, String imagePath, Room room, @Nullable Item item,
			boolean consumable) {
		super(position, size, imagePath, room, 0.01, 0, 0);
		this.item = item;
	}
	
	/**
	 * Not used in this class, but it's used in many other NonStaticObject,
	 * to see example of the used check {@link Tear#animation()},
	 * {@link Fly#animation()}
	 *  or {@link Hero#animation()}.
	 */
	@Override
	public void animation() {
	}
	
	/*
	 * getter and setter
	 */
	
	/**
	 * Returns the item of the GroundItem, that the hero will pick up by colliding with the groundItem.
	 * @return the item of GroundItem
	 */
	public Item getItem() {
		return item;
	}
	
	/**
	 * Sets what item the GroundItem is holding on too.
	 * @param item the item that the player will pick up by colliding with the GroundItem
	 */
	public void setItem(Item item) {
		this.item = item;
	}
	
	/**
	 * Returns what state the GroundItem is in, whether it's consumable or not.
	 * @return {@code true} if the GroundItem can be consumed , {@code false} otherwise.
	 */
	public boolean getConsumable() {
		return consumable;
	}
	
	/**
	 * Sets what state the GroundItem is in, whether it's consumable or not.
	 * <p>
	 * if consumable is set to {@code true} then the item will be consumable, if it's set to {@code false} then the item will not be consumable.
	 *  
	 * @param consumable the consumable state of the GroundItem
	 */
	public void setConsumable(boolean consumable) {
		this.consumable = consumable;
	}
}
