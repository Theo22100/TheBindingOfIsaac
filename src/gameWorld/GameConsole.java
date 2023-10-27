package gameWorld;

import java.awt.Color;

import gameWorld.rooms.Room;
import gameobjects.nonstaticobjects.monsters.Clotty;
import gameobjects.nonstaticobjects.monsters.DoubleRedFly;
import gameobjects.nonstaticobjects.monsters.Fly;
import gameobjects.nonstaticobjects.monsters.Monster;
import gameobjects.nonstaticobjects.monsters.RedFly;
import gameobjects.nonstaticobjects.monsters.Spider;
import gameobjects.nonstaticobjects.monsters.Vis;
import gameobjects.nonstaticobjects.monsters.boss.Gurdy;
import gameobjects.nonstaticobjects.monsters.boss.Lamb;
import gameobjects.nonstaticobjects.monsters.boss.Larry;
import gameobjects.nonstaticobjects.monsters.boss.Loki;
import gameobjects.nonstaticobjects.monsters.boss.MiniSteven;
import gameobjects.nonstaticobjects.monsters.boss.Steven;
import libraries.StdDraw;
import libraries.Vector2;

/**
 * The GameConsole is used to cheat by typing a command using key words. The Game Console is 
 * linked to a room as it needs to know where to used certain command.
 * <b>The following key words are the current command available : </b>
 * <p>
 * heal
 * <p>
 * killall
 * <p>
 * spawn &lt;Monster&gt;
 * <p>
 * spawn &lt;Monster&gt; &lt;posX&gt; &lt;posY&gt;
 * <p>
 * set &lt;speed&gt; &lt;value&gt;
 * <p>
 * set &lt;health&gt; &lt;value&gt;
 * 
 */
public class GameConsole {

	
	String text = "";
	String result = "";
	String previousCommand = "";
	Room room;
	
	/**
	 * Constructs a new GameConsole whose room is specified by the {@link Room} argument.
	 * @param r the room that the console is linked to
	 */
	public GameConsole(Room r) {
		this.room = r;
	}
	
	/**
	 * Draws the console with the current and previous commands.
	 */
	public void drawConsole() {
		StdDraw.setPenColor(new Color(0, 0, 0, 100));
		StdDraw.filledRectangle(0, 0, 1, 0.15);
		StdDraw.setPenColor(new Color(190, 190, 190, 100));
		StdDraw.textLeft(0.05, 0.04, "> " + text);
		StdDraw.textLeft(0.05, 0.08, "> " + result);
		StdDraw.textLeft(0.05, 0.11, "> " + previousCommand);
	}
	
	/**
	 * Returns a monster matching the name of the s String.
	 * @param s the name of the monster
	 * @return a monster matching the name of the s String.
	 */
	private Monster monsterFromString(String s) {
		if (s.equals("fly")) return new Fly(new Vector2(), room);
		if (s.equals("spider")) return new Spider(new Vector2(), room);
		if (s.equals("clotty")) return new Clotty(new Vector2(), room);
		if (s.equals("vis")) return new Vis(new Vector2(), room);
		if (s.equals("redfly")) return new RedFly(new Vector2(), room);
		if (s.equals("doubleredfly")) return new DoubleRedFly(new Vector2(), room);
		if (s.equals("gurdy")) return new Gurdy(new Vector2(), room);
		if (s.equals("lamb")) return new Lamb(new Vector2(), room);
		if (s.equals("locki")) return new Loki(new Vector2(), room);
		if (s.equals("Steven")) return new Steven(new Vector2(), room);
		if (s.equals("ministeven")) return new MiniSteven(new Vector2(), room, 0.009);
		if (s.equals("larry")) return new Larry(new Vector2(), room, 4);
		return null;
	}
	
	/**
	 * Checks if the entered text matches a command. If it does 
	 * it goes through the command function.
	 */
	private void execute() {
		text = text.toLowerCase();
		System.out.println("commande : " + text);
		String[] keyword = text.split(" ");
		
//		used to recognize where a double is typed
		String doublePattern = "\\d\\.\\d++";
		
		if (keyword.length > 0) {
			if (keyword[0].equals("spawn")) {
				if (keyword.length >= 2) {
					Monster m = monsterFromString(keyword[1]);
					if (m == null) {
						result = "unknown monster";
						return;
					}
					Vector2 position = room.getHero().getPosition();
					if (keyword.length == 4) {
						if (keyword[2].matches(doublePattern) && keyword[3].matches(doublePattern)) {
							double x = Double.parseDouble(keyword[2]);
							double y = Double.parseDouble(keyword[3]);
							if (m.legalPosition(new Vector2(x,y))) position = new Vector2(x,y);
						}
					}
					m.setPosition(position);
					room.addListMonster(m);
					room.addListNonStaticObject(m);
					result = "new " + keyword[1] + " spawned at the (" + position.getX() + " ; " + position.getY() + " ) coordinate";
				}
			}
			else if (keyword[0].equals("heal")) {
				result = "hero healed";
				room.getHero().setHealth(room.getHero().getMaxHealth());
				
			}
			else if (keyword[0].equals("killall")) {
				result = "all monster killed";
				room.killAllMonster();
			}
			if (keyword[0].equals("set")) {
				if (keyword.length >= 3) {
					if (keyword[1].equals("speed")) {
						if (keyword[2].matches(doublePattern)) {
							room.getHero().setSpeed(Double.parseDouble(keyword[2]));
							result = "hero speed set to " + Double.parseDouble(keyword[2]);
						}
						else result = "speed argument not valid";
					}
					
					if (keyword[1].equals("health")) {
						if (keyword[2].matches("\\d++")) {
							room.getHero().setHealth(Integer.parseInt(keyword[2]));
							result = "hero health set to " + Integer.parseInt(keyword[2]);
						}
						else result = "health argument not valid";
					}
				}
			}
			else result = "unknown command";
		}
		previousCommand = text;
		text = "";
	}
	
	/**
	 * Checks if a key pressed by the player matches with a control or not. 
	 * If that's the case it will do whatever function the key is assigned to.
	 */
	public void processKeysForMovement()
	{
//		TAB
		if (StdDraw.isKeyPressed(9)) {
		}
//		ENTER
		else if (StdDraw.isKeyPressed(10)) {
			if (!text.equals("")) execute();
			text = "";
		}
		
		else if (StdDraw.isKeyPressed(8)) {
			if (text.length() > 0) text = text.substring(0, text.length() - 1);
		}
//		ELSE
		else {
			if (StdDraw.hasNextKeyTyped()) {
				char c = StdDraw.nextKeyTyped();
				if ( c != '' ) text += c;
			}
		}
		
	}
}
