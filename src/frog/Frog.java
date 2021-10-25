package frog;

import gameCommons.Game;
import gameCommons.IFrog;
import util.Case;
import util.Direction;

public class Frog implements IFrog {
	
	private Game game;
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

		System.out.println (c.absc + " : " + c.ord);

		if(0 <= c.absc && c.absc <= game.width && 0 < c.ord && c.ord < game.height){
			this.dir = key;
			this.pos = c;
		}
	}
}
