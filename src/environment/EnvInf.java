package environment;

import gameCommons.GameInf;
import gameCommons.IEnvironment;
import util.Case;

import java.util.ArrayList;
import java.util.Random;

public class EnvInf extends Environment implements IEnvironment {
    private final ArrayList<Lane> backRoutes = new ArrayList<>();

    public EnvInf(GameInf game){
        super(game);

        for(int i = 0; i < 2; i++){
            this.routes.add(new Lane(game, i, 0,false));
        }

        for(int i = 2; i <= game.height; i++){
            boolean isRondin = new Random ().nextBoolean();
            this.routes.add(new Lane(game, i, (isRondin ? 2 : 1), isRondin));
        }
    }

    @Override
    public void addLane(){
        ArrayList<Lane> newRoutes = new ArrayList<>();

        for (Lane r : this.routes) {
            Lane newLane = new Lane(r, r.getOrd() - 1);

            if (r.getOrd() < 0) {
                backRoutes.add(newLane);
            } else {
                newRoutes.add(newLane);
            }
        }

        Lane topLane = new Lane(game, game.height);
        newRoutes.add(topLane);

        routes = newRoutes;
    }

    public void backLane(){
        if(backRoutes.size() == 0)
            return;

        for(int i = 0; i < routes.size(); i++){
            if(i < game.height){
                routes.get(i).addOrd(1);
            } else {
                routes.remove(i);
                i = i - 1;
            }
        }

        Lane oldRoute = backRoutes.get(backRoutes.size()-1);

        Lane last = new Lane(oldRoute, 0);
        backRoutes.remove(oldRoute);
        routes.add(0, last);
    }

    @Override
    public boolean isWinningPosition(Case c) {
        return false;
    }
}
