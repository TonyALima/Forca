package dataManipulation;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class Record {
    private static final String local = new File("").getAbsolutePath();

    public void addPlayer(String name) {
        int np = getAmountOfPlayers();

        new File(local + "/playersData/player" + np).mkdir();

        try (FileWriter file = new FileWriter(local + "/playersData/player" + np + "/name.txt");
            BufferedWriter buffer = new BufferedWriter(file);
            PrintWriter printer = new PrintWriter(buffer)) {
            printer.print(name);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter file = new FileWriter(local + "/playersData/player" + np + "/score.txt");
            BufferedWriter buffer = new BufferedWriter(file);
            PrintWriter printer = new PrintWriter(buffer)) {
            printer.print(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setAmountOfPlayers(getAmountOfPlayers() + 1);
    }

    public void removePlayer(int nPlayer) {
        File file = new File(local + "/playersData/player" + nPlayer);
        if (file.exists()) {
            File[] filesIn = file.listFiles();
            filesIn[0].delete();
            filesIn[1].delete();
            file.delete();

            int n;
            for (int i = nPlayer + 1; i < getAmountOfPlayers(); i++) {
                n = i - 1;
                new File(local + "/playersData/player" + i)
                        .renameTo(new File(local + "/playersData/player" + n));
            }
            setAmountOfPlayers(getAmountOfPlayers() - 1);
        } else {
            System.out.println("this player does not exist");
        }
    }

    protected int getAmountOfPlayers() {
        int np;
        File file = new File(local + "/playersData/amountOfPlayers.txt");
        try {
            FileReader reader = new FileReader(file);
            BufferedReader input = new BufferedReader(reader);
            np = Integer.parseInt(input.readLine());
            input.close();
            return np;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void setAmountOfPlayers(int amount) {
        try (FileWriter file = new FileWriter(local + "/playersData/amountOfPlayers.txt");
            BufferedWriter buffer = new BufferedWriter(file);
            PrintWriter printer = new PrintWriter(buffer)) {
            printer.print("" + amount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[][] listPlayers() {
        Record record = new Record();

        int np = record.getAmountOfPlayers();
        String[][] playersList = new String[np][2];
        File file;
        for (int i = 0; i < np; i++) {
            String currentPlayer = "player" + i;
            file = new File(local + "/playersData/" + currentPlayer + "/name.txt");
            try {
                FileReader reader = new FileReader(file);
                BufferedReader input = new BufferedReader(reader);
                playersList[i][0] = input.readLine();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            file = new File(local + "/playersData/" + currentPlayer + "/score.txt");
            try {
                FileReader reader = new FileReader(file);
                BufferedReader input = new BufferedReader(reader);
                playersList[i][1] = input.readLine();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return playersList;
    }

    public static void updatePlayerRecord(int nPlayer, int score) {
        try (FileWriter file = new FileWriter(local + "/playersData/player" + nPlayer + "/score.txt");
            BufferedWriter buffer = new BufferedWriter(file);
            PrintWriter printer = new PrintWriter(buffer)) {
            printer.print(score);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playerDataExists() {
        File file = new File(local + "/playersData");
        if (!file.exists()) {
            file.mkdir();
            setAmountOfPlayers(0);
        }
    }
}
