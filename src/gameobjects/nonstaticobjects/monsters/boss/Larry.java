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
import gameobjects.staticobjects.Poop;
import gameobjects.staticobjects.StaticObject;
import libraries.Vector2;
import resources.BossInfos;
import resources.ImagePaths;

/**
 * Larry is a Monster that is a Boss. 
 * It has the special ability of having a snake like body. 
 * This body will go down when Larry's health reaches a certain value.
 * <p>
 * This class has a lot of variable related to the length, like the ArrayList of Vector2 
 * that stores the ten PreviousPosition of the previous bodyPart. 
 * And the integer variable bodyLength that holds Larry's body length at it's creation.
 * <p>
 * There's also a movingTimer so that Larry can change it's direction randomly.
 * 
 * <p>
 * To check what the other variable from Larry are please refer to {@link Monster}, 
 * {@link NonStaticObject} and {@link GameObject}.
 */
public class Larry extends SummoningMonster {

	private static final long serialVersionUID = 1L;
	private List<LarryBody> body = new ArrayList<LarryBody>();
	private int movingTimer = 1;
	private int headHealth;
	private int bodyLength;
	
	private int poop = 60;
	
	private boolean splitted;
	private List<Vector2[]> tenPreviousPosition = new ArrayList<Vector2[]>();
	
	
	/**
	 * LarryBody is a Monster that can't move on it's own.
	 * <p>
	 * To check what the variable from LarryBody are please refer to {@link Monster}, 
	 * {@link NonStaticObject} and {@link GameObject}.
	 */
	private class LarryBody extends Monster {
		private static final long serialVersionUID = 1L;
		
		/**
		 * Constructs a new LarryBody whose position is specified by the {@link Vector2} argument 
		 * and whose room is specified by the {@link Room}. 
		 * 
		 * @param position the position of the new LarryBody
		 * @param room the room the new LarryBody will be in
		 */
		public LarryBody(Vector2 position, Room room) {
			super(position, BossInfos.LARRY_SIZE, ImagePaths.LARRY_BODY, BossInfos.LARRY_SPEED, BossInfos.LARRY_DAMAGE, 
					BossInfos.LARRY_HEALTH, room, BossInfos.LARRY_ATTACK_SPEED,0 ,0);
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
//			hand to hand attack
			if (timer == 0) attackHero();
			if (timer != 0) timer --;
		}
	}

	
	/**
	 * Constructs a new Laryy whose position is specified by the {@link Vector2} argument 
	 * and whose room is specified by the {@link Room}.
	 * 
	 * @throws IllegalArgumentException unless the bodylength is greater or equal to 0.
	 * 
	 * @param position the position of the new Larry
	 * @param room the room the new Larry will be in
	 * @param bodyLength the length of Larry's body
	 */
	public Larry(Vector2 position, Room room, int bodyLength) {
		super(position, BossInfos.LARRY_SIZE, ImagePaths.LARRY_HEAD, BossInfos.LARRY_SPEED, BossInfos.LARRY_DAMAGE, 
				BossInfos.LARRY_HEALTH, room, BossInfos.LARRY_ATTACK_SPEED,0 ,0);

		if (bodyLength < 0) throw new IllegalArgumentException("bodyLength can't be negative");
		setHealth(BossInfos.LARRY_HEALTH);
		this.bodyLength = bodyLength;
//		divides the total health of larry to fit his body size
		int health = (int) (getHealth() / (bodyLength + 1));
		
//		save the currentPosition to a 10 long array
		Vector2[] tenCurrentPosition = new Vector2[7];
		for (int i = 0; i < tenCurrentPosition.length; i++) tenCurrentPosition[i] = getPosition();
		
//		creates the body
		for (int i = 0 ; i < bodyLength; i++) {
			LarryBody body = new LarryBody(position, room);
			body.setHealth(health);
			this.body.add(body);
			getRoom().addListMonster(body);
			getRoom().addListNonStaticObject(body);
			tenPreviousPosition.add(tenCurrentPosition.clone());
		}
		int additional_health = (int) (getHealth() - (health * (body.size() + 1 ) ));
		headHealth = health + additional_health;
		
		
	}
	
	/**
	 * Not used in this class, but it's used in many other NonStaticObject, 
	 * to see example of a used function check {@link Tear#animation()} or {@link Hero#animation()}.
	 */
	@Override
	public void animation() {
		
	}

