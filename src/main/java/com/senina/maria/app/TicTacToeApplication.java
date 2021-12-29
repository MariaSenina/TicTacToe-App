package com.senina.maria.app;

import javax.swing.*;

public class TicTacToeApplication {
    public static void main(String[] args) {
        //Create a frame
        JFrame frame = new TicTacToe();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setTitle("Tic-Tac-Toe");
        //Set frame visibility
        frame.setVisible(true);
    }
}
