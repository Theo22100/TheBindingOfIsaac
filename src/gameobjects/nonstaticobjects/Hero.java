package gameobjects.nonstaticobjects;


import java.awt.Color;
import java.awt.Font;
import java.util.List;

import gameWorld.rooms.Room;
import gameobjects.GameObject;
import gameobjects.Item;
import gameobjects.nonstaticobjects.grounditem.GroundItem;
import gameobjects.staticobjects.Door;
import libraries.StdDraw;
import libraries.Vector2;
import resources.HeroInfos;
import resources.ImagePaths;
import resources.ItemInfos;


/**
 * A hero is a NonStaticObject, it's the object that will be played by the player. So it uses a lot 
 * of unique variable, such as an integer attackSpeed, that represents how fast he can attack the monsters.
 * <p>
 * There's also the double reach, that's how far the projectile shot by the hero will go.
 * <p>
 * And the last one is the Item array Inventory, used to store every Item the player collects, 
 * the Hero's inventory only has two spaces and the Hero will drop the first item upon collecting 
 * an item if it's inventory's full.
 * <p>
 * It also has three integer variable, money, keys, bomb, used to know how many item of the same name the hero has.
 * <p>
 * They are also four different integer arrays, like itemCooldown, initialItemCooldownValue, itemDuration, initialItemDurationValue, 
 * That are used when an item can be used, to know how long the player has to wait before using it again and how long the effect 
 * will last.
 * <p>
 * There's some variable allocated to animation such as a String orientation to know where the hero is facing and a 
 * String legImagePath that represents the legs of the hero.
 * 
 * To check what the other variable from Hero are please refer to {@link NonStaticObject} and {@link GameObject}.
 * 
 */
public class Hero extends NonStaticObject
{
	private static final long serialVersionUID = 1L;
	
	private int attackSpeed;
	private double reach;
	private int cooldown = 0;
	private Item[] inventory = new Item[2];
	private int[] itemCooldown = new int[2];
	private int[] initialItemCooldownValue = {1,1};
	private int[] itemDuration = new int[2];
	private int[] initialItemDurationValue = {1,1};
	
	private int money = 0;
	private int keys = 0;
	private int bomb = 0;
	private boolean interact = false;
	private List<String> tearAnimationSprite = null;
	private String legImagePath = ImagePaths.FRONT_LEG_0;
	private String orientation = "FRONT";
	private String[] heroImagePaths = new String[4];
	private int staticTimer = 0;
	
	private boolean invincible;
	
	/**
	 * Constructs a new Hero, whose position and size are specified by the {@link Vector2} argument and 
	 * whose room is specified by the {@link Room} argument.
	 * <p>
	 * It also affects the new Hero's maxHealth to same number as the health parameter.
	 * 
	 * @param position the position of the new Hero
	 * @param size the size of the new Hero
	 * @param speed the speed of the new Hero
	 * @param frontImagePath the image of the new Hero facing north
	 * @param backImagePath the image of the new Hero facing south
	 * @param sideImagePath the image of the new Hero facing est 
	 * @param otherSideImagePath the image of the new Hero facing west
	 * @param attackSpeed the speed the new Hero will be attacking
	 * @param reach the distance the projectile, shot by the new Hero, can travel before dying
	 * @param damage the damage done by the new Hero
	 * @param health the health of the new Hero
	 */
	public Hero(Vector2 position, Vector2 size, double speed, String frontImagePath, String backImagePath
			, String sideImagePath, String otherSideImagePath, int attackSpeed, double reach, double damage, double health)
	{
		super(position, size, frontImagePath, null, speed, damage, health);
		heroImagePaths[0] = frontImagePath;
		heroImagePaths[1] = backImagePath;
		heroImagePaths[2] = sideImagePath;
		heroImagePaths[3] = otherSideImagePath;
		this.attackSpeed = attackSpeed;
		this.reach = reach;
		setAnimationState(20);
	}

	
	/**
	 * Updates Hero object movement. Also decreases the cooldowns used to attack, to know whether the Hero is hit, 
	 * to know whether the hero is not walking, and the usable item re-use and duration cooldowns.
	 * <p>
	 * If the itemDuration cooldown is equal to 1 then the item effect is removed.
	 */
	public void updateGameObject()
	{
//		Tears cooldown
		int tmp = cooldown;
		if (tmp !=0) cooldown--;
		if ( !getDirection().equals(new Vector2(0.0, 0.0)) ) {
			move();
			staticTimer = 10;
			animation();
		}
//		timer to check if the hero is not walking
		else if(staticTimer >= 0 ) staticTimer--;
		
		isHitCooldown();
		if (getHealth() <= 0) setDead(true);
		
		if (staticTimer == 0) {
			setAnimationState(20);
			setImagePath( heroImagePaths[0]);
			legImagePath = ImagePaths.FRONT_LEG_0;
		}
		
//		usable item re-use and duration cooldown
		if (itemCooldown[0] != 0) itemCooldown[0]--;
		if (itemCooldown[1] != 0) itemCooldown[1]--;
		if (itemDuration[0] != 0) {
			itemDuration[0]--;
			if (itemDuration[0] == 1) removeItemBonus(0);
		}
		if (itemDuration[1] != 0) {
			itemDuration[1]--;
			if (itemDuration[1] == 1) removeItemBonus(1);
		}
		
		if (interact == true) interact = false;
	}
	
