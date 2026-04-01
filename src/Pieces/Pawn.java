package Pieces;

import ChessBoard.Cell;
import ChessBoard.ChessBoard;
import java.util.ArrayList;

public class Pawn extends Piece {

    int direction;

    public Pawn(String color, Cell position) {
        super(color, "pawn", position);

        if (color.equals("black"))
            direction = 1;
        else
            direction = -1;

        this.position.setImage("pawn", color);
    }

    @Override
    public ArrayList<Cell> possibleMoves() {
        ArrayList<Cell> possibleMoves = new ArrayList<>();
        int x = position.getRow();
        int y = position.getCol();

        ChessBoard board = (ChessBoard) position.getBoard();
        Cell[][] cell = board.getCells();

        int nextX = x + direction;

        if (nextX >= 0 && nextX < 8) {
            // Forward 1 step
            if (!cell[nextX][y].isOccupied()) {
                possibleMoves.add(cell[nextX][y]);

                // Double step (only if 1 step is also free)
                if (!hasMoved()) {
                    int doubleX = x + 2 * direction;
                    if (doubleX >= 0 && doubleX < 8 && !cell[doubleX][y].isOccupied()) {
                        possibleMoves.add(cell[doubleX][y]);
                    }
                }
            }

            // Capture left
            if (y - 1 >= 0) {
                if (cell[nextX][y - 1].isOccupied() && !cell[nextX][y - 1].getOccupyingPiece().getColor().equals(color)) {
                    possibleMoves.add(cell[nextX][y - 1]);
                } else if (cell[nextX][y - 1] == board.enPassantTarget) {
                    possibleMoves.add(cell[nextX][y - 1]);
                }
            }

            // Capture right
            if (y + 1 < 8) {
                if (cell[nextX][y + 1].isOccupied() && !cell[nextX][y + 1].getOccupyingPiece().getColor().equals(color)) {
                    possibleMoves.add(cell[nextX][y + 1]);
                } else if (cell[nextX][y + 1] == board.enPassantTarget) {
                    possibleMoves.add(cell[nextX][y + 1]);
                }
            }
        }

        return possibleMoves;
    }
}
