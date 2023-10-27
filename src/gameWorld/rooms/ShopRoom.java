package gameWorld.rooms;


import java.util.ArrayList;
import java.util.List;

import gameobjects.Item;
import gameobjects.nonstaticobjects.Hero;
import gameobjects.nonstaticobjects.grounditem.*;
import gameobjects.staticobjects.StaticObject;
import libraries.StdDraw;
import libraries.Vector2;
import libraries.addedByUs.Utils;
import resources.ItemInfos;

/**
 * A ShopRoom is a Room, that holds three items with a set price. 
 * Those item can be bought by the Hero in exchange for some coins.
 * <p>
 * To check what the other variable from NonStaticObject are please refer to {@link Room}.
 * @see GroundItem
 */
public class ShopRoom extends Room{
	
	private static final long serialVersionUID = 1L;
	
	private List<Integer> listPrice = new ArrayList<Integer>();
	private List<GroundItem> listShopItem = new ArrayList<GroundItem>();
	
	/**
	 * Constructs a new ShopRoom, whose Hero is specified by the {@link Hero} argument. 
	 * And spawns every shop item with the price label in the room.
	 * 
	 * @param hero the hero of the new ShopRoom
	 */
	public ShopRoom(Hero hero) {
		super(hero);
		spawnItem();
		setListStaticObject(new ArrayList<StaticObject>());
		addEveryThingTolNonStaticObject();
	}
	
	/**
	 * Spawns the three items that will be sold and adds them to the 
	 * list of ShopItem, and GroundItem.
	 * <p>
	 * The three items spawn in the middle of the room.
	 */
	private void spawnItem() {
	    GroundItem firstItem = Utils.randomGroundObject(this);
	    while (priceOfGroundItem(firstItem) == 0) firstItem = Utils.randomGroundObject(this);
		GroundItem secondItem = Utils.randomGroundObject(this);
	    while (priceOfGroundItem(secondItem) == 0) secondItem = Utils.randomGroundObject(this);
		GroundItem thirdItem = Utils.randomGroundObject(this);
	    while (priceOfGroundItem(thirdItem) == 0) thirdItem = Utils.randomGroundObject(this);
	    
		firstItem.setPosition(new Vector2(0.3, 0.5));
		secondItem.setPosition(new Vector2(0.5, 0.5));
		thirdItem.setPosition(new Vector2(0.7, 0.5));
		
		listPrice.add(priceOfGroundItem(firstItem));
		listPrice.add(priceOfGroundItem(secondItem));
		listPrice.add(priceOfGroundItem(thirdItem));
		listShopItem.add(firstItem);
		listShopItem.add(secondItem);
		listShopItem.add(thirdItem);
		
		this.addListGroundItem(firstItem);
		this.addListGroundItem(secondItem);
		this.addListGroundItem(thirdItem);
	}
	
	/**
	 * Return the price of a given groundItem.
	 * @param groundItem the groundItem
	 * @return the price of the groundItem.
	 */
	private static int priceOfGroundItem(GroundItem groundItem) {
		if (groundItem == null) return 0;
		Item it = groundItem.getItem();
		if (it == null) return 0;
		if (it.equals(ItemInfos.BLOOD_OF_THE_MARTHYR_ITEM)) return 8;
		else if ( it.equals(ItemInfos.BOMBE_ITEM) ) return 5;
		else if ( it.equals(ItemInfos.HP_UP_ITEM) ) return 8;
		else if ( it.equals(ItemInfos.JESUS_JUICE_ITEM) ) return 8;
		else if ( it.equals(ItemInfos.LUNCH_ITEM) ) return 5;
		else if ( it.equals(ItemInfos.PENTAGRAM_ITEM) ) return 8; 
		else if ( it.equals(ItemInfos.STIGMATA_ITEM) ) return 5;
		return 0;
	}
	
	/**
	 * Compared to the {@link Room#makeGroundItemPlay()} function, 
	 * this one needs to know if the Hero is interacting with the item to buy it.
	 * <p>
	 * If the Hero interacts to buy the item and has enough coins then he will lose the coin value of that item and receive it in his inventory, 
	 * dropping the first item of his inventory if he didn't have any space left in his inventory.
	 * <p>
	 * If the Hero interacts with an item that's not sold by the shop it will add it to his inventory, and drop 
	 * the first item of his inventory if he didn't have any space left in his inventory.
	 */
	@Override
	public void makeGroundItemPlay() {
		GroundItem groundItemToDelete = null;
		GroundItem groundItemToAdd = null;
		for (GroundItem gi: lGroundItem ) {
//			if player hit the corresponding key
			if (gi.collide(getHero()) && getHero().getInteract() == true) {
//				if the item is in the shop
				if (listShopItem.size() > 0 && listShopItem.contains(gi)) {
					int index = listShopItem.indexOf(gi);
					if (getHero().getMoney() >= listPrice.get(index )) {
						groundItemToAdd = getHero().addInventory(gi.getItem());
						getHero().setMoney(getHero().getMoney() - listPrice.get(index));
						groundItemToDelete = gi;
					}
				}
				else {
					if (gi instanceof LifeItem) {
//						check if the hero already has max health
						if (!getHero().isFullyHealled()) {
							getHero().loseHealth(-( (LifeItem) gi).getValue());
							groundItemToDelete = gi;
						}
					}
					else {
						groundItemToAdd = getHero().addInventory(gi.getItem());
						groundItemToDelete = gi;
					}
				}
			}
		}
		if (groundItemToAdd != null) lGroundItem.add(groundItemToAdd);
		if (groundItemToDelete!= null) {
			if (listShopItem.contains(groundItemToDelete)) {
				int index = listShopItem.indexOf(groundItemToDelete);
				listPrice.remove(index);
				listShopItem.remove(index);
			}
			lGroundItem.remove(groundItemToDelete);
		}
	}
	
	/**
	 * Draws every GroundItem in the ShopRoom. 
	 * On top of that if the GroundItem is from the shop it draws the price label.
	 */
	@Override
	public void drawGroundItem() {
		for (GroundItem gi : getListGroundItem()) {
			gi.drawGameObject();
			if (listShopItem.contains(gi)) {
				StdDraw.setPenColor(StdDraw.WHITE);
				StdDraw.text(gi.getPosition().getX(), gi.getPosition().getY() + 0.05,
					"" + listPrice.get(listShopItem.indexOf(gi)) + "$");
			}
		}
	}
}
