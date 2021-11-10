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

        for(int i = 1; i < game.height-2; i++){
            this.routes.add(new Lane(game, i));
        }

        this.routes.add(new Lane(game, game.height-2, 0));
    }

    @Override
    public void addLane(){
    }

    @Override
    public boolean isSafe(Case c) {
        for(Lane route : routes){
            if(!route.isSafe(c)){
                return false;
            }
        }

        return !(0 > c.absc || 0 > c.ord || c.absc >= game.width || c.ord >= game.height);
    }

    @Override
    public boolean isWinningPosition(Case c) {
        return c.ord == game.height - 1;
    }

    @Override
    public void update() {
        for(Lane route : routes){
            route.update();
        }
    }

    @Override
    public void backLane(){

    }

    public ICaseSpecial getSpecialFrogCase(Case frogCase){
        for(Lane l : routes){
            if(l.getOrd() == frogCase.ord){
                return l.getSpecialCases(frogCase);
            }
        }

        return null;
    }
}
