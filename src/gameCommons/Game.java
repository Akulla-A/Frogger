package gameCommons;

import frog.Frog;
import graphicalElements.IFroggerGraphics;

import java.util.Random;

public class Game {
	public final Random randomGen = new Random();

	// Caracteristique de la partie
	public final int width;
	public final int height;
	public final int minSpeedInTimerLoops;
	public final double defaultDensity;

	// Lien aux objets utilis�s
	protected IEnvironment environment;
	protected Frog frog1;
	protected Frog frog2;
	protected IFroggerGraphics graphic;
	private final boolean isInfinite;

	/**
	 * 
	 * @param graphic
	 *            l'interface graphique
	 * @param width
	 *            largeur en cases
	 * @param height
	 *            hauteur en cases
	 * @param minSpeedInTimerLoop
	 *            Vitesse minimale, en nombre de tour de timer avant d�placement
	 * @param defaultDensity
	 *            densite de voiture utilisee par defaut pour les routes
	 */
	public Game(IFroggerGraphics graphic, int width, int height, int minSpeedInTimerLoop, double defaultDensity, boolean isInfinite) {
		super();
		this.graphic = graphic;
		this.width = width;
		this.height = height;
		this.minSpeedInTimerLoops = minSpeedInTimerLoop;
		this.defaultDensity = defaultDensity;
		this.isInfinite = isInfinite;
	}

	public Frog getFrog(boolean second){
		return (second ? this.frog2 : this.frog1);
	}

	/**
	 * Lie l'objet frog � la partie
	 * 
	 * @param frog
	 */
	public void setFrog(IFrog frog, boolean second) {
		if(second){
			this.frog2 = (Frog)frog;
		} else {
			this.frog1 = (Frog)frog;
		}
	}

	/**
	 * Lie l'objet environment a la partie
	 * 
	 * @param environment
	 */
	public void setEnvironment(IEnvironment environment) {
		this.environment = environment;
	}

	/**
	 * 
	 * @return l'interface graphique
	 */
	public IFroggerGraphics getGraphic() {
		return graphic;
	}

	/**
	 * Teste si la partie est perdue et lance un �cran de fin appropri� si tel
	 * est le cas
	 * 
	 * @return true si le partie est perdue
	 */
	public boolean testLose(Frog frog) {
		if(frog.isAlive() && (!this.environment.isSafe(frog.getPosition()) || frog.isGonnaDie())){
			frog.setAlive(false);
			frog.setAliveEnd(System.currentTimeMillis());
			return true;
		}
		return false;
	}

	/**
	 * Teste si la partie est gagnee et lance un �cran de fin appropri� si tel
	 * est le cas
	 * 
	 * @return true si la partie est gagn�e
	 */
	public boolean testWin(Frog frog) {
		if(!isInfinite && frog.isAlive() && this.environment.isWinningPosition (frog.getPosition())){
			frog.setAlive(false);
			frog.setAliveEnd(System.currentTimeMillis());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Actualise l'environnement, affiche la grenouille et verifie la fin de
	 * partie.
	 */
	public void update() {
		graphic.clear();
		environment.update();

		this.graphic.add(frog1, 4);

		if(frog2 != null){
			this.graphic.add(frog2, 4);
		}

		testLose(frog1);
		testWin(frog1);

		if(frog2 != null){
			testWin(frog2);
			testLose(frog2);
		}

		boolean frog2Finish = (frog2 != null && frog2.isAlive ());
		boolean frog1Finish = frog1.isAlive();

		if (frog2Finish || frog1Finish){ return; }

		String txt1 = "Grenouille 1 à " + (this.environment.isWinningPosition (frog1.getPosition()) ? "gagné" : "perdu") +" en " + (System.currentTimeMillis()-frog1.getStartTime())/1000 + " sec";
		if(frog2 != null){
			String txt2 = "Grenouille 2 à " + (this.environment.isWinningPosition (frog2.getPosition()) ? "gagné" : "perdu") +" en " + (frog2.getAliveEndTime()-frog2.getStartTime())/1000 + " sec";
			this.graphic.endGameScreen(txt1, txt2);
			return;
		}

		this.graphic.endGameScreen(txt1);
	}

	public IEnvironment getEnvironment(){
		return this.environment;
	}
}
