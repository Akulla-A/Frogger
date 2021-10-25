package frog;

import gameCommons.Game;
import gameCommons.IFrog;
import util.Case;

public class Frog {
	
	private Game game;
	private boolean alive = true;
	private int aliveTicks = 0;
	private Case pos;

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
}
