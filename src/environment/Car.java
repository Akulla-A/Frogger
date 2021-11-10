package environment;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import frog.Frog;
import util.Case;
import gameCommons.Game;
import graphicalElements.Element;
import util.Direction;
import util.SpriteCase;
import util.SpriteLoader;

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

	public Case getCarPosition(){
		int ord = this.leftPosition.ord-1;
		int absc = this.leftPosition.ord;
		Case position = new Case(absc, ord);
		return position;
	}

	public Car(Game game, Case leftPosition, boolean leftToRight, int length){
		this.game = game;
		this.leftPosition = leftPosition;
		this.leftToRight = leftToRight;
		this.length = length;

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
			SpriteCase c = new SpriteCase(leftPosition.absc + i, leftPosition.ord, spriteCar.get(length-1).get(i + (leftToRight ? length : 0)));
			roadCases.add(c);
			game.getGraphic().add(c, 3);
		}
	}

	public Car(Game game, Case leftPosition, boolean leftToRight){
		this(game, leftPosition, leftToRight, (new Random()).nextInt(3)+1);
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
					SpriteCase c = new SpriteCase(leftPosition.absc + i, leftPosition.ord, spriteCar.get(length-1).get(i + (leftToRight ? length : 0)));
					roadCases.add(c);
					game.getGraphic().add(c, 3);
				}
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

	public void isRondin(boolean rondin){
		int absc = this.game.getFrog().getPosition().ord + 1;
		if(this.rondin == true & this.game.getFrog().getPosition() == this.getCarPosition()){
			this.game.getFrog().move(Direction.left);
		}
	}

	public void removeSprites(){
		for(SpriteCase e : roadCases){
			game.getGraphic().remove(e, 3);
		}
	}
}
