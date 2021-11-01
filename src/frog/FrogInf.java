package frog;

import environment.LaneInf;
import gameCommons.Game;
import gameCommons.GameInf;
import gameCommons.IFrog;
import util.Case;
import util.Direction;

public class FrogInf implements IFrog {
    private GameInf game;
    private boolean alive = true;
    private int aliveTicks = 0;
    private Case pos;
    private Direction dir;

    public void setAlive(boolean state){
        alive = state;
    }

    public boolean isAlive(){
        return alive;
    }

    public void addAliveTime(){
        ++aliveTicks;
    }

    public int getAliveTime(){
        return aliveTicks;
    }

    public FrogInf(GameInf game){
        this.game = game;
        this.pos = new Case((int)game.width/2, 1);
    }

    public Case getPosition(){
        return pos;
    }

    public Direction getDirection(){
        return dir;
    }

    public void move(Direction key){
        if(!isAlive())
            return;

        Case c = pos;

        switch(key){
            case up:
                game.getEnvironment().addLane();
                game.addScore();
                break;
            case down:
                game.getEnvironment().backLane();
                game.subScore();
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
