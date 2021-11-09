package gameCommons;

import environment.EnvInf;
import environment.Environment;
import frog.Frog;
import frog.FrogInf;
import graphicalElements.Element;
import graphicalElements.IFroggerGraphics;

import java.awt.*;
import java.util.Random;

public class GameInf {
    public final Random randomGen = new Random();

    // Caracteristique de la partie
    public final int width;
    public final int height;
    public final int minSpeedInTimerLoops;
    public final double defaultDensity;

    // Lien aux objets utilis�s
    private IEnvironment environment;
    private FrogInf frog;
    private IFroggerGraphics graphic;
    private int score = 0;
    protected long startTime;

    /**
     * @param graphic             l'interface graphique
     * @param width               largeur en cases
     * @param height              hauteur en cases
     * @param minSpeedInTimerLoop Vitesse minimale, en nombre de tour de timer avant d�placement
     * @param defaultDensity      densite de voiture utilisee par defaut pour les routes
     */
    public GameInf(IFroggerGraphics graphic, int width, int height, int minSpeedInTimerLoop, double defaultDensity) {
        super();
        this.graphic = graphic;
        this.width = width;
        this.height = height;
        this.minSpeedInTimerLoops = minSpeedInTimerLoop;
        this.defaultDensity = defaultDensity;
        this.startTime = System.currentTimeMillis();
    }


    public FrogInf getFrog() {
        return this.frog;
    }

    /**
     * Lie l'objet frog � la partie
     *
     * @param frog
     */
    public void setFrog(IFrog frog) {
        this.frog = (FrogInf) frog;
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
    public boolean testLose() {
        if(frog.isAlive() && (!this.environment.isSafe(frog.getPosition()) || frog.isGonnaDie())){
            frog.setAlive(false);
            return true;
        }
        return false;
    }

    /**
     * Actualise l'environnement, affiche la grenouille et verifie la fin de
     * partie.
     */
    public void update() {
        graphic.clear();
        environment.update();

        this.graphic.add(frog, 4);

        frog.addAliveTime();

        if (testLose()) {
            this.graphic.endGameScreen("Score : " + score + ". En " + (System.currentTimeMillis()-this.startTime)/1000 + " sec");
        }
    }

    /**
     * Récupérer l'environnement, utilisé pour bouger la grenouille et relier cela pour les lanes
     */

    public IEnvironment getEnvironment(){
        return this.environment;
    }

    public void addScore(){
        ++score;
    }
    public void subScore(){
        --score;
    }
}