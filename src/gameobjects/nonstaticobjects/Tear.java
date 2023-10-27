package gameobjects.nonstaticobjects;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

import gameWorld.rooms.Room;
import gameobjects.GameObject;
import gameobjects.nonstaticobjects.monsters.Monster;
import libraries.Vector2;
import resources.ImagePaths;
import resources.RoomInfos;

/**
 * A Tear is a NonStaticObject, that could be considered as a projectile.
 * It's used by the Hero and the Monster as a way to attack.
 * <p>
 * It uses a GameObject variable as a way to know who created this Tear, and to make sure not to hit it.
 * <p>
 * There's also a Vector2 positionInitial to store the position the Tear was created, that will be used 
 * to know how far the Tear traveled.
 * <p>
 * The last two variables are, a double reach that indicate how far the Tear can go, and a boolean, trueDead
 * to check if the Tear is truly dead or if it's dead but going through the death animation.
 * 
 * To check what the other variable from Monster are please refer to {@link NonStaticObject} and {@link GameObject}.
 * @see Hero
 * @see Monster
 */
public class Tear extends NonStaticObject{

	private static final long serialVersionUID = 1L;
	
	private Vector2 positionInitial;
	private GameObject gameObject;
	private double reach;
	private boolean trueDead = false;
	private List<String> animationSprite = new ArrayList<String>();
	
	/*
	 * constructor
	 */

	/**
	 * Constructs a new Tear, whose position and size are specified by the {@link Vector2} argument, 
	 * whose room is specified by the {@link Room} argument, and whose gameObject is specified by the {@link GameObject} argument.
	 * <p>
	 * Both the imagePath and animation are Nullable, as there's a default image and animation to the Tear.
	 * 
	 * @param direction the direction the new Tear will go to
	 * @param position the position the new Tear starts at
	 * @param damage the damage dealt by the new Tear
	 * @param reach the reach of the new Tear
	 * @param room the room the new Tear is in
	 * @param speed the speed of the new Tear
	 * @param gameObject the gameObject that created the new Tear
	 * @param imagePaths the image of the new Tear
	 * @param animationSprite the images that compose the new Tear animation
	 */
	public Tear(Vector2 direction, Vector2 position, double damage, double reach, Room room, double speed, GameObject gameObject, 
			@Nullable String imagePaths, @Nullable List<String> animationSprite) {
		super(position, RoomInfos.TILE_SIZE.scalarMultiplication(0.3), imagePaths, room, speed, damage, 5);
		this.setDirection(direction);
		this.positionInitial = position;
		this.reach = reach;
		this.gameObject = gameObject;
		this.getRoom().addListTear(this);
		this.getRoom().addListNonStaticObject(this);
//		default imagePaths
		if (imagePaths == null || imagePaths.equals("")) this.setImagePath(ImagePaths.TEAR);
		
		if (animationSprite == null || ( !animationSprite.equals(null) && animationSprite.size() == 0)) {
//			default animation
			this.animationSprite.add(ImagePaths.TEAR1);
			this.animationSprite.add(ImagePaths.TEAR2);
			this.animationSprite.add(ImagePaths.TEAR3);
			this.animationSprite.add(ImagePaths.TEAR4);
			this.animationSprite.add(ImagePaths.TEAR5);
			this.animationSprite.add(ImagePaths.TEAR6);
			this.animationSprite.add(ImagePaths.TEAR7);
			this.animationSprite.add(ImagePaths.TEAR8);
		}
		else this.animationSprite = animationSprite;
		this.setAnimationState(this.animationSprite.size() * 2);
	}
	
	/*
	 * functions
	 */
	
	/**
	 * Updates Tear object movement if it's not dead. Otherwise it goes through the {@code #animation()}.
	 */
	public void updateGameObject()
	{
		if (!getDead()) move();
		else animation();
	}
	
	/**
	 * Decreases the animationState by one, and checks if it matches a case, 
	 * if so the image path will be change.
	 */
	@Override
	public void animation() {
		this.setAnimationState(this.getAnimationState() - 1);
		if (getAnimationState() == 0) trueDead = true;
		else if (getAnimationState() %2 == 0) {
			setImagePath(animationSprite.get( animationSprite.size() - 1 - getAnimationState() / 2 ) );
		}
	}
	
	/**
	 * Moves the Tear by a vector and speed only if the move if a legal one.
	 * <p>
	 * To see how the position is considered legal check {@link NonStaticObject#legalMove(Vector2)}.
	 */
	private void move()
	{
		Vector2 normalizedDirection = getNormalizedDirection();
		Vector2 positionAfterMoving = getPosition().addVector(normalizedDirection);
		setPosition(positionAfterMoving);
		if (!this.legalMove(positionAfterMoving) || positionAfterMoving.distance(positionInitial) > reach) {
			setDead(true);
		}
	}

	
	/*
	 *  getter and setter
	 */
	
	/**
	 * Get the dead state of the tear, whether it's truly dead or not.
	 * @return {@code true} if truly dead, {@code false} otherwise.
	 */
	public boolean getTrueDead() {
		return trueDead;
	}
	
	/**
	 * Returns the GameObject that created the Tear.
	 * @return the GameObject that created the Tear.
	 */
	public GameObject getGameObject() {
		return gameObject;
	}
}
