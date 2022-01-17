package forca;

import java.util.Arrays;

public class Player {

    public Verify rw;
    private int life;
    private String letter;
    private String finalHits;
    private boolean winOrLose;
    private boolean ok;
    private static int currentScore;

    //Constructor
    public Player(int length) {
        this.letter = "";
        this.winOrLose = false;
        this.ok = false;
        this.life = 5;
        this.rw = new Verify();
        this.rw.right = new String[length];
        Arrays.fill(this.rw.right, "_ ");
        this.rw.wrong = new String[6];
        this.finalHits = "";
        for (int i = 0; i < length; i++) this.finalHits += this.rw.right[i];
    }

    // Getters init

    public int getCurrentScore() {
        return currentScore;
    }

    public boolean isOk() {
        return ok;
    }

    public boolean isWinOrLose() {
        return winOrLose;
    }

    public String getFinalHits() {
        return finalHits;
    }

    public int getLife() {
        return life;
    }

    public String getLetter() {
        return letter;
    }
    // Getters end

    // Setters init

    public void setCurrentScore(int currentScore) {
        Player.currentScore = currentScore;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void setWinOrLose(boolean winOrLose) {
        this.winOrLose = winOrLose;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public void setFinalHits(int length) {
        this.finalHits = "";
        for (int i = 0; i < length; i++) this.finalHits += this.rw.right[i];
    }

    public void setFinalHits(String finalHits) {
        this.finalHits = finalHits;
    }
    // Setters end

    // Methods
    public void testWin(String word) {
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            if (this.rw.right[i].equals(word.charAt(i) + "")) {
                count++;
            }
        }
        if (count == word.length()) {
            this.winOrLose = true;
            this.life = 6;
        }
    }
}