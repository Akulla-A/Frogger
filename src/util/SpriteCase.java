package util;

import java.awt.image.BufferedImage;

public class SpriteCase extends Case implements Sprite {
    private final BufferedImage sprite;

    public SpriteCase(int absc, int ord, BufferedImage sprite){
        super(absc, ord);
        this.sprite = sprite;
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
