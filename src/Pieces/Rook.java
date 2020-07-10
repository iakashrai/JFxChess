package Pieces;

import ChessBoard.Cell;
import ChessBoard.ChessBoard;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class Rook extends  Piece{

    public Rook(String color, Cell position) {
        super(color, "rook", position);
        this.position.setImage("rook",color);
    }

    @Override
    public ArrayList<Cell> possibleMoves() {
        ArrayList<Cell> possibleMoves = new ArrayList<Cell>();
        int row = position.getRow(); //row
        int column = position.getCol(); //column

        Cell[][] cells = position.getBoard().getCells();
        //Up
        for(int r=row-1, c=column; r>=0 ; r--){
            if(!cells[r][c].isOccupied() ){
                possibleMoves.add(cells[r][c]);
            }
            else{
                if(!cells[r][c].getOccupyingPiece().color.equals(this.color)) {
                    possibleMoves.add(cells[r][c]);
                    break;
                }
                else
                    break;
            }
        }

        //Down
        for(int r=row+1, c=column; r<=7 ; r++){
            if(!cells[r][c].isOccupied() ){
                possibleMoves.add(cells[r][c]);
            }
            else{
                if(!cells[r][c].getOccupyingPiece().color.equals(this.color)) {
                    possibleMoves.add(cells[r][c]);
                    break;
                }
                else
                    break;
            }
        }

        //Left
        for(int r=row, c=column-1; c>=0 ; c--){
            if(!cells[r][c].isOccupied() ){
                possibleMoves.add(cells[r][c]);
            }
            else{
                if(!cells[r][c].getOccupyingPiece().color.equals(this.color)) {
                    possibleMoves.add(cells[r][c]);
                    break;
                }
                else
                    break;
            }
        }

        //Right
        for(int r=row, c=column+1; c<=7; c++){
            if(!cells[r][c].isOccupied() ){
                possibleMoves.add(cells[r][c]);
            }
            else {
                if(!cells[r][c].getOccupyingPiece().color.equals(this.color)) {
                    possibleMoves.add(cells[r][c]);
                    break;
                }
                else
                    break;
            }
        }

        System.out.println("Possible Moves :");
        for(Cell c:possibleMoves){
            System.out.print(c.getRow()+""+c.getCol());
        }
        return possibleMoves;
    }
}
