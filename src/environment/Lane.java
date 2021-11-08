package environment;

import gameCommons.Game;
import gameCommons.Main;
import graphicalElements.Element;
import util.Case;

import java.awt.*;
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

	public Lane(Game game, int ord){
		this.game = game;
		this.ord = ord;

		// aléatoire
		Random r = new Random();
		this.speed = r.nextInt(3) + 1;
		this.leftToRight = r.nextBoolean();
		this.density = r.nextDouble()%0.01+0.025;

		for(int i = 0; i < game.width; i++){
			ICaseSpecial c = Main.getSpecialCase(i, ord);

			if(c != null){
				specialCases.add(c);
			}
		}
	}

	public void update() {
		for (ICaseSpecial c : specialCases){
			this.game.getGraphic().add((Element)c);
		}

		for(int i = 0; i < cars.size(); i++){
			Car c = cars.get(i);

			if(c.update(tic % speed == 0)){
				cars.remove(c);
				--i;
			}
		}

		mayAddCar();
	}

	// TODO : ajout de methodes
	public ArrayList<Car> getCars(){
		return cars;
	}

	public boolean isSafe(Case c){
		for(Car car : cars){
			if(car.inBounds(c)){
				return false;
			}
		}

		return true;
	}

	/*
	 * Fourni : mayAddCar(), getFirstCase() et getBeforeFirstCase() 
	 */

	/**
	 * Ajoute une voiture au d�but de la voie avec probabilit� �gale � la
	 * densit�, si la premi�re case de la voie est vide
	 */
	private void mayAddCar() {
		if (isSafe(getFirstCase()) && isSafe(getBeforeFirstCase())) {
			double rnd = game.randomGen.nextDouble();

			if (rnd < density) {
				cars.add(new Car(game, getBeforeFirstCase(), leftToRight));
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