	/**
	 * Moves the Hero by a vector and speed if the position is considered legal. 
	 * It also moves the groundItem by calling {@link Hero#moveGroundItem()}. 
	 * <p>
	 * To see how the position is considered legal check {@link Hero#legalMove(Vector2)}.
	 */
	private void move()
	{
		Vector2 normalizedDirection = getNormalizedDirection();
		Vector2 positionAfterMoving = getPosition().addVector(normalizedDirection);
		if (legalMove(positionAfterMoving)) setPosition(positionAfterMoving);
		this.setDirection(new Vector2());
		
	}
	
	/**
	 * Used to make the legs of the Hero Move.
	 */
	@Override
	public void animation() {
		if (orientation.equals("FRONT") || orientation.equals("BACK")) {
			switch (getAnimationState()) {
			case 20: legImagePath = ImagePaths.FRONT_LEG_0; break;
			case 18: legImagePath = ImagePaths.FRONT_LEG_1; break;
			case 16: legImagePath = ImagePaths.FRONT_LEG_2; break;
			case 14: legImagePath = ImagePaths.FRONT_LEG_3; break;
			case 12: legImagePath = ImagePaths.FRONT_LEG_4; break;
			case 10: legImagePath = ImagePaths.FRONT_LEG_5; break;
			case 8: legImagePath = ImagePaths.FRONT_LEG_6; break;
			case 6: legImagePath = ImagePaths.FRONT_LEG_7; break;
			case 4: legImagePath = ImagePaths.FRONT_LEG_8; break;
			case 2: legImagePath = ImagePaths.FRONT_LEG_9; break;
			case 0: setAnimationState(23); break;
			default: break;
			}
		}
		else if (orientation.equals("SIDE")) {
			switch (getAnimationState()) {
			case 20: legImagePath = ImagePaths.SIDE_LEG_0; break;
			case 18: legImagePath = ImagePaths.SIDE_LEG_1; break;
			case 16: legImagePath = ImagePaths.SIDE_LEG_2; break;
			case 14: legImagePath = ImagePaths.SIDE_LEG_3; break;
			case 12: legImagePath = ImagePaths.SIDE_LEG_4; break;
			case 10: legImagePath = ImagePaths.SIDE_LEG_5; break;
			case 8: legImagePath = ImagePaths.SIDE_LEG_6; break;
			case 6: legImagePath = ImagePaths.SIDE_LEG_7; break;
			case 4: legImagePath = ImagePaths.SIDE_LEG_8; break;
			case 2: legImagePath = ImagePaths.SIDE_LEG_9; break;
			case 0: setAnimationState(23); break;
			default: break;
			}
		}
		else if (orientation.equals("OTHER_SIDE")) {
			switch (getAnimationState()) {
			case 20: legImagePath = ImagePaths.OTHER_SIDE_LEG_0; break;
			case 18: legImagePath = ImagePaths.OTHER_SIDE_LEG_1; break;
			case 16: legImagePath = ImagePaths.OTHER_SIDE_LEG_2; break;
			case 14: legImagePath = ImagePaths.OTHER_SIDE_LEG_3; break;
			case 12: legImagePath = ImagePaths.OTHER_SIDE_LEG_4; break;
			case 10: legImagePath = ImagePaths.OTHER_SIDE_LEG_5; break;
			case 8: legImagePath = ImagePaths.OTHER_SIDE_LEG_6; break;
			case 6: legImagePath = ImagePaths.OTHER_SIDE_LEG_7; break;
			case 4: legImagePath = ImagePaths.OTHER_SIDE_LEG_8; break;
			case 2: legImagePath = ImagePaths.OTHER_SIDE_LEG_9; break;
			case 0: setAnimationState(23); break;
			default: break;
			}
		}
		setAnimationState(getAnimationState() - 1);
	}
	
