package gameobjects.nonstaticobjects.monsters;

import gameWorld.rooms.Room;
import gameobjects.GameObject;
import gameobjects.nonstaticobjects.Hero;
import gameobjects.nonstaticobjects.NonStaticObject;
import gameobjects.nonstaticobjects.Tear;
import libraries.Vector2;
import resources.ImagePaths;
import resources.MonsterInfos;

/**
 * A Clotty is a Monster that shoot projectile from a distance in all 4 cardinal direction.
 * <p>
 * To check what the other variable from Spider are please refer to {@link Monster} , {@link NonStaticObject} and {@link GameObject}.
 *
 */
public class Clotty extends Monster {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new clotty whose position is specified with a {@link Vector2} argument, 
	 * and whose room is specified by a {@link Room} argument.
	 * 
	 * @param position the position of the new Fly
	 * @param room the room the new Fly will be in
	 */
	public Clotty(Vector2 position, Room room) {
		super(position, MonsterInfos.CLOTTY_SIZE, ImagePaths.CLOTTY, MonsterInfos.CLOTTY_SPEED, MonsterInfos.CLOTTY_DAMAGE,
				MonsterInfos.CLOTTY_HEALTH, room, MonsterInfos.CLOTTY_ATTACK_SPEED, 0.3, 0,
				0.3, MonsterInfos.CLOTTY_REACH, MonsterInfos.CLOTTY_DISTANT_ATTACK_SPEED);
	}
	
	/**
	 * Not used in this class, but it's used in many other NonStaticObject, 
	 * to see example of a used function check {@link Tear#animation()} or {@link Hero#animation()}.
	 */
	@Override
	public void animation() {
	}

	/**
	 * Checks if the DistantMonster can shoot (if the cooldown is equal to 0).
	 * <p>
	 * If that's the case then it creates a {@code new Tear#Tear(Vector2, Vector2, int, double, Room, double, GameObject, String)}, 
	 * at the monsters position in all 4 cardinal direction.
	 * 
	 */
	@Override
	public void shoot() {
		if (cooldown ==0) {
			new Tear(new Vector2(0,1), this.getPosition(),
					this.getDamage(), getReach(), this.getRoom(),
					0.01, this, getTearImagePath(), getTearAnimationSprite());
			new Tear(new Vector2(0,-1), this.getPosition(),
					this.getDamage(), getReach(), this.getRoom(),
					0.01, this, getTearImagePath(), getTearAnimationSprite());
			new Tear(new Vector2(1,0), this.getPosition(),
					this.getDamage(), getReach(), this.getRoom(),
					0.01, this, getTearImagePath(), getTearAnimationSprite());
			new Tear(new Vector2(-1,0), this.getPosition(),
					this.getDamage(), getReach(), this.getRoom(),
					0.01, this, getTearImagePath(), getTearAnimationSprite());
			cooldown = getDistantAttackSpeed();
		}
	}
	
}
