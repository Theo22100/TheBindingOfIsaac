package gameobjects.nonstaticobjects.monsters;

import java.util.ArrayList;
import java.util.List;

import gameWorld.rooms.Room;
import gameobjects.GameObject;
import gameobjects.nonstaticobjects.NonStaticObject;
import gameobjects.nonstaticobjects.Tear;
import libraries.Vector2;
import resources.ImagePaths;

/**
 * A Monster is a NonStaticObject, it uses different variables such as, 
 * a integer variable attackSpeed, that represents the speed the Monster will be able to damage the hero. 
 * <p>
 * Another one is the double forwardDistance, that is the distance from the hero, that will make the Monster move forwards. 
 * <p>
 * The last one is the double backwardDistance, that is the distance from the hero, that will make the Monster move backwards. 
 * <p>
 * Because the Monster can also attack from a certain distance, there's another constructor that holds some other variable, 
 * such as a double shootingDistance that represents the distance between the hero and the monster, 
 * that the monster will start shooting a projectile at the hero.
 * <p>
 * The next unique variable is a double, reach, used to know how far the projectile should go. 
 * And, is a double, distantAttackSpeed,  used to know how fast the DistantMonster can shoot.
 * 
 * <p>
 * <b> Creating a Monster </b>
 * <p>
 * Here's an example to create a Monster that only attacks hand to hand : 
 * <pre>
 * Monster monster = new Monster(new Vector2(0.5, 0.5), new Vector2(0.1,0.1), "image/monster", 
 * 0.2, 1, 5, currentRoom, 50, 0.5, 0);
 * </pre>
 * <p>
 * Here's an example to create a Monster that can distantly attack : 
 * <pre>
 * Monster monster = new Monster(new Vector2(0.5, 0.5), new Vector2(0.1,0.1), "image/monster", 
 * 0.2, 1, 5, currentRoom, 50, 0.5, 0, 0.7, 0.3, 50);
 * </pre>
 * 
 * To check what the other variable from Monster are please refer to {@link NonStaticObject} and {@link GameObject}.
 * 
 */
public abstract class Monster extends NonStaticObject{

	private static final long serialVersionUID = 1L;
	
	protected int timer = 0;
	private double forwardDistance;
	private double backwardDistance;
	private int attackSpeed; //	hand to hand timer
	private double reach;
	private double shootingDistance;
	protected int cooldown; // shootingCooldown
	private int distantAttackSpeed;
	private List<String> tearAnimationSprite = null;
	private String tearImagePath = ImagePaths.RED_TEAR;
	
	/*
	 * constructors
	 */
	
	/**
	 * Constructs a new Monster, whose position and size are specified by the {@link Vector2} argument and 
	 * whose room is specified by the {@link Room} argument.
	 * 
	 * <p>
	 * Here forwardDistance, backwardDistance and shootingDistance are all the referring to 
	 * the distance from the DistantMonster and the Hero.
	 * 
	 * @param position the position of the new Monster
	 * @param size the size of the new Monster
	 * @param imagePath the image of the new Monster
	 * @param speed the speed of the new Monster
	 * @param damage the damage done by the new Monster
	 * @param health the health of the new Monster
	 * @param room the room the new Monster will be in
	 * @param attackSpeed the speed to which the new Monster will attack on hand to hand
	 * @param forwardDistance the distance that the new Monster will start moving forward
	 * @param backwardDistance the distance that the new Monster will start moving backward
	 */
	public Monster(Vector2 position, Vector2 size, String imagePath, double speed, int damage,
			int health, Room room, int attackSpeed, double forwardDistance, double backwardDistance) {
		super(position, size, imagePath, room, speed, damage, health);
		this.attackSpeed = attackSpeed;
		this.forwardDistance = forwardDistance;
		this.backwardDistance = backwardDistance;
		this.attackSpeed = attackSpeed;
		this.shootingDistance = 0;
	}

