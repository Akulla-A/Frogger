package environment;

import frog.Frog;
import gameCommons.Game;
import gameCommons.IEnvironment;
import gameCommons.IFrog;
import util.Case;

import java.util.ArrayList;

public class Environment implements IEnvironment {
    protected ArrayList<Lane> routes = new ArrayList<>();
    protected Game game;

    // Constructeur vide, pour éviter de créer les voitures, qu'on va créer dans EnvInf
    public Environment(){}

    public Environment(Game game){
        this.game = game;

        for(int i = 1; i <= game.height-2; i++){
            boolean isRondin = (i >= game.height/2);
            this.routes.add(new Lane(game, i, (isRondin ? 2 : 1), isRondin));
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
            /*
                islowerFrog et down = false. Alors le plus haut essaye de monter la route, on ignore
                islowerFrog et down = true. Le plus bas essaye de descendre la route, on ignore
                On veut ignorer ces 2 cas, on vérifie l'inverse
            */

            Case p2 = frogSecond.getPosition();
            boolean secondAlive = frogSecond.isAlive();
            Frog twinFrog = game.getFrog(!movingFrog.isSecond());
            boolean islowerFrog = movingFrog.getPosition().ord >= twinFrog.getPosition().ord;

            if(secondAlive && (p2.ord <= 2 || p2.ord >= game.height - 2) && frogSecond.equals(movingFrog)){
                return false;
            } else if(twinFrog.isAlive() && (down != islowerFrog)){
                return false;
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
        for(Lane route : routes){
            if(!route.isSafe(c)){
                return false;
            }
        }

        return !(0 > c.absc || 0 > c.ord || c.absc >= game.width || c.ord >= game.height);
    }

    @Override
    public boolean isWinningPosition(Case c) {
        return c.ord >= game.height - 2;
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
