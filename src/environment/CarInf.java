package environment;

import gameCommons.Game;
import gameCommons.GameInf;
import graphicalElements.Element;
import util.Case;

import java.awt.*;
import java.util.Random;

public class CarInf {
    private GameInf game;
    private Case leftPosition;
    private boolean leftToRight;
    private int length;
    private final Color colorLtR = Color.BLACK;
    private final Color colorRtL = Color.BLUE;

    public CarInf(GameInf game, Case leftPosition, boolean leftToRight, int length){
        this.game = game;
        this.leftPosition = leftPosition;
        this.leftToRight = leftToRight;
        this.length = length;
    }

    public CarInf(GameInf game, Case leftPosition, boolean leftToRight){
        this.game = game;
        this.leftPosition = leftPosition;
        this.leftToRight = leftToRight;
        this.length = (new Random()).nextInt(3)+1;
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

            if(this.leftPosition.absc >= game.width || this.leftPosition.absc < -length){
                return true;
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
}
