package chess.entities;

import board.Board;
import board.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "B";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getCols()];

        Position aux = new Position(0, 0);

        // nw
        aux.setValues(position.getRow() - 1, position.getCol() - 1);
        while (getBoard().isPositionValid(aux) && !getBoard().isPiece(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
            aux.setValues(aux.getRow() - 1, aux.getCol() - 1);
        }
        if (getBoard().isPositionValid(aux) && isThereOpponentPiece(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
        }

        // ne
        aux.setValues(position.getRow() - 1, position.getCol() + 1);
        while (getBoard().isPositionValid(aux) && !getBoard().isPiece(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
            aux.setValues(aux.getRow() - 1, aux.getCol() + 1);
        }
        if (getBoard().isPositionValid(aux) && isThereOpponentPiece(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
        }

        // se
        aux.setValues(position.getRow() + 1, position.getCol() + 1);
        while (getBoard().isPositionValid(aux) && !getBoard().isPiece(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
            aux.setValues(aux.getRow() + 1, aux.getCol() + 1);
        }
        if (getBoard().isPositionValid(aux) && isThereOpponentPiece(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
        }

        // sw
        aux.setValues(position.getRow() + 1, position.getCol() - 1);
        while (getBoard().isPositionValid(aux) && !getBoard().isPiece(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
            aux.setValues(aux.getRow() + 1, aux.getCol() - 1);
        }
        if (getBoard().isPositionValid(aux) && isThereOpponentPiece(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
        }
        return mat;
    }
}
