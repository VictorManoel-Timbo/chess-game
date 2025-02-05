package board;

public abstract class Piece {

    protected Position position;
    private Board board;

    public Piece(Board board) {
        this.board = board;
        this.position = null;
    }

    protected Board getBoard() {
        return board;
    }

    public abstract boolean[][] possibleMoves();

    public boolean isPossibleMove(Position position) {
        return this.possibleMoves()[position.getRow()][position.getCol()];
    }

    public boolean isTherePossibleMove() {
        boolean[][] moves = possibleMoves();
        for (int i = 0; i < moves.length; i++) {
            for (int j = 0; j < moves.length; j++) {
                if (moves[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }
}
