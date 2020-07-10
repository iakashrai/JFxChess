package Pieces;

import ChessBoard.Cell;
import ChessBoard.ChessBoard;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class Queen extends Piece {

    public Queen(String color, Cell cell) {
        super(color,"queen",cell);
        this.position.setImage("queen",color);
    }

    @Override
    public ArrayList<Cell> possibleMoves() {
        ArrayList<Cell> possibleMoves = new ArrayList<>();

        int row = position.getRow();
        int column = position.getCol();

        Cell[][] cells = position.getBoard().getCells();

        System.out.println("UP");
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
            System.out.println("___________________\n");
        }

        System.out.println("Down");
        //Down
        for(int r=row+1, c=column; r<=7 ; r++){
            System.out.println("Cell"+c+""+r);
            System.out.println("Occupied "+cells[r][c].isOccupied());
            if(!cells[r][c].isOccupied()){
                possibleMoves.add(cells[r][c]);
                System.out.println("Added");
            }
            else{
                System.out.println("Else condition Initated");
                System.out.println("Occupying piece and color "+cells[r][c].getOccupyingPiece().getName()+" "+cells[r][c].getOccupyingPiece().getColor());
                System.out.println("Occupied by same color "+cells[r][c].getOccupyingPiece().color.equals(this.color));
                if(!cells[r][c].getOccupyingPiece().color.equals(this.color)) {
                    possibleMoves.add(cells[r][c]);
                    System.out.println("Added");
                    break;
                }
                else
                    break;
            }
            System.out.println("___________________\n");
        }

        System.out.println("Left");
        //Left
        for(int r=row, c=column-1; c>=0 ; c--){
            System.out.println("Cell"+c+""+r);
            System.out.println("Occupied "+cells[r][c].isOccupied());
            if(!cells[r][c].isOccupied() ){
                possibleMoves.add(cells[r][c]);
                System.out.println("Added");
            }
            else{
                System.out.println("Else condition Initated");
                System.out.println("Occupying piece and color "+cells[r][c].getOccupyingPiece().getName()+" "+cells[r][c].getOccupyingPiece().getColor());
                System.out.println("Occupied by same color "+cells[r][c].getOccupyingPiece().color.equals(this.color));
                if(!cells[r][c].getOccupyingPiece().color.equals(this.color)) {
                    possibleMoves.add(cells[r][c]);
                    System.out.println("Added");
                    break;
                }
                else
                    break;
            }
            System.out.println("___________________\n");
        }

        System.out.println("Right");
        //Right
        for(int r=row, c=column+1; c<=7; c++){
            System.out.println("Cell"+c+""+r);
            System.out.println("Occupied "+cells[r][c].isOccupied());
            if(!cells[r][c].isOccupied() ){
                possibleMoves.add(cells[r][c]);
                System.out.println("Added");
            }
            else {
                System.out.println("Else condition Initated");
                System.out.println("Occupying piece and color "+cells[r][c].getOccupyingPiece().getName()+" "+cells[r][c].getOccupyingPiece().getColor());
                System.out.println("Occupied by same color "+cells[r][c].getOccupyingPiece().color.equals(this.color));
                if(!cells[r][c].getOccupyingPiece().color.equals(this.color)) {
                    possibleMoves.add(cells[r][c]);
                    System.out.println("Added");
                    break;
                }
                else
                    break;
            }
            System.out.println("___________________\n");
        }

        System.out.println("Right Up");
        //Right Up
        for(int r=row-1, c=column+1; r>=0 && c<=7 ; r--, c++){
            System.out.println("Cell"+c+""+r);
            System.out.println("Occupied "+cells[r][c].isOccupied());
            if(!cells[r][c].isOccupied()){
                possibleMoves.add(cells[r][c]);
                System.out.println("Added");
            }
            else{
                System.out.println("Else condition Initated");
                System.out.println("Occupying piece and color "+cells[r][c].getOccupyingPiece().getName()+" "+cells[r][c].getOccupyingPiece().getColor());
                System.out.println("Occupied by same color "+cells[r][c].getOccupyingPiece().color.equals(this.color));
                if(!cells[r][c].getOccupyingPiece().getColor().equals(this.color)) {
                    possibleMoves.add(cells[r][c]);
                    System.out.println("Added");
                    break;
                }
                else
                    break;
            }
            System.out.println("___________________\n");
        }

        System.out.println("Right Down");
        //Right Down
        for(int r=row+1, c=column+1; r<=7 && c<=7 ; r++, c++){
            System.out.println("Cell"+c+""+r);
            System.out.println("Occupied "+cells[r][c].isOccupied());
            if(!cells[r][c].isOccupied()){
                possibleMoves.add(cells[r][c]);
                System.out.println("Added");
            }
            else {
                System.out.println("Else condition Initated");
                System.out.println("Occupying piece and color "+cells[r][c].getOccupyingPiece().getName()+" "+cells[r][c].getOccupyingPiece().getColor());
                System.out.println("Occupied by same color "+cells[r][c].getOccupyingPiece().color.equals(this.color));
                if(!cells[r][c].getOccupyingPiece().getColor().equals(this.color)) {
                    possibleMoves.add(cells[r][c]);
                    System.out.println("Added");
                    break;
                }
                else
                    break;
            }
            System.out.println("___________________\n");
        }

        System.out.println("Left Up");
        //Left Up
        for(int r=row-1, c=column-1; r>=0 && c>=0 ; r--, c--){
            System.out.println("Cell"+c+""+r);
            System.out.println("Occupied "+cells[r][c].isOccupied());
            if(!cells[r][c].isOccupied()){
                possibleMoves.add(cells[r][c]);
                System.out.println("Added");
            }
            else{
                System.out.println("Else condition Initated");
                System.out.println("Occupying piece and color "+cells[r][c].getOccupyingPiece().getName()+" "+cells[r][c].getOccupyingPiece().getColor());
                System.out.println("Occupied by same color "+cells[r][c].getOccupyingPiece().color.equals(this.color));
                if(!cells[r][c].getOccupyingPiece().getColor().equals(this.color)) {
                    possibleMoves.add(cells[r][c]);
                    System.out.println("Added");
                    break;
                }
                else
                    break;
            }
            System.out.println("___________________\n");
        }

        System.out.println("Left Down");
        //Left Down
        for(int r=row+1, c=column-1; r<=7 && c>=0 ; r++, c--){
            System.out.println("Cell"+c+""+r);
            System.out.println("Occupied "+cells[r][c].isOccupied());
            if(!cells[r][c].isOccupied() ){
                possibleMoves.add(cells[r][c]);
                System.out.println("Added");
            }
            else{
                System.out.println("Else condition Initated");
                System.out.println("Occupying piece and color "+cells[r][c].getOccupyingPiece().getName()+" "+cells[r][c].getOccupyingPiece().getColor());
                System.out.println("Occupied by same color "+cells[r][c].getOccupyingPiece().color.equals(this.color));
                if(!cells[r][c].getOccupyingPiece().getColor().equals(this.color)) {
                    possibleMoves.add(cells[r][c]);
                    System.out.println("Added");
                    break;
                }
                else
                    break;
            }
            System.out.println("___________________\n");
        }



        System.out.println("Possible Moves :");
        for(Cell c:possibleMoves){
            System.out.print((c.getCol()+1)+""+(c.getRow()+1)+" ");
        }
        return possibleMoves;
    }
}
