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
	private Game game;
	private int ord;
	private int speed;
	private ArrayList<Car> cars = new ArrayList<>();
	private boolean leftToRight;
	private double density;
	private int tic = 0;
	private ArrayList<ICaseSpecial> specialCases = new ArrayList<>();
	private ArrayList<SpriteCase> roadCases = new ArrayList<>();
	public static final BufferedImage bottomSprite = SpriteLoader.getPicture("roadbottom.png");
	public static final BufferedImage topSprite = SpriteLoader.getPicture("roadtop.png");
	public static final BufferedImage concreteSprite = SpriteLoader.getPicture("concrete.png");
	public static final BufferedImage spriteWater = SpriteLoader.getPicture("water.png");

	private boolean isRondin;

	public Lane(Game game, int ord, boolean isRondin){
		this(game, ord, new Random().nextInt(2) + 1, isRondin);
	}

	public Lane(Game game, int ord, int speed, boolean isRondin){
		this.game = game;
		this.ord = ord;
		this.isRondin = isRondin;

		// aléatoire
		Random r = new Random();
		this.speed = (isRondin ? 2 : 1);
		this.leftToRight = ord%2==0;

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

	public void update() {
		IFrog frog1 = this.game.getFrog(false);
		IFrog frog2 = this.game.getFrog(true);
		Case pos1 = frog1.getPosition();
		Case pos2 = frog2.getPosition();

		++tic;
		boolean alreadyMoveFrog1 = false;
		boolean alreadyMoveFrog2 = false;

		boolean canSpawn = true;

		for(int i = 0; i < cars.size(); i++){
			boolean carTickMove = (speed != 0 && tic % speed == 0);
			Car c = cars.get(i);
			boolean inBoundsFrog1 = c.inBounds(pos1);
			boolean inBoundsFrog2 = c.inBounds(pos2);

			if(c.update(carTickMove)){
				cars.remove(c);
				--i;
			}

			if(this.isRondin && inBoundsFrog1 && carTickMove && !alreadyMoveFrog1){
				alreadyMoveFrog1 = true;
				frog1.move(leftToRight ? Direction.right : Direction.left);
			}

			if(this.isRondin && inBoundsFrog2 && carTickMove && !alreadyMoveFrog2){
				alreadyMoveFrog2 = true;
				frog2.move(leftToRight ? Direction.right : Direction.left);
			}
		}

		mayAddCar();
	}

	// TODO : ajout de methodes
	public ArrayList<Car> getCars(){
		return cars;
	}

	public boolean isSafe(Case c){
		if(c.ord != ord){
			return true;
		}

		boolean onCar = false;

		for(Car car : cars){
			if(car.inBounds(c)){
				onCar = true;
			}
		}

		return onCar == isRondin;
	}

	public boolean isRondin(){
		return isRondin;
	}

	/*
	 * Fourni : mayAddCar(), getFirstCase() et getBeforeFirstCase() 
	 */

	/**
	 * Ajoute une voiture au d�but de la voie avec probabilit� �gale � la
	 * densit�, si la premi�re case de la voie est vide
	 */
	private void mayAddCar() {
		boolean isFirstSafe = isSafe(getFirstCase());
		boolean isBeforeSafe = isSafe(getBeforeFirstCase());

		isFirstSafe = (isRondin != isFirstSafe);
		isBeforeSafe = (isRondin != isBeforeSafe);
		boolean isPreLengthSafe = (isRondin != isSafe(new Case(getFirstCase().absc + 5, getFirstCase().ord)));

		if (speed != 0 && isFirstSafe && isBeforeSafe && (!isRondin || isPreLengthSafe)) {
			double rnd = game.randomGen.nextDouble();

			if (rnd < density) {
				cars.add(new Car(game, getBeforeFirstCase(), leftToRight, isRondin));
			}
		}
	}

	protected Case getFirstCase() {
		if (leftToRight)
			return new Case(0, ord);

		return new Case(game.width - 1, ord);
	}

	protected Case getBeforeFirstCase() {
		if (leftToRight)
			return new Case(-1, ord);

		return new Case(game.width, ord);
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
}
