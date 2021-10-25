package environment;

import java.util.ArrayList;
import java.util.Random;

import util.Case;
import gameCommons.Game;

public class Lane {
	private Game game;
	private int ord;
	private int speed;
	private ArrayList<Car> cars = new ArrayList<>();
	private boolean leftToRight;
	private double density;
	private int tic = 0;

	public Lane(Game game, int ord, int speed, ArrayList<Car> cars, boolean leftToRight, double density){
		this.game = game;
		this.ord = ord;
		this.speed = speed;
		this.cars = cars;
		this.leftToRight = leftToRight;
		this.density = density;
	}

	public void update() {
		++tic;

		for(Car c : cars){
			boolean outOfBounds = c.update(speed % tic == 0);

			if(outOfBounds){
				cars.remove (c);
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
			if (game.randomGen.nextDouble() < density) {
				cars.add(new Car(game, getBeforeFirstCase(), leftToRight));
			}
		}
	}

	private Case getFirstCase() {
		if (leftToRight)
			return new Case(0, ord);

		return new Case(game.width - 1, ord);
	}

	private Case getBeforeFirstCase() {
		if (leftToRight)
			return new Case(-1, ord);

		return new Case(game.width, ord);
	}
}
