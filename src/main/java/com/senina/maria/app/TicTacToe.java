package com.senina.maria.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class TicTacToe extends JFrame {
    //define constants
    private static final String FILE_NAME = "statistics.txt";
    private static final int BOARD_SIZE = 3;
    //initialize variables
    private GameButtonActionListener actionListener;
    private JPanel optionsPanel;
    private JPanel sideChoicePanel;
    private JPanel gamePanel;
    private JLabel pickSideLabel;
    private JButton[][] playButtons;
    private JButton reset;
    private JButton xButton;
    private JButton oButton;

    public TicTacToe() {
        playButtons = new JButton[BOARD_SIZE][BOARD_SIZE];
        //Add main game panel
        gamePanel = new JPanel();
        //Add options panel
        optionsPanel = new JPanel();

        addPlayButtons();
        addActionListenersToPlayButtons();
        addSidePickPanel();
        addResetButton();
        addStatisticsButton();
        createFile();
        addLayoutPattern();
    }

    public JButton getXButton() {
        return xButton;
    }

    public JButton getOButton() {
        return oButton;
    }

    public void addSidePickPanel() {
        //Create choice panel
        sideChoicePanel = new JPanel();
        //Add text "Pick a side"
        pickSideLabel = new JLabel();
        pickSideLabel.setText("Pick a side: ");
        sideChoicePanel.add(pickSideLabel);

        //Add buttons
        xButton = new JButton("X");
        oButton = new JButton("O");

        sideChoicePanel.add(xButton);
        sideChoicePanel.add(oButton);

        //Player choice
        recognizePlayerSideChoice();
    }

    public void recognizePlayerSideChoice() {
        xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionListener.setMove("X");
            }
        });

        oButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionListener.setMove("O");
            }
        });
    }

    public void addPlayButtons() {
        for (int i = 0; i < playButtons.length; i++) {
            for (int j = 0; j < playButtons[i].length; j++) {
                playButtons[i][j] = new JButton();
                gamePanel.add(playButtons[i][j]);
                playButtons[i][j].setFont(new Font("Arial", Font.BOLD, 60));
            }
        }
    }

    private void addActionListenersToPlayButtons() {
        actionListener = new GameButtonActionListener(playButtons, this);

        for (int i = 0; i < playButtons.length; i++) {
            for (int j = 0; j < playButtons[i].length; j++) {
                playButtons[i][j].addActionListener(actionListener);
            }
        }

        actionListener.setMove("X");
    }

    private void createFile() {
        File file = new File(FILE_NAME);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("ERROR: Something went wrong during file creation");
            }
        }
    }

    private void addLayoutPattern() {
        sideChoicePanel.setLayout(new FlowLayout());
        gamePanel.setLayout(new GridLayout(BOARD_SIZE,BOARD_SIZE));
        getContentPane().add(BorderLayout.NORTH, sideChoicePanel);
        getContentPane().add(BorderLayout.CENTER, gamePanel);
        getContentPane().add(BorderLayout.SOUTH, optionsPanel);
    }

    public void resetGame() {
        for (int i = 0; i < playButtons.length; i++) {
            for (int j = 0; j < playButtons[i].length; j++) {
                playButtons[i][j].setText("");
                playButtons[i][j].setEnabled(true);
                xButton.setEnabled(true);
                oButton.setEnabled(true);
            }
        }
        actionListener.setGameArray(new String[BOARD_SIZE][BOARD_SIZE]);
        actionListener.setMove("X");
    }

    private void addResetButton() {
        reset = new JButton("Reset game");
        optionsPanel.add(reset);

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
    }

    private void addStatisticsButton() {
        JButton statistics = new JButton("Match history");
        optionsPanel.add(statistics);

        statistics.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File file = new File(FILE_NAME);
                    Scanner reader = new Scanner(file);
                    if (file.length() == 0) {
                        JOptionPane.showMessageDialog(null,"There are no statistics yet.\nTime to play!",
                                "Match history", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String statistics = "Latest Results: \n";
                        while (reader.hasNextLine()) {
                            statistics = statistics + "\n" + reader.nextLine();
                        }
                        JOptionPane.showMessageDialog(null, statistics,
                                "Match history", JOptionPane.INFORMATION_MESSAGE);
                    }
                    reader.close();
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null,"Something went wrong, please try again later",
                            "Match history", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
