package ChessBoard;

import Images.Image;
import Pieces.Piece;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;

import static javafx.scene.paint.Color.*;

public class Cell extends Button{
    ChessBoard board;
    int row;
    int col;
    boolean occupied;
    Piece occupyingPiece;

    public Cell(int row, int col, ChessBoard board){
        this.row = row;
        this.col = col;

        if((row+col)%2==0) {
            this.setBackground(new Background( new BackgroundFill(WHITE,null,null)));;
        }
        else {
            this.setBackground(new Background( new BackgroundFill(GREY,null,null)));;
        }

        occupyingPiece = null;
        occupied = false;

        this.board = board;
    }

    void resetColor(){
        if((row+col)%2==0) {
            this.setBackground(new Background( new BackgroundFill(WHITE,null,null)));;
//            this.color = "white";
        }
        else {
            this.setBackground(new Background( new BackgroundFill(GREY,null,null)));;
//            this.color = "black";
        }
    }

    public void selectedCellColor() {
//        System.out.println("selected Color Called on Cell "+x+y);
        this.setBackground(new Background( new BackgroundFill(LIGHTGREEN,null,null)));
    }

    public void possibleCellColor() {
//        System.out.println("Possible cell Color Called on Cell "+x+y);
        this.setBackground(new Background( new BackgroundFill(LIGHTBLUE,null,null)));
    }

    public void inAttackCellColor() {
//        System.out.println("Attack Color Called on Cell "+x+y);
        this.setBackground(new Background( new BackgroundFill(RED,null,null)));
    }

    public Piece getOccupyingPiece() {
        return occupyingPiece;
    }

    public void addPiece(Piece piece) {
        this.occupyingPiece = piece;
        this.occupied = true;
    }

    public void deletePiece(){
        this.getOccupyingPiece().setPosition(null);
        this.getOccupyingPiece().setAlive(false);
        this.occupyingPiece = null;
        this.occupied = false;
//        System.out.println("After Delete occupinPiece is "+ occupyingPiece);
    }

//    public String getColor() {
//        return color;
//    }

//    public void setColor(String color) {
//        this.color = color;
//    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setImage(String name,String color) {
        this.setGraphic(new Image().getImage(name,color));
    }

    public ChessBoard getBoard() {
        return board;
    }

    public void setBoard(ChessBoard board) {
        this.board = board;
    }
}
