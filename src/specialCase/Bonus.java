package specialCase;

import environment.ICaseSpecial;
import frog.Frog;
import graphicalElements.Element;

import java.awt.*;

public class Bonus extends Element implements ICaseSpecial {
    public Bonus(int abs, int ord){
        super(abs, ord, Color.YELLOW);
    }

    @Override
    public void onFrogMove(Frog frog) {
        frog.addAliveTime();
    }
}
