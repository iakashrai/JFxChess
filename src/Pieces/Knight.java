package Pieces;

import ChessBoard.Cell;
import ChessBoard.ChessBoard;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class Knight extends Piece{
    public Knight(String color,Cell position) {
        super(color, "knight", position);
        this.position.setImage("knight",color);
    }

    @Override
    public ArrayList<Cell> possibleMoves() {
        ArrayList<Cell> possibleMoves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getCol();

        Cell[][] cells = position.getBoard().getCells();

        if(row-2>=0 && col-1>=0){
            if(!cells[row-2][col-1].isOccupied())
                possibleMoves.add(cells[row-2][col-1]);
            else
                if(!cells[row-2][col-1].getOccupyingPiece().getColor().equals(color))
                    possibleMoves.add(cells[row-2][col-1]);
        }

        if(row-2>=0 && col+1<=7){
            if(!cells[row-2][col+1].isOccupied())
                possibleMoves.add(cells[row-2][col+1]);
            else
                if(!cells[row-2][col+1].getOccupyingPiece().getColor().equals(color))
                    possibleMoves.add(cells[row-2][col+1]);
        }

        if(row+2<=7 && col-1>=0){
            if(!cells[row+2][col-1].isOccupied())
                possibleMoves.add(cells[row+2][col-1]);
            else {
                if (!cells[row + 2][col - 1].getOccupyingPiece().getColor().equals(color))
                    possibleMoves.add(cells[row + 2][col - 1]);
            }
        }

        if(row+2<=7 && col+1<=7){
            if(!cells[row+2][col+1].isOccupied())
                possibleMoves.add(cells[row+2][col+1]);
            else
            if(!cells[row+2][col+1].getOccupyingPiece().getColor().equals(color))
                possibleMoves.add(cells[row+2][col+1]);
        }

        if(row-1>=0 && col-2>=0){
            if(!cells[row-1][col-2].isOccupied())
                possibleMoves.add(cells[row-1][col-2]);
            else
            if(!cells[row-1][col-2].getOccupyingPiece().getColor().equals(color))
                possibleMoves.add(cells[row-1][col-2]);
        }

        if(row-1>=0 && col+2<=7){
            if(!cells[row-1][col+2].isOccupied())
                possibleMoves.add(cells[row-1][col+2]);
            else
            if(!cells[row-1][col+2].getOccupyingPiece().getColor().equals(color))
                possibleMoves.add(cells[row-1][col+2]);
        }

        if(row+1<=7 && col-2>=0){
            if(!cells[row+1][col-2].isOccupied())
                possibleMoves.add(cells[row+1][col-2]);
            else
            if(!cells[row+1][col-2].getOccupyingPiece().getColor().equals(color))
                possibleMoves.add(cells[row+1][col-2]);
        }

        if(row+1<=7 && col+2<=7){
            if(!cells[row+1][col+2].isOccupied())
                possibleMoves.add(cells[row+1][col+2]);
            else
            if(!cells[row+1][col+2].getOccupyingPiece().getColor().equals(color))
                possibleMoves.add(cells[row+1][col+2]);
        }

        System.out.println("Possible Moves :");
        for(Cell c:possibleMoves){
            System.out.print(c.getRow()+""+c.getCol());
        }
        
        return possibleMoves;
    }
}
