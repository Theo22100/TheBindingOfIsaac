package gameobjects.nonstaticobjects.monsters.boss;

import java.util.ArrayList;
import java.util.List;

import gameWorld.rooms.BossRoom;
import gameWorld.rooms.Room;
import gameobjects.GameObject;
import gameobjects.nonstaticobjects.Hero;
import gameobjects.nonstaticobjects.NonStaticObject;
import gameobjects.nonstaticobjects.Tear;
import gameobjects.nonstaticobjects.monsters.Monster;
import gameobjects.nonstaticobjects.monsters.SummoningMonster;
import libraries.Vector2;
import resources.BossInfos;
import resources.ImagePaths;

/**
 * Steven is a Monster that is a Boss. 
 * It has the special ability of being able to summon another Monster when 
 * it's total health reaches half of the initial one. 
 * So it has a boolean variable miniStevenSpawned, to check if this monster 
 * has already been spawned.
 * <p>
 * To check what the other variable from Steven are please refer to {@link Monster}, 
 * {@link NonStaticObject} and {@link GameObject}.
 * @see MiniSteven
 */
public class Steven extends SummoningMonster {

	private static final long serialVersionUID = 1L;
	private boolean miniStevenSpawned;
	
	/**
	 * Constructs a new Steven, whose position is specified by the {@link Vector2} argument and 
	 * whose room is specified by the {@link Room} argument.
	 * @param position the position of Steven
	 * @param room the room Steven will be in
	 */
	public Steven(Vector2 position, Room room) {
		super(position, BossInfos.STEVEN_SIZE, ImagePaths.STEVEN, BossInfos.STEVEN_SPEED, BossInfos.STEVEN_DAMAGE, 
				BossInfos.STEVEN_HEALTH, room, BossInfos.STEVEN_ATTACK_SPEED, BossInfos.STEVEN_FORWARD_DISTANCE,
				BossInfos.STEVEN_BACKWARD_DISTANCE, BossInfos.STEVEN_SHOOTING_DISTANCE, 
				BossInfos.STEVEN_REACH, BossInfos.STEVEN_DISTANT_ATTACK_SPEED);
		
	}

	/**
	 * Not used in this class, but it's used in many other NonStaticObject, 
	 * to see example of a used function check {@link Tear#animation()} or {@link Hero#animation()}.
	 */
	@Override
	public void animation() {
	}
	
	/**
	 * Updates Steven movement and attack cooldowns based on the hero position.
	 * @return the small head of Steven if Steven reaches half of it's size.
	 */
	@Override
	public List<Monster> updateSummoningGameObject() {
//	 	moving forwards or backwards
			isHitCooldown();
			if ( ((getRoom().getHero().getPosition().distance(this.getPosition())) <= getForwardDistance()) &&
					(getRoom().getHero().getPosition().distance(this.getPosition()) >= getRoom().getHero().getSize().getX()) 
					) this.move(true);
			else if (getRoom().getHero().getPosition().distance(this.getPosition()) <= getBackwardDistance()) this.move(false);
			if (getShootingDistance() != 0 && getRoom().getHero().getPosition().distance(this.getPosition()) <= getShootingDistance()) this.shoot();
//			hand to hand attack timer
			if (timer == 0) attackHero();
			if (timer != 0) timer --;
			if (getHealth() <= 0 ) setDead(true);
			if (cooldown !=0) {
				cooldown --;
			}
			if (getHealth() == getMaxHealth() / 2 && !miniStevenSpawned) {
				miniStevenSpawned = true;
				Monster miniSteven = new MiniSteven(getPosition(), getRoom(), getRoom().getHero().getSpeed() * 0.9);
				setHealth(BossInfos.STEVEN_HEALTH / 4);
				if (getRoom() instanceof BossRoom) ((BossRoom) getRoom()).addListBoss(miniSteven);
				setImagePath(ImagePaths.STEVEN_HEADLESS);
				ArrayList<Monster> monsters = new ArrayList<Monster>();
				monsters.add(miniSteven);
				return monsters;
			}
			return null;
	 }

}