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
    private int height;
    private ArrayList<LaneInf> backRoutes = new ArrayList<>();

    public EnvInf(GameInf game){
        this.game = game;
        height = game.height;

        for(int i = 0; i < 2; i++){
            this.routes.add(new LaneInf(game, i, 0));
        }

        for(int i = 2; i <= game.height; i++){
            this.routes.add(new LaneInf(game, i));
        }
    }

    @Override
    public void addLane(){
        ArrayList<LaneInf> newRoutes = new ArrayList<>();

        for (LaneInf r : routes) {
            if (r.getOrd() == 0) {
                backRoutes.add(r);
                continue;
            }

            LaneInf newLane = new LaneInf(r , r.getOrd() - 1);
            newRoutes.add(newLane);
        }

        LaneInf topLane = new LaneInf (game, game.height);
        newRoutes.add(topLane);

        routes = newRoutes;
    }

    public void backLane(){
        if(backRoutes.size() < 1)
            return;

        for(int i = 0; i < routes.size(); i++){
            if(i < game.height){
                routes.get(i).addOrd(1);
            } else {
                routes.remove(i);
                i = i - 1;
            }
        }

        LaneInf last = new LaneInf(backRoutes.get(backRoutes.size()-1), 0);
        backRoutes.remove(backRoutes.size()-1);
        routes.add(0, last);

        System.out.println("Check 1 ==========");
        for(LaneInf r : routes){
            System.out.println(r);
        }

        System.out.println("Check 2 =========");
        for(LaneInf r : backRoutes){
            System.out.println(r);
        }
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
        for(LaneInf route : routes) {
            route.update();
        }

        for(LaneInf route : backRoutes) {
            route.updateOutside();
        }
    }

    @Override
    public ICaseSpecial getSpecialFrogCase(Case frogCase){
        for(LaneInf l : routes){
            if(l.getOrd() == frogCase.ord){
                return l.getSpecialCases(frogCase);
            }
        }

        return null;
    }
}
