package panels;

import util.SpriteLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EndScreen extends JPanel implements KeyListener {
    private int pixelByCase = 32;
    private final int width;
    private final int height;

    public EndScreen(JFrame frame, int width, int height, int pixelByCase, String firstMessage, String secondMessage){
        this.pixelByCase = pixelByCase;
        this.width = width;
        this.height = height;

        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(width * pixelByCase, height * pixelByCase));

        frame.getContentPane().add(this);
        frame.pack();
        frame.addKeyListener(this);

        JLabel label = new JLabel(firstMessage);
        label.setFont(new Font("Verdana", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment (SwingConstants.BOTTOM);
        label.setForeground (Color.WHITE);
        label.setBounds(0, 0, width*pixelByCase, height/2 * pixelByCase);

        if(secondMessage != null){
            JLabel label2 = new JLabel(secondMessage);
            label2.setFont(new Font("Verdana", Font.BOLD, 20));
            label2.setHorizontalAlignment(SwingConstants.CENTER);
            label2.setForeground (Color.WHITE);
            label2.setVerticalAlignment (SwingConstants.TOP);
            label2.setBounds(0, height/2 * pixelByCase, width*pixelByCase, height/2 * pixelByCase);
            frame.getContentPane().add(label2);
        }

        frame.getContentPane().add(label);
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(SpriteLoader.getPicture("background_end.png"), 0, 0, width*pixelByCase, height*pixelByCase, this);
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }
}
