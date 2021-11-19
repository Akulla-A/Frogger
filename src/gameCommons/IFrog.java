package gameCommons;

import util.Case;
import util.Direction;

public interface IFrog {
	
	/**
	 * Donne la position actuelle de la grenouille
	 * @return Case
	 */
	Case getPosition();
	
	/**
	 * Donne la direction de la grenouille, c'est � dire de son dernier mouvement 
	 * @return Direction
	 */
	Direction getDirection();
	
	/**
	 * D�place la grenouille dans la direction donn�e et teste la fin de partie
	 * @param key
	 */
	void move(Direction key);

	// Ajouté par nous
	void setGonnaDie(boolean b);
	boolean isGonnaDie();

	void setAliveEnd(long time);
	long getAliveEndTime();

	void addAliveTime();
	boolean isAlive();

	void addScore();
	int getScore();
}
