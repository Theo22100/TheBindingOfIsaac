package gameWorld;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import gameWorld.rooms.BossRoom;
import gameWorld.rooms.MobRoom;
import gameWorld.rooms.Room;
import gameWorld.rooms.ShopRoom;
import gameWorld.rooms.SpawnRoom;
import gameobjects.nonstaticobjects.Hero;
import gameobjects.nonstaticobjects.grounditem.Bomb;
import gameobjects.nonstaticobjects.grounditem.GroundItem;
import gameobjects.nonstaticobjects.heros.Gaper;
import gameobjects.nonstaticobjects.heros.Magdalene;
import gameobjects.staticobjects.Door;
import libraries.Keybinding;
import libraries.Keybinding.SpecialKeys;
import libraries.StdDraw;
import libraries.Vector2;
import libraries.addedByUs.Data;
import libraries.addedByUs.Music;
import libraries.addedByUs.Utils;
import resources.HeroInfos;
import resources.ImagePaths;
import resources.RoomInfos;
import resources.SoundPaths;


/**
 * The GameWorld is the World composed of the Dungeon, 
 * and the Hero selected by the Player when the world was created.
 * <p>
 * There are two important variable, the current Floor and the current Room, 
 * that allows the player to move in the dungeon.
 */
public class GameWorld implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	protected Room currentRoom;
	private Hero hero;
	private Floor currentFloor;
	private Dungeon dungeon;
	
	private boolean paused = false;
	private int keycooldown = 10;
	private int blackScreenCooldown = 0;
	private Color previousScreenColor = new Color(0,0,0,50);
	
	private int posXCross = 2;
	private int soundValue = 3;
	private boolean stop;
	private boolean win;
	private boolean controlMenu;
	private boolean saveMenu;
	private boolean showMap = false;
	
	private String soundImage = ImagePaths.SOUND_BAR_3;
	private LinkedHashMap<String, Integer> controls;
	
	private int keyChange = -1;
	
	private String saveName;
	
	private transient GameConsole console = null;
	
