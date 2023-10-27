package gameobjects.nonstaticobjects;

import java.awt.Color;

import org.eclipse.jdt.annotation.Nullable;

import gameWorld.HitBox;
import gameWorld.rooms.Room;
import gameobjects.GameObject;
import gameobjects.Item;
import gameobjects.staticobjects.Door;
import gameobjects.staticobjects.Poop;
import gameobjects.staticobjects.Stair;
import gameobjects.staticobjects.StaticObject;
import gameobjects.staticobjects.Wall;
import libraries.Vector2;

/**
 * A NonStaticObject is a GameObject that as the special ability of being able to move. 
 * It uses some unique variable such as a Vector2 for the direction the object will go, 
 * a double speed used to know fast the object will travel in the given direction.
 * <p>
 * There also are two important variable, double health which indicate how much health the object has, 
 * and double damage that represents how much health the GameObject will damage other objects.
 * <p>
 * The last variables are the integer animationState that indicate what state of animation the object is in, 
 * the others are boolean, one named dead used to know if the object is dead, and isHit to know if the object as been hit.
 * 
 * To check what the other variable from NonStaticObject are please refer to {@link GameObject}.
 */
public abstract class NonStaticObject extends GameObject {
	
	private static final long serialVersionUID = 1L;
	
	protected double speed;
	protected Vector2 direction = new Vector2();
	private double damage;
	protected double health;
	private double maxHealth;
	private int animationState;
	private boolean isHit = false;
	private int cooldownisHit = 0;
	private boolean dead = false;
	
	/**
	 * Constructs a new NonStaticObject, whose position and size are specified by the {@link Vector2} argument,
	 * whose room is specified by the {@link Room} argument, and whose item is specified by the {@link Item} argument.
	 * <p>
	 * Note that if you want to create a object that does not deal damage to anything, you just have to create it with {@code damage = 0}.
	 * 
	 * @throws IllegalArgumentException unless both {@code position} and
	 *                                  {@code size} aren't null
	 * 
	 * @param position the position of the new NonStaticObject
	 * @param size the size of the new GroundItem
	 * @param imagePath the image of the new NonStaticObject
	 * @param room the room the new GroundItem will be in
	 * @param speed the speed of the new NonStaticObject
	 * @param damage the damage of the new NonStaticObject
	 * @param health the health of the new NonStaticObject
	 */
	public NonStaticObject(Vector2 position, Vector2 size, String imagePath, @Nullable Room room,
			double speed, double damage, double health) {
		super(position, size, imagePath, room);
		this.speed = speed;
		this.damage = damage;
		this.health = health;
		this.maxHealth = health;
	}
	
	/*
	 * functions
	 */
	
	/**
	 * Not used in this class, but it's used in many other NonStaticObject,
	 * to see example of the used check {@link Tear#animation()}
	 *  or {@link Hero#animation()}.
	 */
	public abstract void animation();
	
	/**
	 * Changes the hit cooldown by decreasing it by 1, except when it reaches 0 where it stops and sets the Color to null.
	 * 
	 * @return the new value assigned to cooldownisHit.
	 */
	public int isHitCooldown() {
		if (cooldownisHit == 0) {
			setColor(null);
			isHit = false;
			return cooldownisHit;
		}
		return cooldownisHit--;
	}
	
	
	/**
	 * Retrieves a amount of damage from the objects health. 
	 * If the health is greater than the maxHealth then it set the health to the maxHealth.
	 * 
	 * @param damage the amount of damage you want the object to receive.
	 */
	public void loseHealth(double damage) {
		this.health -= damage;
		if (damage < 0 && health > maxHealth) health = maxHealth;
	}
	
	/**
	 * Normalizes the direction vector linked to the object.
	 * 
	 * @return a normalized direction direction.
	 */
	public Vector2 getNormalizedDirection()
	{
		Vector2 normalizedVector = new Vector2(direction);
		normalizedVector.euclidianNormalize(speed);
		return normalizedVector;
	}
	
	/**
	 * Checks if the move is legal, it's legal if the position 
	 * does not collide with any wall, or any door, or any solid {@link StaticObject}.
	 * 
	 * <p>
	 * To check how two hitboxes are colliding, refer to {@link HitBox}.
	 * <p>
	 * Returns a boolean, {@code true} if it's legal, {@code false} otherwise.
	 * 
	 * @param position, object position
	 * @return {@code true} if the move is legal, {@code false} otherwise.
	 */
	public boolean legalMove(Vector2 position) {
		for (StaticObject so :getRoom().getListStaticObject()) {
			if (so instanceof Poop && this instanceof Tear) break;
			if (so instanceof Stair && this instanceof Hero && this.collideWithPosition(position, so)) return true;
			if (so.getTrap() == false && this.collideWithPosition(position, so) ) {
				return false;
			}
		}
		for (Wall w: getRoom().getListWall()) {
			if (w.getHitBox() !=null && this.collideWithPosition(position, w)) {
				return false;
			}
			if (w.getSecondHitBox() != null && w.getSecondHitBox().collide(position, this.getHitBox())) {
				return false;
			}
		}
		for (Door d: getRoom().getListDoor()) if (this.collideWithPosition(position, d)) return false;
		return true;
	}
	
