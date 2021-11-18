package environment;

import frog.Frog;
import gameCommons.IFrog;
import util.Case;
import util.Sprite;

import java.awt.*;

public interface ICaseSpecial extends Sprite {
    void onFrogMove(IFrog frog);

    Color getCaseColor();

    Case getPosition();

    boolean deleteOnUse();

    ICaseSpecial recreate(int absc, int ord);
}
