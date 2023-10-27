package libraries.addedByUs;

import java.util.Collection;

import gameWorld.rooms.Room;
import gameobjects.GameObject;
import gameobjects.nonstaticobjects.grounditem.GroundItem;
import gameobjects.nonstaticobjects.grounditem.Key;
import gameobjects.nonstaticobjects.grounditem.LifeItem;
import gameobjects.nonstaticobjects.grounditem.Money;
import gameobjects.nonstaticobjects.monsters.Clotty;
import gameobjects.nonstaticobjects.monsters.DoubleRedFly;
import gameobjects.nonstaticobjects.monsters.Fly;
import gameobjects.nonstaticobjects.monsters.Monster;
import gameobjects.nonstaticobjects.monsters.Spider;
import gameobjects.nonstaticobjects.monsters.Vis;
import gameobjects.nonstaticobjects.monsters.boss.Gurdy;
import gameobjects.nonstaticobjects.monsters.boss.Lamb;
import gameobjects.nonstaticobjects.monsters.boss.Larry;
import gameobjects.nonstaticobjects.monsters.boss.Loki;
import gameobjects.nonstaticobjects.monsters.boss.Steven;
import libraries.Vector2;
import resources.ImagePaths;
import resources.ItemInfos;
import resources.RoomInfos;

/**
 * The Utils Class is just a class full of function that are used across 
 * many different class and do not have their place in the said class.
 *
 */
public class Utils {

	/**
	 * Returns the number of appearance of a integer in a list of integer
	 * @param list the list of integer
	 * @param value the integer value
	 * @return the number of appearance of the integer in the list
	 */
	public static int occuranceNumber(Collection<Integer> list, int value) {
		int number = 0;
		for (int i: list) {
			if (i == value) number++;
		}
		return number;
	}
	
	/**
	 * Returns a random GroundItem.
	 * @param room the room.
	 * @return a random GroundItem.
	 */
	public static GroundItem randomGroundObject(Room room) {
		int chance = (int) ( Math.random() * 100 + 1);
		GroundItem gi = randomLegendaryGroundObject(room);
		if (chance <= 75) {
			gi = randomCommonGroundObject(room);
		}
		else if (chance <= 99) {
			gi = randomRareGroundObject(room);
		}
		
		gi.setPosition(randomPosition(gi, room));
		return gi;
	}
	
	/**
	 * Returns a random Common GroundItem.
	 * @param room the room
	 * @return a random Common GroundItem.
	 */
	public static GroundItem randomCommonGroundObject(Room room) {
		int chance = (int) ( Math.random() * 100 + 1);
		Vector2 position = new Vector2(0.5,0.5);
		if (chance <= 20) {
			return ( new GroundItem(position, ItemInfos.BOMBE_SIZE, ImagePaths.BOMB, room, ItemInfos.BOMBE_ITEM, true) );
		}
		else if (chance <= 40) {
			return ( new Key(position, room));
		}
		else if (chance <= 60) {
			return ( new Money(position, room, 2));
		}
		else if (chance <= 80) {
			return ( new LifeItem(position, room, true));
		}
		else {
			return ( new LifeItem(position, room, false) );
		}
	}
	
	/**
	 * Returns a random Rare GroundItem.
	 * @param room the room
	 * @return a random Rare GroundItem.
	 */
	public static GroundItem randomRareGroundObject(Room room) {
		int chance = (int) ( Math.random() * 100 + 1);
		Vector2 position = new Vector2(0.5,0.5);
		if (chance <= 15) {
			return ( new Money(position, room, 5) );
		}
		else if (chance <= 30) {
			return ( new GroundItem(position, ItemInfos.HP_UP_SIZE, ImagePaths.HP_UP, room, ItemInfos.HP_UP_ITEM, false) );
		}
		else if (chance <= 45) {
			return ( new GroundItem(position, ItemInfos.JESUS_JUICE_SIZE, ImagePaths.JESUS_JUICE, room, ItemInfos.JESUS_JUICE_ITEM, false) );
		}
		else if (chance <= 60) {
			return ( new GroundItem(position, ItemInfos.BLOOD_OF_THE_MARTHYR_SIZE, ImagePaths.BLOOD_OF_THE_MARTYR, 
					room, ItemInfos.BLOOD_OF_THE_MARTHYR_ITEM, false) );
		}
		else if (chance <= 75) {
			return ( new GroundItem(position, ItemInfos.LUNCH_SIZE, ImagePaths.LUNCH, room, ItemInfos.LUNCH_ITEM, false) );
		}
		else if (chance <= 90) {
			return ( new GroundItem(position, ItemInfos.STIGMATA_SIZE, ImagePaths.STIGMATA, room, ItemInfos.STIGMATA_ITEM, false) );
		}
		return ( new GroundItem(position, ItemInfos.PENTAGRAM_SIZE, ImagePaths.PENTAGRAM, room, ItemInfos.PENTAGRAM_ITEM, false) );
	}
	
