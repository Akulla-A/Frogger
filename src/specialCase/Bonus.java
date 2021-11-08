package specialCase;

import environment.ICaseSpecial;
import frog.Frog;
import graphicalElements.Element;
import util.Case;

import java.awt.*;

public class Bonus extends Element implements ICaseSpecial {
    public Bonus(int abs, int ord){
        super(abs, ord, Color.YELLOW);
    }

    @Override
    public void onFrogMove(Frog frog) {
        frog.addAliveTime();
    }

    @Override
    public Color getCaseColor() {
        return this.color;
    }

    @Override
    public Case getPosition(){
        return new Case(this.absc, this.ord);
    }

    @Override
    public boolean deleteOnUse() { return true; };
}
