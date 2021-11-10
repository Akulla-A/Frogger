package environment;

import gameCommons.Game;
import gameCommons.GameInf;
import graphicalElements.Element;
import util.Case;
import util.SpriteCase;
import util.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class CarInf {
    private GameInf game;
    private Case leftPosition;
    private boolean leftToRight;
    private int length;
    private final Color colorLtR = Color.BLACK;
    private final Color colorRtL = Color.BLUE;
    private ArrayList<SpriteCase> roadCases = new ArrayList<>();
    public static final ArrayList<ArrayList<BufferedImage>> spriteCar = new ArrayList<>();

    public CarInf(GameInf game, Case leftPosition, boolean leftToRight, int length){
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

    public CarInf(GameInf game, Case leftPosition, boolean leftToRight){
        this(game, leftPosition, leftToRight, (new Random()).nextInt(3)+1);
    }

    public void newOrd(int ord){
        leftPosition = new Case(leftPosition.absc, ord);
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

    public boolean updateOutside(boolean moving){
        if(moving){
            this.leftPosition = new Case(this.leftPosition.absc + (leftToRight ? 1 : -1), this.leftPosition.ord);

            if(this.leftPosition.absc >= game.width || this.leftPosition.absc < -length){
                return true;
            }
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
    }
}