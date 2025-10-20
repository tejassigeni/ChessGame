package com.ChessGame.Engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChessGameUI extends JFrame {

    private static final int BOARD_SIZE = 8;
    private static final int SQUARE_SIZE = 80;
    private JPanel boardPanel;
    private JButton[][] squares;
    private ChessGameEngine gameEngine;
    private int selectedRow = -1;
    private int selectedCol = -1;

    public ChessGameUI() {
        gameEngine = new ChessGameEngine();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        boardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        squares = new JButton[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                squares[row][col] = new JButton();
                squares[row][col].setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
                squares[row][col].setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.GRAY);
                final int finalRow = row;
                final int finalCol = col;
                squares[row][col].addActionListener(e -> handleSquareClick(finalRow, finalCol));
                boardPanel.add(squares[row][col]);
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        JPanel statusPanel = new JPanel();
        JLabel statusLabel = new JLabel("White's turn");
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.SOUTH);

        updateBoardDisplay();

        pack();
        setLocationRelativeTo(null);
    }

    private void handleSquareClick(int row, int col) {
        if (gameEngine.isGameOver()) {
            JOptionPane.showMessageDialog(this, "Game is already over! " + gameEngine.getWinner() + " wins.");
            return;
        }
        if (selectedRow == -1) {
            char piece = gameEngine.getBoard()[row][col];
            if (piece != ' ' && (Character.isUpperCase(piece) == gameEngine.isWhiteTurn())) {
                selectedRow = row;
                selectedCol = col;
                squares[row][col].setBackground(Color.YELLOW);
            }
        } else {
            boolean moveSuccess = gameEngine.makeMove(selectedRow, selectedCol, row, col);
            squares[selectedRow][selectedCol].setBackground((selectedRow + selectedCol) % 2 == 0 ? Color.WHITE : Color.GRAY);
            selectedRow = -1;
            selectedCol = -1;
            if (moveSuccess) {
                updateBoardDisplay();

                // Show checkmate/winner dialog
                if (gameEngine.isGameOver()) {
                    String msg = "Checkmate! " + gameEngine.getWinner() + " wins!";
                    JOptionPane.showMessageDialog(this, msg, "Game Over", JOptionPane.INFORMATION_MESSAGE);
                } else if (gameEngine.isInCheck(gameEngine.isWhiteTurn())) {
                    JOptionPane.showMessageDialog(this, "Check!", "Check", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    private void updateBoardDisplay() {
        char[][] board = gameEngine.getBoard();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JButton button = squares[row][col];
                char piece = board[row][col];
                button.setText("");
                String imageName = getImageNameForPiece(piece);
                if (imageName != null) {
                    java.net.URL imgURL = getClass().getResource("/images/" + imageName + ".png");
                    if (imgURL != null) {
                        button.setIcon(new ImageIcon(imgURL));
                    } else {
                        button.setIcon(null);
                        System.err.println("Missing image: " + imageName + ".png");
                    }
                } else {
                    button.setIcon(null);
                }
            }
        }
    }

    private String getImageNameForPiece(char piece) {
        switch (piece) {
            case 'P': return "white-pawn";
            case 'R': return "white-rook";
            case 'N': return "white-knight";
            case 'B': return "white-bishop";
            case 'Q': return "white-queen";
            case 'K': return "white-king";
            case 'p': return "black-pawn";
            case 'r': return "black-rook";
            case 'n': return "black-knight";
            case 'b': return "black-bishop";
            case 'q': return "black-queen";
            case 'k': return "black-king";
            default: return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGameUI ui = new ChessGameUI();
            ui.setVisible(true);
        });
    }
}
