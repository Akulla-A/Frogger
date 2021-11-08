package environment;

import frog.Frog;
import util.Case;

import java.awt.*;

public interface ICaseSpecial {
    void onFrogMove(Frog frog);

    Color getCaseColor();

    Case getPosition();

    boolean deleteOnUse();
}