	/*
	 * getter and setter
	 */

	/**
	 * Sets the state of hit, whether it's hit or not.
	 * <p>
	 * if isHit is {@code true} then on top of setting the value to the wanted one, 
	 * it will also set the Color to red, and starts a isHit timer.
	 * 
	 * @param isHit the hit state you want the NonStaticObject
	 */
	public void SetIsHit(boolean isHit) {
		this.isHit = isHit;
		if (isHit == true) {
			cooldownisHit = 15;
			setColor(new Color(255,0,0));
		}
	}
	/**
	 * Sets the state of hit, whether it's hit or not.
	 * @param isHit the hit state you want the NonStaticObject
	 */
	public void SetIsHitValue(boolean isHit) {
		this.isHit = isHit;
	}
		
	/**
	 * Returns the Health of the NonStaticObject.
	 * @return the health of the NonStaticObject.
	 */
	public double getHealth() {
		return health;
	}
	
	/**
	 * Sets the health of the NonStaticObject. 
	 * It doesn't change the max health value.
	 * @param health the new health of the NonStaticObject
	 */
	public void setHealth(double health) {
		this.health = health;
	}
	
	/**
	 * Returns the Speed of the NonStaticObject.
	 * @return the Speed of the NonStaticObject.
	 */
	public double getSpeed()
	{
		return speed;
	}
	
	/**
	 * Sets the speed of the NonStaticObject.
	 * @param speed the new speed of the NonStaticObject
	 */
	public void setSpeed(double speed)
	{
		this.speed = speed;
	}
	
	/**
	 * Returns the direction of the NonStaticObject.
	 * @return the direction of the NonStaticObject.
	 */
	public Vector2 getDirection()
	{
		return direction;
	}
	
	/**
	 * Sets the direction of the NonStaticObject.
	 * @param direction the new direction of the NonStaticObject
	 */
	public void setDirection(Vector2 direction)
	{
		this.direction = direction;
	}
	
	/**
	 * Returns the damage that the NonStaticObject deals to others object.
	 * @return the damage that the NonStaticObject.
	 */
	public double getDamage() {
		return damage;
	}
	
	/**
	 * Sets the damage of the NonStaticObject.
	 * @param damage the new damage of the NonStaticObject
	 */
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	/**
	 * Returns the isHit state of the NonStaticObject.
	 * @return the isHit state of the NonStaticOBject.
	 */
	public boolean getIsHit() {
		return isHit;
	}
	
	/**
	 * Returns the value of the hit cooldown of the NonStaticObject.
	 * @return the value of the hit cooldown
	 */
	public int getHitCooldown() {
		return cooldownisHit;
	}
	
	/**
	 * Sets the new value of the hit cooldown of the NonStaticObject.
	 * @param cooldownisHit the value of the hit cooldown
	 */
	public void setHitCooldown(int cooldownisHit) {
		this.cooldownisHit = cooldownisHit ;
	}
	
	/**
	 * Returns the animation state of the NonStaticObject.
	 * @return the animation state of the NonStaticObject.
	 */
	public int getAnimationState() {
		return animationState;
	}
	
	/**
	 * Sets the animation state of the NonStaticObject.
	 * @param animationState the animation state  of the NonStaticObject
	 */
	public void setAnimationState(int animationState) {
		this.animationState = animationState;
	}
	
	/**
	 * Returns the state of death of the NonStaticObject.
	 * @return {@code true} if the NonStaticObject is dead, {code false} otherwise.
	 */
	public boolean getDead() {
		return dead;
	}
	
	/**
	 * Set the state of death of the NonStaticObject.
	 * If dead is {@code true} then the NonStaticObject is considered dead, 
	 * if dead is {@code false} then the NonStaticObject is considered alive.
	 * @param dead the state of death of the NonStaticObject
	 */
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	/**
	 * Returns the Hero max health in double precision.
	 * @return the Hero max Health.
	 */
	public double getMaxHealth() {
		return maxHealth;
	}
	
	/**
	 * Sets the Hero new max health in double precision.
	 * @param maxHealth the Hero new max Health
	 */
	public void setMaxHealth(double maxHealth) {
		this.maxHealth = maxHealth;
	}
}
