package ChessBoard;

import Pieces.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.control.Alert;

import java.util.ArrayList;

public class ChessBoard extends GridPane {

    protected Cell[][] cells;
    protected ArrayList<Cell> srcPossibleMoves;
    protected Cell src;
    protected boolean firstClick;
    public boolean whiteTurn = true;
    public Cell enPassantTarget = null; // Cell behind the pawn that just double-moved
    
    // GameManager properties
    private GameManager gameManager;
    private String playerWhite = "Player 1";
    private String playerBlack = "Player 2";
    private javafx.scene.control.Label statusLabel;

    public ChessBoard() {

        //Setting Fix Size for Cells
        for (int row = 0; row < 8; row++) {
            RowConstraints rc = new RowConstraints();
            rc.setMinHeight(75);
            rc.setMaxHeight(75);
            this.getRowConstraints().add(rc);
        }

        for (int col = 0; col < 8; col++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setMinWidth(75);
            cc.setMaxWidth(75);
            this.getColumnConstraints().add(cc);
        }

        this.cells = new Cell[8][8];
        ButtonHandler onClick = new ButtonHandler();

        //Adding cells to gridpane
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                cells[row][column] = new Cell(row, column, this);
                cells[row][column].setOnMouseClicked(onClick);
                cells[row][column].setMinSize(75, 75);
                this.add(cells[row][column], column, row);
            }
        }
        this.setGridLinesVisible(true);
        this.firstClick = false;
    }

    public void setGameManager(GameManager gm) { this.gameManager = gm; }
    
    public void setPlayers(String w, String b) {
        this.playerWhite = w;
        this.playerBlack = b;
    }
    
    public void setStatusLabel(javafx.scene.control.Label label) { this.statusLabel = label; }
    
    public void updateStatus() {
        if(statusLabel != null) {
            String currPlayer = whiteTurn ? playerWhite : playerBlack;
            statusLabel.setText(currPlayer + "'s Turn (" + (whiteTurn ? "White" : "Black") + ")");
        }
    }
    
    public void saveGame() {
        if (gameManager == null) return;
        try (java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileWriter(GameManager.SAVE_FILE))) {
            pw.println(playerWhite);
            pw.println(playerBlack);
            pw.println(whiteTurn);
            for (int r=0; r<8; r++) {
                for (int c=0; c<8; c++) {
                    if (cells[r][c].isOccupied()) {
                        Piece p = cells[r][c].getOccupyingPiece();
                        pw.println(p.getName() + "," + p.getColor() + "," + r + "," + c + "," + p.hasMoved());
                    }
                }
            }
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
        }
    }

    public class ButtonHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent mouseEvent) {
            Cell clickedCell = (Cell) mouseEvent.getSource();

            String turnColor = whiteTurn ? "white" : "black";

            if (!firstClick) {
                // First click: selecting a piece
                if (clickedCell.isOccupied() && clickedCell.getOccupyingPiece().getColor().equals(turnColor)) {
                    selectCell(clickedCell);
                }
            } else {
                // Second click
                if (clickedCell.isOccupied() && clickedCell.getOccupyingPiece().getColor().equals(turnColor)) {
                    // Clicked on friendly piece, change selection
                    src.resetColor();
                    resetColor(srcPossibleMoves);
                    selectCell(clickedCell);
                } else {
                    // Clicked on empty cell or enemy piece
                    boolean validMove = false;
                    for (Cell c : srcPossibleMoves) {
                        if (c.equals(clickedCell)) {
                            validMove = true;
                            break;
                        }
                    }

                    if (validMove) {
                        makeMove(src, clickedCell);
                        src.resetColor();
                        resetColor(srcPossibleMoves);
                        firstClick = false;
                        src = null;
                        srcPossibleMoves = null;

                        // Switch turn
                        whiteTurn = !whiteTurn;
                        updateStatus();
                        
                        // Check for check/checkmate
                        String nextColor = whiteTurn ? "white" : "black";
                        boolean gameOver = false;
                        if (isInCheck(nextColor)) {
                            if (isCheckmate(nextColor)) {
                                gameOver = true;
                                String winner = whiteTurn ? playerBlack : playerWhite;
                                gameManager.handleGameOver("Checkmate!", winner + " wins the game!");
                            } else {
                                // highlight king in check
                                highlightKing(nextColor);
                            }
                        } else {
                            if (isCheckmate(nextColor)) { // Stalemate actually
                                gameOver = true;
                                gameManager.handleGameOver("Draw", "Stalemate! Nobody wins.");
                            }
                        }
                        
                        if (!gameOver) {
                            saveGame(); // auto save after a successful move
                        }
                    } else {
                        // Invalid move, simply deselect
                        src.resetColor();
                        if (srcPossibleMoves != null) resetColor(srcPossibleMoves);
                        firstClick = false;
                        src = null;
                        srcPossibleMoves = null;
                    }
                }
            }
        }
    }

    void selectCell(Cell cell) {
        this.firstClick = true;
        src = cell;
        cell.selectedCellColor();
        srcPossibleMoves = cell.getOccupyingPiece().possibleMoves();
        filterLegalMoves(srcPossibleMoves, cell.getOccupyingPiece());
        
        for (Cell c : srcPossibleMoves) {
            if (c.isOccupied())
                c.inAttackCellColor();
            else
                c.possibleCellColor();
        }
    }

    // Filter moves to prevent self-check
    void filterLegalMoves(ArrayList<Cell> moves, Piece p) {
        ArrayList<Cell> legal = new ArrayList<>();
        Cell start = p.getPosition();
        String color = p.getColor();

        for (Cell dest : moves) {
            // Simulate move
            Piece destPiece = dest.getOccupyingPiece();
            dest.setOccupyingPieceSilently(p);
            start.setOccupyingPieceSilently(null);
            p.setPosition(dest);

            if (!isInCheck(color)) {
                legal.add(dest);
            }

            // Undo
            p.setPosition(start);
            start.setOccupyingPieceSilently(p);
            dest.setOccupyingPieceSilently(destPiece);
        }

        moves.clear();
        moves.addAll(legal);
    }

    void makeMove(Cell source, Cell dest) {
        Piece p = source.getOccupyingPiece();
        boolean isPawn = p.getName().equals("pawn");
        boolean isKing = p.getName().equals("king");

        // En passant capture
        if (isPawn && dest == enPassantTarget) {
            int captureRow = source.getRow();
            int captureCol = dest.getCol();
            cells[captureRow][captureCol].deletePiece();
        }

        // Reset en passant target
        enPassantTarget = null;
        
        // Handle pawn double move setting en passant target
        if (isPawn && Math.abs(dest.getRow() - source.getRow()) == 2) {
            enPassantTarget = cells[(source.getRow() + dest.getRow()) / 2][source.getCol()];
        }

        // Castling
        if (isKing && Math.abs(dest.getCol() - source.getCol()) == 2) {
            int rookCol = dest.getCol() == 6 ? 7 : 0;
            int newRookCol = dest.getCol() == 6 ? 5 : 3;
            Cell rookSrc = cells[source.getRow()][rookCol];
            Cell rookDest = cells[source.getRow()][newRookCol];
            Piece rook = rookSrc.getOccupyingPiece();
            
            rookDest.addPiece(rook);
            rookDest.setGraphic(rook.getImage());
            rookSrc.setGraphic(null);
            rookSrc.setOccupyingPieceSilently(null);
            rookSrc.setOccupied(false);
            rook.setPosition(rookDest);
            rook.setHasMoved(true);
        }

        if (dest.isOccupied()) {
            dest.deletePiece();
        }
        
        dest.addPiece(p);
        dest.setGraphic(p.getImage());

        source.setGraphic(null);
        source.setOccupyingPieceSilently(null);
        source.setOccupied(false);
        p.setPosition(dest);
        p.setHasMoved(true);

        // Pawn promotion
        if (isPawn && (dest.getRow() == 0 || dest.getRow() == 7)) {
            Piece queen = new Queen(p.getColor(), dest);
            dest.addPiece(queen);
            dest.setGraphic(queen.getImage());
        }
    }

    public boolean isInCheck(String color) {
        // Find king
        int kingRow = -1, kingCol = -1;
        outer:
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (cells[r][c].isOccupied()) {
                    Piece p = cells[r][c].getOccupyingPiece();
                    if (p.getName().equals("king") && p.getColor().equals(color)) {
                        kingRow = r;
                        kingCol = c;
                        break outer;
                    }
                }
            }
        }
        if (kingRow == -1) return false; // Should never happen unless testing
        String enemyColor = color.equals("white") ? "black" : "white";
        return isSquareAttacked(kingRow, kingCol, enemyColor);
    }

    public boolean isSquareAttacked(int r, int c, String attackerColor) {
        // Check all cells for pieces of attackerColor that can move to r,c
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (cells[row][col].isOccupied()) {
                    Piece p = cells[row][col].getOccupyingPiece();
                    if (p.getColor().equals(attackerColor)) {
                        ArrayList<Cell> moves = p.possibleMoves();
                        for (Cell move : moves) {
                            if (move.getRow() == r && move.getCol() == c) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isCheckmate(String color) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (cells[r][c].isOccupied()) {
                    Piece p = cells[r][c].getOccupyingPiece();
                    if (p.getColor().equals(color)) {
                        ArrayList<Cell> moves = p.possibleMoves();
                        filterLegalMoves(moves, p);
                        if (!moves.isEmpty()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    void highlightKing(String color) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (cells[r][c].isOccupied()) {
                    Piece p = cells[r][c].getOccupyingPiece();
                    if (p.getName().equals("king") && p.getColor().equals(color)) {
                        cells[r][c].inAttackCellColor();
                    }
                }
            }
        }
    }

    void resetColor(ArrayList<Cell> resetCells) {
        for (Cell c : resetCells) {
            c.resetColor();
        }
        // Also reset background grid in case king highlight remains
        for (int r = 0; r < 8; r++) {
            for (int c=0; c < 8; c++) {
                cells[r][c].resetColor();
            }
        }
    }

    public void addPieces() {
        cells[0][0].addPiece(new Rook("black", cells[0][0]));
        cells[0][7].addPiece(new Rook("black", cells[0][7]));

        cells[0][1].addPiece(new Knight("black", cells[0][1]));
        cells[0][6].addPiece(new Knight("black", cells[0][6]));

        cells[0][2].addPiece(new Bishop("black", cells[0][2]));
        cells[0][5].addPiece(new Bishop("black", cells[0][5]));

        cells[0][3].addPiece(new Queen("black", cells[0][3]));
        cells[0][4].addPiece(new King("black", cells[0][4]));

        //White
        cells[7][0].addPiece(new Rook("white", cells[7][0]));
        cells[7][7].addPiece(new Rook("white", cells[7][7]));

        cells[7][1].addPiece(new Knight("white", cells[7][1]));
        cells[7][6].addPiece(new Knight("white", cells[7][6]));

        cells[7][2].addPiece(new Bishop("white", cells[7][2]));
        cells[7][5].addPiece(new Bishop("white", cells[7][5]));

        cells[7][3].addPiece(new Queen("white", cells[7][3]));
        cells[7][4].addPiece(new King("white", cells[7][4]));

        for (int col = 0; col < 8; col++) {
            cells[1][col].addPiece(new Pawn("black", cells[1][col]));
        }

        for (int col = 0; col < 8; col++) {
            cells[6][col].addPiece(new Pawn("white", cells[6][col]));
        }
    }

    public Cell[][] getCells() {
        return this.cells;
    }

    public GridPane board() {
        return this;
    }
}
