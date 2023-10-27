package gameobjects.nonstaticobjects.heros;

import gameobjects.nonstaticobjects.Hero;
import libraries.Vector2;
import resources.HeroInfos;
import resources.ImagePaths;

/**
 * Magdalene class is a shortcut to creating the Hero. With all Images and informations already referred to in {@link HeroInfos}.
 *
 * To check the variable from Hero are check {@link Hero}.
 */
public class Magdalene extends Hero {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new Magnalene Hero.
	 */
	public Magdalene() {
		super(new Vector2(0.5,0.5), HeroInfos.HERO_SIZE, HeroInfos.MAGDALENE_SPEED, ImagePaths.MAGDALENE, ImagePaths.MAGDALENE_BACK,
				ImagePaths.MAGDALENE_SIDE, ImagePaths.MAGDALENE_OTHER_SIDE, HeroInfos.MAGDALENE_ATTACK_SPEED, 
				HeroInfos.MAGDALENE_REACH, HeroInfos.MAGDALENE_DAMAGE, HeroInfos.MAGDALENE_HEALTH);
	}
}
