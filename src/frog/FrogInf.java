package frog;

import gameCommons.Game;
import gameCommons.GameInf;
import util.Case;
import util.Direction;

public class FrogInf extends Frog {
    private GameInf game;
    private boolean alive = true;
    private int aliveTicks = 0;
    private Case pos;
    private Direction dir;

    public FrogInf(GameInf game){
        super((Game)game);
        this.game = game;
        this.pos = new Case((int)game.width/2, 1);
    }

    @Override
    public void move(Direction key){
        if(!isAlive())
            return;

        Case c = pos;

        switch(key){
            case up:
                game.getEnvironment().addLane();
                aliveTicks++;
                break;
            case down:
                game.getEnvironment().backLane();
                aliveTicks--;
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
        }
    }
}
