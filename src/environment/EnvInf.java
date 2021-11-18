package environment;

import gameCommons.Game;
import gameCommons.IEnvironment;
import util.Case;

import java.util.Random;

public class EnvInf extends Environment implements IEnvironment {
    public EnvInf(Game game){
        super();
        this.game = game;

        for(int i = 0; i < 2; i++){
            this.routes.add(new Lane(game, i, 0,false));
        }

        boolean lastChoice = false;
        int count = 0;

        for(int i = 2; i <= game.height; i++){
            boolean isRondin = new Random().nextBoolean();

            if(lastChoice != isRondin && count <= 3 ){
                this.routes.add(new Lane(game, i, (isRondin ? 2 : 1), lastChoice));
                ++count;
            } else {
                this.routes.add(new Lane(game, i, (isRondin ? 2 : 1), isRondin));
                lastChoice = isRondin;
            }
        }
    }

    @Override
    public boolean isWinningPosition(Case c) {
        return false;
    }
}
