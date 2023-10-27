package gameobjects.nonstaticobjects.monsters;

import java.util.List;

import gameWorld.rooms.Room;
import gameobjects.GameObject;
import gameobjects.nonstaticobjects.NonStaticObject;
import gameobjects.staticobjects.Door;
import gameobjects.staticobjects.Wall;
import libraries.Vector2;

/**
 * A FlyingMonster is a Monsters, it's mainly used because of it's unique ability to fly above things that are on the ground. 
 * So this class changes the way {@link NonStaticObject} checks if a move is legal. 
 * <p>
 * To check what the variable from FlyingMonster are please refer to {@link Monster} , {@link NonStaticObject} and {@link GameObject}.
 */
public abstract class FlyingMonster extends Monster{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new FlyingMonster that can only attack on hand to hand, whose position and size are specified by the {@link Vector2} argument and 
	 * whose room is specified by the {@link Room} argument.
	 * 
	 * <p>
	 * Here forwardDistance, backwardDistance and shootingDistance are all the referring to 
	 * the distance from the DistantMonster and the Hero.
	 * 
	 * @param position the position of the new FlyingMonster
	 * @param size the size of the new FlyingMonster
	 * @param imagePath the image of the new FlyingMonster
	 * @param speed the speed of the new FlyingMonster
	 * @param damage the damage done by the new FlyingMonster
	 * @param health the health of the new FlyingMonster
	 * @param room the room the new FlyingMonster will be in
	 * @param attackSpeed the speed to which the new FlyingMonster will attack on hand to hand
	 * @param forwardDistance the distance that the new FlyingMonster will start moving forward
	 * @param backwardDistance the distance that the new FlyingMonster will start moving backward
	 */
	public FlyingMonster(Vector2 position, Vector2 size, String imagePath, double speed, int damage, int health,
			Room room, int attackSpeed, double forwardDistance, double backwardDistance) {
		super(position, size, imagePath, speed, damage, health, room, attackSpeed, forwardDistance, backwardDistance);
	}

	/**
	 * Constructs a new FlyingMonster that can distantly attack, whose position and size are specified by the {@link Vector2} argument and 
	 * whose room is specified by the {@link Room} argument.
	 * 
	 * <p>
	 * Here forwardDistance, backwardDistance and shootingDistance are all the referring to 
	 * the distance from the DistantMonster and the Hero.
	 * 
	 * @param position the position of the new FlyingMonster
	 * @param size the size of the new FlyingMonster
	 * @param imagePath the image of the new FlyingMonster
	 * @param speed the speed of the new FlyingMonster
	 * @param damage the damage done by the new FlyingMonster
	 * @param health the health of the new FlyingMonster
	 * @param room the room the new DistantMonster will be in
	 * @param attackSpeed the speed to which the new FlyingMonster will attack on hand to hand
	 * @param forwardDistance the distance that the new FlyingMonster will start moving forward
	 * @param backwardDistance the distance that the new FlyingMonster will start moving backward
	 * @param shootingDistance the distance that the new FlyingMonster will start shooting at the hero
	 * @param reach the reach for the projectile shot by the new FlyingMonster
	 * @param distantAttackSpeed the speed to which the new FlyingMonster will attack by shooting
	 */
	public FlyingMonster(Vector2 position, Vector2 size, String imagePath, double speed, int damage, int health,
			Room room, int attackSpeed, double forwardDistance, double backwardDistance, double shootingDistance,
			double reach, int distantAttackSpeed) {
		super(position, size, imagePath, speed, damage, health, room, attackSpeed, forwardDistance, backwardDistance,
				shootingDistance, reach, distantAttackSpeed);
	}
	
	/**
	 * Constructs a new FlyingMonster that can distantly attack, whose position and size are specified by the {@link Vector2} argument and 
	 * whose room is specified by the {@link Room} argument.
	 * 
	 * <p>
	 * Here forwardDistance, backwardDistance and shootingDistance are all the referring to 
	 * the distance from the DistantMonster and the Hero.
	 * 
	 * <p>
	 * Here you can specify the projectile image and animation images that will be used.
	 * 
	 * @param position the position of the new FlyingMonster
	 * @param size the size of the new FlyingMonster
	 * @param imagePath the image of the new FlyingMonster
	 * @param speed the speed of the new FlyingMonster
	 * @param damage the damage done by the new FlyingMonster
	 * @param health the health of the new FlyingMonster
	 * @param room the room the new DistantMonster will be in
	 * @param attackSpeed the speed to which the new FlyingMonster will attack on hand to hand
	 * @param forwardDistance the distance that the new FlyingMonster will start moving forward
	 * @param backwardDistance the distance that the new FlyingMonster will start moving backward
	 * @param shootingDistance the distance that the new FlyingMonster will start shooting at the hero
	 * @param reach the reach for the projectile shot by the new FlyingMonster
	 * @param distantAttackSpeed the speed to which the new FlyingMonster will attack by shooting
	 * @param tearAnimationSprite the tear animation used when it dies
	 * @param tearImagePath the tear initial image
	 */
	public FlyingMonster(Vector2 position, Vector2 size, String imagePath, double speed, int damage, int health,
			Room room, int attackSpeed, double forwardDistance, double backwardDistance, double shootingDistance,
			double reach, int distantAttackSpeed, List<String> tearAnimationSprite, String tearImagePath) {
		super(position, size, imagePath, speed, damage, health, room, attackSpeed, forwardDistance, backwardDistance,
				shootingDistance, reach, distantAttackSpeed, tearAnimationSprite, tearImagePath);
	}
	
	
	/*
	 *  functions
	 */
	
	/**
	 * Checks if the move is legal or not, it checks if the FlyingMonster collides with any wall or Door,
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
