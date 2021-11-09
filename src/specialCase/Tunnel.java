package specialCase;

import environment.ICaseSpecial;
import frog.Frog;
import gameCommons.IFrog;
import graphicalElements.Element;
import util.Case;
import util.Direction;
import util.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tunnel extends Element implements ICaseSpecial {
    public static final BufferedImage sprite = SpriteLoader.getPicture("trap.png");
    public Tunnel(int abs, int ord){
        super(abs, ord, Color.BLACK);
    }

    @Override
    public void onFrogMove(IFrog frog) {
        Direction c;
        Case pos = frog.getPosition();

        switch(frog.getDirection ()){
            case up:
                c = Direction.down;
                break;
            case down:
                c = Direction.up;
                break;
            case left:
                c = Direction.right;
                break;
            case right:
                c = Direction.left;
                break;
            default:
                return;
        }

        frog.move(c);
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
    public Tunnel recreate(int absc, int ord){
        return new Tunnel(absc, ord);
    }

    @Override
    public BufferedImage getSprite() {
        return sprite;
    }
}