	/**
	 * Updates Lary's movement as well as Larry's body movement. 
	 * Also updates the hit and attack cooldown. And Larry's total health gets updated.
	 * 
	 * @return Larry's half body if it splits in two.
	 */
	@Override
	public List<Monster> updateSummoningGameObject() {
		isHitCooldown();
//		recounts the real life of Larry
		updateHealth();
//		main head is dead
		if (getHealth() == 0) deleteAllBody();
		if (bodyLength > 0) updateBodySize();
//		hand to hand attack
		if (timer == 0) attackHero();
		if (timer != 0) timer --;

		if (getHealth() <= 0 ) setDead(true);
		if ( !getDirection().equals(new Vector2()) && movingTimer > 0) move();
		else {
			int chance = (int)(Math.random() * 100 + 1);
			if (chance <= 25) setDirection(new Vector2(1,0));
			else if (chance > 25 && chance <= 50) setDirection(new Vector2(-1,0));
			else if (chance > 50 && chance <= 75) setDirection(new Vector2(0,-1));
			else if (chance > 75) setDirection(new Vector2(0,1));
			movingTimer = 10 * (int) (Math.random() * 10 + 1);
		}
		if (movingTimer > 0) movingTimer--;
		
//		updates Larry body movement
		if (body.size() > 0) {
			body.get(0).setPosition(tenPreviousPosition.get(0)[0] );
			for (int i = body.size() - 1; i > 0; i--) {
				body.get(i).setPosition( tenPreviousPosition.get(i)[0] );
				tenPreviousPosition.set(i, updateTenPreviousPosition(tenPreviousPosition.get(i), body.get(i - 1).getPosition()) );
			}
			tenPreviousPosition.set(0, updateTenPreviousPosition(tenPreviousPosition.get(0), getPosition()) );
		}
		
		
		if (splitted == false && getHealth() == BossInfos.LARRY_HEALTH / 2) {
			splitted = true;
			return splitLarry();
		}
		return null;
	}
	
	
	/**
	 * Returns the new Array of Vector2 position where the newPosition is added to the end of list. 
	 * And every other element is set to the next element.
	 * @param tenPreviousPosition the previous Array of Vector2 position
	 * @param newPosition the new position that will be added to the Array
	 * @return the new Array of Vector2 position.
	 */
	public Vector2[] updateTenPreviousPosition(Vector2[] tenPreviousPosition, Vector2 newPosition) {
		for (int i = 0; i < tenPreviousPosition.length - 1; i++) tenPreviousPosition[i] = tenPreviousPosition[i + 1];
		tenPreviousPosition[tenPreviousPosition.length - 1] = newPosition;
		return tenPreviousPosition;
	}
	
	/**
	 * Updates Larry's body size.
	 */
	public void updateBodySize() {
		int palier = (BossInfos.LARRY_HEALTH / bodyLength);
		if (body.size() != 0 && getHealth() == palier * ( body.size() - 1) ) {
			deleteLastBody();
		}
		return;
	}
	
	/**
	 * Sets Larry's health to the totalHealth if that's not already the case.
	 */
	private void updateHealth() {
		if (getHealth() != getTotalHealth()) setHealth(getTotalHealth());
	}
	
	/**
	 * Returns the total health of Larry's head and all body parts.
	 * @return the total health of Larry's head and all body parts.
	 */
	private int getTotalHealth() {
		int totalHealth = headHealth;
		for (LarryBody bodies : body) totalHealth += bodies.getHealth();
		return totalHealth;
	}
	
	/**
	 * Deletes all of Larry body parts except from it's head.
	 */
	private void deleteAllBody() {
		for (LarryBody bodies: body) bodies.setDead(true);
	}
	
