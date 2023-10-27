package gameobjects.nonstaticobjects.monsters.boss;

import gameWorld.rooms.Room;
import gameobjects.nonstaticobjects.Hero;
import gameobjects.nonstaticobjects.Tear;
import gameobjects.nonstaticobjects.monsters.Monster;
import libraries.Vector2;
import resources.BossInfos;
import resources.ImagePaths;

/**
 * Lamb is a Monster that is a Boss. 
 * It has some special attack pattern.
 * <p>
 * He will start by shooting straight at the player while his health is still not half of 
 * the initial value. When it's smaller or equal to half of initial health then he will 
 * shoot projectiles around him.
 * <p>
 * During all the his shooting attack phase he will keep moving forward.
 */
public class Lamb extends Monster{
	private static final long serialVersionUID = 1L;

	/**
     * Constructs a new Lamb, whose position is specified by the {@link Vector2} argument, 
     * and whose room is specified by the {@link Room} argument.
     * 
     * @param position the position of the new new Lamb.
     * @param room the room the new Lamb will be in.
     */
	public Lamb(Vector2 position, Room room) {
		super(position, BossInfos.LAMB_SIZE, ImagePaths.LAMB, BossInfos.LAMB_SPEED, BossInfos.LAMB_DAMAGE,
				BossInfos.LAMB_HEALTH, room, BossInfos.LAMB_ATTACK_SPEED, BossInfos.LAMB_FORWARD_DISTANCE, BossInfos.LAMB_BACKWARD_DISTANCE,
				BossInfos.LAMB_SHOOTING_DISTANCE, BossInfos.LAMB_REACH, BossInfos.LAMB_DISTANT_ATTACK_SPEED);
	}
	
	/**
	 * Not used in this class, but it's used in many other NonStaticObject, 
	 * to see example of a used function check {@link Tear#animation()} or {@link Hero#animation()}.
	 */
	@Override
	public void animation() {
	}
	
	/**
	 * Checks if Lamb can shoot (if the cooldown is equal to 0).
	 * <p>
	 * If Lamb health is greater or equal to it's initial health, it will shoot using the
	 * {@link Monster#shoot()} method. Otherwise it will use the {@link #shootCircle(int)} 
	 * method.
	 * 
	 */
	@Override
	public void shoot() {
		if (getHealth() >= BossInfos.LAMB_HEALTH / 2) {
			super.shoot();
		}
		else if (cooldown == 0) {
			cooldown = getDistantAttackSpeed() ;
			shootCircle(60);
		}
	}
	
	
	/**
	 * Summons a circle of tear with the angle 
	 * @param angle the angle spacing the tears.
	 */
	private void shootCircle(int angle) {
		Vector2 circlePosition = getPosition();
		double x = 0.1 + circlePosition.getX();
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
}