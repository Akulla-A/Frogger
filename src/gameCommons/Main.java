package gameCommons;

import environment.Environment;
import environment.ICaseSpecial;
import frog.Frog;
import graphicalElements.FroggerGraphic;
import graphicalElements.IFroggerGraphics;
import specialCase.Bonus;
import specialCase.Ice;
import specialCase.Trap;
import specialCase.Tunnel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Main {
	public static ICaseSpecial getSpecialCase(int abs, int ord){
		Random r = new Random ();

		if(r.nextFloat () < 0.8){
			return null;
		} else {
			switch(r.nextInt(4)){
				case 0:
					return new Bonus(abs, ord);
				case 1:
					return new Ice (abs, ord);
				case 2:
					return new Trap (abs, ord);
				case 3:
					return new Tunnel (abs, ord);
				default:
					return null;
			}
		}
	}

	public static void main(String[] args) {

		//Caract�ristiques du jeu
		int width = 26;
		int height = 20;
		int tempo = 100;
		int minSpeedInTimerLoops = 3;
		double defaultDensity = 0.2;
		
		//Cr�ation de l'interface graphique
		IFroggerGraphics graphic = new FroggerGraphic(width, height);
		//Cr�ation de la partie
		Game game;

		if(args.length > 0 && args[0].equals("-infini")){
			game = new GameInf(graphic, width, height, minSpeedInTimerLoops, defaultDensity);
		} else {
			game = new Game(graphic, width, height, minSpeedInTimerLoops, defaultDensity);
		}
		//Cr�ation et liason de la grenouille
		IFrog frog1 = new Frog(game, false);
		game.setFrog(frog1, false);
		graphic.setFrog(frog1, false);

		if((args.length > 1 && args[1].equals("-2players")) || args.length > 0 && args[0].equals("-2players")){
			IFrog frog2 = new Frog(game, true);
			game.setFrog(frog2, true);
			graphic.setFrog(frog2, true);
		}

		//Cr�ation et liaison de l'environnement
		IEnvironment env = new Environment(game);
		game.setEnvironment(env);
				
		//Boucle principale : l'environnement s'acturalise tous les tempo milisecondes
		Timer timer = new Timer(tempo, e -> {
			game.update();
			graphic.repaint();
		});
		timer.setInitialDelay(0);
		timer.start();
	}
}
