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
	}

	public Case getPosition(){
		return pos;
	}

	public Direction getDirection(){
		return dir;
	}

	public void move(Direction key){
		this.dir = key;
	}
}
