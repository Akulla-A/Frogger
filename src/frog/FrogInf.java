package frog;

import environment.EnvInf;
import environment.ICaseSpecial;
import gameCommons.Game;
import gameCommons.IFrog;
import util.Case;
import util.Direction;
import util.Sprite;
import util.SpriteLoader;

import java.awt.image.BufferedImage;

public class FrogInf extends Frog implements IFrog, Sprite {
	public FrogInf(Game game, boolean isSecond){
		super(game, isSecond);
		this.pos = new Case(game.width/2, 1);
	}

	@Override
	public void move(Direction key){
		if(!isAlive())
			return;

		Case c;

		switch(key){
			case up:
				c = new Case(pos.absc, pos.ord + 1);

				// Le changement a déjà été géré ?
				if (game.getEnvironment().changeLane(false, this))
					return;

				break;
			case down:
				c = new Case(pos.absc, pos.ord - 1);

				if (game.getEnvironment().changeLane(true, this))
					return;

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

		if(0 <= c.absc && c.absc < game.width && 0 < c.ord && c.ord < game.height-1){
			this.dir = key;
			this.pos = c;

			ICaseSpecial specCase = game.getEnvironment().getSpecialFrogCase(pos);

			if(specCase != null){
				specCase.onFrogMove(this);
			}
		}
	}
}
