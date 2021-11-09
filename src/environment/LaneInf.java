package environment;

import gameCommons.Game;
import gameCommons.GameInf;
import gameCommons.Main;
import graphicalElements.Element;
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
    private ArrayList<ICaseSpecial> specialCases;

    public LaneInf(GameInf game, int ord, int speed){
        this(game, ord);
        this.speed = speed;
    }

    public LaneInf(GameInf game, int ord){
        this.game = game;
        this.ord = ord;
        this.specialCases = new ArrayList<>();

        // aléatoire
        Random r = new Random();
        this.speed = r.nextInt(3) + 1;
        this.leftToRight = r.nextBoolean();
        this.density = r.nextDouble()%0.01+0.05;

        for(int i = 0; i < game.width; i++){
            ICaseSpecial c = Main.getSpecialCase(i, ord);

            if(c != null){
                specialCases.add(c);
                game.getGraphic().add(c, 3);
            }
        }
    }

    public LaneInf(LaneInf l, int newOrd){
        this.game = l.game;
        this.ord = newOrd;
        this.speed = l.speed;
        this.cars = l.cars;
        this.leftToRight = l.leftToRight;
        this.density = l.density;
        this.tic = l.tic;

        // Modifier ord des voitures
        for (CarInf car : cars){
            car.newOrd(newOrd);
        }

        ArrayList<ICaseSpecial> newCases = new ArrayList<>();

        for (ICaseSpecial spec : l.specialCases){
            newCases.add(spec.recreate(spec.getPosition().absc, newOrd));
        }

        this.specialCases = newCases;
    }

    //ajoute un entier i en paramètre à l'attribut ord
    public void addOrd(int i){
        this.ord += i;
        for (CarInf car : cars){
            car.newOrd(this.ord);
        }

        ArrayList<ICaseSpecial> newCases = new ArrayList<>();

        for (ICaseSpecial spec : this.specialCases){
            newCases.add(spec.recreate(spec.getPosition().absc, this.ord));
        }

        this.specialCases = newCases;
    }

    //soustrait un entier i en paramètre à l'attribut ord
    public void subOrd(int i){
        this.addOrd(-i);
    }

    public int getOrd(){
        return ord;
    }

    public void update() {
        if(this.speed == 0)
            return;

        for(int i = 0; i < cars.size(); i++){
            CarInf c = cars.get(i);
            if(c.update(tic % speed == 0)) {
                cars.remove(c);
                --i;
            }
        }

        mayAddCar();
    }

    public void updateOutside(){
        for(int i = 0; i < cars.size(); i++){
            CarInf c = cars.get(i);
            if(c.updateOutside(tic % speed == 0)) {
                cars.remove(c);
                --i;
            }
        }
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

    public String toString(){
        return "Ord : " + ord + ". Speed : " + speed + ". l2r : + " + leftToRight;
    }
}