package forca;

public class Verify {

    public String[] right;
    public String[] wrong;

    // Methods
    public void verify(String word, Player player) {
        int z = 0;
        for (int i = 0; i < word.length(); i++) {
            if (player.getLetter().equals(word.charAt(i) + "")) {
                this.right[i] = player.getLetter();
                z++;
            }
        }
        if (z == 0) {
            this.wrong[(5 - player.getLife())] = player.getLetter();
            player.setLife(player.getLife() - 1);
            if (player.getLife() == -1) {
                player.setWinOrLose(true);
            }
        }
    }
}