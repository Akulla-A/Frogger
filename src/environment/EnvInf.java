package environment;

import gameCommons.Game;
import gameCommons.IEnvironment;
import graphicalElements.Element;
import util.Case;
import util.Direction;
import gameCommons.GameInf;
import java.util.ArrayList;

public class EnvInf implements IEnvironment {
    private ArrayList<LaneInf> routes = new ArrayList<>();
    private GameInf game;

    public EnvInf(GameInf game){
        this.game = game;

        for(int i = 2; i <= game.height; i++){
            this.routes.add(new LaneInf(game, i));
        }
    }
    private int height = game.height;
    @Override
    public void addLane(){
        height++;
        routes.add(new LaneInf (game, height));
    }

    @Override
    public boolean isSafe(Case c) {
        for(LaneInf route : routes){
            if(!route.isSafe(c)){
                return false;
            }
        }

        return !(0 > c.absc || 0 > c.ord || c.absc >= game.width || c.ord >= game.height);
    }

    @Override
    public boolean isWinningPosition(Case c) {
        return false;
    }



    @Override
    public void update() {
        if(game.getFrog().getDirection() == Direction.up){
            addLane();
        }
        for(LaneInf route : routes) {
            route.update();
        }

       /* if(game.getFrog().getDirection() == Direction.up) {
            int index = game.getFrog().getPosition().ord -1;
            routes.remove(index);
            routes.add(new LaneInf(game, routes.size()));
        }else{
            if(game.getFrog().getDirection() == Direction.down && game.getFrog().getPosition().ord >= 0 ){
                routes.remove(routes.size()-1);
                int index2 = game.getFrog().getPosition().ord -1;
                routes.add(new LaneInf(game, index2));
            }
        }



                for(LaneInf route : routes) {
                    route.update();
                    if(route.getOrd() >= 0 && route.getOrd() <= game.height-5) {


                        route.addOrd(1);

                    }

                //routes.add(new LaneInf(game, ));
             } else {
                if (game.getFrog().getDirection() == Direction.down ) {
                    routes.get(i).subOrd(1);

                }

            }*/
        }


}

