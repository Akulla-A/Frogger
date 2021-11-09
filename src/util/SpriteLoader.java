package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SpriteLoader {
    static public BufferedImage getPicture(String spriteName) {
        try {
            // https://stackoverflow.com/questions/9864267/loading-resources-like-images-while-running-project-distributed-as-jar-archive/9866659#9866659
            // https://stackoverflow.com/questions/14661571/java-how-can-i-import-and-display-sprites?noredirect=1&lq=1
            var pic = SpriteLoader.class.getResource("/resources/images/" + spriteName);

            try {
                var img = ImageIO.read(pic);
                return img;
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                return null;
            } catch (java.io.IOException e){
                System.out.println(e.getMessage());
                return null;
            }
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
