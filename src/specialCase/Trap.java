package specialCase;

import environment.ICaseSpecial;
import frog.Frog;
import graphicalElements.Element;
import util.Case;

import java.awt.*;

public class Trap extends Element implements ICaseSpecial {
    public Trap(int abs, int ord){
        super(abs, ord, Color.RED);
    }

    @Override
    public void onFrogMove(Frog frog) {
        frog.setAlive(false); // A fix
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
