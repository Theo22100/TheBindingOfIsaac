package libraries.addedByUs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Scanner;

import gameWorld.GameWorld;

/**
 * The Data Class is used to save, load and read data from files. 
 * 
 * For example in this game it's used to load/save the controls, but also the GameWorld.
 */
public class Data {

	@SuppressWarnings("serial")
	public static final LinkedHashMap<String,Integer> defaultControl = new LinkedHashMap<String,Integer>() {
	{ put("Walk Up :", 90); put("Walk Down :", 83); put("Walk Left :", 81); put("Walk Right :", 68); 
	put("Shoot Up / Menu Up :", 38); put("Shoot Down / Menu Down :", 40); put("Shoot Left / Menu Left :", 37); put("Shoot Right / Menu Right :", 39);
	put("Use Bomb :", 66); put("Use First Item :", 85); put("Use Second Item :", 73); put("Drop First Item :", 49); put("Drop Second Item :", 50);
	put("Interact :", 32); put("Pause :", 27); put("Map :", 77); put("Confirm :", 10);} 
	};
	
	/**
	 * Checks if all the files used to store data exists. 
	 * If they don't it creates the said files.
	 */
	public static void Check() {
		File binding = new File("res");
		File saves = new File("res/saves");
		File screenshots = new File("res/screenshots");
		File controls = new File("res/controls.txt");
		if (!binding.exists()) binding.mkdir();
		if(!saves.exists()) saves.mkdir();
		if(!screenshots.exists()) screenshots.mkdir();
		if (!controls.exists())
			try {
				controls.createNewFile();
				PrintWriter pw;
				try {
					pw = new PrintWriter(controls);
					for (String s: defaultControl.keySet()) {
						pw.println(s + defaultControl.get(s));
					}
					pw.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Saves the GameWorld onto a txt whose name is specified by the String argument.
	 * 
	 * @param gameWorld the GameWorld that will be saved
	 * @param name the name of the .txt file
	 */
	public static void saveGameWorld(GameWorld gameWorld, String name) {
		try {
			FileOutputStream f = new FileOutputStream(new File("res/saves/" + name + ".txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);
//			writes the object onto the name .txt file
			o.writeObject(gameWorld);
			
			o.close();
			f.close();
			
		} catch( Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns a new GameWorld by reading the data stored from the name .txt file.
	 * @param name the name of the file
	 * @return the new GameWorld read from the file.
	 */
	public static GameWorld loadGameWorld (String name) {
		GameWorld gameWorld = null;
		try {
			FileInputStream f = new FileInputStream(new File("res/saves/" + name + ".txt"));
			ObjectInputStream o = new ObjectInputStream(f);
			
			gameWorld = (GameWorld) o.readObject();
			
			o.close();
			f.close();
		} catch( Exception e) {
			e.printStackTrace();
		}
		return gameWorld;
	}
	
	/**
	 * Saves the Controls associated by the player into the res/controls.txt file.
	 * <p>
	 * keyCodeList is a LinkedHashMap whose key is an action for example "Use Bomb:". 
	 * And whose value is the integer value of the keyboard key.
	 * 
	 * @param keyCodeList the action linked to the value of a key
	 */
	public static void setControls(LinkedHashMap<String,Integer> keyCodeList) {
		File controls = new File("res/controls.txt");
		PrintWriter pw;
		try {
			pw = new PrintWriter(controls);
			for (String s: keyCodeList.keySet()) {
//				writes the value of the key onto the .txt file
				pw.println(s + keyCodeList.get(s));
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the Controls associated by the player into the res/controls.txt file.
	 * <p>
	 * keyCodeList is a LinkedHashMap whose key is an action for example "Use Bomb:". 
	 * And whose value is the integer value of the keyboard key.
	 * 
	 * @return the LinkedHashMap associated to action linked to the value of a key.
	 */
	public static LinkedHashMap<String,Integer> getControls() {
		File controls = new File("res/controls.txt");
		LinkedHashMap<String,Integer> controlsKey = new LinkedHashMap<String,Integer>();
		Scanner sc;
		try {
			sc = new Scanner(controls);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (line.contains(":")) {
//					gets all the keyCode from the file
					int keyCode = Integer.parseInt( line.substring(line.indexOf(':') + 1) );
//					gets all the Action from linked to the KeyCode from the file
					String action = line.substring(0, line.indexOf(':') + 1 );
					controlsKey.put(action , keyCode);
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return controlsKey;
	}
}
