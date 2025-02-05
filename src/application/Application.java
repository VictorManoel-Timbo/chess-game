package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.exceptions.ChessException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ChessMatch match = new ChessMatch();
        List<ChessPiece> capturedPieces = new ArrayList<>();

        while (!match.isCheckMate()) {
            try {
                UI.clearScreen();
                UI.printMatch(match, capturedPieces);
                System.out.println();
                System.out.print("source: ");
                ChessPosition source = UI.readChessPosition(scanner);

                boolean[][] possibleMoves = match.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(match.getPieces(), possibleMoves);

                System.out.println();
                System.out.print("target: ");
                ChessPosition target = UI.readChessPosition(scanner);
                System.out.println();

                ChessPiece capturedPiece = match.chessMove(source, target);

                if (capturedPiece != null) {
                    capturedPieces.add(capturedPiece);
                }

                if (match.getPromoted() != null) {
                    System.out.print("Entre com a peça para promoção (B/H/R/Q): ");
                    String type = scanner.nextLine();
                    while (!type.equalsIgnoreCase("Q") && !type.equalsIgnoreCase("R") && !type.equalsIgnoreCase("B") && !type.equalsIgnoreCase("H")) {
                        System.out.print("Valor inválido! Digite novamente (B/H/R/Q): ");
                        type = scanner.nextLine();
                    }
                    match.replacePromotedPiece(type);
                }
            } catch (ChessException | InputMismatchException e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
        UI.clearScreen();
        UI.printMatch(match, capturedPieces);
    }
}
