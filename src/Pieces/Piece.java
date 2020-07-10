package Pieces;

import ChessBoard.Cell;
import ChessBoard.ChessBoard;
import Images.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public abstract class Piece {
//    ChessBoard board;
    String color;
    String name;
    boolean alive;
    Cell position;
    ImageView image;

    Piece(String color,String name,Cell position){
        this.color = color;
        this.name =  name;
        this.position = position;
        this.alive = true;
//        this.board = position.getBoard();
    }

    public abstract ArrayList<Cell> possibleMoves();

//    public ArrayList<Cell> attackSquares(){
//        ArrayList<Cell> possibleMoves = possibleMoves();
//        ArrayList<Cell> attackSquares = new ArrayList<Cell>();
//        for (Cell c: possibleMoves) {
//            if (c.isOccupied() && c.getOccupyingPiece().getColor() != this.getColor()){
//                attackSquares.add(c);
//            }
//        }
//        return attackSquares;
//    }

//    public ArrayList<Cell> normalSquares(){
//        ArrayList<Cell> possibleMoves = possibleMoves();
//        ArrayList<Cell> normalSquares = new ArrayList<Cell>();
//        for (Cell c: possibleMoves) {
//            if (!c.isOccupied()){
//                normalSquares.add(c);
//            }
//        }
//        return normalSquares;
//    }


    public void delete(){
        this.alive = false;
        this.position = null;
    }

    public ImageView getImage() {
        return new Image().getImage(name,color);
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Cell getPosition() {
        return position;
    }

    public void setPosition(Cell position) {
        this.position = position;
    }

}
