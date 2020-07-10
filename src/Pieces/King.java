package Pieces;

import ChessBoard.Cell;

import java.util.ArrayList;

public class King extends Piece{
    public King(String color, Cell position) {
        super(color, "king", position);
        this.position.setImage("king",color);
    }

    @Override
    public ArrayList<Cell> possibleMoves() {
        ArrayList<Cell> possibleMoves = new ArrayList<>();
        int row = position.getRow();
        int column = position.getCol();

        Cell[][] cells = position.getBoard().getCells();
        //Down
        if(row+1<8) {
            if (!cells[row+1][column].isOccupied())
                possibleMoves.add(cells[row+1][column]);
            else {
                if (!cells[row + 1][column].getOccupyingPiece().color.equals(this.color))
                    possibleMoves.add(cells[row+1][column]);
            }
        }

        //Up
        if(row-1>=0) {
            if (!cells[row - 1][column].isOccupied())
                possibleMoves.add(cells[row - 1][column]);
            else {
                if (!cells[row - 1][column].getOccupyingPiece().color.equals(this.color))
                    possibleMoves.add(cells[row - 1][column]);
            }
        }

        //Right
        if(column+1<8) {
            if (!cells[row][column+1].isOccupied())
                possibleMoves.add(cells[row][column+1]);
            else {
                if (!cells[row][column+1].getOccupyingPiece().color.equals(this.color))
                    possibleMoves.add(cells[row][column+1]);
            }
        }

        //Left
        if(column-1>=0) {
            if (!cells[row][column-1].isOccupied())
                possibleMoves.add(cells[row][column-1]);
            else {
                if (!cells[row][column-1].getOccupyingPiece().color.equals(this.color))
                    possibleMoves.add(cells[row][column-1]);
            }
        }

        //Right Down
        if(row+1<8 && column+1<8) {
            if (!cells[row + 1][column + 1].isOccupied())
                possibleMoves.add(cells[row + 1][column + 1]);
            else {
                if (!cells[row + 1][column + 1].getOccupyingPiece().color.equals(this.color))
                    possibleMoves.add(cells[row + 1][column + 1]);
            }
        }

        //Left Up
        if(row-1>=0 && column-1>=0) {
            if (!cells[row - 1][column - 1].isOccupied())
                possibleMoves.add(cells[row - 1][column - 1]);
            else {
                if (!cells[row - 1][column - 1].getOccupyingPiece().color.equals(this.color))
                    possibleMoves.add(cells[row - 1][column - 1]);
            }
        }

        //Left Down
        if(row+1<8 && column-1>=0) {
            if (!cells[row+1][column-1].isOccupied())
                possibleMoves.add(cells[row+1][column-1]);
            else {
                if (!cells[row + 1][column-1].getOccupyingPiece().color.equals(this.color))
                    possibleMoves.add(cells[row+1][column-1]);
            }
        }

        //Right Up
        if(row-1>=0 && column+1<8) {
            if (!cells[row-1][column+1].isOccupied())
                possibleMoves.add(cells[row-1][column+1]);
            else {
                if (!cells[row-1][column+1].getOccupyingPiece().color.equals(this.color))
                    possibleMoves.add(cells[row-1][column+1]);
            }
        }

        System.out.println("Possible Moves :");
        for(Cell c:possibleMoves){
            System.out.print(c.getRow()+""+c.getCol());
        }

        return possibleMoves;
    }
}
