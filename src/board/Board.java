package board;

import board.exceptions.BoardException;

public class Board {

    private int rows;
    private int cols;
    private Piece[][] pieces;

    public Board(int rows, int cols) {
        if (rows < 1 || cols < 1) {
            throw new BoardException("Erro ao criar tabuleiro: deve ter pelo mesno uma linha e uma coluna");
        }
        this.rows = rows;
        this.cols = cols;
        this.pieces = new Piece[rows][cols];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Piece piece(int row, int col) {
        if (!isPositionValid(row, col)) {
            throw new BoardException("Posição não existe no tabuleiro");
        }
        return pieces[row][col];
    }

    public Piece piece(Position position) {
        if (!isPositionValid(position)) {
            throw new BoardException("Posição não existe no tabuleiro");
        }
        return pieces[position.getRow()][position.getCol()];
    }

    public void placePiece(Piece piece, Position position) {
        if (isPiece(position)) {
            throw new BoardException("Já existe uma peça nesta posição " + position);
        }
        pieces[position.getRow()][position.getCol()] = piece;
        piece.position = position;
    }

    public boolean isPositionValid(Position position) {
        return isPositionValid(position.getRow(), position.getCol());
    }

    private boolean isPositionValid(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public boolean isPiece(Position position) {
        if (!isPositionValid(position)) {
            throw new BoardException("Posição não existe no tabuleiro");
        }
        return piece(position) != null;
    }

    public Piece removePiece(Position position) {
        if (!isPositionValid(position)) {
            throw new BoardException("Posição não existe no tabuleiro");
        }
        if (piece(position) == null) {
            return null;
        }
        Piece aux = piece(position);
        aux.position = null;
        pieces[position.getRow()][position.getCol()] = null;
        return aux;
    }
}
