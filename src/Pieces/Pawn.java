package Pieces;

import ChessBoard.Cell;
import ChessBoard.ChessBoard;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class Pawn extends Piece {

    int direction;
    boolean firstTime;

    public Pawn(String color, Cell position) {
        super(color, "pawn", position);

        if (color.equals("black"))
            direction = 1;
        else
            direction = -1;

//        start = position.getX();

        this.position.setImage("pawn", color);

    }

    @Override
    public ArrayList<Cell> possibleMoves() {
        ArrayList<Cell> possibleMoves = new ArrayList<>();
        int x = position.getRow();
        int y = position.getCol();

        System.out.println("Cell: X:" + x + " Y: " + y);
        Cell[][] cell = position.getBoard().getCells();

        if ((x + direction) < 8 && (y - 1) >= 0) {
            if (cell[x + direction][y - 1].isOccupied()) {
                if (!cell[x + direction][y - 1].getOccupyingPiece().color.equals(color)) {
                    possibleMoves.add(cell[x+direction][y-1]);
                    System.out.println("1");
                }
            }
        }

        if ((x + direction) < 8 && (y + 1) >= 0) {
            if (cell[x + direction][y + 1].isOccupied()) {
                if (!cell[x + direction][y + 1].getOccupyingPiece().color.equals(color)) {
                    possibleMoves.add(cell[x + direction][y + 1]);
                    System.out.println("2");
                }
            }
        }

        if (x + direction < 8) {
            if (!cell[x + direction][y].isOccupied()) {
                possibleMoves.add(cell[x + direction][y]);
                System.out.println("3");
            }
        }
//        if((y+direction)<8){
//            if(!cell[y+direction][x].isOccupied()) {
//                possibleMoves.add(cell[y+direction][x]);
//                System.out.println("4");
//            }
//        }

        System.out.println("Possible Moves :");
        for (Cell c : possibleMoves) {
            System.out.print(c.getRow() + "" + c.getCol());
        }

        return possibleMoves;
    }
}
