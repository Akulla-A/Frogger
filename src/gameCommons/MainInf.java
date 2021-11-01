package gameCommons;

import environment.EnvInf;

import frog.FrogInf;
import graphicalElements.FroggerGraphic;
import graphicalElements.IFroggerGraphics;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainInf {
    public static void main(String[] args) {

        //Caract�ristiques du jeu
        int width = 26;
        int height = 26;
        int tempo = 100;
        int minSpeedInTimerLoops = 3;
        double defaultDensity = 0.2;

        //Cr�ation de l'interface graphique
        IFroggerGraphics graphic = new FroggerGraphic(width, height);
        //Cr�ation de la partie
        GameInf game = new GameInf(graphic, width, height, minSpeedInTimerLoops, defaultDensity);
        //Cr�ation et liaison de la grenouille
        IFrog frog = new FrogInf(game);
        game.setFrog(frog);
        graphic.setFrog(frog);
        //Cr�ation et liaison de l'environnement
        EnvInf env = new EnvInf(game);
        game.setEnvironment(env);

        //Boucle principale : l'environnement s'actualise tous les tempo milisecondes
        Timer timer = new Timer(tempo, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.update();
                graphic.repaint();
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }
}
