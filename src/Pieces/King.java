package Pieces;

import ChessBoard.Cell;
import ChessBoard.ChessBoard;

import java.util.ArrayList;

public class King extends Piece{
    public King(String color, Cell position) {
        super(color, "king", position);
        this.position.setImage("king",color);
    }

    // Prevent infinite recursion during isSquareAttacked
    private boolean generatingCastling = false;

    @Override
    public ArrayList<Cell> possibleMoves() {
        ArrayList<Cell> possibleMoves = new ArrayList<>();
        int row = position.getRow();
        int column = position.getCol();

        ChessBoard board = (ChessBoard) position.getBoard();
        Cell[][] cells = board.getCells();

        //Down
        if(row+1<8) {
            if (!cells[row+1][column].isOccupied())
                possibleMoves.add(cells[row+1][column]);
            else if (!cells[row + 1][column].getOccupyingPiece().getColor().equals(this.color))
                possibleMoves.add(cells[row+1][column]);
        }

        //Up
        if(row-1>=0) {
            if (!cells[row - 1][column].isOccupied())
                possibleMoves.add(cells[row - 1][column]);
            else if (!cells[row - 1][column].getOccupyingPiece().getColor().equals(this.color))
                possibleMoves.add(cells[row - 1][column]);
        }

        //Right
        if(column+1<8) {
            if (!cells[row][column+1].isOccupied())
                possibleMoves.add(cells[row][column+1]);
            else if (!cells[row][column+1].getOccupyingPiece().getColor().equals(this.color))
                possibleMoves.add(cells[row][column+1]);
        }

        //Left
        if(column-1>=0) {
            if (!cells[row][column-1].isOccupied())
                possibleMoves.add(cells[row][column-1]);
            else if (!cells[row][column-1].getOccupyingPiece().getColor().equals(this.color))
                possibleMoves.add(cells[row][column-1]);
        }

        //Right Down
        if(row+1<8 && column+1<8) {
            if (!cells[row + 1][column + 1].isOccupied())
                possibleMoves.add(cells[row + 1][column + 1]);
            else if (!cells[row + 1][column + 1].getOccupyingPiece().getColor().equals(this.color))
                possibleMoves.add(cells[row + 1][column + 1]);
        }

        //Left Up
        if(row-1>=0 && column-1>=0) {
            if (!cells[row - 1][column - 1].isOccupied())
                possibleMoves.add(cells[row - 1][column - 1]);
            else if (!cells[row - 1][column - 1].getOccupyingPiece().getColor().equals(this.color))
                possibleMoves.add(cells[row - 1][column - 1]);
        }

        //Left Down
        if(row+1<8 && column-1>=0) {
            if (!cells[row+1][column-1].isOccupied())
                possibleMoves.add(cells[row+1][column-1]);
            else if (!cells[row + 1][column-1].getOccupyingPiece().getColor().equals(this.color))
                possibleMoves.add(cells[row+1][column-1]);
        }

        //Right Up
        if(row-1>=0 && column+1<8) {
            if (!cells[row-1][column+1].isOccupied())
                possibleMoves.add(cells[row-1][column+1]);
            else if (!cells[row-1][column+1].getOccupyingPiece().getColor().equals(this.color))
                possibleMoves.add(cells[row-1][column+1]);
        }

        if (generatingCastling) return possibleMoves;

        // Castling
        if (!hasMoved()) {
            generatingCastling = true;
            String enemyColor = color.equals("white") ? "black" : "white";

            // Kingside (short castling)
            if (column == 4 && cells[row][7].isOccupied()) {
                Piece rightRook = cells[row][7].getOccupyingPiece();
                if (rightRook.getName().equals("rook") && rightRook.getColor().equals(this.color) && !rightRook.hasMoved()) {
                    // Check if path is empty
                    if (!cells[row][5].isOccupied() && !cells[row][6].isOccupied()) {
                        // Check if passing through or landing in check
                        if (!board.isSquareAttacked(row, 4, enemyColor) &&
                            !board.isSquareAttacked(row, 5, enemyColor) &&
                            !board.isSquareAttacked(row, 6, enemyColor)) {
                            possibleMoves.add(cells[row][6]);
                        }
                    }
                }
            }

            // Queenside (long castling)
            if (column == 4 && cells[row][0].isOccupied()) {
                Piece leftRook = cells[row][0].getOccupyingPiece();
                if (leftRook.getName().equals("rook") && leftRook.getColor().equals(this.color) && !leftRook.hasMoved()) {
                    // Check if path is empty
                    if (!cells[row][1].isOccupied() && !cells[row][2].isOccupied() && !cells[row][3].isOccupied()) {
                        // Check if passing through or landing in check
                        if (!board.isSquareAttacked(row, 4, enemyColor) &&
                            !board.isSquareAttacked(row, 3, enemyColor) &&
                            !board.isSquareAttacked(row, 2, enemyColor)) {
                            possibleMoves.add(cells[row][2]);
                        }
                    }
                }
            }
            generatingCastling = false;
        }

        return possibleMoves;
    }
}
