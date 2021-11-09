package environment;

import frog.Frog;
import gameCommons.IFrog;
import util.Case;

import java.awt.*;

public interface ICaseSpecial {
    void onFrogMove(IFrog frog);

    Color getCaseColor();

    Case getPosition();

    boolean deleteOnUse();

    // J'avais besoin d'avoir une fonction avec un type template pour recréer l'objet facilement dans laneInf
    <T extends ICaseSpecial> T recreate(int absc, int ord);
}
