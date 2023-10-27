package gameobjects.nonstaticobjects.heros;

import gameobjects.nonstaticobjects.Hero;
import libraries.Vector2;
import resources.HeroInfos;
import resources.ImagePaths;

/**
 * Gaper class is a shortcut to creating the Hero. With all Images and informations already referred to in {@link HeroInfos}.
 *
 * To check the variable from Hero are check {@link Hero}.
 */
public class Gaper extends Hero{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new Gaper Hero.
	 */
	public Gaper() {
		super(new Vector2(0.5,0.5), HeroInfos.HERO_SIZE, HeroInfos.GAPER_SPEED, ImagePaths.GAPER, ImagePaths.GAPER_BACK,
				ImagePaths.GAPER_SIDE, ImagePaths.GAPER_OTHER_SIDE, HeroInfos.GAPER_ATTACK_SPEED, 
				HeroInfos.GAPER_REACH, HeroInfos.GAPER_DAMAGE, HeroInfos.GAPER_HEALTH);
	}

}
