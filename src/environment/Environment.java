package environment;

import gameCommons.Game;
import gameCommons.IEnvironment;
import util.Case;

import java.util.ArrayList;

public class Environment implements IEnvironment {
    private ArrayList<Lane> routes = new ArrayList<>();
    private Game game;

    public Environment(Game game){
        this.game = game;

        for(int i = 0; i < game.height; i++){
            this.routes.add(new Lane(game, i));
        }
    }
    @Override
    public boolean isSafe(Case c) {
        for(Lane route : routes){
            if(!route.isSafe(c)){
                return false;
            }
        }

        return 0 > c.absc || c.absc > game.width || 0 >= c.ord || c.ord >= game.height;
    }

    @Override
    public boolean isWinningPosition(Case c) {
        return c.ord == game.height-1;
    }

    @Override
    public void update() {
        for(Lane route : routes){
            route.update();
        }
    }
}
