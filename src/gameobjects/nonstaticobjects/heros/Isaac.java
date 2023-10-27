package gameobjects.nonstaticobjects.heros;

import gameobjects.nonstaticobjects.Hero;
import libraries.Vector2;
import resources.HeroInfos;
import resources.ImagePaths;

/**
 * Isaac class is a shortcut to creating the Hero. With all Images and informations already referred to in {@link HeroInfos}.
 *
 * To check the variable from Hero are check {@link Hero}.
 */
public class Isaac extends Hero{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new Isaac Hero.
	 */
	public Isaac() {
		super(new Vector2(0.5,0.5), HeroInfos.HERO_SIZE, HeroInfos.ISAAC_SPEED, ImagePaths.ISAAC, ImagePaths.ISAAC_BACK,
				ImagePaths.ISAAC_SIDE, ImagePaths.ISAAC_OTHER_SIDE, HeroInfos.ISAAC_ATTACK_SPEED, 
				HeroInfos.ISAAC_REACH, HeroInfos.ISAAC_DAMAGE, HeroInfos.ISAAC_HEALTH);
	}
}
