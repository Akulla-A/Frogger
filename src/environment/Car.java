package environment;

import java.awt.Color;
import java.util.Random;

import util.Case;
import gameCommons.Game;
import graphicalElements.Element;

public class Car {
	private Game game;
	private Case leftPosition;
	private boolean leftToRight;
	private int length;
	private final Color colorLtR = Color.BLACK;
	private final Color colorRtL = Color.BLUE;

	public Car(Game game, Case leftPosition, boolean leftToRight, int length){
		this.game = game;
		this.leftPosition = leftPosition;
		this.leftToRight = leftToRight;
		this.length = length;
	}

	public Car(Game game, Case leftPosition, boolean leftToRight){
		this.game = game;
		this.leftPosition = leftPosition;
		this.leftToRight = leftToRight;
		this.length = (new Random()).nextInt(3)+1;
	}

	//TODO : ajout de methodes
	public boolean inBounds(Case p){
		return this.leftPosition.ord != p.ord && (this.leftPosition.absc <= p.absc || p.absc <= this.leftPosition.absc + length);
	}

	public boolean update(boolean moving){

		if(moving){
			this.leftPosition = new Case(this.leftPosition.absc + 1, this.leftPosition.ord);

			if(this.leftPosition.absc >= game.width || this.leftPosition.absc < -length){
				return true;
			}
		}

		addToGraphics();
		return false;
	}

	/* Fourni : addToGraphics() permettant d'ajouter un element graphique correspondant a la voiture*/
	private void addToGraphics() {
		for (int i = 0; i < length; i++) {
			Color color = colorRtL;
			if (this.leftToRight){
				color = colorLtR;
			}
			game.getGraphic()
					.add(new Element(leftPosition.absc + i, leftPosition.ord, color));
		}
	}

}
