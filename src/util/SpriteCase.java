package util;

import java.awt.image.BufferedImage;

public class SpriteCase extends Case implements Sprite {
    private BufferedImage sprite;

    public SpriteCase(int absc, int ord, BufferedImage sprite){
        super(absc, ord);
        this.sprite = sprite;
    }

    public SpriteCase(int absc, int ord, SpriteCase e){
        super(absc, ord);
        this.sprite = e.getSprite();
    }

    @Override
    public BufferedImage getSprite() {
        return sprite;
    }

    @Override
    public Case getPosition() {
        return this;
    }
}
