package specialCase;

import environment.ICaseSpecial;
import frog.Frog;
import graphicalElements.Element;

import java.awt.*;

public class Trap extends Element implements ICaseSpecial {
    public Trap(int abs, int ord){
        super(abs, ord, Color.RED);
    }

    @Override
    public void onFrogMove(Frog frog) {
        frog.setAlive(false); // A fix
    }
}