	/**
	 * Constructs a new Monster that can distantly attack, whose position and size are specified by the {@link Vector2} argument and 
	 * whose room is specified by the {@link Room} argument.
	 * 
	 * <p>
	 * Here forwardDistance, backwardDistance and shootingDistance are all the referring to 
	 * the distance from the DistantMonster and the Hero.
	 * 
	 * @param position the position of the new Monster
	 * @param size the size of the new Monster
	 * @param imagePath the image of the new Monster
	 * @param speed the speed of the new Monster
	 * @param damage the damage done by the new Monster
	 * @param health the health of the new Monster
	 * @param room the room the new Monster will be in
	 * @param attackSpeed the speed to which the new Monster will attack on hand to hand
	 * @param forwardDistance the distance that the new Monster will start moving forward
	 * @param backwardDistance the distance that the new Monster will start moving backward
	 * @param shootingDistance the distance that the new Monster will start shooting at the hero
	 * @param reach the reach for the projectile shot by the new Monster
	 * @param distantAttackSpeed the speed to which the new Monster will attack by shooting
	 */
	public Monster(Vector2 position, Vector2 size, String imagePath, double speed, int damage,
			int health, Room room, int attackSpeed, double forwardDistance, double backwardDistance, 
			double shootingDistance, double reach, int distantAttackSpeed) {
		super(position, size, imagePath, room, speed, damage, health);
		this.attackSpeed = attackSpeed;
		this.forwardDistance = forwardDistance;
		this.attackSpeed = attackSpeed;
		this.shootingDistance = shootingDistance;
		this.reach = reach;
		this.distantAttackSpeed = distantAttackSpeed;
		this.tearAnimationSprite = new ArrayList<String>();
		this.tearAnimationSprite.add(ImagePaths.RED_TEAR1);
		this.tearAnimationSprite.add(ImagePaths.RED_TEAR2);
		this.tearAnimationSprite.add(ImagePaths.RED_TEAR3);
		this.tearAnimationSprite.add(ImagePaths.RED_TEAR4);
		this.tearAnimationSprite.add(ImagePaths.RED_TEAR5);
		this.tearAnimationSprite.add(ImagePaths.RED_TEAR6);
		this.tearAnimationSprite.add(ImagePaths.RED_TEAR7);
		this.tearAnimationSprite.add(ImagePaths.RED_TEAR8);
		this.tearImagePath = ImagePaths.RED_TEAR;
	}
	
	/**
	 * Constructs a new Monster that can distantly attack, whose position and size are specified by the {@link Vector2} argument and 
	 * whose room is specified by the {@link Room} argument.
	 * 
	 * <p>
	 * Here forwardDistance, backwardDistance and shootingDistance are all the referring to 
	 * the distance from the DistantMonster and the Hero.
	 * 
	 * <p> 
	 * This constructor specifies the Tear image Animation with a List of String. An the Initial Tear image.
	 * 
	 * @param position the position of the new Monster
	 * @param size the size of the new Monster
	 * @param imagePath the image of the new Monster
	 * @param speed the speed of the new Monster
	 * @param damage the damage done by the new Monster
	 * @param health the health of the new Monster
	 * @param room the room the new Monster will be in
	 * @param attackSpeed the speed to which the new Monster will attack on hand to hand
	 * @param forwardDistance the distance that the new Monster will start moving forward
	 * @param backwardDistance the distance that the new Monster will start moving backward
	 * @param shootingDistance the distance that the new Monster will start shooting at the hero
	 * @param reach the reach for the projectile shot by the new Monster
	 * @param distantAttackSpeed the speed to which the new Monster will attack by shooting
	 * @param tearAnimationSprite the tear animation used when it dies
	 * @param tearImagePath the tear initial image
	 */
	public Monster(Vector2 position, Vector2 size, String imagePath, double speed, int damage,
			int health, Room room, int attackSpeed, double forwardDistance, double backwardDistance, 
			double shootingDistance, double reach, int distantAttackSpeed, 
			List<String> tearAnimationSprite, String tearImagePath) {
		super(position, size, imagePath, room, speed, damage, health);
		this.attackSpeed = attackSpeed;
		this.forwardDistance = forwardDistance;
		this.attackSpeed = attackSpeed;
		this.shootingDistance = shootingDistance;
		this.reach = reach;
		this.distantAttackSpeed = distantAttackSpeed;
		if (tearAnimationSprite.equals(null) || tearAnimationSprite.size() == 0) {
			this.tearAnimationSprite = new ArrayList<String>();
			this.tearAnimationSprite.add(ImagePaths.RED_TEAR1);
			this.tearAnimationSprite.add(ImagePaths.RED_TEAR2);
			this.tearAnimationSprite.add(ImagePaths.RED_TEAR3);
			this.tearAnimationSprite.add(ImagePaths.RED_TEAR4);
			this.tearAnimationSprite.add(ImagePaths.RED_TEAR5);
			this.tearAnimationSprite.add(ImagePaths.RED_TEAR6);
			this.tearAnimationSprite.add(ImagePaths.RED_TEAR7);
			this.tearAnimationSprite.add(ImagePaths.RED_TEAR8);
		}
		else this.tearAnimationSprite = tearAnimationSprite;
		if (tearImagePath == null || tearImagePath == "") this.tearImagePath = ImagePaths.RED_TEAR;
		else this.tearImagePath = tearImagePath;
	}
	
	/*
	 *  functions
	 */
	
