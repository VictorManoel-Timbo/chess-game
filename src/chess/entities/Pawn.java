package chess.entities;

import board.Board;
import board.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    private ChessMatch match;

    public Pawn(Board board, Color color, ChessMatch match) {
        super(board, color);
        this.match = match;
    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getCols()];

        Position aux = new Position(0, 0);

        if (getColor() == Color.WHITE) {
            aux.setValues(position.getRow() - 1, position.getCol());
            if (getBoard().isPositionValid(aux) && !getBoard().isPiece(aux)) {
                mat[aux.getRow()][aux.getCol()] = true;
            }
            aux.setValues(position.getRow() - 2, position.getCol());
            Position p = new Position(position.getRow() - 1, position.getCol());
            if (getBoard().isPositionValid(aux) && !getBoard().isPiece(aux) && getBoard().isPositionValid(p) && !getBoard().isPiece(p) && getMoveCount() == 0) {
                mat[aux.getRow()][aux.getCol()] = true;
            }
            aux.setValues(position.getRow() - 1, position.getCol() - 1);
            if (getBoard().isPositionValid(aux) && isThereOpponentPiece(aux)) {
                mat[aux.getRow()][aux.getCol()] = true;
            }
            aux.setValues(position.getRow() - 1, position.getCol() + 1);
            if (getBoard().isPositionValid(aux) && isThereOpponentPiece(aux)) {
                mat[aux.getRow()][aux.getCol()] = true;
            }

            if (position.getRow() == 3) {
                Position left = new Position(position.getRow(), position.getCol() - 1);
                if (getBoard().isPositionValid(left) && isThereOpponentPiece(left) && getBoard().piece(left) == match.getEnPassantVulnerable()) {
                    mat[left.getRow() - 1][left.getCol()] = true;
                }
                Position rigth = new Position(position.getRow(), position.getCol() + 1);
                if (getBoard().isPositionValid(rigth) && isThereOpponentPiece(rigth) && getBoard().piece(rigth) == match.getEnPassantVulnerable()) {
                    mat[rigth.getRow() - 1][rigth.getCol()] = true;
                }
            }
        } else {
            aux.setValues(position.getRow() + 1, position.getCol());
            if (getBoard().isPositionValid(aux) && !getBoard().isPiece(aux)) {
                mat[aux.getRow()][aux.getCol()] = true;
            }
            aux.setValues(position.getRow() + 2, position.getCol());
            Position p = new Position(position.getRow() + 1, position.getCol());
            if (getBoard().isPositionValid(aux) && !getBoard().isPiece(aux) && getBoard().isPositionValid(p) && !getBoard().isPiece(p) && getMoveCount() == 0) {
                mat[aux.getRow()][aux.getCol()] = true;
            }
            aux.setValues(position.getRow() + 1, position.getCol() - 1);
            if (getBoard().isPositionValid(aux) && isThereOpponentPiece(aux)) {
                mat[aux.getRow()][aux.getCol()] = true;
            }
            aux.setValues(position.getRow() + 1, position.getCol() + 1);
            if (getBoard().isPositionValid(aux) && isThereOpponentPiece(aux)) {
                mat[aux.getRow()][aux.getCol()] = true;
            }

            if (position.getRow() == 4) {
                Position left = new Position(position.getRow(), position.getCol() - 1);
                if (getBoard().isPositionValid(left) && isThereOpponentPiece(left) && getBoard().piece(left) == match.getEnPassantVulnerable()) {
                    mat[left.getRow() + 1][left.getCol()] = true;
                }
                Position rigth = new Position(position.getRow(), position.getCol() + 1);
                if (getBoard().isPositionValid(rigth) && isThereOpponentPiece(rigth) && getBoard().piece(rigth) == match.getEnPassantVulnerable()) {
                    mat[rigth.getRow() + 1][rigth.getCol()] = true;
                }
            }
        }
        return mat;
    }
}
