package gameWorld.rooms;

import gameobjects.nonstaticobjects.Hero;

/**
 * A SpawnRoom is a Room, that is safe, as it doesn't have any monster or harmful traps. 
 * It's the room the Hero will first appear in.
 * <p>
 * To check what the other variable from NonStaticObject are please refer to {@link Room}.
 */
public class SpawnRoom extends Room{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new ShopRoom, whose Hero is specified by the {@link Hero} argument. 
	 * 
	 * @param hero the hero of the new SpawnRoom
	 */
	public SpawnRoom(Hero hero) {
		super(hero);
		addEveryThingTolNonStaticObject();
	}
	
	
}