	/**
	 * Updates the Monster based on the distance between it and the Hero.
	 * <p>
	 * If the Hero distance is bigger or equal than the forward distance it moves forwards. 
	 * If the Hero distance is smaller or equal than the backward distance it moves backwards. 
	 * If the Hero distance is smaller or equal than the shooting distance the Monster shoots at the Hero.
	 * <p>
	 * If the hand to hand timer is equal to 0, it launches the {@code Monster#attackHero(Hero)} function.
	 * 
	 * <p>
	 * Also decreases the hand to hand timer if it's not 0.
	 * 
	 */
	public void updateGameObject() {
//		hit cooldown
		isHitCooldown();
//	 	moving forwards or backwards
		if ( ((getRoom().getHero().getPosition().distance(this.getPosition())) <= forwardDistance) &&
				!getRoom().getHero().collide(this) ) this.move(true);
		else if (getRoom().getHero().getPosition().distance(this.getPosition()) <= backwardDistance) this.move(false);
		if (shootingDistance != 0 && getRoom().getHero().getPosition().distance(this.getPosition()) <= shootingDistance) this.shoot();
//		hand to hand attack cooldown
		if (timer == 0) attackHero();
		if (timer != 0) timer --;
		
		if (getHealth() <= 0 ) setDead(true);
//		distant attack cooldown
		if (cooldown !=0) cooldown --;
	}
	
	/**
	 * Moves the monster forward or backward based on the hero's position and the boolean forward.
	 * <p>
	 * If forward is true it moves forwards, 
	 * else it moves backwards.
	 * 
	 * @param forward a boolean
	 */
	protected void move(boolean forward) {
		direction = (getRoom().getHero().getPosition().subVector(this.getPosition()));
		Vector2 normalizedDirection = getNormalizedDirection();
		Vector2 positionAfterMoving = getPosition().addVector(normalizedDirection);
		if (!forward) positionAfterMoving = getPosition().subVector(normalizedDirection);
		
		if (this.legalMove(positionAfterMoving)) setPosition(positionAfterMoving);
		direction = new Vector2();
	}

	/**
	 * Checks if the hero collides with the Monster.
	 * <p>
	 * If it does then make the hero lose some health based on the damage of the Monster. 
	 * It also sets the cooldown timer for the hand to hand attack.
	 * <p>
	 * Otherwise it doesn't change anything.
	 * 
	 * @returns {@code true} if the monster attacked the hero, {@code false} otherwise.
	 */
	protected boolean attackHero() {
		if (getRoom().getHero().getInvincible()) return false;
		if (this.getHitBox().collide( getRoom().getHero().getHitBox() ) ) {
			getRoom().getHero().loseHealth(this.getDamage());
			getRoom().getHero().SetIsHit(true);
			timer = attackSpeed;
			return true;
		}
		return false;
	}
	
	
	/**
	 * Checks if the DistantMonster can shoot (if the cooldown is equal to 0).
	 * <p>
	 * If that's the case then it creates a {@code new Tear#Tear(Vector2, Vector2, int, double, Room, double, GameObject, String)}, 
	 * at the monsters position and with a direction determined based on the DistantMonster and the Hero.
	 * 
	 */
	protected void shoot() {
		if (cooldown ==0) {
			new Tear(getRoom().getHero().getPosition().subVector(this.getPosition()), this.getPosition(),
					this.getDamage(), reach, this.getRoom(),
					0.01, this, tearImagePath, tearAnimationSprite);
			cooldown = distantAttackSpeed;
		}
	}
	
	/*
	 * getter and setter
	 */
	
	/**
	 * Returns the forwardDistance of the monster.
	 * @return the forwardDistance of the monster.
	 */
	public double getForwardDistance() {
		return forwardDistance;
	}
	
	/**
	 * Returns the backwardDistance of the monster.
	 * @return the backwardDistance of the monster.
	 */
	public double getBackwardDistance() {
		return backwardDistance;
	}
	
	/**
	 * Returns the shootingDistance of the monster.
	 * @return the shootingDistance of the monster.
	 */
	public double getShootingDistance() {
		return shootingDistance;
	}
	
	/**
	 * Returns the shooting reach of the monster.
	 * @return the shooting reach of the monster.
	 */
	public double getReach() {
		return reach;
	}
	
	/**
	 * Returns the tear animation sprite.
	 * @return the tear animation sprite.
	 */
	public List<String> getTearAnimationSprite(){
		return tearAnimationSprite;
	}
	
	/**
	 * Returns the tear imagePath.
	 * @return the tear imagePath.
	 */
	public String getTearImagePath() {
		return tearImagePath;
	}
	
	
	/**
	 * Return the distantAttackSpeed.
	 * @return the distantAttackSpeed.
	 */
	public int getDistantAttackSpeed() {
		return attackSpeed;
	}
}
