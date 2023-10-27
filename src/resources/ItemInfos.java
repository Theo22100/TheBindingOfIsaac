package resources;

import gameobjects.Item;
import libraries.Vector2;

public class ItemInfos {
	public static Vector2 BOMBE_SIZE = new Vector2(0.02 * 2 ,0.021 * 2);
	public static Item BOMBE_ITEM =  new Item(ImagePaths.BOMB, 1, "bomb", BOMBE_SIZE, false);
	
	public static Vector2 HP_UP_SIZE = new Vector2(0.032 *2, 0.032 * 2);
	public static Item HP_UP_ITEM = new Item(ImagePaths.HP_UP, 1, "hp_up", HP_UP_SIZE, true);
//	DONNE UN COEUR PLEIN
	
	public static Vector2 BLOOD_OF_THE_MARTHYR_SIZE = new Vector2(0.032 *2, 0.032 * 2);
	public static Item BLOOD_OF_THE_MARTHYR_ITEM = new Item(ImagePaths.BLOOD_OF_THE_MARTYR, 1, "blood_of_the_marthyr", BLOOD_OF_THE_MARTHYR_SIZE, false);
	public static double BLOOD_OF_THE_MARTHYR_ITEM_ADDED_DAMAGE = 1;
	
	
	public static Vector2 JESUS_JUICE_SIZE = new Vector2(0.032 *2, 0.032 * 2);
	public static Item JESUS_JUICE_ITEM = new Item(ImagePaths.JESUS_JUICE, 1, "jesus_juice", JESUS_JUICE_SIZE, true);
	public static double JESUS_JUICE_ADDED_DAMAGE = 0.5;
	public static double JESUS_JUICE_ADDED_RANGE = 0.25;
	
	public static Vector2 LUNCH_SIZE = new Vector2(0.032 *2, 0.032 * 2);
	public static Item LUNCH_ITEM = new Item(ImagePaths.LUNCH, 1, "lunch", LUNCH_SIZE, true);
//	DONNE UN COEUR VIDE
	
	public static Vector2 MAGIC_MUSHROOM_SIZE = new Vector2(0.032 *2, 0.032 * 2);
	public static Item MAGIC_MUSHROOM_ITEM = new Item(ImagePaths.MAGIC_MUSHROOM, 1, "magic_mushroom", MAGIC_MUSHROOM_SIZE, true);
	public static double MAGIC_MUSHROOM_ADDED_DAMAGE = 1;
	public static double MAGIC_MUSHROOM_ADDED_RANGE = 5;
	public static double MAGIC_MUSHROOM_ADDED_ATTACK_SPEED = 0.3;
//	REMPLIE TOUT LES COEURS
	
	
//	DONNE UN COEUR
	public static Vector2 PENTAGRAM_SIZE = new Vector2(0.032 *2, 0.032 * 2);
	public static Item PENTAGRAM_ITEM = new Item(ImagePaths.PENTAGRAM, 1, "pentagram", PENTAGRAM_SIZE, true);
	public static double PENTAGRAM_ADDED_DAMAGE = 1;
	
	public static Vector2 STIGMATA_SIZE = new Vector2(0.032 * 2, 0.032 * 2);
	public static Item STIGMATA_ITEM = new Item(ImagePaths.STIGMATA, 1, "stigmata", STIGMATA_SIZE, false);
	public static double STIGMATA_ADDED_DAMAGE = 0.3;
}
