package frog;

import environment.ICaseSpecial;
import gameCommons.Game;
import gameCommons.IFrog;
import util.Case;
import util.Direction;
import util.Sprite;
import util.SpriteLoader;

import java.awt.image.BufferedImage;

public class Frog implements IFrog, Sprite {
	protected Game game;
	protected boolean alive = true;
	protected int aliveTicks = 0;
	protected Case pos;
	protected Direction dir;
	protected boolean gonnaDie = false;
	protected long startTime;
	protected boolean isSecond;
	protected long endTime;

	public Frog(Game game, boolean isSecond){
		this.game = game;
		this.pos = new Case(game.width/2, 0);
		this.startTime = System.currentTimeMillis();
		this.isSecond = isSecond;
	}

	public Frog(Game game, int x){
		this(game, x, 0);
	}

	public Frog(Game game, int x, int y){
		this.game = game;
		this.pos = new Case(x, y);
		this.startTime = System.currentTimeMillis();
		this.isSecond = false;
	}

	public long getStartTime(){ return startTime; };

	public static final BufferedImage sprite = SpriteLoader.getPicture("frog_bottom.png");
	public static final BufferedImage sprite2 = SpriteLoader.getPicture("frog_bottom2.png");

	public void setAlive(boolean state){
		alive = state;
	}

	public void setGonnaDie(boolean state) { gonnaDie = true; }

	public boolean isGonnaDie(){ return gonnaDie; }

	public boolean isAlive(){
		return alive;
	}

	public void addAliveTime(){
		++aliveTicks;
	}

	public int getAliveTime(){
		return aliveTicks;
	}

	@Override
	public BufferedImage getSprite() {
		return (isSecond ? sprite2 : sprite);
	}

	public Case getPosition(){
		return pos;
	}

	public Direction getDirection(){
		return dir;
	}

	public void setAliveEnd(long time){
		endTime = time;
	};

	public long getAliveEndTime(){
		return endTime != 0 ? endTime : System.currentTimeMillis();
	}

	public void move(Direction key){
		if(!isAlive())
			return;

		Case c;

		switch(key){
			case up:
				c = new Case(pos.absc, pos.ord + 1);
				break;
			case down:
				c = new Case(pos.absc, pos.ord - 1);
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