	/**
	 * Draws the Hero based on it's position, imagePath, size and degree. It draws the Hero's head first and then the legs.
	 * <p>
	 * If a color is specified then it uses {@link StdDraw#picture(double, double, String, double, double, double, Color, int)} 
	 * to draw a transparent filter of the color on top of the GameObject.
	 */
	@Override
    public void drawGameObject()
	{
    	if (getColor() == null) {
			StdDraw.picture(getPosition().getX(), getPosition().getY(), legImagePath, HeroInfos.LEG_SIZE.getX(), HeroInfos.LEG_SIZE.getY(),
					getDegree());
    		StdDraw.picture(getPosition().getX(), getPosition().getY(), getImagePath(), getSize().getX(), getSize().getY(),
				getDegree());
    	} else { 
    		StdDraw.picture(getPosition().getX(), getPosition().getY(), legImagePath, HeroInfos.LEG_SIZE.getX(), HeroInfos.LEG_SIZE.getY(),
				getDegree(),getColor(), getTransparency());
    		StdDraw.picture(getPosition().getX(), getPosition().getY(), getImagePath(), getSize().getX(), getSize().getY(),
				getDegree(),getColor(), getTransparency());
    	}
	}
	
	/**
	 * Checks if the move is legal or not, it checks if the Hero collides with a closed Doors, if it is then the move
	 * is considered not legal otherwise it continues through the other checks from {@link NonStaticObject#legalMove(Vector2)}.
	 * <p>
	 * If it is the move is legal it returns true, otherwise returns false.
	 * 
	 * @return {@code true} if the move is legal, {@code false} otherwise.
	 */
	@Override
	public boolean legalMove(Vector2 position) {
		for (Door d: getRoom().getListDoor()) {
			if (d.getHitBox().collide(position, getHitBox())) {
				if (d.getClosed()) return false;
				return true;
			}
		}
		
		return super.legalMove(position);
	}
	
	/*
	 * Moving from key inputs. Direction vector is later normalized.
	 * it also changes the ImagePah so that it matches the correct orientation
	 */
	
	/**
	 * Adds one to the direction vector, 
	 * also changes the image to match the direction.
	 */
	public void goUpNext()
	{
		getDirection().addY(1);
		setImagePath(heroImagePaths[1]);
		orientation = "BACK";
	}
	
	/**
	 * Adds one to the direction vector, 
	 * also changes the image to match the direction.
	 */
	public void goDownNext()
	{
		getDirection().addY(-1);
		setImagePath(heroImagePaths[0]);
		orientation = "FRONT";
	}
	
	/**
	 * Adds one to the direction vector, 
	 * also changes the image to match the direction.
	 */
	public void goLeftNext()
	{
		getDirection().addX(-1);
		setImagePath(heroImagePaths[3]);
		orientation = "OTHER_SIDE";
		
	}
	
	/**
	 * Adds one to the direction vector, 
	 * also changes the sprite to match the direction.
	 */
	public void goRightNext()
	{
		getDirection().addX(1);
		setImagePath(heroImagePaths[2]);
		orientation = "SIDE";
	}
	
	/**
	 * Checks if the Hero can attack (cooldown is equal to 0). If it is, 
	 * it creates a tear with the direction from the parameter at the Hero coordinates, 
	 * and then sets the attackCooldown back to the attackSpeed.
	 * 
	 * <p>
	 * To check how the Tear is created go to {@link Tear}
	 * 
	 * @param direction the direction which the tear will go
	 * @param room the room the tear will be in
	 */
	public void attack(Vector2 direction, Room room) {
		if (cooldown != 0 ) return;
		new Tear(direction,this.getPosition(),this.getDamage(),reach, room, 0.02, this,ImagePaths.TEAR, tearAnimationSprite);
		cooldown = attackSpeed;
	}
	
