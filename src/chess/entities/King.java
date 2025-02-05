package chess.entities;

import board.Board;
import board.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    private ChessMatch match;

    public King(Board board, Color color, ChessMatch match) {
        super(board, color);
        this.match = match;
    }

    private boolean canMove(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return piece == null || piece.getColor() != this.getColor();
    }

    private boolean testRookCastling(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return piece != null && piece instanceof Rook && piece.getColor() == this.getColor() && piece.getMoveCount() == 0;
    }

    @Override
    public String toString() {
        return "K";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getCols()];

        Position aux = new Position(0, 0);

        // above
        aux.setValues(position.getRow() - 1, position.getCol());
        if (getBoard().isPositionValid(aux) && canMove(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
        }

        // below
        aux.setValues(position.getRow() + 1, position.getCol());
        if (getBoard().isPositionValid(aux) && canMove(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
        }

        // left
        aux.setValues(position.getRow(), position.getCol() - 1);
        if (getBoard().isPositionValid(aux) && canMove(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
        }

        // right
        aux.setValues(position.getRow(), position.getCol() + 1);
        if (getBoard().isPositionValid(aux) && canMove(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
        }

        // nw
        aux.setValues(position.getRow() - 1, position.getCol() - 1);
        if (getBoard().isPositionValid(aux) && canMove(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
        }

        // ne
        aux.setValues(position.getRow() + 1, position.getCol() + 1);
        if (getBoard().isPositionValid(aux) && canMove(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
        }

        // sw
        aux.setValues(position.getRow() + 1, position.getCol() - 1);
        if (getBoard().isPositionValid(aux) && canMove(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
        }

        // se
        aux.setValues(position.getRow() - 1, position.getCol() + 1);
        if (getBoard().isPositionValid(aux) && canMove(aux)) {
            mat[aux.getRow()][aux.getCol()] = true;
        }

        if (getMoveCount() == 0 && !match.isCheck()) {
            Position smallRook = new Position(position.getRow(), position.getCol() + 3);
            if (testRookCastling(smallRook)) {
                Position p1 = new Position(position.getRow(), position.getCol() + 1);
                Position p2 = new Position(position.getRow(), position.getCol() + 2);
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
                    mat[position.getRow()][position.getCol() + 2] = true;
                }
            }
            Position bigRook = new Position(position.getRow(), position.getCol() - 4);
            if (testRookCastling(bigRook)) {
                Position p1 = new Position(position.getRow(), position.getCol() - 1);
                Position p2 = new Position(position.getRow(), position.getCol() - 2);
                Position p3 = new Position(position.getRow(), position.getCol() - 3);
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
                    mat[position.getRow()][position.getCol() - 2] = true;
                }
            }
        }

        return mat;
    }
}
