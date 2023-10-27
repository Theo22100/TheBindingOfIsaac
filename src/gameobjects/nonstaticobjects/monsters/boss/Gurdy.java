package gameobjects.nonstaticobjects.monsters.boss;

import gameWorld.rooms.Room;
import gameobjects.GameObject;
import gameobjects.nonstaticobjects.Hero;
import gameobjects.nonstaticobjects.NonStaticObject;
import gameobjects.nonstaticobjects.Tear;
import gameobjects.nonstaticobjects.monsters.Monster;
import libraries.Vector2;
import resources.BossInfos;
import resources.ImagePaths;

/**
 * Loki is a Monster that is a Boss. 
 * He doesn't move but attacks with some special patterns. 
 * The patterns are either a circle or a semi circle aimed at the player.
 * <p>
 * To check what the other variable from Larry are please refer to {@link Monster}, 
 * {@link NonStaticObject} and {@link GameObject}.
 */
public class Gurdy extends Monster {

	private static final long serialVersionUID = 1L;

	/**
     * Constructs a new Gurdy, whose position is specified by the {@link Vector2} argument, 
     * and whose room is specified by the {@link Room} argument.
     * 
     * @param position the position of the new new Gurdy.
     * @param room the room the new Gurdy will be in.
     */
	public Gurdy(Vector2 position, Room room) {
		super(position, BossInfos.GURDY_SIZE, ImagePaths.GURDY, 0,  BossInfos.GURDY_DAMAGE,  BossInfos.GURDY_HEALTH,
				room,  BossInfos.GURDY_ATTACK_SPEED, 0, 0, 1,  BossInfos.GURDY_REACH,  BossInfos.GURDY_DISTANT_ATTACK_SPEED);
		cooldown = 90;
	}

	/**
	 * Not used in this class, but it's used in many other NonStaticObject, 
	 * to see example of a used function check {@link Tear#animation()} or {@link Hero#animation()}.
	 */
	@Override
	public void animation() {
	}
	
	
	/**
	 * Updates the hitCooldown and the attack Timer.
	 */
	@Override
	public void updateGameObject() {
		isHitCooldown();
		if (getHealth() <= 0) setDead(true);
//		hand to hand attack
		if (timer == 0) attackHero();
		else timer --;
		if (cooldown == 0) shoot();
		else cooldown--;
		
	}
	
	/**
	 * Summons a circle of tear spaced out by a given angle.
	 * @param angle the angle spacing each tears
	 */
	private void shootCircle(int angle) {
		Vector2 circlePosition = getPosition();
		double x = 0.15 + circlePosition.getX();
		double y = circlePosition.getY();
		Vector2 firstPosition = new Vector2(x,y);
//		position init
		
		getRoom().addListTear(new Tear(getPosition().subVector(firstPosition).scalarMultiplication(-1),
				firstPosition, getDamage(), getReach(), getRoom(),
				0.01, this, getTearImagePath(), getTearAnimationSprite()));
		for (int i = 0; i < 360; i += angle) {
			firstPosition = rotateAroundPoint(firstPosition, angle, circlePosition);
			getRoom().addListTear(new Tear(getPosition().subVector(firstPosition).scalarMultiplication(-1),
					firstPosition, getDamage(), getReach(), getRoom(),
					0.01, this, getTearImagePath(), getTearAnimationSprite()));
		}
	}
	
	/**
	 * Rotates a point around an origin point with a given angle. 
	 * Returns the rotated point.
	 * @param position the position of the point.
	 * @param angle the angle of rotation.
	 * @param origin the origin point.
	 * @return the rotated point.
	 */
	private Vector2 rotateAroundPoint(Vector2 position, int angle, Vector2 origin) {
		double rad_angle = Math.toRadians(angle);
		Vector2 pos = position.subVector(origin);
		double x = Math.cos(rad_angle) * pos.getX() - Math.sin(rad_angle) * pos.getY();
		double y = Math.sin(rad_angle) * pos.getX() + Math.cos(rad_angle) * pos.getY();
		pos = new Vector2(x,y);
		return pos.addVector(origin);
	}
	
	/**
	 * Summons four tear in each cardinal direction.
	 */
	private void shootCardinalPoint() {
		getRoom().addListTear(new Tear(new Vector2(0,1),
				getPosition(), getDamage(), getReach(), getRoom(),
				0.01, this, getTearImagePath(), getTearAnimationSprite()));
		getRoom().addListTear(new Tear(new Vector2(0,-1),
				getPosition(), getDamage(), getReach(), getRoom(),
				0.01, this, getTearImagePath(), getTearAnimationSprite()));
		getRoom().addListTear(new Tear(new Vector2(1,0),
				getPosition(), getDamage(), getReach(), getRoom(),
				0.01, this, getTearImagePath(), getTearAnimationSprite()));
		getRoom().addListTear(new Tear(new Vector2(-1,0),
				getPosition(), getDamage(), getReach(), getRoom(),
				0.01, this, getTearImagePath(), getTearAnimationSprite()));
	}
	
	/**
	 * Shoots a custom pattern, either {@link #shootCircle(int)} or {@link #shootCardinalPoint()}.
	 */
	@Override
	protected void shoot() {
		if (((int) (Math.random() * 100) <= 50)) shootCardinalPoint();
		else {
			int angle = 2 + ((int) Math.random() * 5 );
			shootCircle(angle * 10);
		}
		cooldown = 90;
	}
}