	/**
	 * Adds an item into the Hero inventory, if the Item as the name "money", "key" or "bomb" then it will be added in the hero variable of the same name.
	 * <p>
	 * Otherwise it adds the item in the inventory if the inventory still has some space in. 
	 * Else it will drop the first item of it's inventory and add the new item to the inventory. 
	 * 
	 * @param item the item that'll be added to the inventory
	 * @return the GroundItem that is the item drop from the inventory.
	 */
	public GroundItem addInventory(Item item) {
		if (item == null) return null;
		else if (item.getName() == "key") {
			keys+= item.getQuantity();
		}
		else if (item.getName() == "money" && money < HeroInfos.MAX_COIN) {
			money+= item.getQuantity();
			if (money > HeroInfos.MAX_COIN) money = HeroInfos.MAX_COIN;
		}
		else if (item.getName() == "bomb") {
			bomb+= item.getQuantity();
		}
		else if (inventory[0] == null) {
			inventory[0] = item;
			if (item.getPassiveItem()) updateItemBonus(item);
		}
		else if (inventory[1] == null) {
			inventory[1] = item;
			if (item.getPassiveItem()) updateItemBonus(item);
		}
		else {
			GroundItem old_item = dropInventory(0);
			inventory[1] = item;
			if (item.getPassiveItem()) updateItemBonus(item);
			return old_item;
		}
		return null;
	}

	/**
	 * Drops the Item linked to the slot if it exists. It will add the GroundItem, 
	 * that represents the Item that was in the inventory, to the list of GroundItem.
	 * 
	 * @param slot the slot the item will be dropped
	 * @return the GroundItem that is the item dropped from the inventory.
	 */
	public GroundItem dropInventory(int slot) {
		if (inventory.length <= slot || inventory[slot] == null) return null;
		Item tmp = inventory[slot];
		GroundItem droppedItem = new GroundItem(getPosition(), tmp.getSize(), tmp.getImagePath(), getRoom(), tmp, false);
		removeItemBonus(slot);
		if (slot < inventory.length - 1) {
			Item it = inventory[slot +1];
			if (it != null) inventory[slot] = new Item(it.getImagePath(), it.getQuantity(), it.getName(), it.getSize(), it.getPassiveItem());
			else inventory[slot] = null;
			inventory[slot + 1] = null;
		}
		else inventory[slot] = null;
		return droppedItem;
	}
	
	/**
	 * Updates the bonus effect from the item.
	 * @param item the item
	 */
	public void updateItemBonus(Item item) {
		switch (item.getName()) {
		case "blood_of_the_marthyr": setDamage(getDamage() + ItemInfos.BLOOD_OF_THE_MARTHYR_ITEM_ADDED_DAMAGE); break;
		case "jesus_juice": {
			setDamage(getDamage() + ItemInfos.JESUS_JUICE_ADDED_DAMAGE);
			reach += ItemInfos.JESUS_JUICE_ADDED_RANGE;
			break;
		}
		case "lunch": setMaxHealth(getMaxHealth() + 2); break;
		case "hp_up": {
			setMaxHealth(getMaxHealth() + 2);
			setHealth(getHealth() + 2); 
			break;
		}
		case "magic_mushroom": {
			setDamage(getDamage() + ItemInfos.MAGIC_MUSHROOM_ADDED_DAMAGE);
			reach += ItemInfos.MAGIC_MUSHROOM_ADDED_RANGE;
			attackSpeed -= ItemInfos.MAGIC_MUSHROOM_ADDED_ATTACK_SPEED;
			break;
		}
		case "pentagram": {
			setDamage(getDamage() + ItemInfos.PENTAGRAM_ADDED_DAMAGE);
			setMaxHealth(getMaxHealth() + 2);
			break;
		}
		case "stigmata": {
			setDamage(getDamage() + ItemInfos.STIGMATA_ADDED_DAMAGE);
			break;
		}
		default: break;
		}
	}
	
	/**
	 * Removes the itemBonus given by the item from the slot.
	 * @param slot the slot in the inventory
	 */
	public void removeItemBonus(int slot) {
		if (inventory.length < slot && inventory[slot] != null) return;
		if (!inventory[slot].getPassiveItem() && itemCooldown[slot] == 0) return;
		switch (inventory[slot].getName()) {
		case "blood_of_the_marthyr": setDamage(getDamage() - ItemInfos.BLOOD_OF_THE_MARTHYR_ITEM_ADDED_DAMAGE); break;
		case "jesus_juice": {
			setDamage(getDamage() - ItemInfos.JESUS_JUICE_ADDED_DAMAGE);
			reach -= ItemInfos.JESUS_JUICE_ADDED_RANGE;
			break;
		}
		case "lunch": {
			setMaxHealth(getMaxHealth() - 2);
			break;
		}
		case "hp_up": {
			setMaxHealth(getMaxHealth() - 2);
			break;
		}
		case "magic_mushroom": {
			setDamage(getDamage() - ItemInfos.MAGIC_MUSHROOM_ADDED_DAMAGE);
			reach -= ItemInfos.MAGIC_MUSHROOM_ADDED_RANGE;
			attackSpeed += ItemInfos.MAGIC_MUSHROOM_ADDED_ATTACK_SPEED;
			break;
		}
		case "pentagram": {
			setDamage(getDamage() - ItemInfos.PENTAGRAM_ADDED_DAMAGE);
			setMaxHealth(getMaxHealth() - 2);
			break;
		}
		case "stigmata": {
			setDamage(getDamage() - ItemInfos.STIGMATA_ADDED_DAMAGE);
			break;
		}
		default: break;
		}
		
		if (getHealth() > getMaxHealth()) setHealth(getMaxHealth());
	}
	
