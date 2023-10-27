package gameobjects.nonstaticobjects.monsters.boss;

import java.util.ArrayList;
import java.util.List;

import gameWorld.rooms.Room;
import gameobjects.GameObject;
import gameobjects.nonstaticobjects.Hero;
import gameobjects.nonstaticobjects.NonStaticObject;
import gameobjects.nonstaticobjects.Tear;
import gameobjects.nonstaticobjects.monsters.Fly;
import gameobjects.nonstaticobjects.monsters.Monster;
import gameobjects.nonstaticobjects.monsters.SummoningMonster;
import libraries.Vector2;
import libraries.addedByUs.Utils;
import resources.BossInfos;
import resources.ImagePaths;

/**
 * Loki is a Monster that is a Boss. 
 * He can teleport randomly in the room. And when there's not 
 * enough flies he can spawn new ones.
 * <p>
 * During all the his shooting attack phase he will keep moving forward.
 * <p>
 * To check what the other variable from Larry are please refer to {@link Monster}, 
 * {@link NonStaticObject} and {@link GameObject}.
 *
 */
public class Loki extends SummoningMonster{

	private int tp_cooldown = 60;
	private int summon_cooldown = 30;
	
	private static final long serialVersionUID = 1L;

	/**
     * Constructs a new Loki, whose position is specified by the {@link Vector2} argument, 
     * and whose room is specified by the {@link Room} argument.
     * 
     * @param position the position of the new new Loki.
     * @param room the room the new Loki will be in.
     */
    public Loki(Vector2 position, Room room) {
        super( position, BossInfos.LOKI_SIZE, ImagePaths.LOKI, 0, BossInfos.STEVEN_DAMAGE, 
        BossInfos.LOKI_HEALTH, room, BossInfos.LOKI_ATTACK_SPEED, 0, 0
        , BossInfos.LOKI_SHOOTING_DISTANCE, BossInfos.LOKI_REACH, BossInfos.LOKI_DISTANT_ATTACK_SPEED);
    }
    
    /**
	 * Updates the hitCooldown and the attack Timer as well as the distant attack Timer.
	 */
    @Override
	public List<Monster> updateSummoningGameObject() {
		isHitCooldown();
//		hand to hand attack
		if (timer == 0) attackHero();
		else timer --;
		if (cooldown == 0) shoot();
		else cooldown--;
		
		if (tp_cooldown == 0) {
			this.setPosition(Utils.randomPosition(this, getRoom()));
			tp_cooldown = 60 + (int) (Math.random() * 30);
		}
		tp_cooldown--;
		
		
		if (summon_cooldown == 0) {
			summon_cooldown = 450 + (int) (Math.random() * 100);
			if (numberOfFlyInRoom(getRoom().getListMonster()) >= 3) return null;
			return summonMonsters();
		}
		else summon_cooldown--;
		return null;
	}
    
	/**
	 * Returns the number of flies in the list of Monsters.
	 * @param monsters the list of Monsters.
	 * @return the number of flies in the list of Monsters.
	 */
	private int numberOfFlyInRoom(List<Monster> monsters) {
		int number = 0; 
		for (Monster m: monsters) if (m instanceof Fly) number++;
		return number;
	}
	
	/**
	 * Returns the list of monsters summoned.
	 * @return the list of monsters summoned.
	 */
	private List<Monster> summonMonsters() {
		List<Monster> monsters = new ArrayList<Monster>();
		int numberToSummon = 1 + (int) (Math.random() * 2);
		for (int i = 0; i < numberToSummon; i++) {
			Fly fly = new Fly(new Vector2(), getRoom());
			fly.setPosition(Utils.randomPosition(fly, getRoom()));
			monsters.add(fly);
		}
		return monsters;
	}

	/**
	 * Not used in this class, but it's used in many other NonStaticObject, 
	 * to see example of a used function check {@link Tear#animation()} or {@link Hero#animation()}.
	 */
	@Override
	public void animation() {
	}
}
