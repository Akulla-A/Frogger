package graphicalElements;

import frog.Frog;
import gameCommons.IFrog;
import util.Case;
import util.Direction;
import util.Sprite;
import util.SpriteLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class FroggerGraphic extends JPanel implements IFroggerGraphics, KeyListener {
	private ArrayList<Element> elementsToDisplay;
	private ArrayList<ArrayList<Sprite>> spriteToDisplay;
	private int pixelByCase = 32;
	private int width;
	private int height;
	private IFrog frog;
	private JFrame frame;

	public FroggerGraphic(int width, int height) {
		this.width = width;
		this.height = height;
		elementsToDisplay = new ArrayList<Element>();
		spriteToDisplay = new ArrayList<>();

		// initialise à 5. Le but est de pouvoir faire un ordre d'affichage ( Routes avant cases spéciales )
		for(int i = 0; i < 5; i++){
			spriteToDisplay.add(new ArrayList<Sprite>());
		}

		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(width * pixelByCase, height * pixelByCase));

		JFrame frame = new JFrame("Frogger");
		this.frame = frame;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
		frame.addKeyListener(this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Element e : elementsToDisplay) {
			g.setColor(e.color);
			g.fillRect(pixelByCase * e.absc, pixelByCase * (height - 1 - e.ord), pixelByCase, pixelByCase - 1);
		}

		for(ArrayList<Sprite> v : spriteToDisplay){
			for (Sprite e : v) {
				Case pos = e.getPosition();

				if(pos.absc < 0 || pos.ord < 0 || pos.ord > height || pos.absc > width)
					continue;

				g.drawImage(e.getSprite(), pixelByCase * e.getPosition().absc, pixelByCase * (height - 1 - e.getPosition().ord), pixelByCase, pixelByCase - 1, this);
			}
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			frog.move(Direction.up);
			break;
		case KeyEvent.VK_DOWN:
			frog.move(Direction.down);
			break;
		case KeyEvent.VK_LEFT:
			frog.move(Direction.left);
			break;
		case KeyEvent.VK_RIGHT:
			frog.move(Direction.right);
		}
	}

	public void clear() {
		this.elementsToDisplay.clear();
	}
	public void add(Element e) {
		this.elementsToDisplay.add(e);
	}

	public void clearSprite() {
		this.spriteToDisplay.clear();
	}

	public void add(Sprite e, int index) {
		if(index == 3){
			//System.out.println(this.spriteToDisplay.get(index).size());
		}
		this.spriteToDisplay.get(index).add(e);
	}

	public void remove(Sprite e, int index){
		this.spriteToDisplay.get(index).remove(e);
	}

	public void setFrog(IFrog frog) {
		this.frog = frog;
	}

	public void endGameScreen(String s) {
		frame.remove(this);

		// https://stackoverflow.com/questions/12477522/jframe-to-image-without-showing-the-jframe
		//BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//Graphics2D graphics = bi.createGraphics();
		//graphics.drawImage(SpriteLoader.getPicture("background_end.png"), 0, 0, this);

		JLabel label = new JLabel(s);
		label.setFont(new Font("Verdana", 1, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setSize(this.getSize());

		frame.getContentPane().add(label);

		frame.repaint();
	}

	public Graphics getGraphics(){
		return frame.getGraphics();
	}
}
