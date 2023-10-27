package gameWorld;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gameWorld.rooms.BossRoom;
import gameWorld.rooms.MobRoom;
import gameWorld.rooms.Room;
import gameWorld.rooms.ShopRoom;
import gameWorld.rooms.SpawnRoom;
import gameobjects.nonstaticobjects.Hero;
import gameobjects.staticobjects.Door;
import libraries.Vector2;
import resources.ImagePaths;

/**
 * A Floor is a list of Rooms all being accessible via one Door or more.
 * <p> 
 * This class has two import variables, the first one being the list of Rooms, lRoom. 
 * And the second one is the actualPosition, also called curentRoom in {@link GameWorld}.
 * <p>
 * We also store the SpawnRoom's position as that's the place where the hero will first spawn in.
 * 
 * <p>
 * A Floor can either be constructed randomly by specifying the length, width and number 
 * of MobRooms wanted. Or by referring a list of Rooms.
 * 
 * @see Hero Door Room
 */
public class Floor implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Room[][] lRoom;
	private boolean bossRoomPlaced = false;
	private boolean shopRoomPlaced = false;
	private int numberRoomPlaced = 0;
	private int numberRoom;
	
	private Vector2 posSpawn;
	
	private Vector2 actualPosition;
	
	/*
	 * constructor
	 */
	
	/**
	 * Constructs a new Floor, whose Hero is specified by he {@link Hero} argument.
	 * 
	 * @param numberRoom the numbers of MobRoom on the new Floor
	 * @param width the new Floor width
	 * @param length the new Floor length
	 * @param hero the hero of the new Floors
	 */
	public Floor(int numberRoom, int width, int length, Hero hero) {
		if (width * length < numberRoom + 3) throw new IllegalArgumentException("the width and length is to small to fit every room");
		if (numberRoom < 5) throw new IllegalArgumentException("not enought rooms ");
		
		Room[][] rooms = new Room[width][length];
		this.numberRoom = numberRoom;
		
		posSpawn = new Vector2((Math.random() * width), (Math.random() * length));
		actualPosition = posSpawn;
		createFloor(rooms, hero);
		placeBossRoom(rooms, hero);
		lRoom = rooms;
		updateDoor();
	}

	/**
	 * Constructs a new Floor, whose Hero is specified by he {@link Hero} argument.
	 * 
	 * @param lRoom the desired already set adjustment of the Floor
	 * @param hero the hero of the new Floors
	 */
	public Floor(Room[][] lRoom, Hero hero) {
		this.lRoom = lRoom;
		
		for (int i = 0; i < lRoom.length; i++) {
			for (int j =0; j < lRoom[i].length; j++) {
				if (lRoom[i][j] instanceof SpawnRoom) posSpawn = new Vector2(i,j);
			}
		}
		actualPosition = posSpawn;
		updateDoor();
	}
	
	/**
	 * Places a boss Room with a given minimum distance to the spawn. 
	 * This minimal distance depends on the number of rooms placed.
	 * 
	 * @param rooms the room list
	 * @param hero the current hero
	 */
	private void placeBossRoom(Room[][] rooms, Hero hero) {
		while (!bossRoomPlaced) {
			for (int w = 0; w < rooms.length; w ++) {
				for (int l = 0; l < rooms[w].length; l++) {
					if (rooms[w][l] == null && adjacentRoom(w, l, rooms)) {
						if (distantRoom(posSpawn, w, l) >= 1 ) {
							// 1/2 chance to place a room
							if ( (int) (Math.random() * 1 + 1 )== 1) {
								rooms[w][l] = new BossRoom(hero);
								bossRoomPlaced = true;
								return;
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Creates the list of Rooms, and places them in the new Floor.
	 * 
	 * @param rooms the list of room with the Spawn already placed
	 * @param hero the hero
	 */
	private void createFloor(Room[][] rooms, Hero hero) {
		rooms[(int)(posSpawn.getX())][(int)(posSpawn.getY())] = new SpawnRoom(hero);
		this.numberRoomPlaced = 1;
		while (numberRoom - 1 > this.numberRoomPlaced) {
			for (int w = 0; w < rooms.length; w ++) {
				for (int l = 0; l < rooms[w].length; l++) {
					if (rooms[w][l] == null && adjacentRoom(w, l, rooms) ) {
						// 1/2 chance to place a room
						if ( (int) (Math.random() * 1 + 1 ) == 1) {
							placeRoom(rooms, hero, w, l);
							if (numberRoom - 1 <= this.numberRoomPlaced) return;
						}
					}
				}
			}
		}
	}
	
	/**
	 * Returns the distance from the x, y, coordinate to the room's coordinate.
	 * @param posRoom the coordinate of the room
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return the distance from the x, y, coordinate to the room's coordinate.
	 */
	private int distantRoom(Vector2 posRoom, int x, int y) {
		int w = ((int) posRoom.getX());
		int l = ((int) posRoom.getY());
		if ( w > x ) return 1 + distantRoom(new Vector2(w - 1, l), x, y);
		else if ( w < x ) return 1 + distantRoom(new Vector2(w + 1, l), x, y);
		else if ( l > y ) return 1 + distantRoom(new Vector2(w, l - 1), x, y);
		else if ( l < y ) return 1 + distantRoom(new Vector2(w, l + 1), x, y);
//		if it's the same position
		else return 0;
	}
	
	
	/**
	 * Places a room at the wanted coordinates w and l. 
	 * The Room can either be a ShopRoom or a MobRoom based on 
	 * the set percentage.
	 * 
	 * @param rooms the room list
	 * @param hero the current hero
	 * @param w the w coordinate referring to x
	 * @param l the w coordinate referring to y
	 */
	private void placeRoom(Room[][] rooms, Hero hero, int w, int l) {
		int proba =(int) (Math.random() * 100 + 1);
		// 1/4 chance
		if (!shopRoomPlaced && proba <25) {
			rooms[w][l] = new ShopRoom(hero);
			shopRoomPlaced = true;
			numberRoomPlaced++;
		}
		// 3/4 chance
		else if (shopRoomPlaced || (!shopRoomPlaced && numberRoomPlaced < numberRoom - 2)) {
			rooms[w][l] = new MobRoom(hero);
			numberRoomPlaced++;
		}
	}
	
	/**
	 * Checks if there's a room either, on the right, on the left, on top or on the bottom 
	 * of the room at the coordinates w and l.
	 * 
	 * @param w the w coordinate on the x axes
	 * @param l the w coordinate on the y axes
	 * @param lRoom the list of room
	 * @return {@code true} if there's a room next to the selected one, {@code false} otherwise.
	 */
	private boolean adjacentRoom(int w, int l, Room[][] lRoom){
		if (validCoordinate(w + 1, l, lRoom) && lRoom[w + 1][l] != null) return true;
		else if (validCoordinate(w - 1, l, lRoom) && lRoom[w - 1][l] != null) return true;
		else if (validCoordinate(w, l + 1, lRoom) && lRoom[w][l + 1] != null) return true;
		else if (validCoordinate(w, l - 1, lRoom) && lRoom[w][l - 1] != null) return true;

		return false;
	}
	
	/**
	 * Checks if the coordinates are in the list.
	 * 
	 * @param w the w coordinate on the x axes
	 * @param l the w coordinate on the y axes
	 * @param lRoom the list of room
	 * @return {@code true} if the coordinates are in the list, {@code false} otherwise.
	 */
	private boolean validCoordinate(int w, int l, Room[][] lRoom) {
		if (w < 0 || w >= lRoom.length || l < 0 || l >= lRoom[0].length) return false;
		return true;
	}
	
	
	/**
	 * Returns every direction of the rooms that are adjacent to the tested one.
	 * <p>
	 * <b>
	 * Example:</b>  
	 * if there's a door on the north and east it will 
	 * return {"NORTH";"EAST"}
	 * 
	 * @param r the room that needs to be tested
	 * @return the list of cardinal direction.
	 */
	private List<String> nearRoom(Room r) {
		List<String> t = new ArrayList<String>();
		for (int w=0; w < lRoom.length; w++) {
			for (int l =0; l< lRoom[w].length; l++) {
				if (lRoom[w][l] == r) {
					if (validCoordinate(w-1, l, lRoom) && lRoom[w-1][l] != null) {
						if (lRoom[w-1][l] instanceof BossRoom) t.add("NORTH:BOSS");
						else t.add("NORTH");
					}
					if (validCoordinate(w+1, l, lRoom) && lRoom[w+1][l] != null) {
						if (lRoom[w+1][l] instanceof BossRoom) t.add("SOUTH:BOSS");
						else t.add("SOUTH");
					}
					if (validCoordinate(w, l+1, lRoom) && lRoom[w][l+1] != null) {
						if (lRoom[w][l+1] instanceof BossRoom) t.add("EST:BOSS");
						else t.add("EST");
					}
					if (validCoordinate(w, l-1, lRoom) && lRoom[w][l-1] != null) {
						if (lRoom[w][l-1] instanceof BossRoom) t.add("WEST:BOSS");
						else t.add("WEST");
					}
				}
			}
		}
		return t;
	}
	
	/**
	 * Sets every Door in every Room based on the Rooms next to it.
	 */
	private void updateDoor() {
		for(int w = 0; w < lRoom.length; w++) {
			for (int l = 0; l < lRoom.length; l++) {
				if (lRoom[w][l] != null) {
					List<String> tmp = nearRoom(lRoom[w][l]);
					for (String s : tmp) {
						boolean closedDoor = true;
						String openedDoorImage = ImagePaths.OPENED_DOOR;
						String closedDoorImage = ImagePaths.CLOSED_DOOR;
						if (s.contains(":BOSS") || lRoom[w][l] instanceof BossRoom) {
							openedDoorImage = ImagePaths.OPENED_BOSS_DOOR;
							closedDoorImage = ImagePaths.CLOSED_BOSS_DOOR;
						}
						if (lRoom[w][l] instanceof SpawnRoom || lRoom[w][l] instanceof ShopRoom) closedDoor = false;
						lRoom[w][l].addListDoor(new Door(s.replace(":BOSS", ""), lRoom[w][l], closedDoor, openedDoorImage, closedDoorImage));
					}
					lRoom[w][l].generateWalls();
				}
			}
		}
	}
	
	/**
	 * Returns the spawning room of the Floor.
	 * @return the spawning room of the Floor.
	 */
	public Room getSpawn() {
		return lRoom[(int) posSpawn.getX()][(int) posSpawn.getY()];
	}
	
	/**
	 * Gets the room that's next to the current one based on the orientation.
	 * 
	 * @param orientation, the orientation ("NORTH","SOUTH","WEST" or "EST")
	 * @return the room that's next to the selected orientation.
	 */
	public Room getNextRoom(String orientation) {
		if (orientation.equals("WEST")) {
			actualPosition = actualPosition.addVector(new Vector2(0, -1));
		} else if (orientation.equals("EST")) {
			actualPosition = actualPosition.addVector(new Vector2(0, 1));
		} else if (orientation.equals("SOUTH")){
			actualPosition = actualPosition.addVector(new Vector2(1, 0));	
		} else if (orientation.equals("NORTH")) {
			actualPosition = actualPosition.addVector(new Vector2(-1, 0));
		}
		return lRoom[(int) actualPosition.getX()][(int) actualPosition.getY()];
	}
	

	/**
	 * Transforms the list of rooms to a visual representation of the floor.
	 */
	@Override
	public String toString() {
		String t = "";
		for (int w = 0; w < lRoom.length; w++) {
			t += "|";
			for (int l = 0; l < lRoom[w].length; l++) {
				if (lRoom[w][l] == lRoom[(int) actualPosition.getX()][(int) actualPosition.getY()]) t+="!|";
				else{
					if (lRoom[w][l] == null) t+= " |";
					if (lRoom[w][l] instanceof MobRoom && !(lRoom[w][l] instanceof BossRoom)) t += ".|";
					if (lRoom[w][l] instanceof SpawnRoom) t += "@|";
					if (lRoom[w][l] instanceof ShopRoom) t += "$|";
					if (lRoom[w][l] instanceof BossRoom) t += "*|";
				}
			}
			t += "\n";
		}
		return t;
	}
	
	
	/**
	 * Returns the position of the room in the floor.
	 * @param r the room
	 * @return the position of the room in the floor.
	 */
	public Vector2 getPosition(Room r) {
		for (int w = 0; w < lRoom.length; w++) {
			for (int l = 0; l < lRoom.length; l++) {
				if (lRoom[w][l] != null && lRoom[w][l].equals(r)) return new Vector2(w,l);
			}
		}
		return null;
	}
	
	/**
	 * Returns a 3 x 3 area of all rooms next to the one selected.
	 * @param r the room
	 * @return a 3 x 3 area of all rooms next to the one selected.
	 */
	public Room[][] getArroundRooms(Room r) {
		Room[][] around = new Room[3][3];
		int i = 0;
		int j = 0;
		Vector2 coordinateRoom = getPosition(r);
		if (coordinateRoom == null) return around;
		for (int w = -1; w <= 1; w++) {
			for (int l = -1; l <= 1; l++) {
				if (validCoordinate((int) (coordinateRoom.getX() ) + w, (int) (coordinateRoom.getY() ) + l, lRoom)) {
					
					around[i][j] = lRoom[(int) (coordinateRoom.getX() ) + w][(int) (coordinateRoom.getY() ) + l];
				}
				j++;
			}
			i++;
			j = 0;
		}
		return around;
	}
	
	
	
	/*
	 * getter and setter
	 */
	
	/**
	 * Returns the list of Room of the Floor.
	 * @return the list of Room of the Floor.
	 */
	public Room[][] getListRoom(){
		return lRoom;
	}
}