	/**
	 * Uses the item from the slot in the inventory. And sets a cooldown for using it again, as well as a item effect cooldown.
	 * @param slot the slot of inventory 
	 */
	public void useItem(int slot) {
		if (inventory.length <= slot || inventory[slot] == null) return;
		if (!inventory[slot].getPassiveItem() && itemCooldown[slot] == 0) {
			initialItemCooldownValue[slot] = 200;
			initialItemDurationValue[slot] = 70;
			
			itemCooldown[slot] = initialItemCooldownValue[slot];
			itemDuration[slot] = initialItemDurationValue[slot];
			updateItemBonus(inventory[slot]);
		}
	}
	
	
	/**
	 * Checks if the Hero has full health.
	 * 
	 * @return {@code true} if it has, {@code false} otherwise. 
	 */
	public boolean isFullyHealled() {
		return (getHealth() == getMaxHealth());
	}
	
	/**
	 * 
	 * Draws every Hero information, such as the health, the amount of money, bomb and keys, 
	 * and the objects in the Inventory.
	 * 
	 */
	public void drawHeroInfo() {
		drawHeart();
		StdDraw.setFont(new Font("Candara", Font.BOLD, 16));
		drawMoney();
		drawBomb();
		drawKey();
		drawInventory();
	}
	
	/**
	 * Draws the health of the Hero.
	 * 
	 * @param h, the hero
	 */
	private void drawHeart() {
		double x = 0; 
		for (int i = 0; i < getMaxHealth(); i++) {
			if (i%2 == 0) {
				StdDraw.picture(0.05 + x, 0.95, ImagePaths.EMPTY_HEART_HUD, 0.05, 0.05);
				x += 0.04;
			}
		}
		x = 0;
		for (int i = 1; i <= getHealth(); i++) {			
			if (i%2 == 0) {
				StdDraw.picture(0.05 + x, 0.95, ImagePaths.HEART_HUD, 0.05, 0.05);
				x += 0.04;
			}
			else StdDraw.picture(0.05 + x, 0.95, ImagePaths.HALF_HEART_HUD, 0.05, 0.05);
		}
	}
	
	
	/**
	 * Draws the amount of keys of the Hero.
	 */
	private void drawKey() {
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.picture(0.3, 0.98, ImagePaths.KEY, 0.035*0.75, 0.05*0.75);
		StdDraw.text(0.35, 0.98, ": " + keys, 0.05);
	}
	
	/**
	 * Draws the amount of money of the Hero.
	 */
	private void drawMoney() {
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.picture(0.3, 0.95, ImagePaths.COIN, 0.036, 0.026);
		StdDraw.text(0.35, 0.95, ": " + money, 0.05);
	}
	
	/**
	 * Draws the amount of bomb of the Hero.
	 */
	private void drawBomb() {
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.picture(0.3, 0.92, ImagePaths.BOMB, 0.040, 0.042);
		StdDraw.text(0.35, 0.92, ": " + bomb, 0.05);
	}
	
