package environment;

import gameCommons.Game;
import gameCommons.GameInf;
import gameCommons.Main;
import graphicalElements.Element;
import util.Case;
import util.Direction;
import util.SpriteCase;
import util.SpriteLoader;

import java.awt.image.BufferedImage;
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
    private ArrayList<SpriteCase> roadCases;

    public LaneInf(GameInf game, int ord){
        this(game, ord, new Random().nextInt(3) + 1);
    }

    public LaneInf(GameInf game, int ord, int speed){
        this.game = game;
        this.ord = ord;
        this.speed = speed;
        this.specialCases = new ArrayList<>();
        this.roadCases = new ArrayList<>();

        // aléatoire
        Random r = new Random();
        this.leftToRight = r.nextBoolean();
        this.density = r.nextDouble()%0.01+0.05;

        if(speed != 0){
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

            if(ord % 2 == 0){
                sprite = Lane.topSprite;
            } else {
                sprite = Lane.bottomSprite;
            }

            SpriteCase c = new SpriteCase(i, ord, sprite);
            roadCases.add(c);
            game.getGraphic().add(c, 0);
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
        this.roadCases = new ArrayList<>();

        // Modifier ord des voitures
        for (CarInf car : cars){
            car.newOrd(newOrd);
        }

        ArrayList<ICaseSpecial> newCases = new ArrayList<>();

        for (ICaseSpecial spec : l.specialCases){
            game.getGraphic().remove(spec, 3);

            ICaseSpecial newSpec = spec.recreate(spec.getPosition().absc, newOrd);
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

        this.roadCases = newRoadCases;
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
        if(this.speed == 0)
            return;

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
        if (this.speed != 0 && isSafe(getFirstCase()) && isSafe(getBeforeFirstCase())) {
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