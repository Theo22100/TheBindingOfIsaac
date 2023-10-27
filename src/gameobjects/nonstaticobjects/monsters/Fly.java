package gameobjects.nonstaticobjects.monsters;

import gameWorld.rooms.Room;
import gameobjects.GameObject;
import gameobjects.nonstaticobjects.NonStaticObject;
import libraries.Vector2;
import resources.ImagePaths;
import resources.MonsterInfos;

/**
 * A fly is a FlyingMonster that can shoot projectile from a distance.
 * <p>
 * To check what the other variable from Spider are please refer to {@link Monster} , {@link NonStaticObject} and {@link GameObject}.
 *
 */
public class Fly extends FlyingMonster{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new fly whose position is specified with a {@link Vector2} argument, 
	 * and whose room is specified by a {@link Room} argument.
	 * 
	 * @param position the position of the new Fly
	 * @param room the room the new Fly will be in
	 */
	public Fly(Vector2 position, Room room) {
		super(position, MonsterInfos.FLY_SIZE, ImagePaths.FLY, 
				MonsterInfos.FLY_SPEED, MonsterInfos.FLY_DAMAGE, MonsterInfos.FLY_HEALTH, room, 
				MonsterInfos.FLY_ATTACK_SPEED, 1, 0, 1, MonsterInfos.FLY_REACH, MonsterInfos.FLY_DISTANT_ATTACK_SPEED);
	}

	/**
	 * Makes the Fly moves its wings by changing images.
	 */
	@Override
	public void animation() {
		switch ( getAnimationState() ) {
		case 4: setImagePath(ImagePaths.FLY); break; 
		case 0: setImagePath(ImagePaths.FLY_1); setAnimationState(8); break;
		}
		setAnimationState(getAnimationState() - 1);
	}
	
	
	/**
	 * Updates the Fly and it's flying animation.
	 */
	@Override
	public void updateGameObject() {
		super.updateGameObject();
		animation();
	}

}
