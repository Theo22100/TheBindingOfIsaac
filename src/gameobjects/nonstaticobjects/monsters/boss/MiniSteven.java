package gameobjects.nonstaticobjects.monsters.boss;

import gameWorld.HitBox;
import gameWorld.rooms.Room;
import gameobjects.GameObject;
import gameobjects.nonstaticobjects.Hero;
import gameobjects.nonstaticobjects.NonStaticObject;
import gameobjects.nonstaticobjects.Tear;
import gameobjects.nonstaticobjects.monsters.FlyingMonster;
import gameobjects.nonstaticobjects.monsters.Monster;
import libraries.Vector2;
import resources.BossInfos;
import resources.ImagePaths;

/**
 * MiniSteven is a FlyingMonster it's the other part of the Monster {@link Steven}.
 * <p>
 * It has two different timer, backwardTimer used to know how long it's going back 
 * and iddleTimer used to know how long it will be standing still.
 * <p>
 * It also has two boolean variable, forward used to know if MiniSteven is going forward 
 * or backwards. Or iddle which refers to whether MiniSteven is moving or waiting.
 * 
 * <p>
 * To check what the other variable from MiniSteven are please refer to {@link FlyingMonster}, {@link Monster}, 
 * {@link NonStaticObject} and {@link GameObject}.
 */
public class MiniSteven extends FlyingMonster {

	private static final long serialVersionUID = 1L;
	private boolean forward = true;
	private int backwardTimer;
	private int iddleTimer;
	private boolean iddle;
	private double initialSpeed;
	
	/**
	 * Constructs a new MiniSteven, whose position is specified by the {@link Vector2} argument, 
	 * and whose room is specified by the {@link Room} argument.
	 * 
	 * @param position the position of the new new MiniSteven.
	 * @param room the room the new MiniSteven will be in.
	 * @param speed the speed of the new MiniSteven.
	 */
	public MiniSteven(Vector2 position, Room room, double speed) {
		super( position, BossInfos.STEVEN_SIZE, ImagePaths.STEVEN_HEAD, speed, BossInfos.STEVEN_DAMAGE, 
		BossInfos.STEVEN_HEALTH / 2, room, BossInfos.STEVEN_ATTACK_SPEED, 0,
		0);
		setHitBox(new HitBox(BossInfos.STEVEN_SIZE.scalarMultiplication(0.2), this));
		initialSpeed = speed;
	}

	/**
	 * Not used in this class, but it's used in many other NonStaticObject, 
	 * to see example of a used function check {@link Tear#animation()} or {@link Hero#animation()}.
	 */
	@Override
	public void animation() {
		
	}
	
	/**
	 * Updates MiniSteven movement and attack cooldown.
	 */
	@Override
	public void updateGameObject() {
// 	moving forwards or backwards
		System.out.println("test");
		isHitCooldown();
		if ( forward && !getRoom().getHero().collide(this) ) this.move(true);
		else if (backwardTimer > 0) this.move(false);
	
		if (iddle && iddleTimer > 0) iddleTimer--;
		else if (iddle) {
			iddle = false;
			forward = true;
			setSpeed(initialSpeed);
		}
		if (!forward && backwardTimer > 0) backwardTimer--;
		else if (!forward && !iddle) {
			iddle = true;
			iddleTimer = (int) (20 + Math.random() * 20 + 1);
		}
		
//		hand to hand attack timer
		if (timer == 0) {
			if (attackHero()) {
				forward = false;
				setSpeed(getSpeed() * 0.25);
				backwardTimer = (int) (30 + Math.random() * 50 + 1);
			}
		}
		if (getIsHit() && forward) {
			forward = false;
			iddleTimer = (int) (5 + Math.random() * 10 + 1);
		}
		if (timer != 0) timer --;
		if (getHealth() <= 0 ) setDead(true);
		if (cooldown !=0) cooldown --;
	}
}
