package environment;

import gameCommons.Game;
import gameCommons.GameInf;
import util.Case;
import util.Direction;

import java.util.ArrayList;
import java.util.Random;

public class LaneInf {
    private GameInf game;
    private int ord;
    private int speed;
    private ArrayList<CarInf> cars = new ArrayList<>();
    private boolean leftToRight;
    private double density;
    private int tic = 0;

    public LaneInf(GameInf game, int ord){
        this.game = game;
        this.ord = ord;

        // aléatoire
        Random r = new Random();
        this.speed = r.nextInt(3) + 1;
        this.leftToRight = r.nextBoolean();
        this.density = r.nextDouble()%0.01+0.05;
    }
    //ajoute un entier i en paramètre à l'attribut ord
    public void addOrd(int i){
        this.ord += i;
    }

    //soustrait un entier i en paramètre à l'attribut ord
    public void subOrd(int i){
        this.ord -= i;
    }


    public int getOrd(){
        return ord;
    }

    public void update() {
        /*if(ord == game.getFrog().getPosition().ord -1 && game.getFrog().getDirection() == Direction.up){
            for(int j = 0; j < cars.size(); j++){
                if(ord == game.getFrog().getPosition().ord -1 && game.getFrog().getDirection() == Direction.up){
                    ord--;
                }else{
                     if(){}
                } */
            for(int i = 0; i < cars.size(); i++){
                CarInf c = cars.get(i);
                if(c.update(tic % speed == 0)) {
                    cars.remove(c);
                    --i;
                }
            }
        mayAddCar();
        /*if (game.getFrog().getDirection() == Direction.up) {

            this.ord += 1;
        }*/
    }

    // TODO : ajout de methodes
    public ArrayList<CarInf> getCars(){
        return cars;
    }

    public boolean isSafe(Case c){
        for(CarInf car : cars){
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
                cars.add(new CarInf(game, getBeforeFirstCase(), leftToRight));
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
