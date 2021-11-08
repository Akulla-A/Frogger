package gameCommons;

import frog.Frog;
import graphicalElements.Element;
import graphicalElements.IFroggerGraphics;

import java.awt.*;
import java.util.Random;

public class GameInf extends Game{
    public final Random randomGen = new Random();

    // Caracteristique de la partie
    public final int width;
    public final int height;
    public final int minSpeedInTimerLoops;
    public final double defaultDensity;

    // Lien aux objets utilis�s
    private IEnvironment environment;
    private Frog frog;
    private IFroggerGraphics graphic;
    private float startTime;

    /**
     * @param graphic             l'interface graphique
     * @param width               largeur en cases
     * @param height              hauteur en cases
     * @param minSpeedInTimerLoop Vitesse minimale, en nombre de tour de timer avant d�placement
     * @param defaultDensity      densite de voiture utilisee par defaut pour les routes
     */
    public GameInf(IFroggerGraphics graphic, int width, int height, int minSpeedInTimerLoop, double defaultDensity) {
        super(graphic, width, height, minSpeedInTimerLoop, defaultDensity);
        this.graphic = graphic;
        this.width = width;
        this.height = height;
        this.minSpeedInTimerLoops = minSpeedInTimerLoop;
        this.defaultDensity = defaultDensity;
    }

    @Override
    public void update() {
        graphic.clear();
        environment.update();
        this.graphic.add(new Element (frog.getPosition(), Color.GREEN));

        if(testLose()){
            this.graphic.endGameScreen("Vous avez traversé : " + frog.getAliveTime() + " routes");
        }
    }

    /**
     * Récupérer l'environnement, utilisé pour bouger la grenouille et relier cela pour les lanes
     */

    public IEnvironment getEnvironment(){
        return this.environment;
    }
}
