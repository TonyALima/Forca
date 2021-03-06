package forca;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Spritesheet {

    private BufferedImage spritesheet;
    private final int scale;

    // Constructor
    public Spritesheet(String path, int scale) {
        this.scale = 32 * scale;
        try {
            spritesheet = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters init
    public int getScale() {
        return scale;
    }

    public BufferedImage getSprite(int i, int j) {
        /*
            Get a position (i, j) and return
            the respective image in the sheet
        */

        int x = j * 32;
        int y = i * 32;

        return spritesheet.getSubimage(x, y, 32, 32);
    }
    // Getters end
}