	/**
	 * Draws the inventory of the Hero.
	 */
	private void drawInventory() {
		if(inventory[0] != null) {
			Item item0 = inventory[0];
			StdDraw.picture(0.79, 0.95, item0.getImagePath(), item0.getSize().getX(), item0.getSize().getY());
			if (!item0.getPassiveItem() ) {
//				rectangle for the item effect cooldown
				StdDraw.setPenColor(new Color(90,90,90));
				StdDraw.filledRectangle(0.75, 0.95, 0.003, 0.03);
				StdDraw.setPenColor(new Color(90,90,255));
//				draws the growing rectangle
				double multiplierValue = 300.0 / initialItemDurationValue[0];
				double cooldownHeight = 0.03 - ((double)(itemDuration[0] * multiplierValue) / 10000.0);
				double rectangleYPos = 0.95 - ( (0.03 - cooldownHeight) );
				StdDraw.filledRectangle(0.75, rectangleYPos, 0.002, cooldownHeight);
//				rectangle for the next use cooldown
				StdDraw.setPenColor(new Color(90,90,90));
				StdDraw.filledRectangle(0.83, 0.95, 0.003, 0.03);
				StdDraw.setPenColor(new Color(210,255,255));
				double multiplierValue1 = 300.0 / initialItemCooldownValue[0];
				double cooldownHeight1 = 0.03 - ((double)(itemCooldown[0] * multiplierValue1) / 10000.0);
				double rectangleYPos1 = 0.95 - ( (0.03 - cooldownHeight1) );
				StdDraw.filledRectangle(0.83, rectangleYPos1, 0.002, cooldownHeight1);
			}
			if (inventory[1] != null) {
				Item item1 = inventory[1];
				StdDraw.picture(0.91, 0.95, item1.getImagePath(), item1.getSize().getX(), item1.getSize().getY());
				if (!item1.getPassiveItem() ) {
//					rectangle for the item effect cooldown
					StdDraw.setPenColor(new Color(90,90,90));
					StdDraw.filledRectangle(0.87, 0.95, 0.003, 0.03);
					StdDraw.setPenColor(new Color(90,90,255));
					double multiplierValue = 300.0 / initialItemDurationValue[1];
					double cooldownHeight = 0.03 - ((double)(itemDuration[1] * multiplierValue) / 10000.0);
					double rectangleYPos = 0.95 - ( (0.03 - cooldownHeight) );
					StdDraw.filledRectangle(0.87, rectangleYPos, 0.002, cooldownHeight);
//					rectangle for the next use cooldown
					StdDraw.setPenColor(new Color(90,90,90));
					StdDraw.filledRectangle(0.95, 0.95, 0.003, 0.03);
					StdDraw.setPenColor(new Color(210,255,255));
					double multiplierValue1 = 300.0 / initialItemCooldownValue[1];
					double cooldownHeight1 = 0.03 - ((double)(itemCooldown[1] * multiplierValue1) / 10000.0);
					double rectangleYPos1 = 0.95 - ( (0.03 - cooldownHeight1) );
//					y = 0.95 by default
//					height = 0.03
					StdDraw.filledRectangle(0.95, rectangleYPos1, 0.002, cooldownHeight1);
				}
			}
		}
	}
	
	
	/*
	 * getter and setter
	 */
	
	/**
	 * Returns the Hero inventory.
	 * @return the Hero inventory.
	 */
	public Item[] getInventory(){
		return inventory;
	}

	/**
	 * Sets what state the Hero is in, whether can interact with something or not.
	 * <p>
	 * if interact is set to {@code true} then the Hero will be able to interact, if it's set to {@code false} then the Hero will not be able to interact.
	 * 
	 * @param interact the interact state
	 */
	public void setInteract(boolean interact) {
		this.interact = interact;
	}
	
	/**
	 * Returns what state the Hero is in, whether it's interact or not.
	 * @return {@code true} if the Hero can interact, {@code false} otherwise.
	 */
	public boolean getInteract() {
		return interact;
	}
	
	/**
	 * Returns the amount of money that the Hero has.
	 * @return the amount of money that the Hero has.
	 */
	public int getMoney() {
		return money;
	}
	
	/**
	 * Sets the amount of money that the Hero has.
	 * @param money the amount of money that the Hero has
	 */
	public void setMoney(int money) {
		this.money = money;
	}
	
	/**
	 * Returns the amount of keys that the Hero has.
	 * @return the amount of keys that the Hero has.
	 */
	public int getKey() {
		return keys;
	}

	/**
	 * Returns the amount of bomb that the Hero has.
	 * @return the amount of bomb that the Hero has.
	 */
	public int getBomb() {
		return bomb;
	}
	
	/**
	 * Sets the new amount of bomb that the Hero has.
	 * @param bomb the new amount of bomb that the Hero has
	 */
	public void setBomb(int bomb) {
		this.bomb = bomb;
	}
	
	/**
	 * Returns the invincible state of the player.
	 * @return the invincible state of the player.
	 */
	public boolean getInvincible() {
		return invincible;
	}
	
	
	/**
	 * Sets the invincible state of the player.
	 * @param invincible the invincible state
	 */
	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}
	
}
