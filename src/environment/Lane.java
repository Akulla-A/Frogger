package environment;

import gameCommons.Game;
import gameCommons.IFrog;
import gameCommons.Main;
import util.Case;
import util.Direction;
import util.SpriteCase;
import util.SpriteLoader;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Lane {
	private final Game game;
	private int ord;
	private final int speed;
	private ArrayList<Car> cars = new ArrayList<>();
	private final boolean leftToRight;
	private final double density;
	private int tic = 0;
	private ArrayList<ICaseSpecial> specialCases = new ArrayList<>();
	private ArrayList<SpriteCase> roadCases = new ArrayList<>();
	private static final BufferedImage bottomSprite = SpriteLoader.getPicture("roadbottom.png");
	private static final BufferedImage topSprite = SpriteLoader.getPicture("roadtop.png");
	private static final BufferedImage concreteSprite = SpriteLoader.getPicture("concrete.png");
	private static final BufferedImage spriteWater = SpriteLoader.getPicture("water.png");
	private final boolean isRondin;

	public Lane(Game game, int ord){ this(game, ord, new Random().nextInt(2) + 1, new Random().nextBoolean ()); }

	public Lane(Game game, int ord, int speed, boolean isRondin){
		this.game = game;
		this.ord = ord;
		this.isRondin = isRondin;

		// aléatoire
		Random r = new Random();
		this.speed = speed;
		this.leftToRight = (isRondin ? ord%2==0 : r.nextBoolean());

		this.density = r.nextDouble()%0.01+(isRondin ? 0.05 : 0.025);

		if (speed != 0 && !isRondin){
			for(int i = 0; i < game.width; i++){
				ICaseSpecial c = Main.getSpecialCase(i, ord);

				if(c != null){
					specialCases.add(c);
					game.getGraphic().add(c, 3);
				}
			}
		}

		for(int i = 0; i < game.width; i++){
			BufferedImage sprite;

			if(speed == 0){
				sprite = concreteSprite;
			} else if (isRondin){
				sprite = spriteWater;
			} else {
				if(ord % 2 == 0){
					sprite = topSprite;
				} else {
					sprite = bottomSprite;
				}
			}

			SpriteCase c = new SpriteCase(i, ord, sprite);
			roadCases.add(c);
			game.getGraphic().add(c, 0);
		}
	}

	public void update(boolean force) {
		IFrog frog1 = this.game.getFrog(false);
		IFrog frog2 = this.game.getFrog(true);
		Case pos1 = frog1.getPosition();
		Case pos2 = null;
		int abscSpace = (leftToRight ? game.width : 0);

		if(frog2 != null){
			pos2 = frog2.getPosition();
		}

		++tic;

		for(int i = 0; i < cars.size(); i++){
			boolean carTickMove = (speed != 0 && tic % speed == 0 && !force);
			Car c = cars.get(i);
			boolean inBoundsFrog1 = c.inBounds(pos1);
			boolean inBoundsFrog2 = (pos2 != null && c.inBounds(pos2));

			if(c.update(carTickMove)){
				cars.remove(c);
				c.removeSprites(false);
				--i;
			}

			if((leftToRight && abscSpace < c.getCarPosition().absc) || abscSpace > c.getCarPosition().absc){
				abscSpace = c.getCarPosition().absc;
			}

			if(this.isRondin && carTickMove){
				if(inBoundsFrog1){
					frog1.move(leftToRight ? Direction.right : Direction.left);
				}

				if(inBoundsFrog2){
					frog2.move(leftToRight ? Direction.right : Direction.left);
				}
			}
		}

		mayAddCar(abscSpace);
	}

	public boolean isSafe(Case c){
		if(c.ord != ord){
			return true;
		}

		boolean onCar = false;

		for(Car car : cars){
			if(car.inBounds(c)){
				onCar = true;
				break;
			}
		}

		return onCar == isRondin;
	}

	/*
	 * Fourni : mayAddCar(), getFirstCase() et getBeforeFirstCase() 
	 */

	/**
	 * Ajoute une voiture au d�but de la voie avec probabilit� �gale � la
	 * densit�, si la premi�re case de la voie est vide
	 */
	private void mayAddCar(int freeSpace) {
		// J'ai refais la fonction car celle de base ne vérifie pas les cases après la première
		double rnd = game.randomGen.nextDouble();
		int requiredSpace = (isRondin ? 5 : 3);
		boolean enoughSpace = (leftToRight ? (requiredSpace < freeSpace) : (requiredSpace > freeSpace));

		if (speed != 0 && rnd < density && enoughSpace) {
			cars.add(new Car(game, getBeforeFirstCase(), leftToRight, isRondin));
		}
	}

	public Case getFirstCase() {
		return new Case(leftToRight ? 0 : game.width - 1, ord);
	}

	public Case getBeforeFirstCase() {
		return new Case(leftToRight ? -1 : game.width, ord);
	}

	public ICaseSpecial getSpecialCases(Case frogCase){
		for(ICaseSpecial spec : specialCases){
			if(spec.getPosition().absc == frogCase.absc){

				if(spec.deleteOnUse()){
					specialCases.remove(spec);
					game.getGraphic().remove(spec, 3);
				}

				return spec;
			}
		}

		return null;
	}

	public int getOrd(){
		return ord;
	}

	public void addOrd(int i){
		this.ord += i;

		for (Car car : cars){
			car.newOrd(this.ord);
		}

		ArrayList<ICaseSpecial> newCases = new ArrayList<>();

		for (ICaseSpecial spec : this.specialCases){
			game.getGraphic().remove(spec, 3);

			ICaseSpecial newSpec = spec.recreate(spec.getPosition().absc, this.ord);
			newCases.add(newSpec);
			game.getGraphic().add(newSpec, 3);
		}

		ArrayList<SpriteCase> newRoadCases = new ArrayList<>();
		for(SpriteCase spr : this.roadCases){
			game.getGraphic().remove(spr, 0);

			SpriteCase c = new SpriteCase(spr.absc, this.ord, spr.getSprite());
			newRoadCases.add(c);
			game.getGraphic().add(c, 0);
		}

		this.specialCases = newCases;
		this.roadCases = newRoadCases;
	}
}
