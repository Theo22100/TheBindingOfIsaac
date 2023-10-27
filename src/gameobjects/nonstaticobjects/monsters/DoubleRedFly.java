package gameobjects.nonstaticobjects.monsters;

import java.util.ArrayList;
import java.util.List;

import gameWorld.rooms.Room;
import gameobjects.GameObject;
import gameobjects.nonstaticobjects.Hero;
import gameobjects.nonstaticobjects.NonStaticObject;
import gameobjects.nonstaticobjects.Tear;
import libraries.Vector2;
import resources.ImagePaths;
import resources.MonsterInfos;

/**
 * A double red fly is a FlyingMonster that upon being defeated creates two smaller double red fly.
 * <p>
 * To check what the other variable from Spider are please refer to {@link Monster} , {@link NonStaticObject} and {@link GameObject}.
 */
public class DoubleRedFly extends FlyingSummoningMonster {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new double red fly whose position is specified with a {@link Vector2} argument, 
	 * and whose room is specified by a {@link Room} argument.
	 * 
	 * @param position the position of the new Fly
	 * @param room the room the new Fly will be in
	 */
	public DoubleRedFly(Vector2 position, Room room) {
		super(position, MonsterInfos.DOUBLE_RED_FLY_SIZE, ImagePaths.DOUBLE_RED_FLY, MonsterInfos.DOUBLE_RED_FLY_SPEED, 
				MonsterInfos.DOUBLE_RED_FLY_DAMAGE, MonsterInfos.DOUBLE_RED_FLY_HEALTH, room, 
				MonsterInfos.DOUBLE_RED_FLY_ATTACK_SPEED, 1, 0);
	}
	
	/**
	 * Not used in this class, but it's used in many other NonStaticObject, 
	 * to see example of a used function check {@link Tear#animation()} or {@link Hero#animation()}.
	 */
	@Override
	public void animation() {
	}
	
	
	/**
	 * Checks if the double red fly is dead, if it is 
	 * returns two smaller red flies. Otherwise return null.
	 * @return null if the double red fly isn't dead, a list that contains two smaller red flies otherwise.
	 */
	@Override
	public List<Monster> updateSummoningGameObject() {
		super.updateGameObject();
		if (!getDead()) return null;
		
		RedFly fly1 = new RedFly(getPosition().addVector(new Vector2(0,0.01)), getRoom());
		RedFly fly2 = new RedFly(getPosition().addVector(new Vector2(0,-0.01)), getRoom());
		ArrayList<Monster> listMonsters = new ArrayList<Monster>();
		listMonsters.add(fly1);
		listMonsters.add(fly2);
		return listMonsters;
	}
	
}
