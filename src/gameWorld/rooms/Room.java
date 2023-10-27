package gameWorld.rooms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gameobjects.GameObject;
import gameobjects.nonstaticobjects.Hero;
import gameobjects.nonstaticobjects.NonStaticObject;
import gameobjects.nonstaticobjects.Tear;
import gameobjects.nonstaticobjects.grounditem.Bomb;
import gameobjects.nonstaticobjects.grounditem.Chest;
import gameobjects.nonstaticobjects.grounditem.GroundItem;
import gameobjects.nonstaticobjects.grounditem.LifeItem;
import gameobjects.nonstaticobjects.monsters.Monster;
import gameobjects.nonstaticobjects.monsters.SummoningMonster;
import gameobjects.nonstaticobjects.monsters.boss.Larry;
import gameobjects.staticobjects.DestroyableStaticObject;
import gameobjects.staticobjects.Door;
import gameobjects.staticobjects.Rock;
import gameobjects.staticobjects.Spike;
import gameobjects.staticobjects.StaticObject;
import gameobjects.staticobjects.Wall;
import libraries.StdDraw;
import libraries.Vector2;
import libraries.addedByUs.Utils;
import resources.RoomInfos;

/**
 * A room, is a place that the Hero can move , and that is 
 * surrounded by some {@link Wall}. 
 * It can also have adjacent Room next to it that are accessible via a {@link Door}. 
 * <p>
 * The Room holds a lot of variable, most of them being list of precise GameObjects that 
 * are present in the room, like the TearList or the WallList ... . 
 * The Room also has an Hero inside of it.
 * <p>
 * The List of NonStaticObject is mainly used by the {@link #makeTearPLay()} function 
 * as a way to know everything that the Tear can harm in the Room.
 * 
 * <p>
 * Usually a Room is created with a child class and not directly with the Room's constructor.
 * <p>
 * Their are a lot of different type of Room to check them you can check, {@link SpawnRoom}, {@link MobRoom}, {@link ShopRoom} and {@link BossRoom}.
 */
