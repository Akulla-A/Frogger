package frog;

import environment.ICaseSpecial;
import gameCommons.Game;
import gameCommons.IFrog;
import util.Case;
import util.Direction;

public class Frog implements IFrog {

	protected Game game;
	protected boolean alive = true;
	protected int aliveTicks = 0;
	protected Case pos;
	protected Direction dir;

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

	public Frog(Game game){
		this.game = game;
		this.pos = new Case((int)game.width/2, 0);
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
