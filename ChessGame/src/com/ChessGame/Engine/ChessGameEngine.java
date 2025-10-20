package com.ChessGame.Engine;

import java.util.*;

public class ChessGameEngine {

    private char[][] board;
    private boolean isWhiteTurn;
    private boolean gameOver = false;
    private String winner = null;

    public ChessGameEngine() {
        initializeBoard();
        isWhiteTurn = true;
    }

    private void initializeBoard() {
        board = new char[8][8];
        board[0] = new char[]{'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'};
        Arrays.fill(board[1], 'p');
        for (int i = 2; i < 6; i++) Arrays.fill(board[i], ' ');
        Arrays.fill(board[6], 'P');
        board[7] = new char[]{'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'};
    }

    public char[][] getBoard() {
        char[][] copy = new char[8][8];
        for (int i = 0; i < 8; i++)
            copy[i] = board[i].clone();
        return copy;
    }

    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    public boolean makeMove(int startRow, int startCol, int endRow, int endCol) {
        if (!isValidMove(startRow, startCol, endRow, endCol) || gameOver) return false;

        char piece = board[startRow][startCol];
        char capturedPiece = board[endRow][endCol];

        board[endRow][endCol] = piece;
        board[startRow][startCol] = ' ';

        if ((piece == 'P' && endRow == 0) || (piece == 'p' && endRow == 7)) {
            board[endRow][endCol] = Character.isUpperCase(piece) ? 'Q' : 'q';
        }

        if (isInCheck(isWhiteTurn)) {
            board[startRow][startCol] = piece;
            board[endRow][endCol] = capturedPiece;
            return false;
        }

        isWhiteTurn = !isWhiteTurn;
        updateGameOver();
        return true;
    }

    private boolean isValidMove(int startRow, int startCol, int endRow, int endCol) {
        if (!isValidPosition(startRow, startCol) || !isValidPosition(endRow, endCol))
            return false;
        char piece = board[startRow][startCol];
        char destPiece = board[endRow][endCol];
        if (piece == ' ') return false;
        boolean isWhitePiece = Character.isUpperCase(piece);
        if (isWhiteTurn != isWhitePiece) return false;
        if (destPiece != ' ' && Character.isUpperCase(destPiece) == isWhitePiece)
            return false;
        return validatePieceMove(piece, startRow, startCol, endRow, endCol);
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    private boolean validatePieceMove(char piece, int startRow, int startCol, int endRow, int endCol) {
        switch (Character.toLowerCase(piece)) {
            case 'p': return PieceValidation.validatePawnMove(board, piece, startRow, startCol, endRow, endCol);
            case 'r': return PieceValidation.validateRookMove(board, startRow, startCol, endRow, endCol);
            case 'n': return PieceValidation.validateKnightMove(board, startRow, startCol, endRow, endCol);
            case 'b': return PieceValidation.validateBishopMove(board, startRow, startCol, endRow, endCol);
            case 'q': return PieceValidation.validateQueenMove(board, startRow, startCol, endRow, endCol);
            case 'k': return PieceValidation.validateKingMove(board, startRow, startCol, endRow, endCol);
            default: return false;
        }
    }

    public boolean isInCheck(boolean isWhiteKing) {
        int[] kingPos = findKing(isWhiteKing);
        if (kingPos == null) return false;
        for (int r = 0; r < 8; r++) for (int c = 0; c < 8; c++) {
            char attacker = board[r][c];
            if (attacker == ' ') continue;
            if (Character.isUpperCase(attacker) == isWhiteKing) continue;
            if (validatePieceMove(attacker, r, c, kingPos[0], kingPos[1]))
                return true;
        }
        return false;
    }

    private int[] findKing(boolean isWhiteKing) {
        char kingChar = isWhiteKing ? 'K' : 'k';
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (board[i][j] == kingChar)
                    return new int[]{i, j};
        return null;
    }

    public boolean isCheckmate(boolean white) {
        if (!isInCheck(white)) return false;
        for (int sr = 0; sr < 8; sr++) for (int sc = 0; sc < 8; sc++) {
            char piece = board[sr][sc];
            if (piece == ' ' || Character.isUpperCase(piece) != white) continue;
            for (int er = 0; er < 8; er++) for (int ec = 0; ec < 8; ec++) {
                if (sr == er && sc == ec) continue;
                char captured = board[er][ec];
                if (isValidMove(sr, sc, er, ec)) {
                    board[er][ec] = piece;
                    board[sr][sc] = ' ';
                    boolean stillInCheck = isInCheck(white);
                    board[sr][sc] = piece;
                    board[er][ec] = captured;
                    if (!stillInCheck) return false;
                }
            }
        }
        return true;
    }

    public void updateGameOver() {
        if (isCheckmate(!isWhiteTurn)) {
            gameOver = true;
            winner = !isWhiteTurn ? "White" : "Black";
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }
    public String getWinner() {
        return winner;
    }
}
