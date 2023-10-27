package gameobjects.nonstaticobjects.monsters;

import java.util.List;

import gameWorld.rooms.Room;
import gameobjects.GameObject;
import gameobjects.nonstaticobjects.NonStaticObject;
import gameobjects.staticobjects.Door;
import gameobjects.staticobjects.Wall;
import libraries.Vector2;

/**
 * A SummoningMonster is a flying monster that has the special ability of being able to 
 * spawn new monsters with the abstract {@link #updateSummoningGameObject()} method.
 * <p>
 * To check what the variable from FlyingMonster are please refer to {@link Monster} , {@link NonStaticObject} and {@link GameObject}.
 */
public abstract class FlyingSummoningMonster extends SummoningMonster {

	private static final long serialVersionUID = 1L;
	
	/*
	 * constructors
	 */

	/**
	 * Constructs a new FlyingSummoningMonster, whose position and size are specified by the {@link Vector2} argument and 
	 * whose room is specified by the {@link Room} argument.
	 * 
	 * <p>
	 * Here forwardDistance, backwardDistance and shootingDistance are all the referring to 
	 * the distance from the DistantMonster and the Hero.
	 * 
	 * @param position the position of the new FlyingSummoningMonster
	 * @param size the size of the new FlyingSummoningMonster
	 * @param imagePath the image of the new FlyingSummoningMonster
	 * @param speed the speed of the new FlyingSummoningMonster
	 * @param damage the damage done by the new FlyingSummoningMonster
	 * @param health the health of the new FlyingSummoningMonster
	 * @param room the room the new Monster will be in
	 * @param attackSpeed the speed to which the new FlyingSummoningMonster will attack on hand to hand
	 * @param forwardDistance the distance that the new FlyingSummoningMonster will start moving forward
	 * @param backwardDistance the distance that the new FlyingSummoningMonster will start moving backward
	 */
	public FlyingSummoningMonster(Vector2 position, Vector2 size, String imagePath, double speed, int damage,
			int health, Room room, int attackSpeed, double forwardDistance, double backwardDistance) {
		super(position, size, imagePath, speed, damage, health, room, attackSpeed, forwardDistance, backwardDistance);
	}

	/**
	 * Constructs a new FlyingSummoningMonster that can distantly attack, whose position and size are specified by the {@link Vector2} argument and 
	 * whose room is specified by the {@link Room} argument.
	 * 
	 * <p>
	 * Here forwardDistance, backwardDistance and shootingDistance are all the referring to 
	 * the distance from the DistantMonster and the Hero.
	 * 
	 * @param position the position of the new FlyingSummoningMonster
	 * @param size the size of the new FlyingSummoningMonster
	 * @param imagePath the image of the new FlyingSummoningMonster
	 * @param speed the speed of the new FlyingSummoningMonster
	 * @param damage the damage done by the new FlyingSummoningMonster
	 * @param health the health of the new FlyingSummoningMonster
	 * @param room the room the new FlyingSummoningMonster will be in
	 * @param attackSpeed the speed to which the new FlyingSummoningMonster will attack on hand to hand
	 * @param forwardDistance the distance that the new FlyingSummoningMonster will start moving forward
	 * @param backwardDistance the distance that the new FlyingSummoningMonster will start moving backward
	 * @param shootingDistance the distance that the new FlyingSummoningMonster will start shooting at the hero
	 * @param reach the reach for the projectile shot by the new FlyingSummoningMonster
	 * @param distantAttackSpeed the speed to which the new FlyingSummoningMonster will attack by shooting
	 */
	public FlyingSummoningMonster(Vector2 position, Vector2 size, String imagePath, double speed, int damage,
			int health, Room room, int attackSpeed, double forwardDistance, double backwardDistance, 
			double shootingDistance, double reach, int distantAttackSpeed) {
		super(position, size, imagePath, speed, damage, health, room, attackSpeed, forwardDistance, backwardDistance, 
				shootingDistance, reach, distantAttackSpeed);
	}
	
	/**
	 * Constructs a new FlyingSummoningMonster that can distantly attack, whose position and size are specified by the {@link Vector2} argument and 
	 * whose room is specified by the {@link Room} argument.
	 * 
	 * <p>
	 * Here forwardDistance, backwardDistance and shootingDistance are all the referring to 
	 * the distance from the DistantMonster and the Hero.
	 * 
	 * <p> 
	 * This constructor specifies the Tear image Animation with a List of String. An the Initial Tear image.
	 * 
	 * @param position the position of the new FlyingSummoningMonster
	 * @param size the size of the new FlyingSummoningMonster
	 * @param imagePath the image of the new FlyingSummoningMonster
	 * @param speed the speed of the new FlyingSummoningMonster
	 * @param damage the damage done by the new FlyingSummoningMonster
	 * @param health the health of the new FlyingSummoningMonster
	 * @param room the room the new FlyingSummoningMonster will be in
	 * @param attackSpeed the speed to which the new FlyingSummoningMonster will attack on hand to hand
	 * @param forwardDistance the distance that the new FlyingSummoningMonster will start moving forward
	 * @param backwardDistance the distance that the new FlyingSummoningMonster will start moving backward
	 * @param shootingDistance the distance that the new FlyingSummoningMonster will start shooting at the hero
	 * @param reach the reach for the projectile shot by the new FlyingSummoningMonster
	 * @param distantAttackSpeed the speed to which the new FlyingSummoningMonster will attack by shooting
	 * @param tearAnimationSprite the tear animation used when it dies
	 * @param tearImagePath the tear initial image
	 */
	public FlyingSummoningMonster(Vector2 position, Vector2 size, String imagePath, double speed, int damage,
			int health, Room room, int attackSpeed, double forwardDistance, double backwardDistance, 
			double shootingDistance, double reach, int distantAttackSpeed, 
			List<String> tearAnimationSprite, String tearImagePath) {
		super(position, size, imagePath, speed, damage, health, room, attackSpeed, forwardDistance, 
				backwardDistance, shootingDistance, reach, distantAttackSpeed, tearAnimationSprite, tearImagePath);
	}
	
	/*
	 *  functions
	 */
	
	/**
	 * Checks if the move is legal or not, it checks if the FlyingSummoningMonster collides with any wall or Door,
	 * if it is then it returns false, otherwise returns false.
	 * 
	 * @return {@code true} if the move is legal, {@code false} otherwise.
	 */
	@Override
	public boolean legalMove(Vector2 position) {
		for (Wall w: getRoom().getListWall()) {
			if (w.collideWithPosition(position, this)) return false;
		}
		for (Door d: getRoom().getListDoor()) if (this.collideWithPosition(position, d)) return false;
		return true;
	}
}
