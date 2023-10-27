package gameloop;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.LinkedHashMap;

import gameWorld.GameWorld;
import gameobjects.nonstaticobjects.Hero;
import gameobjects.nonstaticobjects.heros.Gaper;
import gameobjects.nonstaticobjects.heros.Isaac;
import gameobjects.nonstaticobjects.heros.Magdalene;
import libraries.StdDraw;
import libraries.Timer;
import libraries.addedByUs.Data;
import libraries.addedByUs.Music;
import libraries.addedByUs.Utils;
import resources.DisplaySettings;
import resources.HeroInfos;
import resources.ImagePaths;
import resources.RoomInfos;
import resources.SoundPaths;

/**
 * This class is the Game menu, used to create the game itself. 
 * After a game is created with the class constructor, you can play it by using the launch function.
 * 
 * <p>
 * This class uses a lot of boolean to check what page is currently showing. 
 * And a integer clickCooldown to prevent the player to accidently double click.
 */
public class MainMenu {

	private boolean stop = false;
	private boolean menu = true;
	private boolean load = false;
	private boolean newGame = false;
	private boolean control = false;
	
	private boolean selectCharacter = false;
	private int characterPage = 0;
	
	private int worldSave;
	private int clickCooldown;
	
	private LinkedHashMap<String, Integer> controls;
	private int keyChange = -1;
	Music m = new Music(SoundPaths.MainMusic);
	
	/**
	 * Constructs a new Game. And Initializes the display window.
	 */
	public MainMenu() {
		menu = true;
		initializeDisplay();
		m.changeVolume(-15f);
		m.play();
	}
	
	/**
	 * Launches the game
	 */
	public void launch() {
		Data.Check();
		controls = Data.getControls();
	}
	
	/**
	 * Process the next Game action. And decreases the clickCooldown.
	 */
	public void processGameStep() {
		processGameInput();
		if (clickCooldown > 0) clickCooldown--;
		
	}
	
	/**
	 * Processes the keys presses by the player. 
	 * Draws the window and waits to maintain a constant fps.
	 */
	private void processGameInput() {
		Timer.beginTimer();
		processKeys();
		drawWindow();
		StdDraw.show();
		Timer.waitToMaintainConstantFPS();
	}
	
