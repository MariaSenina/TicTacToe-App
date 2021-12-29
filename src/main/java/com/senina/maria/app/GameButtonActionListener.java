package com.senina.maria.app;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class GameButtonActionListener implements ActionListener {
    private static final String FILE_NAME = "statistics.txt";
    private static final int BOARD_SIZE = 3;
    private String move;
    private JButton[][] playButtons;
    private String[][] gameArray;
    private TicTacToe frame;

    public GameButtonActionListener(JButton[][] playButtons, TicTacToe frame) {
        this.playButtons = playButtons;
        this.frame = frame;
        this.gameArray = new String[BOARD_SIZE][BOARD_SIZE];
    }

    public void setMove(String move) {
        this.move = move;
    }

    public void setGameArray(String[][] gameArray) {
        this.gameArray = gameArray;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Set current dateTime
        String formattedDateTime = formatCurrentDateTime();
        frame.getXButton().setEnabled(false);
        frame.getOButton().setEnabled(false);

        for (int i = 0; i < playButtons.length; i++) {
            for (int j = 0; j < playButtons[i].length; j++) {
                JButton currentButton = playButtons[i][j];

                if(e.getSource() == currentButton) {
                    currentButton.setText(move);
                    currentButton.setEnabled(false);
                    gameArray[i][j] = currentButton.getText();

                    if (checkWinCondition()) {
                        //Write statistics to file
                        writeStatisticToFile(formattedDateTime + " - " + move + " won \n");

                        //Create dialog window
                        JOptionPane.showMessageDialog(this.frame,"'" + move + "', " + " YOU WON!",
                                "Congratulations!", JOptionPane.WARNING_MESSAGE);
                        this.frame.resetGame();
                    } else if (checkForDraw()) {
                        //Write statistics to file
                        writeStatisticToFile(formattedDateTime + " - Draw! \n");

                        //Create dialog window
                        JOptionPane.showMessageDialog(this.frame,"Better luck next time!",
                                "Draw...", JOptionPane.ERROR_MESSAGE);
                        this.frame.resetGame();
                    } else {
                        if (move.equalsIgnoreCase("x")) {
                            move = "O";
                        } else {
                            move = "X";
                        }
                    }
                    break;
                }
            }
        }
    }

    private String formatCurrentDateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return currentDateTime.format(dateTimeFormat);
    }

    private void writeStatisticToFile(String statistic) {
        //Write statistics to file
        try {
            FileWriter writer = new FileWriter(FILE_NAME, true);
            writer.write(statistic);
            writer.close();
        } catch (IOException ex) {
            System.err.println("Something went wrong.");
        }
    }

    public boolean checkForDraw() {
        boolean draw = true;

        for (String[] values : gameArray) {
            for (String value : values) {
                if (value == null) {
                    draw = false;
                    break;
                }
            }

            if (!draw) {
                break;
            }
        }

        return draw;
    }

    public boolean checkWinCondition() {
        return checkColumnWinCondition() || checkRowWinCondition() || checkLeftDiagonalWinCondition() || checkRightDiagonalWinCondition();
    }

    public boolean checkRowWinCondition() {
        boolean win = false;
        boolean equals = false;

        for (int i = 0; i < gameArray.length; i++) {
            for (int j = 0; j < gameArray[i].length - 1; j++) {
                if (gameArray[i][j] == null || gameArray[i][j+1] == null) {
                    equals = false;
                    break;
                } else if (gameArray[i][j].equalsIgnoreCase(gameArray[i][j+1])) {
                    equals = true;
                } else {
                    equals = false;
                    break;
                }
            }

            if (equals) {
                win = true;
            }
        }

        return win;
    }

    public boolean checkColumnWinCondition() {
        boolean win = false;
        boolean equals = false;

        for (int i = 0; i < gameArray.length; i++) {
            for (int j = 0; j < gameArray[i].length - 1; j++) {
                if (gameArray[j][i] == null || gameArray[j + 1][i] == null) {
                    equals = false;
                    break;
                } else if (gameArray[j][i].equalsIgnoreCase(gameArray[j + 1][i])) {
                    equals = true;
                } else {
                    equals = false;
                    break;
                }
            }

            if (equals) {
                win = true;
            }
        }

        return win;
    }

    public boolean checkLeftDiagonalWinCondition() {
        boolean win = false;
        boolean equals = false;

        for (int i = 0; i < gameArray.length - 1; i++) {
            if (gameArray[i][i] == null || gameArray[i + 1][i + 1] == null) {
                equals = false;
                break;
            } else if (gameArray[i][i].equalsIgnoreCase(gameArray[i + 1][i + 1])) {
                equals = true;
            } else {
                equals = false;
                break;
            }
        }

        if (equals) {
            win = true;
        }

        return win;
    }

    public boolean checkRightDiagonalWinCondition() {
        boolean win = false;
        boolean equals = false;

        int i = 0;
        for (int j = gameArray[i].length - 1; j > 0; j--) {
            if (gameArray[i][j] == null || gameArray[i + 1][j - 1] == null) {
                equals = false;
                break;
            } else if (gameArray[i][j].equalsIgnoreCase(gameArray[i + 1][j - 1])) {
                equals = true;
            } else {
                equals = false;
                break;
            }

            i++;
        }

        if (equals) {
            win = true;
        }

        return win;
    }
}
