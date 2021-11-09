package graphicalElements;

import environment.ICaseSpecial;
import frog.Frog;
import gameCommons.IFrog;
import util.Sprite;

import java.awt.*;

public interface IFroggerGraphics {
	
	/**
	 * Ajoute l'�l�ment aux �l�ments � afficher
	 * @param e
	 */
    public void add(Element e);
    
    /**
     * Enl�ve tous les �l�ments actuellement affich�s
     */
    public void clear();
    
    /***
     * Actualise l'affichage
     */
    public void repaint();
    
    /**
     * Lie la grenouille � l'environneemnt graphique
     * @param frog
     */
    public void setFrog(IFrog frog);
    
    /**
     * Lance un �cran de fin de partie
     * @param message le texte � afficher
     */
    public void endGameScreen(String message);

    // ajoutés nous-mêmes
    public Graphics getGraphics();
    public void add(Sprite e, int index);
    public void clearSprite();
    public void remove(Sprite e, int index);
}
