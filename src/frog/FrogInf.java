package frog;

import environment.ICaseSpecial;
import gameCommons.GameInf;
import gameCommons.IFrog;
import util.Case;
import util.Direction;
import util.Sprite;
import util.SpriteLoader;

import java.awt.image.BufferedImage;

public class FrogInf implements IFrog, Sprite {
    private GameInf game;
    private boolean alive = true;
    private int aliveTicks = 0;
    private Case pos;
    private Direction dir;
    private boolean gonnaDie = false;

    public static final BufferedImage sprite = SpriteLoader.getPicture("frog_bottom.png");

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
        this.pos = new Case(game.width/2, 1);
    }

    @Override
    public BufferedImage getSprite() {
        return sprite;
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

    @Override
    public void setGonnaDie(boolean state) { gonnaDie = true; }

    @Override
    public boolean isGonnaDie(){ return gonnaDie; }

    @Override
    public void setAliveEnd(long time) {

    }

    @Override
    public long getAliveEndTime() {
        return 0;
    }
}