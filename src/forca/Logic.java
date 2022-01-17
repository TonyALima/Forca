package forca;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Logic implements Runnable, KeyListener {

    private Word word;
    private Player player;
    private BufferedImage forca;
    private String errors, poxa, instruction;
    private Font font, fontHits;
    private Color colorFont;
    private final Thread gameThread;
    private Rectangle letterSpace;
    public final Spritesheet sheet;
    private boolean isRunning, again;

    // Getters init
    public Word getWord() {
        return word;
    }

    public Player getPlayer() {
        return player;
    }

    public BufferedImage getForca() {
        return forca;
    }

    public String getErrors() {
        return errors;
    }

    public String getPoxa() {
        return poxa;
    }

    public String getInstruction() {
        return instruction;
    }

    public Font getFont() {
        return font;
    }

    public Font getFontHits() {
        return fontHits;
    }

    public Color getColorFont() {
        return colorFont;
    }

    public Thread getGameThread() {
        return gameThread;
    }

    public Rectangle getLetterSpace() {
        return letterSpace;
    }
    // Getters end

    // Constructor
    public Logic() {
        sheet = new Spritesheet("/spritesheet.png", 4);
        this.gameThread = new Thread(this);
    }

    // Methods init
    public void start() {

        this.errors = "";
        this.word = new Word();
        this.word.generate(); // Choice randomly a word for the game

        this.player = new Player(word.getWord().length());

        isRunning = true;

        this.gameThread.start();
    }

    private void restart() {
        this.errors = "";
        this.word = new Word();
        this.word.generate(); // Choice randomly a new word for the game

        this.player = new Player(word.getWord().length());
    }

    public void makeVerify() {
        player.rw.verify(word.getWord(), player);
    }

    private void tick() {
        makeVerify();
        player.setFinalHits(word.getWord().length());
        player.testWin(word.getWord());
        if (player.isWinOrLose()) {
            again = false;
            setImageRender(sheet);
            while (true) {
                synchronized (gameThread) {
                    try {
                        gameThread.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (again) {
                    restart();
                    again = false;
                    break;
                }
            }
        }
    }

    public void setImageRender(Spritesheet sheet) {
        font = new Font("Times", Font.PLAIN, 18);
        fontHits = font;
        colorFont = new Color(0, 0, 0);

        instruction = "Digite uma letra: ";
        letterSpace = new Rectangle(16, 19);
        poxa = "";

        if (player.getLife() == 5) {
            this.forca = sheet.getSprite(0, 0);
            this.errors = "";
        } else if (player.getLife() == 4) {
            this.forca = sheet.getSprite(0, 1);
            this.errors = "Erros: " + player.rw.wrong[0];
        } else if (player.getLife() == 3) {
            this.forca = sheet.getSprite(0, 2);
            this.errors = "Erros: " + player.rw.wrong[0] + "," + player.rw.wrong[1];
        } else if (player.getLife() == 2) {
            this.forca = sheet.getSprite(0, 3);
            this.errors = "Erros: " + player.rw.wrong[0] + "," + player.rw.wrong[1] + "," + player.rw.wrong[2];
        } else if (player.getLife() == 1) {
            this.forca = sheet.getSprite(1, 0);
            this.errors = "Erros: " + player.rw.wrong[0] + "," + player.rw.wrong[1] + "," + player.rw.wrong[2] + "," + player.rw.wrong[3];
        } else if (player.getLife() == 0) {
            this.forca = sheet.getSprite(1, 1);
            this.errors = "Erros: " + player.rw.wrong[0] + "," + player.rw.wrong[1] + "," + player.rw.wrong[2] + "," + player.rw.wrong[3] + "," + player.rw.wrong[4];
        } else if (player.getLife() == -1) {
            font = new Font("Times", Font.PLAIN, 25);
            colorFont = new Color(200, 0, 0);

            this.forca = sheet.getSprite(1, 2);
            this.errors = "VOCÊ PERDEU";
            this.fontHits = new Font("Times", Font.PLAIN, 15);
            this.player.setFinalHits("para isso pressione a BARRA DE ESPAÇO");
            this.poxa = ("A palavra era: " + word.getWord());
            this.instruction = "Tente novamente!";
            this.letterSpace = new Rectangle(0, 0);
            this.player.setLetter("");
            this.player.setCurrentScore(0);
        } else if (player.getLife() == 6) {
            font = new Font("Times", Font.PLAIN, 20);
            colorFont = new Color(0, 200, 0);
            this.fontHits = new Font("Times", Font.PLAIN, 15);
            this.player.setFinalHits("para isso pressione a BARRA DE ESPAÇO");
            this.forca = sheet.getSprite(1, 3);
            this.errors = "VOCÊ VENCEU!!";
            this.instruction = "Parabéns, continue jogando!";
            this.letterSpace = new Rectangle(0, 0);
            this.player.setLetter("");
            this.player.setCurrentScore(this.player.getCurrentScore() + 1);
            if (this.player.getCurrentScore() > Player.getRecord()){
                Player.setRecord(this.player.getCurrentScore());
                Menu.updatePlayerRecord(Player.getNumberPlayer(),this.player.getCurrentScore());
            }
        } else {
            throw new IllegalStateException("life cannot be " + player.getLife());
        }
    }
    // Methods end

    // Interface implementations
    @Override
    public void run() {
        setImageRender(sheet);
        while (isRunning) {
            synchronized (gameThread) {
                try {
                    gameThread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (player.isOk()) {
                tick();
                player.setLetter("");
                setImageRender(sheet);
                player.setOk(false);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (!player.isOk()) {
            player.setLetter(e.getKeyChar() + "");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            player.setOk(true);
            synchronized (gameThread) {
                gameThread.notify();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            again = true;
            synchronized (gameThread) {
                gameThread.notify();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
