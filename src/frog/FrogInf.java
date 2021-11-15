package frog;

import environment.ICaseSpecial;
import gameCommons.GameInf;
import gameCommons.IFrog;
import util.Case;
import util.Direction;
import util.Sprite;
import util.SpriteLoader;

import java.awt.image.BufferedImage;

public class FrogInf extends Frog implements IFrog, Sprite {
    public static final BufferedImage sprite = SpriteLoader.getPicture("frog_bottom.png");

    public FrogInf(GameInf game){
        super(game, game.width/2, 1);
    }

    @Override
    public void move(Direction key){
        if(!isAlive())
            return;

        Case c = pos;

        switch(key){
            case up:
                game.getEnvironment().addLane();
                break;
            case down:
                game.getEnvironment().backLane();
                break;
            case left:
                c = new Case(pos.absc - 1, pos.ord);
                break;
            case right:
                c = new Case(pos.absc + 1, pos.ord);
                break;
            default:
                return;
        }

        if(0 <= c.absc && c.absc < game.width && 0 <= c.ord && c.ord < game.height){
            this.dir = key;
            this.pos = c;

            ICaseSpecial specCase = game.getEnvironment().getSpecialFrogCase(pos);

            if(specCase != null){
                specCase.onFrogMove(this);
            }
        }
    }
}