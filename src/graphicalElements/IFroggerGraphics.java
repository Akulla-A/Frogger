package graphicalElements;

import gameCommons.IFrog;
import util.Sprite;

import java.awt.*;

public interface IFroggerGraphics {
	
	/**
	 * Ajoute l'�l�ment aux �l�ments � afficher
	 * @param e
	 */
	void add(Element e);
    
    /**
     * Enl�ve tous les �l�ments actuellement affich�s
     */
    void clear();
    
    /***
     * Actualise l'affichage
     */
    void repaint();
    
    /**
     * Lie la grenouille � l'environneemnt graphique
     * @param frog
     */
    void setFrog(IFrog frog, boolean isSecond);
    
    /**
     * Lance un �cran de fin de partie
     * @param message le texte � afficher
     */
    void endGameScreen(String message);
    void endGameScreen(String message, String message2);

    // ajoutés nous-mêmes
    Graphics getGraphics();
    void add(Sprite e, int index);
    void clearSprite();
    void remove(Sprite e, int index);
}
