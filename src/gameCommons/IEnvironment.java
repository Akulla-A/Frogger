package gameCommons;

import environment.ICaseSpecial;
import frog.Frog;
import util.Case;

public interface IEnvironment {

	/**
	 * Teste si une case est sure, c'est � dire que la grenouille peut s'y poser
	 * sans mourir
	 *
	 * @param c
	 *            la case � tester
	 * @return vrai s'il n'y a pas danger
	 */
	boolean isSafe(Case c);

	/**
	 * Teste si la case est une case d'arrivee
	 *
	 * @param c
	 * @return vrai si la case est une case de victoire
	 */
	boolean isWinningPosition(Case c);

	/**
	 * Effectue une �tape d'actualisation de l'environnement
	 */
	void update();

	boolean changeLane(boolean down, Frog movingFrog);

	ICaseSpecial getSpecialFrogCase(Case frogPose);
}