	/**
	 * Draws the appropriate window, it can either be controls, new game, load game, or the main menu.
	 */
	private void drawWindow() {
//		control editor
		if (control) {
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
//		hero selector
		else if (selectCharacter) {
			StdDraw.clear(new Color(150,150,150));
			StdDraw.picture(0.5, 0.5, ImagePaths.SELECT_HERO_GUI, 0.8, 0.8);
			String heroImage = ImagePaths.ISAAC;
			String heroName = "ISAAC";
			switch (characterPage) {
				case 0: {
					heroImage = ImagePaths.ISAAC;
					heroName = "ISAAC";
					break;
				}
				case 1: {
					heroImage = ImagePaths.MAGDALENE;
					heroName = "MAGDALENE";
					break;
				}
				case 2: {
					heroImage = ImagePaths.GAPER;
					heroName = "GAPER";
					break;
				}
				default: break;
			}
			StdDraw.setFont(new Font("Candara", Font.BOLD, 30));
			StdDraw.text(0.5, 0.53, heroName); 
			StdDraw.picture(0.5, 0.62, ImagePaths.FRONT_LEG_0, HeroInfos.HERO_SIZE.getX(), HeroInfos.HERO_SIZE.getY());
			StdDraw.picture(0.5, 0.62, heroImage, HeroInfos.HERO_SIZE.getX(), HeroInfos.HERO_SIZE.getY());
		}
//		new game/ load
		else if (newGame || load) {
			StdDraw.clear(new Color(150,150,150));
			StdDraw.picture(0.5, 0.4, ImagePaths.LOAD_OR_NEWGAME_GUI,0.8,0.8);

			StdDraw.setFont(new Font("Candara", Font.BOLD, 17));
			StdDraw.setPenColor(new Color(69,63,63));
			if (newGame) StdDraw.text(0.5, 0.7, "Click where you want the game to be saved");
			if (load) StdDraw.text(0.5, 0.7, "Click on the game you want to load");
			String game1 = "Empty";
			if (new File("res/saves/game1.txt").exists() ) game1 = "Game 1";
			StdDraw.text(0.295, 0.6, game1);
			String game2 = "Empty";
			if (new File("res/saves/game2.txt").exists() ) game2 = "Game 2";
			StdDraw.text(0.7, 0.6, game2);
			String game3 = "Empty";
			if (new File("res/saves/game3.txt").exists() ) game3 = "Game 3";
			StdDraw.text(0.295, 0.475, game3);
			String game4 = "Empty";
			if (new File("res/saves/game4.txt").exists() ) game4 = "Game 4";
			StdDraw.text(0.7, 0.475, game4);
		}
//		main menu
		else {
			StdDraw.clear();
			StdDraw.picture(0.5, 0.5, ImagePaths.MAIN_MENU, 1, 1);
			StdDraw.save("menu.png");
		}
	}
	
	/**
	 * Creates a new GameWorld and plays it. Also plays the Hero wanted by the player.
	 * @param i the game number used to save the game
	 */
	private void playWorld(int i) {
		Hero hero = new Isaac();
		switch (characterPage) {
			case 1: {
				hero = new Magdalene();
				break;
			}
			case 2: {
				hero = new Gaper();
				break;
			}
			default: break;
		}
		GameWorld world = new GameWorld(hero, "game" + i, 5);
		worldLoop(world);
	}
	
	
	/**
	 * Plays a world from a file.
	 * @param save the name of the file.
	 */
	private void playLoadedWorld(String save) {
		GameWorld world = Data.loadGameWorld(save);
		worldLoop(world);
	}
	
	/**
	 * Loops the game played until the game is stopped.
	 * @param world
	 */
	private void worldLoop(GameWorld world) {
		load = false;
		newGame = false;
		selectCharacter = false;
		m.pause();
		while(! world.gameOver()) {
			processNextStep(world);
		}
		m.resumeAudio();
	}
	
	/**
	 * Processes the input from the player and updates the gameWorld.
	 * @param world the gameWorld.
	 */
	private void processNextStep(GameWorld world)
	{
		Timer.beginTimer();
		world.processUserInput();
		world.updateGameWorld();
		world.updateMusic();
		StdDraw.show();
		Timer.waitToMaintainConstantFPS();
	}

	
	/**
	 * initialize the window size and enables the animation doubleBuffering.
	 */
	private static void initializeDisplay()
	{
		// Set the window's size, in pixels.
		// It is strongly recommended to keep a square window.
		StdDraw.setCanvasSize((RoomInfos.NB_TILES) * DisplaySettings.PIXEL_PER_TILE,
				(RoomInfos.NB_TILES + 1) * DisplaySettings.PIXEL_PER_TILE) ;

		// Enables double-buffering.
		// https://en.wikipedia.org/wiki/Multiple_buffering#Double_buffering_in_computer_graphics
		StdDraw.enableDoubleBuffering();
	}
	
	
	/**
	 * processes the Players key and click input.
	 */
	private void processKeys()
	{
//		if player is changing keys from a specific action
		if (control && keyChange != -1) {
			int i = 0;
			
			for (String s: controls.keySet()) {
				if (i == keyChange && StdDraw.keyPressed() != -1) {
					controls.put(s, StdDraw.keyPressed());
					keyChange = -1;
				}
				i++;
			}
		}
		
		if (StdDraw.isMousePressed()) {
			double x = StdDraw.mouseX();
			double y = StdDraw.mouseY();
			if (control) {
				if (x >= 0.31 && x <= 0.68 && y >= 0.018 && y <= 0.08) {
					Data.setControls(controls);
					control = false;
				}
				double espace = 0.047;
				for (int i = 0; i < controls.keySet().size(); i++) {
					if (x >= 0.51 && x <= 0.81 && y <= (0.9 - i * espace) && y >= ( (0.9 - 0.044) - i * espace )) {
						keyChange = i;
					}
				}
			}
			else if (selectCharacter) {
//				play game button
				if (x >= 0.255 && x <= 0.74 && y >= 0.333 && y <= 0.43) {
					playWorld(worldSave);
				}
//				right arrow on selection
				else if (x >= 0.754 && x <= 0.837 && y >= 0.606 && y <= 0.666 && clickCooldown == 0) {
					if (characterPage < 2) characterPage++;
					else characterPage = 0;
					clickCooldown = 7;
				}
//				left arrow on selection
				else if (x >= 0.142 && x <= 0.23 && y >= 0.61 && y <= 0.667 && clickCooldown == 0) {
					if (characterPage > 0) characterPage--;
					else characterPage = 2;
					clickCooldown = 7;
				}
//				back button
				else if (x >= 0.37 && x <= 0.613 && y >= 0.203 && y <= 0.307) {
					characterPage = 0;
					selectCharacter = false;
					newGame = false;
				}
			}
//			loading a game or creating a new one
			else if (newGame || load) {
//				game1
				if (x >= 0.179 && x <= 0.417 && y >= 0.577 && y <= 0.623) {
					worldSave = 1;
					if (load && new File("res/saves/game1.txt").exists()) playLoadedWorld("game1");
					else if (newGame) selectCharacter = true;
				}
//				game2
				else if (x >= 0.179 && x <= 0.417 && y >= 0.452 && y <= 0.495) {
					worldSave = 2;
					if (load && new File("res/saves/game2.txt").exists()) playLoadedWorld("game2");
					else if (newGame) selectCharacter = true;
				}
//				game3
				else if (x >= 0.583 && x <= 0.817 && y >= 0.577 && y <= 0.623) {
					worldSave = 3;
					if (load && new File("res/saves/game3.txt").exists()) playLoadedWorld("game3");
					else if (newGame) selectCharacter = true;
				}
//				game4
				else if (x >= 0.583 && x <= 0.817 && y >= 0.452 && y <= 0.495) {
					worldSave = 4;
					if (load && new File("res/saves/game4.txt").exists()) playLoadedWorld("game4");
					else if (newGame) selectCharacter = true;
				}
				
//				back button
				else if (x >= 0.37 && x <= 0.608 && y >= 0.22 && y <= 0.324) {
					load = false;
					newGame = false;
				}
			}
			
//			main menu
			else if (menu) {
				if (x >= 0.23 && x <= 0.694 && y >= 0.613 && y <= 0.703) {
					newGame = true;
				}
				else if (x >= 0.217 && x <= 0.69 && y >= 0.478 && y <= 0.561) {
					load = true;
				}
				else if (x >= 0.198 && x <= 0.572 && y >= 0.324 && y <= 0.41) {
					control = true;
				}
				else if (x >= 0.174 && x <= 0.704 && y >= 0.193 && y <= 0.278) {
					m.stop();
					stop = true;
				}
			}
		}
	}

	
	/**
	 * Returns the stop state of the MainMenu.
	 * @return {@code true} if the menu should be stopped, {@code false} otherwise.
	 */
	public boolean getStop() {
		return stop;
	}
}
