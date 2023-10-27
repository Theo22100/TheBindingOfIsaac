package gameobjects.nonstaticobjects.grounditem;

import gameWorld.HitBox;
import gameWorld.rooms.Room;
import gameobjects.GameObject;
import gameobjects.nonstaticobjects.NonStaticObject;
import libraries.Vector2;
import resources.ImagePaths;
import resources.ItemInfos;

/**
 * 
 * A Bomb is a GroundItem, but with some special abilities. It 
 * can explode when placed by the player, to damage NonStaticObjects and StaticObjects.
 * <p>
 * It uses a integer variable for explosionDelay to know when the bomb will explode. There's also a boolean 
 * damageEnable used to know when the bomb can damage other objects
 * 
 * To check what the variable from Bomb are please refer to {@link GroundItem} , {@link NonStaticObject} and {@link GameObject}.
 * 
 */
public class Bomb extends GroundItem{

	private static final long serialVersionUID = 1L;
	
	private int explosionDelay;
	private boolean trueDead = false;
	private boolean damageEnable = false;
	
	/**
	 * Constructs a new Bomb, whose position is specified by the {@link Vector2} argument,
	 * and whose room is specified by the {@link Room} argument.
	 * 
	 * @param position the Bomb position
	 * @param room the room the new Bomb will be in
	 */
	public Bomb(Vector2 position, Room room) {
		super(position, ItemInfos.BOMBE_SIZE, ImagePaths.BOMB, room, ItemInfos.BOMBE_ITEM, true);
		this.explosionDelay = -1;
	}
	
	/**
	 * Constructs a new Bomb, whose position is specified by the {@link Vector2} argument,
	 * and whose room is specified by the {@link Room} argument.
	 * <p>
	 * This constructor is used to create an exploding bomb.
	 * 
	 * 
	 * @param position the new Bomb position
	 * @param room the room the new Bomb will be in
	 * @param explosionDelay the time the new Bomb will take to explode
	 * @param damage the damage dealt by the new Bomb when exploding
	 */
	public Bomb(Vector2 position, Room room, int explosionDelay, double damage) {
		super(position, ItemInfos.BOMBE_SIZE, ImagePaths.BOMB, room, ItemInfos.BOMBE_ITEM, false);
		this.explosionDelay = explosionDelay;
		this.setDamage(damage);
	}
	
	/**
	 * Used to change the image of the bomb while it's exploding.
	 */
	@Override
	public void animation() {
		if (explosionDelay >= 0) {
			switch (this.getAnimationState()) {
			case 34: this.setImagePath(ImagePaths.BOMB_1); break;
			case 32: this.setImagePath(ImagePaths.BOMB_2); break;
			case 30: this.setImagePath(ImagePaths.BOMB_3); break;
			case 28: this.setImagePath(ImagePaths.BOMB_4); break;
			case 26: this.setImagePath(ImagePaths.BOMB_5); break;
			case 24: this.setImagePath(ImagePaths.BOMB_6); break;
			case 22: this.setImagePath(ImagePaths.BOMB_7); break;
			case 20: this.setImagePath(ImagePaths.BOMB_8); damageEnable = true; break;
			case 18: this.setImagePath(ImagePaths.BOMB_9); break;
			case 16: this.setImagePath(ImagePaths.BOMB_10); break;
			case 14: this.setImagePath(ImagePaths.BOMB_11); damageEnable = false; break;
			case 12: this.setImagePath(ImagePaths.BOMB_12); break;
			case 10: this.setImagePath(ImagePaths.BOMB_13); break;
			case 8: this.setImagePath(ImagePaths.BOMB_14); break;
			case 6: this.setImagePath(ImagePaths.BOMB_15); break;
			case 4: this.setImagePath(ImagePaths.BOMB_16); break;
			case 2: this.setImagePath(ImagePaths.BOMB_17); break;
			case 0: trueDead = true;
			default : break;
			}
			setAnimationState( getAnimationState() - 1);
		}
	}
	
	
	/**
	 * Updates item object movement.
	 */
	public void updateGameObject()
	{
		if (explosionDelay == -1) return;
		if (!getDead() && explosionDelay > 0) explosionDelay--;
		else if (!getDead()) {
			setAnimationState(34);
			explode();
		}
		if (getDead()) animation();
	}
	
	/**
	 * Make the bomb explode and sets a bigger HitBox to damage everything in the HitBox.
	 */
	public void explode() {
		this.setHitBox(new HitBox(new Vector2(0.15, 0.15), this));
		this.setDead(true);
	}
	
	
	/*
	 * getter and setter
	 */
	
	/**
	 * Returns the explosion delay of the bomb.
	 * @return the explosion delay of the bomb.
	 */
	public int getExplosionDelay() {
		return explosionDelay;
	}
	
	
	/**
	 * Returns the damage state of the bomb. 
	 * @return {@code true} if the bomb can damage other things, {@code false} otherwise.
	 */
	public boolean getDamageEnable() {
		return damageEnable;
	}
	
	/**
	 * Get the dead state of the bomb, whether it's truly dead or not.
	 * @return {@code true} if truly dead, {@code false} otherwise.
	 */
	public boolean getTrueDead() {
		return trueDead;
	}
}
