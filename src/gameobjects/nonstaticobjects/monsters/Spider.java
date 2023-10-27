package gameobjects.nonstaticobjects.monsters;

import gameobjects.GameObject;
import gameobjects.nonstaticobjects.Hero;
import gameobjects.nonstaticobjects.NonStaticObject;
import gameobjects.nonstaticobjects.Tear;
import libraries.Vector2;
import resources.ImagePaths;
import resources.MonsterInfos;

import java.lang.Math;

import org.eclipse.jdt.annotation.Nullable;

import gameWorld.rooms.Room;

/**
 * 
 * A Spider is a Monster, which as a unique way of moving, 
 * using a random direction, length and moving cooldown.
 * <p>
 * To check what the other variable from Spider are please refer to {@link Monster} , {@link NonStaticObject} and {@link GameObject}.
 *
 */
public class Spider extends Monster{
	
	private static final long serialVersionUID = 1L;
	
	private int wait = 0;
	private int nb = 0;
	
	/*
	 * constructor
	 */
	
	/**
	 * Constructs a new Spider, whose position is specified by the {@link Vector2} argument and 
	 * whose room is specified by the {@link Room} argument.
	 * 
	 * @param position the position of the new Spider
	 * @param room the room the new Spider will be in
	 */
	public Spider(Vector2 position, Room room) {
		super(position, MonsterInfos.SPIDER_SIZE, ImagePaths.SPIDER, MonsterInfos.SPIDER_SPEED, 
				MonsterInfos.SPIDER_DAMAGE, MonsterInfos.SPIDER_HEALTH, room, MonsterInfos.SPIDER_ATTACK_SPEED,
				1, 0);
	}
	
	/*
	 * functions
	 */
	
	/**
	 * Move the Spider in a random direction for a random length, 
	 * then has a 30% chance to move straight again. 
	 * Otherwise it waits for a random amount of time.
	 * <p>
	 * Not that forward is not used here so it could be null.
	 * 
	 * @param forward
	 */
	@Override
	protected void move(@Nullable boolean forward) {
		wait--;
		if (wait <0 ) wait = 0;
		
		if (wait == 0) {
			if (nb == 0) {
				direction = new Vector2(Math.random() * 2 - 1, Math.random() * 2 - 1);
				nb = (int)(5 + Math.random() *  5 + 1);
			}
			Vector2 normalizedDirection = getNormalizedDirection();
			Vector2 positionAfterMoving = getPosition().addVector(normalizedDirection);
			if (this.legalMove(positionAfterMoving)) setPosition(positionAfterMoving);
			if (nb == 0) direction = new Vector2();
			wait = (int) (Math.random() * 100 +1);
			nb--;
			if (wait <= 30 || nb > 0) wait = 0;
		}
	}
	
	
	/**
	 * Not used in this class, but it's used in many other NonStaticObject, 
	 * to see example of a used function check {@link Tear#animation()} or {@link Hero#animation()}.
	 */
	@Override
	public void animation() {
	}
}