//	to exclude the music from being saved to the save file
	private transient Music m = null;
	
	/**
	 * Constructs a new GameWorlds, whose Hero is specified by the {@link Hero} argument.
	 * 
	 * @throws IllegalArgumentException unless both {@code hero} and
	 *                                  {@code saveName} aren't null.
	 * @throws IllegalArgumentException unless the number of Floor is greater than 0.
	 *                                   
	 * @param hero the Hero of the GameWorld
	 * @param saveName the file the game will be saved to
	 * @param numberFloor the number of floor the game will have
	 */
	public GameWorld(Hero hero, String saveName, int numberFloor)
	{
		if (hero == null) throw new IllegalArgumentException("The hero isn't declared");
		if (saveName == null || saveName == "") throw new IllegalArgumentException("The saveName has to be declared");
		if (numberFloor <= 0) throw new IllegalArgumentException("The number of Floor has to be greater than 0");
		this.saveName = saveName;
		this.hero = hero;
		Dungeon d = new Dungeon(numberFloor, hero);
		this.dungeon = d;
		currentFloor = d.getCurrentFloor();
		System.out.println(currentFloor.toString());
		currentRoom = currentFloor.getSpawn();
		this.hero.setRoom(currentRoom);
		controls = Data.getControls();
	}

	/**
	 * Processes every input from the Player.
	 */
	public void processUserInput()
	{
		processKeysForMovement();
	}
	
	/**
	 * Returns the game state, whether it's game over or not.
	 * @return {@code true} if the player lost the game, {@code false} otherwise.
	 */
	public boolean gameOver() {
		if (stop == true) if (m != null) m.pause();
		return stop;
	}
	
	/**
	 * Updates the current Room.
	 */
	private void updateGameObjects()
	{
		currentRoom.updateRoom();
	}

	/**
	 * Changes the current Room to the Room that's one to the desired orientation.
	 * 
	 * @param orientation the orientation ("EST", "WEST", "SOUTH", "NORTH") wanted to look for the next door
	 */
	private void changeRoom(String orientation) {
		if (orientation.equals("")) return;
		
		Room tmp = currentFloor.getNextRoom(orientation);
		if (orientation.equals("EST")) hero.getPosition().setX(RoomInfos.TILE_WIDTH + 0.02);
		else if (orientation.equals("WEST")) hero.getPosition().setX(1 - RoomInfos.TILE_WIDTH - 0.02);
		else if (orientation.equals("SOUTH")) hero.getPosition().setY(1 - RoomInfos.TILE_HEIGHT * 2 - 0.02);
		else if (orientation.equals("NORTH")) hero.getPosition().setY(RoomInfos.TILE_HEIGHT + 0.02);
		blackScreenCooldown = 20;
		currentRoom = tmp;
		this.hero.setRoom(currentRoom);
		System.out.println(currentFloor.toString());
	}
	
	/**
	 * Changes the current Floor to the next Floor in the Dungeon if it exist. 
	 * Else it will change the GameWorld to won.
	 */
	private void changeFloor() {
		hero.setPosition(new Vector2(0.5,0.5));
		currentFloor = dungeon.getNextFloor();
		blackScreenCooldown = 20;
		if (currentFloor == null) {
			win = true;
			return;
		}
		currentRoom = currentFloor.getSpawn();
		hero.setRoom(currentRoom);
		System.out.println("new floor : ");
		System.out.println(currentFloor.toString());
	}

	/**
	 * Updates the music playing to match the theme of the room, and changes the volume
	 */
	
	public void updateMusic() {
		if (currentRoom instanceof SpawnRoom) if (m == null || !m.getMusicPath().equals(SoundPaths.SpawnRoomMusic)) {
			if (m != null) m.stop();
			m = new Music(SoundPaths.SpawnRoomMusic);
			m.play();
		}
		if (currentRoom instanceof BossRoom) if (m == null || !m.getMusicPath().equals(SoundPaths.BossRoomMusic)) {
			if (m != null) m.stop();
			m = new Music(SoundPaths.BossRoomMusic);
			m.play();
		}
		if (currentRoom instanceof ShopRoom) if (m == null || !m.getMusicPath().equals(SoundPaths.ShopRoomMusic)) {
			if (m != null) m.stop();
			m = new Music(SoundPaths.ShopRoomMusic);
			m.play();
		}
		if (currentRoom instanceof MobRoom && !(currentRoom instanceof BossRoom)) if (m == null || !m.getMusicPath().equals(SoundPaths.MobRoomMusic)) {
			if (m != null) m.stop();
			m = new Music(SoundPaths.MobRoomMusic);
			m.play();
		}
		
		if (m != null && m.getVolume() != getSoundValue(soundValue)) m.changeVolume(getSoundValue(soundValue));
	}
	
	/**
	 * Returns the soundValue matching the sound bar value.
	 * @param soundValue the sound bar value
	 * @Return the soundValue matching the sound bar value.
	 */
	
	private float getSoundValue(int soundValue) {
		switch (soundValue) {
			case 0 : return -30f;
			case 1 : return -20f;
			case 2 : return -15f;
			case 3 : return -10f;
			case 4 : return -5f;
			case 5 : return 0f;
			case 6 : return 5f;
			default : return 0f;
		}
	}
	
	
	/**
	 * Updates the gameWorld with all the cooldowns.
	 */
	public void updateGameWorld() {
		
		if (blackScreenCooldown > 0) blackScreenCooldown--;
		if (keycooldown > 0) keycooldown --;
		if (!paused && blackScreenCooldown == 0 && !win && !hero.getDead()) {
			updateGameObjects();
			if (currentRoom instanceof BossRoom && checkStairCollision(currentRoom)) changeFloor();
			else changeRoom(checkDoorCollision(currentRoom));
		}
		drawWindow();
	}
	
	/**
	 * Returns the orientation of the door the hero is colliding with or "" if the hero's not colliding with any door.
	 * @param room the currentRoom
	 * @return the orientation of the door ("EAST", "WEST", "NORTH", "SOUTH") the hero is colliding with, or "" if there's no collision.
	 */
	private String checkDoorCollision(Room room) {
		for (Door door: currentRoom.getListDoor()) {
			if (door.collide(hero) && !door.getClosed()) return door.getOrientation();
		}
		return "";
	}
	
	/**
	 * Returns whether there's a collision with a stair or not.
	 * @param room the currentRoom
	 * @return {@code true} if there's a stair in the Room and it collides with the hero.
	 */
	private boolean checkStairCollision(Room room) {
		BossRoom bossRoom = ((BossRoom) (room));
		if (bossRoom.getStairs() != null && bossRoom.getStairs().collide(hero)) return true;
		return false;
	}
	
	/**
	 * Draw a 3x3 Map of the rooms adjacent to the current one in the Floor.
	 */
	private void drawMap() {
		Room[][] aroundCurrentRoom = currentFloor.getArroundRooms(currentRoom);
		double x = 0.84;
		double y = 0.86;
		for (int w = 0; w < aroundCurrentRoom.length; w++) {
			for (int l = 0; l < aroundCurrentRoom[w].length; l++) {
				if (aroundCurrentRoom[w][l] == null) StdDraw.picture(x, y, ImagePaths.MAP_NO_ROOM, 0.05, 0.05);
				else if (aroundCurrentRoom[w][l] instanceof BossRoom) StdDraw.picture(x, y, ImagePaths.MAP_BOSS_ROOM, 0.05, 0.05);
				else StdDraw.picture(x, y, ImagePaths.MAP_NORMAL_ROOM, 0.05, 0.05);
				x += 0.05;
			}
			y -= 0.05;
			x = 0.84;
		}
	}
	
	/**
	 * Draws every information in the window.
	 */
	private void drawWindow() {
		if (hero.getDead()) {
			StdDraw.clear();
			StdDraw.picture(0.5, 0.5, ImagePaths.LOSE_SCREEN, 1, 1);
			StdDraw.show();
		}
		else if (win) {
			StdDraw.clear();
			StdDraw.picture(0.5, 0.5, ImagePaths.WIN_SCREEN, 1.0, 1.0);
			StdDraw.show();
		}
		else if (controlMenu) {
			StdDraw.clear();
			StdDraw.picture(0.5, 0.5, ImagePaths.CONTROL_MENU, 1, 1);
			double y = 0.88;
			int i = 0;
//			prints all the controls on a image
			for (String action: controls.keySet()) {
				int clef = controls.get(action);
				String key = KeyEvent.getKeyText( clef );
				if (keyChange == i) {
					key = "Press the key you want";
				}
				StdDraw.setPenColor(new Color(67,63,60));
				if (Utils.occuranceNumber(controls.values(), clef) > 1) StdDraw.setPenColor(new Color(200,63,60));
				StdDraw.textLeft(0.53, y, key);
				y -= 0.047;
				i++;
			}
		}
		else if (saveMenu){
			StdDraw.clear();
			drawGameObjects();
			StdDraw.picture(0.5, 0.5, ImagePaths.SAVE_MENU, 0.3, 0.27);
			return;
		}
		else if (paused) {
			StdDraw.clear();
			drawGameObjects();
			StdDraw.picture(0.5, 0.5, ImagePaths.PAUSE_MENU, RoomInfos.TILE_WIDTH * 5,  RoomInfos.TILE_HEIGHT * 5);
			StdDraw.picture(0.3, 1 - 0.5 - (posXCross * 0.06), ImagePaths.SELECTOR_CROSS , 0.016*2, 0.016*2);
			StdDraw.picture(0.59, 0.58, soundImage , 0.046 * 2.4, 0.022 * 2.4);
		}
		else if (blackScreenCooldown != 0) {
			if (blackScreenCooldown >= 10) {
				StdDraw.setPenColor(new Color(0, 0, 0, 50));
				StdDraw.filledRectangle(0, 0, 1, 1);
			}
			else if (blackScreenCooldown <= 8) {
				StdDraw.clear();
				drawGameObjects();
				previousScreenColor = new Color(0,0,0, previousScreenColor.getAlpha() - 25);
				StdDraw.setPenColor(previousScreenColor);
				StdDraw.filledRectangle(0, 0, 1, 1);
				return;
			}
			else previousScreenColor = new Color(0, 0, 0, 250);
		}
		else if (console != null) {
			drawGameObjects();
			console.drawConsole();
		}
		
		else drawGameObjects();
	}
	
	/**
	 * Draws all the GameObjects from the Room if the Hero is still alive. 
	 * Otherwise it prints the losing screen.
	 */
	private void drawGameObjects()
	{
		StdDraw.clear();
		currentRoom.drawRoom();
		if (showMap) drawMap();
	}

	/*
	 * Keys processing
	 */

	/**
	 * Checks if a key pressed by the player matches with a control or not. 
	 * If that's the case it will do whatever function the key is assigned to.
	 */
	private void processKeysForMovement()
	{
		if (console != null) {
			if (StdDraw.isKeyPressed(Keybinding.keycodeOf(SpecialKeys.ESCAPE)) ) {
				console = null;
				keycooldown = 10;
				return;
			}
			if (keycooldown == 0) {
				console.processKeysForMovement();
				keycooldown = 5;
			}
			return;
		}

		char c = '';
		if (StdDraw.hasNextKeyTyped()) c = StdDraw.nextKeyTyped();
		
		if (controlMenu && keyChange != -1) {
			int i = 0;
			
			for (String s: controls.keySet()) {
				if (i == keyChange && StdDraw.keyPressed() != -1) {
					controls.put(s, StdDraw.keyPressed());
					keyChange = -1;
				}
				i++;
			}
		}
		if (StdDraw.isKeyPressed( controls.get("Walk Up :")))
		{
			if (!paused) hero.goUpNext();
		}
		if (StdDraw.isKeyPressed( controls.get("Walk Down :")))
		{
			if (!paused) hero.goDownNext();
		}
		if (StdDraw.isKeyPressed( controls.get("Walk Right :")))
		{
			if (!paused) hero.goRightNext();
		}
		if (StdDraw.isKeyPressed( controls.get("Walk Left :")))
		{
			if (!paused) hero.goLeftNext();
		}
		
//		shoot
		if (StdDraw.isKeyPressed( controls.get("Shoot Left / Menu Left :"))) 
		{
			if (!paused) hero.attack(new Vector2(-1, 0), this.currentRoom);
		}
		if (StdDraw.isKeyPressed( controls.get("Shoot Right / Menu Right :"))) 
		{
			if (!paused) hero.attack(new Vector2(1, 0), this.currentRoom);
		}
		if (StdDraw.isKeyPressed( controls.get("Shoot Up / Menu Up :"))) 
		{
			if (!paused) hero.attack(new Vector2(0, 1), this.currentRoom);
			else {
				if (posXCross > 0 && keycooldown == 0) {
					posXCross -= 1;
					keycooldown = 10;
				}
			}
		}
		if (StdDraw.isKeyPressed( controls.get("Shoot Down / Menu Down :"))) 
		{
			if (!paused) hero.attack(new Vector2(0, -1), this.currentRoom);
			else {
				if (posXCross < 3 && keycooldown == 0) {
					posXCross += 1;
					keycooldown = 10;
				}
			}
		}
		
		if (StdDraw.isKeyPressed( controls.get("Use Bomb :")) && hero.getBomb() > 0) {
			if (keycooldown == 0) {
				hero.setBomb(hero.getBomb() - 1);
				currentRoom.addListBomb(new Bomb(hero.getPosition(), currentRoom, 20, hero.getDamage()));
				keycooldown = 5;
			}
		}
		
		if (StdDraw.isKeyPressed( controls.get("Use First Item :"))) {
			if (keycooldown == 0) {
				hero.useItem(0);
				keycooldown = 5;
			}
		}
		
		if (StdDraw.isKeyPressed( controls.get("Use Second Item :"))) {
			if (keycooldown == 0) {
				hero.useItem(1);
				keycooldown = 5;
			}
		}
		
		if (StdDraw.isKeyPressed( controls.get("Confirm :") ))
		{
			if (win || hero.getDead()) stop = true;
			if (posXCross == 1) controlMenu = true;
			if (posXCross == 2) paused = false;
			if (posXCross == 3) saveMenu = true;
			
		}
		if ((StdDraw.isKeyPressed(controls.get("Pause :"))  || StdDraw.isKeyPressed(Keybinding.keycodeOf(SpecialKeys.ESCAPE)) ) && !paused) {
			if (keycooldown == 0) {
				paused = true;
				keycooldown = 5;
			}
		}
		
		if (StdDraw.isKeyPressed(controls.get("Drop First Item :"))) {
			if (keycooldown == 0) {
				GroundItem gi = hero.dropInventory(0);
				if (gi != null) currentRoom.addListGroundItem(gi);
				keycooldown = 20;
			}
		}
		
		if (StdDraw.isKeyPressed(controls.get("Drop Second Item :"))) {
			if (keycooldown == 0) {
				GroundItem gi = hero.dropInventory(1);
				if (gi != null) currentRoom.addListGroundItem(gi);
				keycooldown = 20;
			}
		}
		
		if (StdDraw.isKeyPressed(controls.get("Map :"))) {
			if (keycooldown == 0) {
				showMap = !showMap;
				keycooldown = 10;
			}
		}
		if (StdDraw.isKeyPressed(Keybinding.keycodeOf('k'))) {
			if (keycooldown == 0) {
				currentRoom.killAllMonster();
				keycooldown = 5;
			}
		}
		
		if (c == 'o' && hero.getMoney() < HeroInfos.MAX_COIN) {
			if (keycooldown == 0) {
				hero.setMoney(hero.getMoney() + 10 );
				keycooldown = 5;
			}
		}
		
		if (c == 'h') {
			if (keycooldown == 0) {
				hero.loseHealth(-1);
				keycooldown = 5;
			}
		}
		
		if (c == 'j') {
			if (keycooldown == 0) {
				hero.setInvincible(!hero.getInvincible());
				keycooldown = 5;
			}
		}
		
		if (c == 'l') {
			if (keycooldown == 0) {
				double speed = HeroInfos.ISAAC_SPEED;
				if (hero instanceof Gaper) speed = HeroInfos.GAPER_SPEED;
				else if (hero instanceof Magdalene) speed = HeroInfos.MAGDALENE_SPEED;
				if (hero.getSpeed() == speed) hero.setSpeed(0.04);
				else hero.setSpeed(speed);
				keycooldown = 5;
			}
		}
		
		if (c == 'p') {
			if (keycooldown == 0) {
				double damage = HeroInfos.ISAAC_DAMAGE;
				if (hero instanceof Gaper) damage = HeroInfos.GAPER_DAMAGE;
				else if (hero instanceof Magdalene) damage = HeroInfos.MAGDALENE_DAMAGE;
				
				if (hero.getDamage() == damage) hero.setDamage(1000);
				else hero.setDamage(damage);
				keycooldown = 5;
			}
		}
		
		if (c == 't') {
			if (keycooldown == 0) {
				console = new GameConsole(currentRoom);
				keycooldown = 5;
			}
		}
		
		
		
//		F2 key
		if (StdDraw.isKeyPressed(113)) {
			if (keycooldown == 0) {
				LocalDateTime now = LocalDateTime.now();
				StdDraw.save("res/screenshots/" + now.getYear() + "-" + now.getMonthValue() + "-" + now.getDayOfMonth() +
					"_" + now.getHour() + "." + now.getMinute() + "." + now.getSecond() + ".png");
				keycooldown = 5;
			}
		}
		
		if (StdDraw.isKeyPressed( controls.get("Interact :"))) {
			if (keycooldown == 0) {
				hero.setInteract(true);
				keycooldown = 5;
			}
		}
		
		
		if (StdDraw.isMousePressed()) {
			double x = StdDraw.mouseX();
			double y = StdDraw.mouseY();
			double width = 0.01;
			if (paused && !controlMenu && !saveMenu) {
				if (soundValue != 0 && y >= 0.555 && y <= 0.593 && x >= 0.55 + width *0 && x<= 0.55 + width *1) {
					soundValue = 0;
					soundImage = ImagePaths.SOUND_BAR_1;
				}
				else if (soundValue != 1 && y >= 0.555 && y <= 0.593 && x >= 0.55 + width *1 && x<= 0.55 + width *2) {
					soundValue = 1;
					soundImage = ImagePaths.SOUND_BAR_2;
				}
				else if (soundValue != 2 && y >= 0.555 && y <= 0.593 && x >= 0.55 + width *2 && x<= 0.55 + width *3) {
					soundValue = 2;
					soundImage = ImagePaths.SOUND_BAR_3;
				}
				else if (soundValue != 3 && y >= 0.555 && y <= 0.593 && x >= 0.55 + width *3 && x<= 0.55 + width *4) {
					soundValue = 3;
					soundImage = ImagePaths.SOUND_BAR_4;
				}
				else if (soundValue != 4 && y >= 0.555 && y <= 0.593 && x >= 0.55 + width *4 && x<= 0.55 + width *5) {
					soundValue = 4;
					soundImage = ImagePaths.SOUND_BAR_5;
				}
				else if (soundValue != 5 && y >= 0.555 && y <= 0.593 && x >= 0.55 + width *5 && x<= 0.55 + width *6 + 0.01) {
					soundValue = 5;
					soundImage = ImagePaths.SOUND_BAR_6;
				}
				else if (soundValue != 6 && y >= 0.555 && y <= 0.593 && x >= 0.55 + width *6 + 0.01 && x<= 0.55 + width *7 + 0.01) {
					soundValue = 6;
					soundImage = ImagePaths.SOUND_BAR_7;
				}
			}
			else if (controlMenu) {
				if (x >= 0.31 && x <= 0.68 && y >= 0.018 && y <= 0.08) {
					Data.setControls(controls);
					controlMenu = false;
				}
				
				double espace = 0.047;
				for (int i = 0; i < controls.keySet().size(); i++) {
					if (x >= 0.51 && x <= 0.81 && y <= (0.9 - i * espace) && y >= ( (0.9 - 0.044) - i * espace )) {
						keyChange = i;
					}
				}
			}
			else if (saveMenu) {
				if (x >= 0.35 && x <= 0.475 && y >= 0.49 && y <= 0.56) {
//					if touch button save
					paused = false;
					saveMenu = false;
					posXCross = 2;
					Data.saveGameWorld(this, saveName);
					stop = true;
				}
				else if(x >= 0.52 && x <= 0.64 && y >= 0.48 && y <= 0.56) {
//					if touch button not save
					stop = true;
				}
				else if (x >= 0.37 && x <= 0.62 && y >= 0.38 && y <= 0.46) {
//					if touch button save
					saveMenu = false;
				}
			}
		}
	}
}
