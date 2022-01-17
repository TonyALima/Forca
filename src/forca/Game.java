package forca;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas {

    private Logic logic;
    private final BufferedImage image;
    private Render rendering;
    private JFrame frame;

    // Canvas dimensions
    private final int WIDTH = 160 * 2;
    private final int HEIGHT = 120 * 2;
    private final int SCALE = 2;

    // Constructor
    public Game() {
        Word word = new Word();
        word.generate(); // Choice randomly a word for the game
        this.setSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        initFrame();
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    }

    // Main
    public static void main(String[] args) {
        Game game = new Game();
        game.start(game);
    }

    // Methods
    public synchronized void start(Game game) {
        this.logic = new Logic();
        this.addKeyListener(logic);
        this.logic.start();
        this.rendering = new Render(game);
    }

    public synchronized void stop() {

        try {
            rendering.getRenderThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            logic.getGameThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void initFrame() {
        frame = new JFrame("FORCA");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setAutoRequestFocus(true);
    }

    public void render() {
        
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        g.setColor(new Color(200, 200, 200));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Render game init

        g.drawImage(logic.getForca(), 5, 5, logic.sheet.getScale(), logic.sheet.getScale(), null);

        g.setFont(logic.getFont());
        g.setColor(logic.getColorFont());
        g.drawString(logic.getErrors(), 120, 55);

        g.setFont(logic.getFont());
        g.setColor(logic.getColorFont());
        g.drawString(logic.getInstruction(), 5, 160);


        g.setColor(new Color(100, 100, 100));
        g.fillRect(156, 145, logic.getLetterSpace().width, logic.getLetterSpace().height);

        g.setFont(new Font("Times", Font.PLAIN, 15));
        g.setColor(logic.getColorFont());
        g.drawString(logic.getPoxa(), 120, 105);

        g.setFont(logic.getFont());
        g.setColor(logic.getColorFont());
        g.drawString(logic.getPlayer().getLetter(), 158, 160);

        g.setFont(logic.getFontHits());
        g.setColor(logic.getColorFont());
        g.drawString(logic.getPlayer().getFinalHits(), 5, 210);

        g.setFont(new Font("Times", Font.PLAIN, 11));
        g.setColor(logic.getColorFont());
        g.drawString("Categoria: " + logic.getWord().getCategory(), 120, 10);

        g.setFont(new Font("Times", Font.PLAIN, 9));
        g.setColor(logic.getColorFont());
        g.drawString("made by Tony Albert Lima", 190, 238);

        g.setFont(new Font("Times", Font.PLAIN, 9));
        g.setColor(logic.getColorFont());
        g.drawString("pontos: "+logic.getPlayer().getCurrentScore(), 270,24);

        // Render game end

        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        bs.show();
    }
}
