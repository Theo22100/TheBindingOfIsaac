package gameWorld.rooms;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import gameobjects.nonstaticobjects.Hero;
import gameobjects.nonstaticobjects.monsters.Monster;
import gameobjects.nonstaticobjects.monsters.boss.Gurdy;
import gameobjects.staticobjects.Stair;
import libraries.StdDraw;
import libraries.Vector2;
import libraries.addedByUs.Utils;

/**
 * 
 * A BossRoom is a Room, that upon being defeated allows the Hero to move floors.
 * <p>
 * To check what the other variable from NonStaticObject are please refer to {@link Room}.
 */
public class BossRoom extends MobRoom{
	
	private static final long serialVersionUID = 1L;
	
	private Stair stair = null;
	
	private List<Monster> ListBoss = new ArrayList<Monster>();
	private int initialBossHealth;
	
	/**
	 * Constructs a new BossRoom, whose Hero is specified by the {@link Hero} argument.
	 * 
	 * @param hero the hero of the new BossRoom
	 */
	public BossRoom(Hero hero) {
		super(hero);
		killAllMonster();
		addEveryThingTolNonStaticObject();
		addBoss();
		initialBossHealth = getTotalBossHealth();
	}
	
	/**
	 * Add the boss to the list of monsters.
	 */
	private void addBoss() {
		Monster boss = Utils.randomBoss(this);
		if (boss instanceof Gurdy) removeAllStaticObject();
		boss.setPosition(new Vector2(0.5,0.5));
		addListMonster(boss);
		addListNonStaticObject(boss);
		ListBoss.add(boss);
		
	}
	
	/**
	 * Removes all staticobjects from the room
	 */
	private void removeAllStaticObject() {
		this.getListStaticObject().removeAll(getListStaticObject());
	}
	
	/*
	 * Draws the Room with all the GameObjects inside of it. 
	 * And draws the boss health bar.
	 */
	public void drawRoom()
	{
		super.drawRoom();
		int health =getTotalBossHealth();
		if (health > 0) {
			StdDraw.setPenColor(new Color(30,30,30));
			StdDraw.filledRectangle(0.5, 0.85, 0.2, 0.02);
			health *= -1;
			health += initialBossHealth;
			
			double multiplierValue = 1900 / initialBossHealth;
			double HealthBarLength = 0.19 - ((double)( health * multiplierValue) / 10000.0);
			double rectangleXPos = 0.5 - ( (0.19 - HealthBarLength) );
			StdDraw.setPenColor(new Color(255,100,100));
			StdDraw.filledRectangle(rectangleXPos, 0.85, HealthBarLength, 0.01);
		}
	}
	
	/*
	 * Checks if every Monsters have defeated, if that's the case then 
	 * a staircase to the next floor is added.
	 */
	@Override
	public void updateRoom()
	{
		if (getListMonster().size() == 0) {
			if (!getAlreadyFinished()) {
				stair = new Stair(0, 0, this);
				Vector2 position = Utils.randomTileIndexPosition(stair, this);
				stair.setPosition(position);
				addListStaticObject(stair);
			}
		}
		super.updateRoom();
	}
	
	/**
	 * Adds a monster to the list of boss.
	 * @param boss the monster to add to the list
	 */
	public void addListBoss(Monster boss) {
		ListBoss.add(boss);
	}
	
	/**
	 * Removes a monster from the list of boss.
	 * @param boss the monster to remove to the list
	 */
	public void removeListBoss(Monster boss) {
		ListBoss.remove(boss);
	}
	
	/*
	 * getter and setter
	 */
	
	/**
	 * Returns the total health of all monsters.
	 * @return the total health of all monsters.
	 */
	private int getTotalBossHealth() {
		int somme = 0;
		for (Monster boss: ListBoss) {
			somme+= boss.getHealth();
		}
		return somme;
	}
	
	/**
	 * Returns the stair in the BossRoom.
	 * @return the stair in the BossRoom.
	 */
	public Stair getStairs() {
		return stair;
	}

	/**
	 * Returns the list of boss monsters.
	 * @return the list of boss monsters.
	 */
	public List<Monster> getListBoss(){
		return ListBoss;
	}
	
	/**
	 * Kills every Monsters from the Room.
	 */
	@Override
	public void killAllMonster() {
		super.killAllMonster();
		ListBoss.removeAll(ListBoss);
	}
}
