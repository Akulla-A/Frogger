package specialCase;

import environment.ICaseSpecial;
import frog.Frog;
import graphicalElements.Element;

import java.awt.*;

public class Ice extends Element implements ICaseSpecial {
    public Ice(int abs, int ord){
        super(abs, ord, Color.cyan);
    }

    @Override
    public void onFrogMove(Frog frog) {
        frog.move(frog.getDirection());
        frog.move(frog.getDirection());
    }
}
