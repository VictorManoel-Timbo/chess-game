package chess;

import board.Board;
import board.Piece;
import board.Position;
import chess.entities.*;
import chess.exceptions.ChessException;

import java.util.ArrayList;
import java.util.List;

public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;
    private boolean check;
    private boolean checkMate;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;

    private List<Piece> piecesBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    public ChessMatch() {
        this.turn = 1;
        this.currentPlayer = Color.WHITE;
        board = new Board(8, 8);
        initializeSetup();
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public int getTurn() {
        return turn;
    }

    public boolean isCheck() {
        return check;
    }

    public boolean isCheckMate() {
        return checkMate;
    }

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public ChessPiece getPromoted() {
        return promoted;
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] pieces = new ChessPiece[board.getRows()][board.getCols()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                pieces[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return pieces;
    }

    private void placeNewPiece(char col, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(row, col).toPosition());
        piecesBoard.add(piece);
    }

    public ChessPiece chessMove(ChessPosition sourcePosition, ChessPosition targetPositon) {
        Position source = sourcePosition.toPosition();
        Position target = targetPositon.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);

        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("você não pode se colocar em cheque.");
        }

        ChessPiece movedPiece = (ChessPiece) board.piece(target);

        promoted = null;
        if (movedPiece instanceof Pawn) {
            if((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
                promoted = (ChessPiece) board.piece(target);
                promoted = replacePromotedPiece("Q");
            }
        }

        check = testCheck(getOpponent(currentPlayer));

        if (testCheckMate(getOpponent(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }

        if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
            enPassantVulnerable = movedPiece;
        } else {
            enPassantVulnerable = null;
        }

        return (ChessPiece) capturedPiece;
    }

    public ChessPiece replacePromotedPiece(String type) {
        if (promoted == null) {
            throw new IllegalStateException("Não há peça para ser promovida.");
        }
        if (!type.equalsIgnoreCase("Q") && !type.equalsIgnoreCase("R") && !type.equalsIgnoreCase("B") && !type.equalsIgnoreCase("H")) {
            return promoted;
        }
        Position position = promoted.getChessPosition().toPosition();
        Piece piece = board.removePiece(position);
        piecesBoard.remove(piece);

        ChessPiece promotedPiece = newPiece(type, promoted.getColor());
        board.placePiece(promotedPiece, position);
        piecesBoard.add(promotedPiece);

        return promotedPiece;
    }

    private ChessPiece newPiece(String type, Color color) {
        if (type.equalsIgnoreCase("Q")) {
            return new Queen(board, color);
        }
        if (type.equalsIgnoreCase("R")) {
            return new Rook(board, color);
        }
        if (type.equalsIgnoreCase("B")) {
            return new Bishop(board, color);
        }
        return new Knight(board, color);
    }

    private void validateSourcePosition(Position position) {
        if (!board.isPiece(position)) {
            throw new ChessException("Não existe peça na posição de origem.");
        }
        if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
            throw new ChessException("A peça escolhida não é sua.");
        }
        if (!board.piece(position).isTherePossibleMove()) {
            throw new ChessException("Não existem movimentos possíveis para essa peça");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).isPossibleMove(target)) {
            throw new ChessException("A peça escolhida não pode se move para a posição de destino");
        }
    }

    private Piece makeMove(Position source, Position target) {
        ChessPiece aux = (ChessPiece) board.removePiece(source);
        aux.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(aux, target);

        if (capturedPiece != null) {
            piecesBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        if (aux instanceof King && target.getCol() == source.getCol() + 2) {
            Position sourceRook = new Position(source.getRow(), source.getCol() + 3);
            Position targetRook = new Position(source.getRow(), source.getCol() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(sourceRook);
            board.placePiece(rook, targetRook);
            rook.increaseMoveCount();
        }

        if (aux instanceof King && target.getCol() == source.getCol() - 2) {
            Position sourceRook = new Position(source.getRow(), source.getCol() - 4);
            Position targetRook = new Position(source.getRow(), source.getCol() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(sourceRook);
            board.placePiece(rook, targetRook);
            rook.increaseMoveCount();
        }

        if (aux instanceof Pawn) {
            if (source.getCol() != target.getCol() && capturedPiece == null) {
                Position pawnPassant;
                if (aux.getColor() == Color.WHITE) {
                    pawnPassant = new Position(target.getRow() + 1, target.getCol());
                } else {
                    pawnPassant = new Position(target.getRow() - 1, target.getCol());
                }
                capturedPiece = board.removePiece(pawnPassant);
                capturedPieces.add(capturedPiece);
                piecesBoard.remove(capturedPiece);
            }
        }
        return capturedPiece;
    }

    private void undoMove (Position source, Position target, Piece capturedPiece) {
        ChessPiece aux = (ChessPiece) board.removePiece(target);
        aux.decreaseMoveCount();
        board.placePiece(aux, source);

        if(capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesBoard.add(capturedPiece);
        }

        if (aux instanceof King && target.getCol() == source.getCol() + 2) {
            Position sourceRook = new Position(source.getRow(), source.getCol() + 3);
            Position targetRook = new Position(source.getRow(), source.getCol() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(targetRook);
            board.placePiece(rook, sourceRook);
            rook.decreaseMoveCount();
        }

        if (aux instanceof King && target.getCol() == source.getCol() - 2) {
            Position sourceRook = new Position(source.getRow(), source.getCol() - 4);
            Position targetRook = new Position(source.getRow(), source.getCol() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(targetRook);
            board.placePiece(rook, sourceRook);
            rook.decreaseMoveCount();
        }

        if (aux instanceof Pawn) {
            if (source.getCol() != target.getCol() && capturedPiece == enPassantVulnerable) {
                ChessPiece pawn = (ChessPiece) board.removePiece(target);
                Position pawnPassant;
                if (aux.getColor() == Color.WHITE) {
                    pawnPassant = new Position(3, target.getCol());
                } else {
                    pawnPassant = new Position(4, target.getCol());
                }
                board.placePiece(pawn, pawnPassant);
            }
        }
    }

    private Color getOpponent (Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece findKing(Color color) {
        List<Piece> pieces = piecesBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).toList();
        for (Piece piece : pieces) {
            if (piece instanceof King) {
                return (ChessPiece) piece;
            }
        }
        throw new IllegalStateException("Não existe o rei " + color + " no tabuleiro.");
    }

    private boolean testCheck(Color color) {
        Position kingPosition = findKing(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesBoard.stream().filter(x -> ((ChessPiece) x).getColor() == getOpponent(color)).toList();
        for (Piece piece : opponentPieces) {
            boolean[][] mat = piece.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getCol()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testCheckMate(Color color) {
        if (!testCheck(color)) {
            return false;
        }
        List<Piece> pieces = piecesBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).toList();
        for (Piece piece : pieces) {
            boolean[][] mat = piece.possibleMoves();
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getCols(); j++) {
                    if (mat[i][j]) {
                        Position source = ((ChessPiece) piece).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean auxCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);
                        if (!auxCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position source = sourcePosition.toPosition();
        validateSourcePosition(source);
        return board.piece(source).possibleMoves();
    }

    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private void initializeSetup() {
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
    }
}
