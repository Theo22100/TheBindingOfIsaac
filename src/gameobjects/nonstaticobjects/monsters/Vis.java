package gameobjects.nonstaticobjects.monsters;

import gameWorld.rooms.Room;
import gameobjects.GameObject;
import gameobjects.nonstaticobjects.Hero;
import gameobjects.nonstaticobjects.NonStaticObject;
import gameobjects.nonstaticobjects.Tear;
import libraries.Vector2;
import resources.ImagePaths;
import resources.MonsterInfos;

/**
 * A Vis is a Monster that can only attack on hand to hand.
 * <p>
 * To check what the other variable from Spider are please refer to {@link Monster} , {@link NonStaticObject} and {@link GameObject}.
 */
public class Vis extends Monster {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new vis whose position is specified with a {@link Vector2} argument, 
	 * and whose room is specified by a {@link Room} argument.
	 * 
	 * @param position the position of the new Fly
	 * @param room the room the new Fly will be in
	 */
	public Vis(Vector2 position, Room room) {
		super(position, MonsterInfos.VIS_SIZE, ImagePaths.VIS, MonsterInfos.VIS_SPEED, MonsterInfos.VIS_DAMAGE,
				MonsterInfos.VIS_HEALTH, room, MonsterInfos.VIS_ATTACK_SPEED, 1, 0);
	}
	
	/**
	 * Not used in this class, but it's used in many other NonStaticObject, 
	 * to see example of a used function check {@link Tear#animation()} or {@link Hero#animation()}.
	 */
	@Override
	public void animation() {
	}

}
