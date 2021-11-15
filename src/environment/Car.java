package environment;

import gameCommons.Game;
import graphicalElements.Element;
import util.Case;
import util.SpriteCase;
import util.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Car {
	private Game game;
	private Case leftPosition;
	private boolean leftToRight;
	private int length;
	private final Color colorLtR = Color.BLACK;
	private final Color colorRtL = Color.BLUE;
	private boolean rondin;
	private ArrayList<SpriteCase> roadCases = new ArrayList<>();
	public static final ArrayList<ArrayList<BufferedImage>> spriteCar = new ArrayList<>();
	public static final BufferedImage spriteRondin = SpriteLoader.getPicture("rondin.png");

	private boolean isRondin = false;

	public Case getCarPosition(){
		return leftPosition;
	}

	public Car(Game game, Case leftPosition, boolean leftToRight, int length, boolean isRondin){
		this.game = game;
		this.leftPosition = leftPosition;
		this.leftToRight = leftToRight;
		this.length = (isRondin ? 5 : length);
		this.isRondin = isRondin;

		if (spriteCar.size() < 3){
			// Pour de la taille 1 jusqu'a 3
			for(int j = 1; j < 4; j++){
				ArrayList<BufferedImage> newList = new ArrayList<BufferedImage>();

				// *2 car on fait le côté droit, et le côté gauche
				for(int k = 1; k <= j*2; k++){
					newList.add(SpriteLoader.getPicture("car" + j + "_" + k + ".png"));
				}

				spriteCar.add(newList);
			}
		}

		for(int i = 0; i < length; i++){
			// Initialiser toute la liste
			SpriteCase c;
			if (isRondin){
				c = new SpriteCase(leftPosition.absc + i, leftPosition.ord, spriteRondin);
			} else {
				c = new SpriteCase(leftPosition.absc + i, leftPosition.ord, spriteCar.get(length-1).get(i + (leftToRight ? length : 0)));
			}
			roadCases.add(c);
			game.getGraphic().add(c, 3);
		}
	}

	public Car(Game game, Case leftPosition, boolean leftToRight, boolean isRondin){
		this(game, leftPosition, leftToRight, (new Random()).nextInt(3)+1, isRondin);
	}

	//TODO : ajout de methodes
	public boolean inBounds(Case p){
		int absc = this.leftPosition.absc;
		return ((this.leftPosition.ord == p.ord) && (absc <= p.absc && p.absc <= absc + length));
	}

	public boolean update(boolean moving){
		if(moving){
			this.leftPosition = new Case(this.leftPosition.absc + (leftToRight ? 1 : -1), this.leftPosition.ord);

			removeSprites();
			if(this.leftPosition.absc >= game.width || this.leftPosition.absc < -length){
				return true;
			} else {
				for(int i = 0; i < length; i++){
					// Initialiser toute la liste
					SpriteCase c;

					if (isRondin){
						c = new SpriteCase(leftPosition.absc + i, leftPosition.ord, spriteRondin);
					} else {
						c = new SpriteCase(leftPosition.absc + i, leftPosition.ord, spriteCar.get(length-1).get(i + (leftToRight ? length : 0)));
					}

					roadCases.add(c);
					game.getGraphic().add(c, 3);
				}
			}
		}

		if(this.leftPosition.ord >= 0 && this.leftPosition.ord <= game.height){
			addToGraphics();
		}

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

	public void removeSprites(){
		for(SpriteCase e : roadCases){
			game.getGraphic().remove(e, 3);
		}

		roadCases = new ArrayList<SpriteCase> ();

		for(int i = 0; i < length; i++){
			// Initialiser toute la liste
			SpriteCase c;

			if (isRondin){
				c = new SpriteCase(leftPosition.absc + i, leftPosition.ord, spriteRondin);
			} else {
				c = new SpriteCase(leftPosition.absc + i, leftPosition.ord, spriteCar.get(length-1).get(i + (leftToRight ? length : 0)));
			}
			roadCases.add(c);
			game.getGraphic().add(c, 3);
		}
	}

	// Changer
	public void newOrd(int ord){
		leftPosition = new Case(leftPosition.absc, ord);
		removeSprites ();
	}
}
