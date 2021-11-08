package environment;

import gameCommons.GameInf;
import util.Case;

import java.awt.*;
import java.util.Random;

public class CarInf extends Car {
    private GameInf game;
    private Case leftPosition;
    private boolean leftToRight;
    private int length;
    private final Color colorLtR = Color.BLACK;
    private final Color colorRtL = Color.BLUE;

    public CarInf(GameInf game, Case leftPosition, boolean leftToRight, int length){
        super(game, leftPosition, leftToRight, length );
        this.game = game;
        this.leftPosition = leftPosition;
        this.leftToRight = leftToRight;
        this.length = length;
    }

    public CarInf(GameInf game, Case leftPosition, boolean leftToRight){
        super(game, leftPosition, leftToRight);
        this.game = game;
        this.leftPosition = leftPosition;
        this.leftToRight = leftToRight;
        this.length = (new Random()).nextInt(3)+1;
    }

    public void newOrd(int ord){
        leftPosition = new Case(leftPosition.absc, ord);
    }

    //TODO : ajout de methodes
    public boolean updateOutside(boolean moving){
        if(moving){
            this.leftPosition = new Case(this.leftPosition.absc + (leftToRight ? 1 : -1), this.leftPosition.ord);

            if(this.leftPosition.absc >= game.width || this.leftPosition.absc < -length){
                return true;
            }
        }

        return false;
    }
}