	/**
	 * Returns a random Legendary GroundItem.
	 * @param room the room
	 * @return a random Legendary GroundItem.
	 */
	public static GroundItem randomLegendaryGroundObject(Room room) {
		int chance = (int) ( Math.random() * 100 + 1);
		Vector2 position = new Vector2(0.5,0.5);
		if (chance < 50) return ( new Money(position, room, 20) ); 
		else return ( new GroundItem(position, ItemInfos.MAGIC_MUSHROOM_SIZE, ImagePaths.MAGIC_MUSHROOM, room, ItemInfos.MAGIC_MUSHROOM_ITEM, false) );
	}
	
	/**
	 * Returns a random monster.
	 * @param room the room.
	 * @return a random monster.
	 */
	public static Monster randomMonster(Room room) {
		Monster m = null;
		int proba = (int) (Math.random() * 100 + 1 );
		if (proba <= 20) m = new Spider(new Vector2(), room);
		else if (proba <= 40) m = new Vis(new Vector2(), room);
		else if (proba <= 60) m = new Clotty(new Vector2(),room);
		else if (proba <= 80) m = new DoubleRedFly(new Vector2(), room);
		else m = new Fly(new Vector2(), room);
		return m;
	}
	
	/**
	 * Returns a random Boss.
	 * @param room the room.
	 * @return a random monster.
	 */
	public static Monster randomBoss(Room room) {
		Monster m = null;
		int proba = (int) (Math.random() * 100 + 1 );
		if (proba <= 20) m = new Gurdy(new Vector2(), room);
		else if (proba <= 40) m = new Lamb(new Vector2(), room);
		else if (proba <= 60) m = new Larry(new Vector2(),room, 5);
		else if (proba <= 80) m = new Loki(new Vector2(), room);
		else m = new Steven(new Vector2(), room);
		return m;
	}
	
	
	
	/**
	 * Returns a vector2 coordinate that contains a position in a room.
	 * @param object the game object
	 * @param room the room
	 * @return a random valid position in the room.
	 */
	public static Vector2 randomPosition(GameObject object, Room room) {
		double x_min = RoomInfos.TILE_WIDTH;
		double x_max = 1 - RoomInfos.TILE_WIDTH;
		double y_min = RoomInfos.TILE_HEIGHT;
		double y_max = 1 - (2 * RoomInfos.TILE_HEIGHT);
		double x = (Math.random() * (x_max - x_min ) + x_min);
		double y = (Math.random() * (y_max - y_min ) + y_min);
		Vector2 position = new Vector2(x,y);
		
		while (!object.legalPosition(position)) {
			x = (Math.random() * (x_max - x_min ) + x_min);
		 	y = (Math.random() * (y_max - y_min ) + y_min);
			position = new Vector2(x,y);
		}
		return position;
	}
	
	
	public static Vector2 randomTileIndexPosition(GameObject object, Room room) {
		int x = (int) (Math.random() * (RoomInfos.NB_TILES - 2) + 1);
		int y = (int) (Math.random() * (RoomInfos.NB_TILES - 2) + 1);
		Vector2 position = positionFromTileIndex(x, y);
		while (!object.legalPosition(position) || !legalTileIndexPosition(x, y)) {
			x = (int) (Math.random() * (RoomInfos.NB_TILES - 2) + 1);
			y = (int) (Math.random() * (RoomInfos.NB_TILES - 2) + 1);
			position = positionFromTileIndex(x, y);
		}
		
		return position;
	}
	
	private static boolean legalTileIndexPosition(int x, int y) {
		if ( x == 4 && y == 7) return false;
		if ( x == 4 && y == 1) return false;
		if ( x == 1 && y == 4) return false;
		if ( x == 7 && y == 4) return false;
		return true;
	}
	
	/**
	 * Convert a tile index to a 0-1 position.
	 * 
	 * @param indexX the value in the x abscissa
	 * @param indexY the value in the y abscissa
	 * @return a vector position 
	 */
	public static Vector2 positionFromTileIndex(int indexX, int indexY)
	{
		return new Vector2(indexX * RoomInfos.TILE_WIDTH + RoomInfos.HALF_TILE_SIZE.getX(),
				indexY * RoomInfos.TILE_HEIGHT + RoomInfos.HALF_TILE_SIZE.getY() );
	}
}
