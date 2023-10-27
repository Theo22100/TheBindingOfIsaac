package gameWorld.rooms;

import java.util.ArrayList;

import gameobjects.nonstaticobjects.Hero;
import gameobjects.nonstaticobjects.grounditem.Chest;
import gameobjects.nonstaticobjects.monsters.Monster;
import gameobjects.staticobjects.Door;
import libraries.Vector2;
import libraries.addedByUs.Utils;

/**
 * A MobRoom is a Room, that holds a random amount of Monsters. And which 
 * the Doors are closed, until the all monsters have been defeated.
 * <p>
 * To check what the other variable from NonStaticObject are please refer to {@link Room}.
 */
public class MobRoom extends Room{

	private static final long serialVersionUID = 1L;
	
	private boolean alreadyFinished = false;
	
	/**
	 * Constructs a new MobRoom, whose Hero is specified by the {@link Hero} argument. 
	 * And spawns a random amount of mobs if this room.
	 * 
	 * @param hero the hero of the new MobRoom
	 */
	public MobRoom(Hero hero) {
		super(hero);
		int numberMob = (int) (Math.random() * 3 + 2 );
		generateRock( (int) (Math.random() * 3 ) );
		generateSpikes( (int) (Math.random() * 1) );
		spawnMonster(numberMob);
		addEveryThingTolNonStaticObject();
	}

	/**
	 * Spawn a number of Monster to the Room.
	 * @param number the number of Monster to add
	 */
	public void spawnMonster(int number) {
		ArrayList<Monster> lMonster = new ArrayList<Monster>(5);
		while (number > 0){
			Monster m = Utils.randomMonster(this);
			m.setPosition(Utils.randomPosition(m, this));
			lMonster.add(m);
			number --;
		}
		this.setListMonster(lMonster);
	}
	
	
	/*
	 * Make every entity that compose a room process one step. 
	 * Checks if every Monsters have defeated, if that's the case then 
	 * all the doors open and it drops a random amount of random items.
	 */
	@Override
	public void updateRoom()
	{
		if (getListMonster().size() == 0) {
			if (!alreadyFinished) {
//				spawns a random item
				int chance = (int) (Math.random() * 100 + 1);
//				if (chance >= 25) addListGroundItem(Utils.randomGroundObject(this));
				if (chance >= 10) {
					Chest c = new Chest(new Vector2(), this);
					c.setPosition(Utils.randomPosition(c, this));
					addListGroundItem(c);
				}
//				opens every door
				for (Door d: getListDoor()) d.openDoor();
				
			}
			alreadyFinished = true;
		}
		super.updateRoom();
	}
	
	/*
	 * getter and setter
	 */
	
	/**
	 * Returns the finished state of the Room.
	 * @return the finished state of the Room.
	 */
	public boolean getAlreadyFinished() {
		return alreadyFinished;
	}
}
