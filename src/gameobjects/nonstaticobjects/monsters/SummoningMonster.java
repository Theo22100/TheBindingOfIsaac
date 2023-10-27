package gameobjects.nonstaticobjects.monsters;

import java.util.List;

import gameWorld.rooms.Room;
import gameobjects.GameObject;
import gameobjects.nonstaticobjects.NonStaticObject;
import libraries.Vector2;

/**
 * A SummoningMonster is a monster that has the special ability of being able to 
 * spawn new monsters with the abstract {@link #updateSummoningGameObject()} method.
 * <p>
 * To check what the variable from FlyingMonster are please refer to {@link Monster} , {@link NonStaticObject} and {@link GameObject}.
 */
public abstract class SummoningMonster extends Monster {
	
	private static final long serialVersionUID = 1L;

	/*
	 * constructors
	 */
	
	/**
	 * Constructs a new SummoningMonster, whose position and size are specified by the {@link Vector2} argument and 
	 * whose room is specified by the {@link Room} argument.
	 * 
	 * <p>
	 * Here forwardDistance, backwardDistance and shootingDistance are all the referring to 
	 * the distance from the DistantMonster and the Hero.
	 * 
	 * @param position the position of the new SummoningMonster
	 * @param size the size of the new SummoningMonster
	 * @param imagePath the image of the new SummoningMonster
	 * @param speed the speed of the new SummoningMonster
	 * @param damage the damage done by the new SummoningMonster
	 * @param health the health of the new SummoningMonster
	 * @param room the room the new Monster will be in
	 * @param attackSpeed the speed to which the new SummoningMonster will attack on hand to hand
	 * @param forwardDistance the distance that the new SummoningMonster will start moving forward
	 * @param backwardDistance the distance that the new SummoningMonster will start moving backward
	 */
	public SummoningMonster(Vector2 position, Vector2 size, String imagePath, double speed, int damage,
			int health, Room room, int attackSpeed, double forwardDistance, double backwardDistance) {
		super(position, size, imagePath, speed, damage, health, room, attackSpeed, forwardDistance, backwardDistance);
	}

	/**
	 * Constructs a new SummoningMonster that can distantly attack, whose position and size are specified by the {@link Vector2} argument and 
	 * whose room is specified by the {@link Room} argument.
	 * 
	 * <p>
	 * Here forwardDistance, backwardDistance and shootingDistance are all the referring to 
	 * the distance from the DistantMonster and the Hero.
	 * 
	 * @param position the position of the new SummoningMonster
	 * @param size the size of the new SummoningMonster
	 * @param imagePath the image of the new SummoningMonster
	 * @param speed the speed of the new SummoningMonster
	 * @param damage the damage done by the new SummoningMonster
	 * @param health the health of the new SummoningMonster
	 * @param room the room the new SummoningMonster will be in
	 * @param attackSpeed the speed to which the new SummoningMonster will attack on hand to hand
	 * @param forwardDistance the distance that the new SummoningMonster will start moving forward
	 * @param backwardDistance the distance that the new SummoningMonster will start moving backward
	 * @param shootingDistance the distance that the new SummoningMonster will start shooting at the hero
	 * @param reach the reach for the projectile shot by the new SummoningMonster
	 * @param distantAttackSpeed the speed to which the new SummoningMonster will attack by shooting
	 */
	public SummoningMonster(Vector2 position, Vector2 size, String imagePath, double speed, int damage,
			int health, Room room, int attackSpeed, double forwardDistance, double backwardDistance, 
			double shootingDistance, double reach, int distantAttackSpeed) {
		super(position, size, imagePath, speed, damage, health, room, attackSpeed, forwardDistance, backwardDistance, 
				shootingDistance, reach, distantAttackSpeed);
	}
	
	/**
	 * Constructs a new SummoningMonster that can distantly attack, whose position and size are specified by the {@link Vector2} argument and 
	 * whose room is specified by the {@link Room} argument.
	 * 
	 * <p>
	 * Here forwardDistance, backwardDistance and shootingDistance are all the referring to 
	 * the distance from the DistantMonster and the Hero.
	 * 
	 * <p> 
	 * This constructor specifies the Tear image Animation with a List of String. An the Initial Tear image.
	 * 
	 * @param position the position of the new SummoningMonster
	 * @param size the size of the new SummoningMonster
	 * @param imagePath the image of the new SummoningMonster
	 * @param speed the speed of the new SummoningMonster
	 * @param damage the damage done by the new SummoningMonster
	 * @param health the health of the new SummoningMonster
	 * @param room the room the new SummoningMonster will be in
	 * @param attackSpeed the speed to which the new SummoningMonster will attack on hand to hand
	 * @param forwardDistance the distance that the new SummoningMonster will start moving forward
	 * @param backwardDistance the distance that the new SummoningMonster will start moving backward
	 * @param shootingDistance the distance that the new SummoningMonster will start shooting at the hero
	 * @param reach the reach for the projectile shot by the new SummoningMonster
	 * @param distantAttackSpeed the speed to which the new SummoningMonster will attack by shooting
	 * @param tearAnimationSprite the tear animation used when it dies
	 * @param tearImagePath the tear initial image
	 */
	public SummoningMonster(Vector2 position, Vector2 size, String imagePath, double speed, int damage,
			int health, Room room, int attackSpeed, double forwardDistance, double backwardDistance, 
			double shootingDistance, double reach, int distantAttackSpeed, 
			List<String> tearAnimationSprite, String tearImagePath) {
		super(position, size, imagePath, speed, damage, health, room, attackSpeed, forwardDistance, 
				backwardDistance, shootingDistance, reach, distantAttackSpeed, tearAnimationSprite, tearImagePath);
	}

	
	/**
	 * Returns the list of monsters summoned by the monster.
	 * @return the list of monsters summoned by the monster.
	 */
	public abstract List<Monster> updateSummoningGameObject();
	
}
