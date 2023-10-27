package gameWorld;

import java.awt.Color;
import java.io.Serializable;

import gameobjects.GameObject;
import libraries.StdDraw;
import libraries.Vector2;

/**
 * A HitBox, is a Rectangle that moves with the GameObject that's affected to it. 
 * <p>
 * To check what a GameObject is please refer to {@link GameObject}.
 */
public class HitBox implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * A Rectangle is represented by two points one on the down-left corner (x1,y1) and one on the upper-right (x2, y2).
	 */
	private class Rectangle implements Serializable {

		private static final long serialVersionUID = 1L;
		double x1;
		double x2;
		double y1;
		double y2;
		
		/**
		 * Constructs a new Rectangle based on the size and position of a GameObject. 
		 * The first point (x1,y1) will be in the position of the GameObject minus one half of it's size.
		 * <p> 
		 * And the second point (x2,y2) will be in the position of the GameObject plus one half of it's size.
		 * 
		 * @param size the size of the GameObject
		 * @param position the current position of the GameObject
		 * @param gameObject the GameObject
		 */
		public Rectangle(Vector2 size, Vector2 position, GameObject gameObject) {		
			x1 = position.getX() - ( size.getX() / 2 );
			x2 = position.getX() + ( size.getX() / 2 );
			y1 = position.getY() - ( size.getY() / 2 );
			y2 = position.getY() + ( size.getY() / 2 );
			
		}
		
		/**
		 * Checks if the rectangles is colliding with another one.
		 * 
		 * @param r the other Rectangle
		 * @return {@code true} if they're colliding, {@code false} otherwise.
		 */
		public boolean Collide(Rectangle r) {
			return !( (x1 > r.x2) || (r.x1 > x2) || (r.y1 > y2) || (y1 > r.y2) ) ;
		}
		
	}
	
	private Vector2 size = new Vector2();
	private GameObject objet;
	
	/**
	 * Constructs a new HitBox, whose GameObject is specified by the {@link GameObject} argument.
	 * 
	 * @param size the size of the new HitBox
	 * @param objet the GameObjects that holds this HitBox
	 */
	public HitBox(Vector2 size, GameObject objet) {
		this.size = size;
		this.objet = objet;
	}

	/**
	 * Draws the HitBox.
	 */
	public void drawHitBox() {
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.rectangle(getPosition().getX(), getPosition().getY(), size.getX() / 2, size.getY() / 2);
	}
	
	/**
	 * Draws the HitBox with the selected Color.
	 * @param c the Color you want the HitBox to be
	 */
	public void drawHitBox(Color c) {
		StdDraw.setPenColor(c);
		StdDraw.rectangle(getPosition().getX(), getPosition().getY(), size.getX() / 2, size.getY() / 2);
	}

	/**
	 * Returns the Rectangle corresponding to the HitBox of the GameObject. 
	 * Takes the GameObject position as the position required by Rectangle.
	 * @return the Rectangle corresponding to the HitBox of the GameObject.
	 */
	private Rectangle getRectangle() {
		return new Rectangle(size, getPosition(), this.objet);
	}

	/**
	 * Returns the Rectangle corresponding to the HitBox of the GameObject with the specified position.
	 * @return the Rectangle corresponding to the HitBox of the GameObject with the specified position.
	 */
	private Rectangle getRectangle(Vector2 position) {
		return new Rectangle(size, position, this.objet);
	}
	
	
	/**
	 * Checks if the HitBox is colliding with an other HitBox.
	 * 
	 * @param h the other HitBox
	 * @return {@code true} if it's colliding, {@code false} otherwise.
	 */
	public boolean collide(HitBox h) {
		return getRectangle().Collide(h.getRectangle());
	}
	
	/**
	 * Checks if the HitBox is colliding with an another HitBox at the specified position.
	 * 
	 * @param h the other HitBox
	 * @param objectsPosition the position of the other HitBox
	 * @return {@code true} if it's colliding, {@code false} otherwise.
	 */
	public boolean collide(Vector2 objectsPosition, HitBox h) {
		return getRectangle().Collide(h.getRectangle(objectsPosition));
	}
	
	/**
	 * Checks if the HitBox is colliding with an another HitBox at the specified position 
	 * from the Left.
	 * @param h the other HitBox
	 * @return {@code true} if it's colliding, {@code false} otherwise.
	 */
	public boolean CollideFromLeft(HitBox h) {
		Rectangle r1 = this.getRectangle();
		Rectangle r2 = h.getRectangle();
		
		return !(r1.x2 <= r2.x2);
	}

	public boolean CollideFromRight(HitBox h) {
		Rectangle r1 = this.getRectangle();
		Rectangle r2 = h.getRectangle();
		
		return (r1.x2 <= r2.x2);
	}
	
	public boolean CollideFromTop(HitBox h) {
		Rectangle r1 = this.getRectangle();
		Rectangle r2 = h.getRectangle();
		
		return (r1.y1 <= r2.y1);
	}
	
	public boolean CollideFromBottom(HitBox h) {
		Rectangle r1 = this.getRectangle();
		Rectangle r2 = h.getRectangle();
		
		return (r1.y2 >= r2.y2);
	}
	
	/*
	 * getter and setter
	 */

	/**
	 * Returns the position of the GameObject linked to the HitBox.
	 * @return the position of the GameObject linked to the HitBox.
	 */
	private Vector2 getPosition() {
		return objet.getPosition();
	}
}
