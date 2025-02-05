package chess;

import board.Position;
import chess.exceptions.ChessException;

public class ChessPosition {

    private int row;
    private char col;

    public ChessPosition(int row, char col) {
        if (row < 0 || row > 8 || col < 'a' || col > 'h') {
            throw new ChessException("Posição inválida: valores válidos são de a1 até h8.");
        }
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public char getCol() {
        return col;
    }

    protected Position toPosition() {
        return new Position(8 - row, col - 'a');
    }

    protected static ChessPosition fromPosition(Position position) {
        return new ChessPosition(8 - position.getRow() ,(char)('a' + position.getCol()));
    }

    @Override
    public String toString() {
        return "" + col + row;
    }
}
