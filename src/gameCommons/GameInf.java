package gameCommons;

import frog.Frog;
import graphicalElements.IFroggerGraphics;

public class GameInf extends Game {
    /**
     * @param graphic             l'interface graphique
     * @param width               largeur en cases
     * @param height              hauteur en cases
     * @param minSpeedInTimerLoop Vitesse minimale, en nombre de tour de timer avant d�placement
     * @param defaultDensity      densite de voiture utilisee par defaut pour les routes
     */
    public GameInf(IFroggerGraphics graphic, int width, int height, int minSpeedInTimerLoop, double defaultDensity) {
        super(graphic, width, height, minSpeedInTimerLoop, defaultDensity);
    }

    @Override
    public boolean testWin(Frog frog){
        return false;
    }

    /**
     * Actualise l'environnement, affiche la grenouille et verifie la fin de
     * partie.
     */
    @Override
    public void update() {
        graphic.clear();
        environment.update();

        //this.graphic.add(new Element(frog.getPosition(), Color.GREEN));
        this.graphic.add(frog1, 4);
        this.graphic.add(frog2, 4);

        testLose(frog1);

        if(frog2 != null){
            testLose(frog2);
        }

        boolean frog2Finish = (frog2 != null && frog2.isAlive ());
        boolean frog1Finish = frog1.isAlive();

        if (frog2Finish || frog1Finish){
            return;
        }

        if(frog2 != null){
            String txt1 = "Grenouille 1 à " + (this.environment.isWinningPosition (frog1.getPosition()) ? "gagné" : "perdu") +" en " + (frog1 .getAliveEndTime()-frog1.getStartTime())/1000 + " sec";
            String txt2 = "Grenouille 2 à " + (this.environment.isWinningPosition (frog2.getPosition()) ? "gagné" : "perdu") +" en " + (frog2.getAliveEndTime()-frog2.getStartTime())/1000 + " sec";

            this.graphic.endGameScreen(txt1, txt2);
        } else {
            String txt1 = "Grenouille 1 à " + (this.environment.isWinningPosition (frog1.getPosition()) ? "gagné" : "perdu") +" en " + (System.currentTimeMillis()-frog1.getStartTime())/1000 + " sec";

            this.graphic.endGameScreen(txt1);
        }
    }
}