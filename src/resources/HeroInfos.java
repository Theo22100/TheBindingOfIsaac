package resources;

import libraries.Vector2;

public class HeroInfos
{
//	GENERAL
	public static Vector2 HERO_SIZE = RoomInfos.TILE_SIZE.scalarMultiplication(0.7);
	public static Vector2 LEG_SIZE = RoomInfos.TILE_SIZE.scalarMultiplication(0.7);
	
	public static int MAX_COIN = 100; 
	
//	ISAAC
	public static final double ISAAC_SPEED = 0.01;
	public static final double ISAAC_REACH = 0.4;
	public static final int ISAAC_ATTACK_SPEED = 20;
	public static final int ISAAC_DAMAGE = 1;
	public static final int ISAAC_HEALTH = 6;
//	28 x 33
	
	
//	39 x 39
//	MAGDALENE
	public static final double MAGDALENE_SPEED = 0.01;
	public static final double MAGDALENE_REACH = 0.4;
	public static final int MAGDALENE_ATTACK_SPEED = 20;
	public static final int MAGDALENE_DAMAGE = 1;
	public static final int MAGDALENE_HEALTH = 6;
	
//	GAPER
	public static final double GAPER_SPEED = 0.01;
	public static final double GAPER_REACH = 0.4;
	public static final int GAPER_ATTACK_SPEED = 20;
	public static final int GAPER_DAMAGE = 1;
	public static final int GAPER_HEALTH = 6;
}
