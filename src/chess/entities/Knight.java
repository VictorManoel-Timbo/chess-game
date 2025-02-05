package chess.entities;

import board.Board;
import board.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight  extends ChessPiece {

    public Knight(Board board, Color color) {
        super(board, color);
    }

    private boolean canMove(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return piece == null || piece.getColor() != this.getColor();
    }

    @Override
    public String toString() {
        return "H";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getCols()];

        Position aux = new Position(0, 0);

        aux.setValues(position.getRow() - 1, position.getCol() - 2);
        if (getBoard().isPositionValid(aux) && canMove(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
        }

        aux.setValues(position.getRow() - 1, position.getCol() + 2);
        if (getBoard().isPositionValid(aux) && canMove(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
        }

        aux.setValues(position.getRow() + 1, position.getCol() - 2);
        if (getBoard().isPositionValid(aux) && canMove(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
        }

        aux.setValues(position.getRow() + 1, position.getCol() + 2);
        if (getBoard().isPositionValid(aux) && canMove(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
        }

        aux.setValues(position.getRow() + 2, position.getCol() + 1);
        if (getBoard().isPositionValid(aux) && canMove(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
        }

        aux.setValues(position.getRow() + 2, position.getCol() - 1);
        if (getBoard().isPositionValid(aux) && canMove(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
        }

        aux.setValues(position.getRow() - 2, position.getCol() - 1);
        if (getBoard().isPositionValid(aux) && canMove(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
        }

        aux.setValues(position.getRow() - 2, position.getCol() + 1);
        if (getBoard().isPositionValid(aux) && canMove(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
        }


        return mat;
    }
}