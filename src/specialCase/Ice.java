package specialCase;

import environment.ICaseSpecial;
import frog.Frog;
import gameCommons.IFrog;
import graphicalElements.Element;
import util.Case;

import java.awt.*;

public class Ice extends Element implements ICaseSpecial {
    public Ice(int abs, int ord){
        super(abs, ord, Color.cyan);
    }

    @Override
    public void onFrogMove(IFrog frog) {
        frog.move(frog.getDirection());
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
    public boolean deleteOnUse() { return false; }

    @Override
    public Ice recreate(int absc, int ord){
        return new Ice(absc, ord);
    }
}
