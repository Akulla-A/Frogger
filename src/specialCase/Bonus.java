package specialCase;

import environment.ICaseSpecial;
import frog.Frog;
import gameCommons.IFrog;
import graphicalElements.Element;
import util.Case;
import util.Sprite;
import util.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bonus extends Element implements ICaseSpecial {
    public static final BufferedImage sprite = SpriteLoader.getPicture("specialcase_bonus.png");

    public Bonus(int abs, int ord){
        super(abs, ord, Color.YELLOW);
    }

    @Override
    public void onFrogMove(IFrog frog) {
        frog.addAliveTime();
    }

    @Override
    public Color getCaseColor() {
        return this.color;
    }

    @Override
    public BufferedImage getSprite() {
        return sprite;
    }

    @Override
    public Case getPosition(){
        return new Case(this.absc, this.ord);
    }

    @Override
    public boolean deleteOnUse() {
        return true;
    }

    @Override
    public Bonus recreate(int absc, int ord){
        return new Bonus(absc, ord);
    }
}
