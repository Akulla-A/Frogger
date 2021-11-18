package environment;

import frog.Frog;
import gameCommons.Game;
import gameCommons.GameInf;
import gameCommons.IEnvironment;
import gameCommons.IFrog;
import util.Case;

import java.util.ArrayList;
import java.util.Random;

public class Environment implements IEnvironment {
    protected ArrayList<Lane> routes = new ArrayList<>();
    protected Game game;

    public Environment(Game game){
        this.game = game;

        if(game instanceof GameInf){
            for(int i = 0; i < 2; i++){
                this.routes.add(new Lane(game, i, 0,false));
            }

            for(int i = 2; i <= game.height; i++){
                boolean isRondin = new Random().nextBoolean();
                this.routes.add(new Lane(game, i, (isRondin ? 2 : 1), isRondin));
            }
        } else {
            for(int i = 1; i <= game.height-2; i++){
                boolean isRondin = (i >= game.height/2);
                this.routes.add(new Lane(game, i, (isRondin ? 2 : 1), isRondin));
            }
        }
    }

    @Override
    public boolean changeLane(boolean down, Frog movingFrog){
        IFrog frogFirst = game.getFrog(false);
        IFrog frogSecond = game.getFrog(true);
        Case p1 = frogFirst.getPosition();
        boolean firstAlive = frogFirst.isAlive();
        int offset = (down ? 1 : -1);

        if(frogSecond != null){
            Case p2 = frogSecond.getPosition();
            boolean secondAlive = frogSecond.isAlive();

            if(secondAlive && (p2.ord <= 2 || p2.ord >= game.height - 2) && frogSecond.equals(movingFrog)){
                return false;
            }

            /*
                islowerFrog et down = false. Alors le plus haut essaye de monter la route, on ignore
                islowerFrog et down = true. Le plus bas essaye de descendre la route, on ignore
                On veut ignorer ces 2 cas, on vérifie l'inverse
            */
            Frog twinFrog = game.getFrog(!movingFrog.isSecond());
            boolean islowerFrog = movingFrog.getPosition().ord >= twinFrog.getPosition().ord;

            if(twinFrog.isAlive() && (down != islowerFrog)){
                return false;
            }

            if(!twinFrog.isAlive()){
                twinFrog.setPosition(new Case(twinFrog.getPosition().absc, -10));
            } else {
                twinFrog.setPosition(new Case(twinFrog.getPosition().absc, twinFrog.getPosition().ord + offset));
            }
        }

        if(firstAlive && (p1.ord <= 2 || p1.ord >= game.height - 2) && game.getFrog(false).equals(movingFrog)){
            return false;
        }

        // Vérifier qu'on ne va pas trop bas
        if(down && routes.get(0).getFirstCase().ord == 0){
            return false;
        }

        // Tout les checks fait, on descends la route
        for (Lane r : this.routes) {
            r.addOrd(offset);
        }

        if(!down){
            this.routes.add(new Lane(game, game.height));
        }

        return true;
    }

    @Override
    public boolean isSafe(Case c) {
        /*
        for(Lane route : routes){
            if(!route.isSafe(c)){
                return false;
            }
        }

        return !(0 > c.absc || 0 > c.ord || c.absc >= game.width || c.ord >= game.height);
        */

        return true;
    }

    @Override
    public boolean isWinningPosition(Case c) {
        return !(game instanceof GameInf) && c.ord >= game.height - 2;
    }

    @Override
    public void update() {
        for(Lane route : routes){
            route.update(false);
        }
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
