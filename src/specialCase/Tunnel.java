package specialCase;

import environment.ICaseSpecial;
import frog.Frog;
import graphicalElements.Element;
import util.Case;
import util.Direction;

import java.awt.*;

public class Tunnel extends Element implements ICaseSpecial {
    public Tunnel(int abs, int ord){
        super(abs, ord, Color.gray);
    }

    @Override
    public void onFrogMove(Frog frog) {
        Direction c;
        Case pos = frog.getPosition();

        switch(frog.getDirection ()){
            case up:
                c = Direction.down;
                break;
            case down:
                c = Direction.up;
                break;
            case left:
                c = Direction.right;
                break;
            case right:
                c = Direction.left;
                break;
            default:
                return;
        }

        frog.move(c);
    }
}
