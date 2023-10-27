package gameobjects;

import java.io.Serializable;

import libraries.Vector2;

/**
 * An Item is the class used to store everything in the Heros inventory. 
 * It uses different variables such as the String imagePath, that's what image is going to be used to represent the Item. 
 * And the Vector2 size, that is how big the Item will appear.
 * <p>
 * There's also the String name that refers to the item's name. 
 * And the integer quantity, to know how much of that item is stored. 
 *
 */
public class Item implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String imagePath;
	private int quantity;
	private String name;
	private Vector2 size;
	private boolean passiveItem = false;
	
	/**
	 * Constructs a new Item, whose size is specified by the {@link Vector2} argument.
	 * 
	 * @param imagePath the image of the new Item
	 * @param quantity the quantity stacked in this new Item
	 * @param name the name of the new Item
	 * @param size the size of the new Item
	 * @param passiveItem whether the new Item in consumable or passive
	 */
	public Item(String imagePath, int quantity, String name, Vector2 size, boolean passiveItem) {
		this.imagePath = imagePath;
		this.quantity = quantity;
		this.name = name;
		this.size = size;
		this.passiveItem = passiveItem;
	}
	
	/*
	 * functions
	 */
	
	/**
	 * Checks if an Item is the same as another one.
	 * 
	 * @param item the other Item
	 * @return {@code true} if their the same, {@code false} if not.
	 */
	public boolean equals(Item item) {
		return (this.getName().equals(item.getName()) && this.getImagePath().equals(item.getImagePath()) && 
				this.getQuantity() == item.getQuantity() && this.getSize().equals(item.getSize()));
	}
	
	/*
	 * getter and setter
	 */
	
	/**
	 * Sets the new imagePath for the Item.
	 * @param imagePath the new ImagePath for the Item
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	/**
	 * Returns the imagePath of the Item.
	 * @return the imagePath of the Item.
	 */
	public String getImagePath() {
		return imagePath;
	}
	
	/**
	 * Sets the new quantity hold by the Item.
	 * @param quantity the new hold by the Item
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * Returns the quantity hold by the Item.
	 * @return the quantity hold by the Item.
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * Sets the new name for the Item.
	 * @param name the new name for the Item
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name of the Item.
	 * @return the name of the Item.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the new size for the Item.
	 * @param size the new size for the Item
	 */
	public void setSize(Vector2 size) {
		this.size = size;
	}
	
	/**
	 * Returns the passive state of the Item.
	 * @return the passive state of the Item.
	 */
	public boolean getPassiveItem() {
		return passiveItem;
	}
	
	/**
	 * Sets the new passive state of the Item.
	 * @param passiveItem the new passive state of the Item
	 */
	public void setPassiveItem(boolean passiveItem) {
		this.passiveItem = passiveItem;
	}
	
	/**
	 * Returns the size of the Item.
	 * @return the size of the Item.
	 */
	public Vector2 getSize() {
		return size;
	}
}