	/**
	 * Sets the new body length for Larry. 
	 * If Lary's body length is smaller than the new one then it creates new Body parts and 
	 * returns a list of those new bodyParts. 
	 * Otherwise it returns null.
	 * 
	 * @param length the new body length for Larry
	 * @return a list of new bodyPart for Larry.
	 */
	private List<Monster> setBodyNumber(int length) {
		if (body.size() == length) return null;
		else if (body.size() > length) {
			if (length < 0) throw new IllegalArgumentException("length can't be negative");
			for (int i = body.size() - 1 ; i > length; i--) {
				deleteLastBody();
			}
			return null;
		}
		else {
			this.bodyLength = length;
			List<Monster> newBodyParts = new ArrayList<Monster>();
//			save the currentPosition to a 10 long array
			Vector2[] tenCurrentPosition = new Vector2[7];
			for (int i = 0; i < tenCurrentPosition.length; i++) tenCurrentPosition[i] = getPosition();
			
//			sets the position for the new body part
			Vector2 position = getPosition();
			if (body.size() > 0) position = body.get(body.size() - 1).getPosition();
			
//			adds every body part to match the length
			for (int i = body.size(); i < length; i++) {
				LarryBody body = new LarryBody(position, getRoom());
				this.body.add(body);
				newBodyParts.add(body);
				tenPreviousPosition.add(tenCurrentPosition.clone());
			}
			
//			sets the health to the normal amount
			int health = (int) (getHealth() / body.size() );
			for (LarryBody bodies : body) bodies.setHealth(health);
			headHealth = (int) (health + (getHealth() - (health * body.size())));
			
			return newBodyParts;
		}
	}
	
	/**
	 * Delete the last bodyPart of Larry's body.
	 */
	private void deleteLastBody() {
		if (body.size() == 0) return;
		int health = (int) (getHealth() / body.size() );
		for (LarryBody bodies : body) bodies.setHealth(health);
		headHealth = (int) (health + (getHealth() - (health * body.size())));
		
		LarryBody lastBody = body.get(body.size() - 1);
		body.remove(lastBody);
		lastBody.setDead(true);
	}
	
	/**
	 * Returns a new Larry Monster of body size half of Larry's body size.
	 * @return a new Larry Monster of body size half of Larry's body size.
	 */
	private List<Monster> splitLarry() {
		Vector2 position = getPosition();
		if (body.size() > 0) position = body.get(body.size() - 1).getPosition();
		int newLength = body.size() / 2;
		for (int i = body.size() - 1 ; i >= newLength; i--) {
			body.get(i).setDead(true);
		}
		int health = (int) (getHealth() / (newLength + 1) );
		for (LarryBody bodies : body) bodies.setHealth(health / 2);
		for (int i = body.size() - 1 ; i >= newLength; i--) body.get(i).setHealth(0);
		
		headHealth = health/2 ;
		
		List<Monster> newLarry = new ArrayList<Monster>();
		Larry splitedLary = new Larry(position, getRoom(), 0);
		newLarry.add(splitedLary);
		List<Monster> newLarryBody = splitedLary.setBodyNumber(body.size() - newLength - 1);
		splitedLary.headHealth = headHealth;
		splitedLary.setBodyLength(body.size() - newLength);
		if (newLarryBody != null) {
			newLarry.addAll(newLarryBody);
			for (LarryBody bodies : splitedLary.body) bodies.setHealth(body.get(0).getHealth());
		}
		headHealth += (int) (getHealth() - (splitedLary.getTotalHealth() + getTotalHealth()));
		if (getRoom() instanceof BossRoom) ((BossRoom) getRoom()).addListBoss(splitedLary);;
		return newLarry;
	}
	
	public StaticObject checkLarryPoop() {
		if (poop == 0) {
			poop = 100;
			return poop();
		}
		poop --;
		return null;
	}
	
	private StaticObject poop() {
		Vector2 positionLastBody = getPosition();
		if (body.size() != 0 ) positionLastBody = body.get(body.size() -1).getPosition();
		Poop p = new Poop(0,0,getRoom());
		p.setPosition(positionLastBody);
		if (p.collide(this)) return null;
		return p;
	}
	
	
	
	/**
	 * Moves Larry by a vector and speed if the position is considered legal. 
	 * If the move is not legal then the direction is set to a new Vector2.
	 */
	private void move() {
		Vector2 normalizedDirection = getNormalizedDirection();
		Vector2 positionAfterMoving = getPosition().addVector(normalizedDirection);
		if (legalMove(positionAfterMoving)) setPosition(positionAfterMoving);
		else setDirection(new Vector2());
	}
	
	/**
	 * Retrieves a amount of damage from Larry head Health.
	 * @param damage the amount of damage you want the object to receive.
	 */
	@Override
	public void loseHealth(double damage) {
		headHealth -= damage;
	}
	
	/**
	 * Sets a new body Length for Larry.
	 * @param bodyLength the new bodyLength for Larry
	 */
	public void setBodyLength(int bodyLength) {
		this.bodyLength = bodyLength;
	}
}
