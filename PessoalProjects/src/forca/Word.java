package forca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class Word {

    private final String[] categories = {"animal", "alimento", "parte_do_corpo"};
    private String[] list;

    private String Word;
    private String category;

    // Getters init
    public String getWord() {
        return Word;
    }

    public String getCategory() {
        return category;
    }
    // Getters end

    // Methods
    public void generate() {
        Random random = new Random();

        int cr = random.nextInt(categories.length);
        this.category = categories[cr];

        String arq = "/"+category+".txt";
        InputStream in = getClass().getResourceAsStream(arq);
        BufferedReader input = new BufferedReader(new InputStreamReader(in));

        try {
            int qtd = Integer.parseInt(input.readLine());
            list = new String[qtd];
            for (int i = 0; i< qtd; i++){
                list[i] = input.readLine();
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int wr = random.nextInt(list.length);
        this.Word = list[wr];
    }
}