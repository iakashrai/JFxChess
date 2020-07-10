package ChessBoard;

import Pieces.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class ChessBoard extends GridPane{

    protected Cell[][] cells;
//    protected Piece[][] pieces;
    protected ArrayList<Cell> srcPossibleMoves;
//    protected ArrayList<Cell> attackMoves;
    protected Cell src;
    protected boolean firstClick;

    public ChessBoard() {

        //Setting Fix Size for Cells
        for (int row = 0; row < 8; row++) {
            RowConstraints rc = new RowConstraints();
//            rc.setVgrow(Priority.ALWAYS);
            rc.setMinHeight(75);
            rc.setMaxHeight(75);
//            rc.setFillHeight(true);
            this.getRowConstraints().add(rc);
        }

        for (int col = 0; col < 8; col++) {
            ColumnConstraints cc = new ColumnConstraints();
//            cc.setHgrow(Priority.ALWAYS);
            cc.setMinWidth(75);
            cc.setMaxWidth(75);
//            cc.setFillWidth(true);
            this.getColumnConstraints().add(cc);
        }

        this.cells = new Cell[8][8];

        //Button handler to handle click operations
        ButtonHandler onClick = new ButtonHandler();

        //Adding cells to gridpane
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                cells[row][column] = new Cell(row, column,this);
                cells[row][column].setOnMouseClicked(onClick);
                cells[row][column].setMinSize(75,75);
                this.add(cells[row][column], column, row);
                System.out.print(row+""+column+" ");
            }
            System.out.println(" ");
        }
        this.setGridLinesVisible(true);
        //adding Pieces
        addPieces();

        this.firstClick = false;

    }

    public class ButtonHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent mouseEvent) {
            Cell clickedCell = (Cell) mouseEvent.getSource();

            System.out.println("Cell Row: "+clickedCell.getRow()+"Col "+clickedCell.getCol());

            if(clickedCell.isOccupied()){  // If a cell having piece is clicked
                if(firstClick == false){   // checking if it first click or selection click
                    selectCell(clickedCell);
                }
                else {
                    for (Cell c : srcPossibleMoves) {
                        if (c.equals(clickedCell)) {
                            makeMove(src, clickedCell);
                            src.resetColor();
                            resetColor(srcPossibleMoves);
                            firstClick = false;
                            src = null;
                            srcPossibleMoves = null;
                            break;
                        }
                    }
                }
            }
            else {
                if (firstClick != false) {
                    for (Cell c : srcPossibleMoves) {
                        if (c.equals(clickedCell)) {
                            makeMove(src, clickedCell);
                            src.resetColor();
                            resetColor(srcPossibleMoves);
                            firstClick = false;
                            src = null;
                            srcPossibleMoves = null;
                            break;
                        }
                    }
                }
            }

        }
    }

    void selectCell(Cell cell){
        this.firstClick = true;
        src = cell;
        cell.selectedCellColor();
        srcPossibleMoves = cell.getOccupyingPiece().possibleMoves();
        for (Cell c:srcPossibleMoves){
            if(c.isOccupied())
                c.inAttackCellColor();
            else
                c.possibleCellColor();
        }
    }

    void makeMove(Cell src,Cell dest){

        if(dest.isOccupied())
            dest.deletePiece();
        Piece p = src.occupyingPiece;
        dest.addPiece(p);
        dest.setGraphic(p.getImage());

        src.setGraphic(null);
        src.occupyingPiece = null;
        src.occupied = false;
        p.setPosition(dest);
    }

    void showAttackCells(ArrayList<Cell> attackCells){
        for (Cell c: attackCells) {
            c.inAttackCellColor();
        }
    }

    void showPossibleCells(ArrayList<Cell> possibleCells){
        for (Cell c: srcPossibleMoves) {
            c.possibleCellColor();
        }
    }

    void resetColor(ArrayList<Cell> resetCells){
        for(Cell c:resetCells){
            c.resetColor();
        }

//        for(Cell c:attackMoves){
//            c.resetColor();
//        }
    }

    public void addPieces(){

        cells[0][0].addPiece(new Rook("black",cells[0][0]));
        cells[0][7].addPiece(new Rook("black",cells[0][7]));

        cells[0][1].addPiece(new Knight("black",cells[0][1]));
        cells[0][6].addPiece(new Knight("black",cells[0][6]));

        cells[0][2].addPiece(new Bishop("black",cells[0][2]));
        cells[0][5].addPiece(new Bishop("black",cells[0][5]));

        cells[0][3].addPiece(new Queen("black",cells[0][3]));
        cells[0][4].addPiece(new King("black",cells[0][4]));

        //White
        cells[7][0].addPiece(new Rook("white",cells[7][0]));
        cells[7][7].addPiece(new Rook("white",cells[7][7]));

        cells[7][1].addPiece(new Knight("white",cells[7][1]));
        cells[7][6].addPiece(new Knight("white",cells[7][6]));

        cells[7][2].addPiece(new Bishop("white",cells[7][2]));
        cells[7][5].addPiece(new Bishop("white",cells[7][5]));

        cells[7][3].addPiece(new King("white",cells[7][3]));
        cells[7][4].addPiece(new Queen("white",cells[7][4]));

        for (int col=0; col<8; col++){
            cells[1][col].addPiece(new Pawn("black",cells[1][col]));
        }

        for (int col=0; col<8; col++){
            cells[6][col].addPiece(new Pawn("white",cells[6][col]));
        }
    }

    public void addRowColumnContraints(GridPane gPane){

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPrefWidth(50);
        for(int i=0;i<8;i++){
            gPane.getColumnConstraints().add(columnConstraints);
        }

        RowConstraints rowConstraints = new RowConstraints();

        rowConstraints.setPrefHeight(50);
        for(int i=0;i<8;i++){
            gPane.getRowConstraints().add(rowConstraints);
        }
    }

    public Cell[][] getCells() {
        return this.cells;
    }

    public GridPane board() {
        return this;
    }
}

