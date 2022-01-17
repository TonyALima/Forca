package forca;

import dataManipulation.Record;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

public class Menu extends Record {

    private final JButton selectButton;
    private final JButton removeButton;
    private final JButton addButton;
    private final JButton playButton;
    private final JPanel menuPanel;
    private final JPanel selectPanel;
    private final JPanel addPanel;
    private final JPanel removePanel;
    private final JLabel nameLabel;
    private final JLabel insertNameLabel;
    private final JLabel removeLabel;
    private final JLabel selectLabel;
    private final JComboBox<String> players;
    private final JTextField newPlayer;
    private final Color backButtons = new Color(100, 100, 100);
    private final Color backPanels = new Color(200, 200, 200);
    private final Font font = new Font("Times", Font.PLAIN, 15);
    private final Thread gameThread;

    public Menu(Thread gameThread) {
        playerDataExists();
        this.selectLabel = new JLabel("Escolha");
        this.removeLabel = new JLabel("Remover");
        this.nameLabel = new JLabel("Bom dia");
        this.insertNameLabel = new JLabel("Seu nome:");
        this.selectButton = new JButton("Selecionar jogador");
        this.addButton = new JButton("adicionar jogador");
        this.removeButton = new JButton("Remover jogador");
        this.playButton = new JButton("PLAY");
        this.players = new JComboBox<>();
        this.newPlayer = new JTextField();
        startComponents();
        this.menuPanel = new JPanel();
        this.selectPanel = new JPanel();
        this.addPanel = new JPanel();
        this.removePanel = new JPanel();
        startPanels();
        this.gameThread = gameThread;
    }

    public JPanel getMenuPanel() {
        return menuPanel;
    }

    public JPanel getSelectPanel() {
        return selectPanel;
    }

    public JPanel getAddPanel() {
        return addPanel;
    }

    public JPanel getRemovePanel() {
        return removePanel;
    }

    private void startComponents() {
        selectButton.setBackground(backButtons);
        selectButton.setFont(font);
        selectButton.addActionListener(e -> {
            updatePlayersList();
            Game.setSelectTrue();
            synchronized (gameThread) {
                gameThread.notify();
            }
        }); // go to selectPanel

        Game.setSelectTrue();
        updatePlayersList();

        addButton.setBackground(backButtons);
        addButton.setFont(font);
        addButton.addActionListener(e -> {
            Game.setAddTrue();
            synchronized (gameThread) {
                gameThread.notify();
            }
        }); // go to addPanel

        removeButton.setBackground(backButtons);
        removeButton.setFont(font);
        removeButton.addActionListener(e -> {
            updatePlayersList();
            Game.setRemoveTrue();
            synchronized (gameThread) {
                gameThread.notify();
            }
        }); // go to removePanel

        playButton.setBackground(backButtons);
        playButton.setFont(font);
        playButton.addActionListener(e -> {
            Game.setPlayTrue();
            synchronized (gameThread) {
                gameThread.notify();
            }
        }); // go to the game

        newPlayer.setFont(font);
        newPlayer.addActionListener(e -> {
            addPlayer(newPlayer.getText());
            String command = "java -jar " + new File("").getAbsolutePath() + "/Forca_jar.jar";
            try {
                Runtime.getRuntime().exec(command);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            System.exit(0);
        });


    }

    private void startPanels() {
        GroupLayout menuLayout = new GroupLayout(menuPanel);
        menuPanel.setLayout(menuLayout);
        menuLayout.setHorizontalGroup(
                menuLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGap(20)
                        .addComponent(nameLabel)
                        .addComponent(selectButton)
                        .addComponent(playButton)
                        .addGap(20)
        );
        menuLayout.setVerticalGroup(
                menuLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(menuLayout.createSequentialGroup()
                                .addGap(20)
                                .addComponent(nameLabel)
                                .addGap(10)
                                .addComponent(selectButton)
                                .addGap(10)
                                .addComponent(playButton)
                                .addGap(20))

        );
        menuPanel.setBackground(backPanels);

        GroupLayout selectLayout = new GroupLayout(selectPanel);
        selectPanel.setLayout(selectLayout);
        selectLayout.setHorizontalGroup(
                selectLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGap(20)
                        .addComponent(selectLabel)
                        .addComponent(players)
                        .addComponent(addButton)
                        .addComponent(removeButton)
                        .addGap(20)
        );
        selectLayout.setVerticalGroup(
                selectLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(selectLayout.createSequentialGroup()
                                .addGap(20)
                                .addComponent(selectLabel)
                                .addGap(10)
                                .addComponent(players)
                                .addGap(10)
                                .addComponent(addButton)
                                .addGap(10)
                                .addComponent(removeButton)
                                .addGap(20))

        );
        selectPanel.setBackground(backPanels);

        GroupLayout addLayout = new GroupLayout(addPanel);
        addPanel.setLayout(addLayout);
        addLayout.setHorizontalGroup(
                addLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(addLayout.createSequentialGroup()
                                .addGap(20)
                                .addGroup(addLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(insertNameLabel)
                                        .addComponent(newPlayer))
                                .addGap(20))
        );
        addLayout.setVerticalGroup(
                addLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(addLayout.createSequentialGroup()
                                .addGap(10)
                                .addComponent(insertNameLabel)
                                .addGap(5)
                                .addComponent(newPlayer)
                                .addGap(10))
        );
        addPanel.setBackground(backPanels);

        GroupLayout removeLayout = new GroupLayout(removePanel);
        removePanel.setLayout(removeLayout);
        removeLayout.setHorizontalGroup(
                removeLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGap(5)
                        .addComponent(removeLabel)
                        .addComponent(players)
                        .addGap(5)

        );
        removeLayout.setVerticalGroup(
                removeLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(removeLayout.createSequentialGroup()
                                .addGap(10)
                                .addComponent(removeLabel)
                                .addGap(5)
                                .addComponent(players)
                                .addGap(10))
        );
        removePanel.setBackground(backPanels);
    }

    public void updatePlayersList() {
        String[] list = new String[getAmountOfPlayers()];
        String[][] listPlayers = listPlayers();
        for (int i = 0; i < getAmountOfPlayers(); i++)
            list[i] = i + ": " + listPlayers[i][0] + " / Record -> " + listPlayers[i][1];

        players.setFont(font);
        players.setModel(new DefaultComboBoxModel<>(list));
        players.addActionListener(e -> {
            int numberPlayer = players.getSelectedIndex();
            if (Game.isRemove()) {
                removePlayer(numberPlayer);
                String command = "java -jar " + new File("").getAbsolutePath() + "/Forca_jar.jar";
                try {
                    Runtime.getRuntime().exec(command);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                System.exit(0);
            } else if (Game.isSelect()) {
                Player.setNumberPlayer(numberPlayer);
                Player.setName(listPlayers[numberPlayer][0]);
                Player.setRecord(Integer.parseInt(listPlayers[numberPlayer][1]));
            }
            Game.setMenuTrue();
            synchronized (gameThread) {
                gameThread.notify();
            }
        });
    }

}