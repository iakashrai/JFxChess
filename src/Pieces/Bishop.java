package Pieces;

import ChessBoard.Cell;
import ChessBoard.ChessBoard;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(String color, Cell position) {
        super(color, "bishop", position);
        this.position.setImage("bishop",color);
    }

    @Override
    public ArrayList<Cell> possibleMoves() {
        ArrayList<Cell> possibleMoves = new ArrayList<Cell>();
        int row = position.getRow(); //row
        int column = position.getCol(); //column

        Cell[][] cells = position.getBoard().getCells();

        //Right Up
        for(int r=row-1, c=column+1; r>=0 && c<=7 ; r--, c++){
            if(!cells[r][c].isOccupied()){
                possibleMoves.add(cells[r][c]);
            }
            else{
                if(!cells[r][c].getOccupyingPiece().getColor().equals(this.color)) {
                    possibleMoves.add(cells[r][c]);
                    break;
                }
                else
                    break;
            }
        }

        //Right Down
        for(int r=row+1, c=column+1; r<=7 && c<=7 ; r++, c++){
            if(!cells[r][c].isOccupied()){
                possibleMoves.add(cells[r][c]);
            }
            else {
                if(!cells[r][c].getOccupyingPiece().getColor().equals(this.color)) {
                    possibleMoves.add(cells[r][c]);
                    break;
                }
                else
                    break;
            }
        }

        //Left Up
        for(int r=row-1, c=column-1; r>=0 && c>=0 ; r--, c--){
            if(!cells[r][c].isOccupied()){
                possibleMoves.add(cells[r][c]);
            }
            else{
                if(!cells[r][c].getOccupyingPiece().getColor().equals(this.color)) {
                    possibleMoves.add(cells[r][c]);
                    break;
                }
                else
                    break;
            }
        }

        //Left Down
        for(int r=row+1, c=column-1; r<=7 && c>=0 ; r++, c--){
            if(!cells[r][c].isOccupied() ){
                possibleMoves.add(cells[r][c]);
            }
            else{
                if(!cells[r][c].getOccupyingPiece().getColor().equals(this.color)) {
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
