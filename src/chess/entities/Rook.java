package chess.entities;

import board.Board;
import board.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getCols()];

        Position aux = new Position(0, 0);

        // above
        aux.setValues(position.getRow() - 1, position.getCol());
        while(getBoard().isPositionValid(aux) && !getBoard().isPiece(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
            aux.setRow(aux.getRow() - 1);
        }
        if (getBoard().isPositionValid(aux) && isThereOpponentPiece(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
        }

        // below
        aux.setValues(position.getRow() + 1, position.getCol());
        while(getBoard().isPositionValid(aux) && !getBoard().isPiece(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
            aux.setRow(aux.getRow() + 1);
        }
        if (getBoard().isPositionValid(aux) && isThereOpponentPiece(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
        }


        // left
        aux.setValues(position.getRow(), position.getCol() - 1);
        while(getBoard().isPositionValid(aux) && !getBoard().isPiece(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
            aux.setCol(aux.getCol() - 1);
        }
        if (getBoard().isPositionValid(aux) && isThereOpponentPiece(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
        }

        // right
        aux.setValues(position.getRow(), position.getCol() + 1);
        while(getBoard().isPositionValid(aux) && !getBoard().isPiece(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
            aux.setCol(aux.getCol() + 1);
        }
        if (getBoard().isPositionValid(aux) && isThereOpponentPiece(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
        }
        return mat;
    }
}