public class Room implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	private Hero hero;
	private List<Monster> lMonster = new ArrayList<Monster>();
	private List<Tear> lTear = new ArrayList<Tear>();
	private List<Door> lDoor = new ArrayList<Door>();
	private List<StaticObject> lStaticObject = new ArrayList<StaticObject>();
	protected List<GroundItem> lGroundItem = new ArrayList<GroundItem>();
	private List<NonStaticObject> lNonStaticObject = new ArrayList<NonStaticObject>();
	private List<Wall> lWall = new ArrayList<Wall>();
	private List<Bomb> lBomb = new ArrayList<Bomb>();

	/**
	 * Constructs a new Room, whose Hero is specified by the {@link Hero} argument.
	 * 
	 * @param hero the hero of the new Room
	 */
	public Room(Hero hero) {
		this.hero = hero;
	}
	
	/*
	 * Make every NonStaticObject that's in the room process one step.
	 */
	public void updateRoom()
	{
		makeMonstersPlay();
		makeTearPLay();
		makeGroundItemPlay();
		makeBombPLay();
		makeHeroPlay();
	}

	/**
	 * Updates every monster and checks whether their dead or not. 
	 * If a monster is dead then it's removed from the list of monster contained in the Room.
	 */
	private void makeMonstersPlay() {
		List<Monster> monsterToDelete = new ArrayList<Monster>();
		List<Monster> monsterToAdd = new ArrayList<Monster>();
		for (Monster m: lMonster) {
			if (m.getDead()) monsterToDelete.add(m);
			if (m instanceof Larry) {
				StaticObject so = ( (Larry) m).checkLarryPoop();
				if (so != null) lStaticObject.add(so);
			}
			if (m instanceof SummoningMonster) {
				List<Monster> monsters = ((SummoningMonster) m).updateSummoningGameObject();
				if (monsters != null) System.out.println(monsters.toString());
				if (monsters != null) monsterToAdd.addAll(monsters);
			}
			else m.updateGameObject();
		}
		if (monsterToDelete.size() != 0 ) {
			lNonStaticObject.removeAll(monsterToDelete);
			lMonster.removeAll(monsterToDelete);
		}
		if (monsterToAdd.size() != 0) {
			lNonStaticObject.addAll(monsterToAdd);
			lMonster.addAll(monsterToAdd);
		}
	}

	/**
	 * Updates every groundItem and checks whether their dead or not. 
	 * If a groundItem is dead then it's removed from the list of groundItem contained in the Room.
	 */
	protected void makeGroundItemPlay() {
		
		GroundItem groundItemToDelete = null;
		GroundItem groundItemToAdd = null;
		for (GroundItem gi : lGroundItem) {
			if (hero.collide(gi) && getHero().getInteract() == true) {
				if (gi instanceof LifeItem) {
//					check if the hero already has max health
					if (!hero.isFullyHealled()) {
						hero.loseHealth(-( (LifeItem) gi).getValue());
						groundItemToDelete = gi;
					}
				}
				else if (gi instanceof Chest) {
					if (hero.getKey() > 0) {
						groundItemToDelete = gi;
						groundItemToAdd = Utils.randomGroundObject(this);
						groundItemToAdd.setPosition(gi.getPosition());
					}
				}
				else {
					groundItemToAdd = hero.addInventory(gi.getItem());
					groundItemToDelete = gi;
				}
			}
		}
		if (groundItemToDelete != null) lGroundItem.remove(groundItemToDelete);
		if (groundItemToAdd != null) lGroundItem.add(groundItemToAdd);
	}
	
	/**
	 * Updates the Hero movement. 
	 */
	private void makeHeroPlay()
	{
		hero.updateGameObject();
		for (StaticObject so: lStaticObject) {
			if (so.getTrap() && hero.collide(so) && !hero.getInvincible() && !hero.getIsHit()) {
				hero.loseHealth(so.getDamage());
				hero.SetIsHit(true);
			}
		}
	}

	/**
	 * Updates every Tear and checks whether their dead or not. 
	 * If a Tear is dead then it's removed from the list of Tear contained in the Room.
	 * 
	 * <p>
	 * Also Checks if the Tear is not colliding with any other GameObject. If it is then the Tear is set to Dead, 
	 * and it will go through the death animation. 
	 * If it collides with a NonStaticGameObject that is not equal to the one that created this Tear, then 
	 * the other GameObject will lose some health.
	 */
	private void makeTearPLay()
	{
		List<Tear> tearsToDelete = new ArrayList<Tear>();
		List<StaticObject> staticObjectToDelete = new ArrayList<StaticObject>();
		for (Tear t :lTear) {
			t.updateGameObject();
//			check if its truly dead or going thought the death animation
			if (t.getTrueDead()) tearsToDelete.add(t);
			else if (!t.getDead()) {
//				checks if it's not colliding with any other non static object
				for (NonStaticObject nso : lNonStaticObject) {
					if (t != nso && t.collide(nso)) {
						GameObject go = t.getGameObject();
						if (nso instanceof Hero && ((Hero) nso ).getInvincible()) break;
						if (!nso.getDead() && !go.isSameInstance(nso) ) {
//							if the NonStatic object is a tear and the object that shot the tear is the same that shot the current tear.
							if (nso instanceof Tear && ((Tear) nso).getGameObject().isSameInstance(go)) break;
							if (nso.getHitCooldown() == 0) nso.loseHealth(t.getDamage());
							if (!(nso instanceof Tear)) nso.SetIsHit(true);
							else nso.setDead(true);
							t.setDead(true);
							break;
						}
					}
				}
				for (StaticObject so : lStaticObject) {
//					if the tear hits a destroyable static object 
					if (so instanceof DestroyableStaticObject && so.collide(t)) {
						t.setDead(true);
						((DestroyableStaticObject) so).setDurability(((DestroyableStaticObject) so).getDurability() - 1);
						if (((DestroyableStaticObject) so).getDurability() <= 0) {
							staticObjectToDelete.add(so);
						}
						
					}
				}
			}
		}
		if (staticObjectToDelete.size() != 0) lStaticObject.removeAll(staticObjectToDelete);
		
		if(tearsToDelete.size() != 0) {
			lNonStaticObject.removeAll(tearsToDelete);
			lTear.removeAll(tearsToDelete);
		}
	}
	
	/**
	 * Updates every Bomb and checks whether their dead or not. 
	 * If a Bomb is dead then it's removed from the list of Bomb contained in the Room.
	 * <p>
	 * If a Bomb explodes near a Door it will open the said Door.
	 */
	private void makeBombPLay() {
		List<Bomb> bombsToDelete = new ArrayList<Bomb>();
		List<StaticObject> staticObjectToDelete = new ArrayList<StaticObject>();
		for (Bomb b: lBomb) {
			b.updateGameObject();
			if (b.getDamageEnable()) {
				for (NonStaticObject nso : lNonStaticObject) {
					if (b.collide(nso) && nso.getHitCooldown() == 0 && !nso.getDead()) {
						nso.loseHealth(b.getDamage());
						if (!(nso instanceof Tear)) nso.SetIsHit(true);
						else nso.setDead(true);
					}
				}
				for (Door d: lDoor) {
					if (d.getClosed() && b.collide(d)) d.openDoor();
				}
				for (StaticObject so : lStaticObject) {
					if (so instanceof DestroyableStaticObject && so.collide(b)) {
						if (((DestroyableStaticObject) so).getDurability() == 0) staticObjectToDelete.add(so);
						((DestroyableStaticObject) so).setDurability(((DestroyableStaticObject) so).getDurability() - 1);
					}
				}
			}
			if (b.getTrueDead()) bombsToDelete.add(b);
		}
		
		if (bombsToDelete.size() != 0) lBomb.removeAll(bombsToDelete);
		if (staticObjectToDelete.size() != 0) lStaticObject.removeAll(staticObjectToDelete);
	}
	
	/*
	 * Draws the Room with all the GameObjects inside of it.
	 */
	public void drawRoom()
	{
		// For every tile, set background color.
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.filledRectangle(0, 1, 1, RoomInfos.TILE_HEIGHT);
		StdDraw.setPenColor(50,50,50);
		StdDraw.filledRectangle(0.85, 0.95, 0.12,( RoomInfos.TILE_HEIGHT ) /(2.4) );
		StdDraw.setPenColor(70,70,70);
		StdDraw.filledRectangle(0.79, 0.95, 0.05,( RoomInfos.TILE_HEIGHT ) /(3) );
		StdDraw.filledRectangle(0.91, 0.95, 0.05,( RoomInfos.TILE_HEIGHT ) /(3) );
		StdDraw.setPenColor(73, 42, 33);
		for (int i = 1; i < RoomInfos.NB_TILES - 1; i++)
		{
			for (int j = 1; j < RoomInfos.NB_TILES - 1; j++)
			{
				Vector2 position = Utils.positionFromTileIndex(i, j);
				StdDraw.filledRectangle(position.getX(), position.getY(), RoomInfos.HALF_TILE_SIZE.getX(),
					RoomInfos.HALF_TILE_SIZE.getY());
			}
		}
		
		for (Wall w: lWall) w.drawGameObject();
		for (Door d: lDoor) d.drawGameObject();
		for (StaticObject so: lStaticObject) so.drawGameObject();
		for (Bomb b: lBomb) b.drawGameObject();
		drawGroundItem();
		hero.drawGameObject();
		hero.drawHeroInfo();
		
		for (Monster m: lMonster) m.drawGameObject();
		for (Tear t: lTear) t.drawGameObject();
	}
	
	/**
	 * Draws every GroundItem in the Room.
	 */
	protected void drawGroundItem() {
		for (GroundItem gi : lGroundItem) {
			gi.drawGameObject();
//			gi.getHitBox().drawHitBox();
		}
	}
	
	/*
	 * room generators
	 */
	
	/**
	 * Generates every wall that surround the Room.
	 */
	public void generateWalls() {
		for (int i = 0; i < RoomInfos.NB_TILES; i++) {
			for (int j = 0; j < RoomInfos.NB_TILES; j++) {
				if ( ( i == 0 || j == 0 ) || (i == RoomInfos.NB_TILES - 1 || j == RoomInfos.NB_TILES - 1)) 
					lWall.add(new Wall(i,j,this));
			}
		}
	}
	
	/**
	 * Generates some Rocks randomly and makes sure not to block any Door or the player initial position.
	 */
	protected void generateRock(int number) {
		for (int i = 0; i < number; i++) {
			Rock r = new Rock(2,1, this);
			Vector2 position = Utils.randomTileIndexPosition(r, this);
			r.setPosition(position);
			lStaticObject.add(r);
		}
	}
	
	/**
	 * Generates some Spikes randomly and makes sure not to Spawn in front of a Door or at the player initial position.
	 */
	protected void generateSpikes(int number) {
		for (int i = 0; i <number; i++) {
			Spike s = new Spike(2,1, this);
			Vector2 position = Utils.randomTileIndexPosition(s, this);
			s.setPosition(position);
			lStaticObject.add(s);
		}
	}
	
	/**
	 * Adds every NonStaticObject to the List of NonStaticObject.
	 */
	public void addEveryThingTolNonStaticObject() {
		lNonStaticObject = new ArrayList<NonStaticObject>();
		lNonStaticObject.add(hero);
		lNonStaticObject.addAll(lMonster);
	}
	
	
	
	/**
	 * Kills every Monsters from the Room.
	 */
	public void killAllMonster() {
		lNonStaticObject.removeAll(lMonster);
		lMonster.removeAll(lMonster);
	}
	
	
	/*
	 * adder in list
	 */
	
	/**
	 * Adds a door to the List of Door in the Room.
	 * @param door the door that'll be added to the list
	 */
	public void addListDoor(Door door) {
		lDoor.add(door);
	}

	/**
	 * Adds a monster to the List of Monster in the Room.
	 * @param monster the monster that'll be added to the list
	 */
	public void addListMonster(Monster monster) {
		lMonster.add(monster);
	}

	/**
	 * Adds a staticObject to the List of StaticObject in the Room.
	 * @param staticObject the staticObject that'll be added to the list
	 */
	public void addListStaticObject(StaticObject staticObject) {
		lStaticObject.add(staticObject);
	}
	
	/**
	 * Adds a groundItem to the List of GroundItem in the Room.
	 * @param groundItem the groundItem that'll be added to the list
	 */
	public void addListGroundItem(GroundItem groundItem) {
		lGroundItem.add(groundItem);
	}

	/**
	 * Adds a nonStaticObject to the List of NonStaticObject in the Room.
	 * @param nonStaticObject the nonStaticObject that'll be added to the list
	 */
	public void addListNonStaticObject(NonStaticObject nonStaticObject) {
		lNonStaticObject.add(nonStaticObject);
	}
	
	

	/**
	 * Adds a tear to the List of Tear in the Room.
	 * @param tear the tear that'll be added to the list
	 */
	public void addListTear(Tear tear) {
		lTear.add(tear);
	}
	
	/**
	 * Adds a boob to the List of Bomb in the Room.
	 * @param bomb the bomb that'll be added to the list
	 */
	public void addListBomb(Bomb bomb) {
		lBomb.add(bomb);
	}
	
	/*
	 * getter and setter
	 */
	
	/**
	 * Sets the list of Door of the Room to a new one.
	 * @param lDoor the new list of Door
	 */
	public void setDoor(ArrayList<Door> lDoor) {
		this.lDoor = lDoor;
	}
	
	/**
	 * Returns the list of Door of the Room.
	 * @return the list of Door of the Room.
	 */
	public List<Door> getListDoor(){
		return lDoor;
	}
	
	/**
	 * Sets the list of Monster of the Room to a new one.
	 * @param lMonster the new list of Monster
	 */
	public void setListMonster(ArrayList<Monster> lMonster) {
		this.lMonster = lMonster;
	}
	
	/**
	 * Returns the list of Monster of the Room.
	 * @return the list of Monster of the Room.
	 */
	public List<Monster> getListMonster() {
		return lMonster;
	}
	
	/**
	 * Sets the list of StaticObject of the Room to a new one.
	 * @param lStaticObject the new list of StaticObject
	 */
	public void setListStaticObject(ArrayList<StaticObject> lStaticObject) {
		this.lStaticObject = lStaticObject ;
	}
	
	/**
	 * Returns the list of StaticObject of the Room.
	 * @return the list of StaticObject of the Room.
	 */
	public List<StaticObject> getListStaticObject(){
		return lStaticObject;
	}
	
	/**
	 * Sets the list of NonStaticObject of the Room to a new one.
	 * @param lNonStaticObject the new list of NonStaticObject
	 */
	public void setListNonStaticObject(ArrayList<NonStaticObject> lNonStaticObject) {
		this.lNonStaticObject = lNonStaticObject ;
	}
	
	/**
	 * Returns the list of NonStaticObject of the Room.
	 * @return the list of NonStaticObject of the Room.
	 */
	public List<NonStaticObject> getListNonStaticObject(){
		return lNonStaticObject;
	}
	
	/**
	 * Sets the list of GroundItem of the Room to a new one.
	 * @param lGroundItem the new list of GroundItem
	 */
	public void setListGroundItem(ArrayList<GroundItem> lGroundItem) {
		this.lGroundItem = lGroundItem ;
	}

	/**
	 * Returns the list of GroundItem of the Room.
	 * @return the list of GroundItem of the Room.
	 */
	public List<GroundItem> getListGroundItem(){
		return lGroundItem;
	}
	
	/**
	 * Sets the list of Wall of the Room to a new one.
	 * @param lWall the new list of Wall
	 */
	public void setListWall(ArrayList<Wall> lWall) {
		this.lWall = lWall ;
	}
	
	/**
	 * Returns the list of Wall of the Room.
	 * @return the list of Wall of the Room.
	 */
	public List<Wall> getListWall(){
		return lWall;
	}
	
	/**
	 * Returns the Hero of the Room.
	 * @return the Hero of the Room.
	 */
	public Hero getHero() {
		return this.hero;
	}
}
