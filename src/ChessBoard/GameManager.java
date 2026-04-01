package ChessBoard;

import Pieces.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.*;
import java.util.Optional;

public class GameManager extends Application {

    private Stage primaryStage;
    public static final String SAVE_FILE = "savegame.dat";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        stage.setTitle("JFxChess");
        showMainMenu();
        stage.show();
    }

    public void showMainMenu() {
        VBox menu = new VBox(20);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(50));
        menu.setStyle("-fx-background-color: #2b2b2b;");

        Label title = new Label("JFxChess");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        title.setStyle("-fx-text-fill: white;");

        Button newGameBtn = new Button("New Game");
        newGameBtn.setPrefWidth(200);
        newGameBtn.setStyle("-fx-font-size: 18px;");
        newGameBtn.setOnAction(e -> showSetupMenu());

        Button loadGameBtn = new Button("Load Game");
        loadGameBtn.setPrefWidth(200);
        loadGameBtn.setStyle("-fx-font-size: 18px;");
        File f = new File(SAVE_FILE);
        if (!f.exists()) {
            loadGameBtn.setDisable(true);
        }
        loadGameBtn.setOnAction(e -> loadGame());

        Button quitBtn = new Button("Quit");
        quitBtn.setPrefWidth(200);
        quitBtn.setStyle("-fx-font-size: 18px;");
        quitBtn.setOnAction(e -> Platform.exit());

        menu.getChildren().addAll(title, newGameBtn, loadGameBtn, quitBtn);
        primaryStage.setScene(new Scene(menu, 600, 600));
    }

    private void showSetupMenu() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50));
        layout.setStyle("-fx-background-color: #2b2b2b;");

        Label title = new Label("Game Setup");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        title.setStyle("-fx-text-fill: white;");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(15);
        grid.setHgap(10);

        Label p1Label = new Label("Player 1 Name:");
        p1Label.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        TextField p1Field = new TextField("Player 1");

        Label p2Label = new Label("Player 2 Name:");
        p2Label.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        TextField p2Field = new TextField("Player 2");

        Label colorLabel = new Label("Player 1 Color:");
        colorLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        ComboBox<String> colorBox = new ComboBox<>();
        colorBox.getItems().addAll("White", "Black");
        colorBox.setValue("White");

        grid.add(p1Label, 0, 0);
        grid.add(p1Field, 1, 0);
        grid.add(p2Label, 0, 1);
        grid.add(p2Field, 1, 1);
        grid.add(colorLabel, 0, 2);
        grid.add(colorBox, 1, 2);

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> showMainMenu());
        
        Button startBtn = new Button("Start Battle!");
        startBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        startBtn.setOnAction(e -> {
            String p1Name = p1Field.getText().trim();
            String p2Name = p2Field.getText().trim();
            if(p1Name.isEmpty()) p1Name = "Player 1";
            if(p2Name.isEmpty()) p2Name = "Player 2";
            
            String whitePlayer = colorBox.getValue().equals("White") ? p1Name : p2Name;
            String blackPlayer = colorBox.getValue().equals("White") ? p2Name : p1Name;
            
            ChessBoard board = new ChessBoard();
            board.addPieces(); // Standard initial pieces
            startGame(whitePlayer, blackPlayer, board, true);
        });

        buttons.getChildren().addAll(backBtn, startBtn);
        layout.getChildren().addAll(title, grid, buttons);

        primaryStage.setScene(new Scene(layout, 600, 600));
    }

    public void startGame(String whitePlayer, String blackPlayer, ChessBoard board, boolean isNewGame) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #333333;");

        // Top info bar
        HBox topBar = new HBox(20);
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(15));
        
        Label statusLabel = new Label();
        statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        statusLabel.setStyle("-fx-text-fill: white;");
        topBar.getChildren().add(statusLabel);
        root.setTop(topBar);

        board.setGameManager(this);
        board.setPlayers(whitePlayer, blackPlayer);
        board.setStatusLabel(statusLabel);
        board.updateStatus();
        
        if (isNewGame) {
            board.saveGame(); // Save initial state so we can resume immediately if closed
        }
        
        StackPane boardContainer = new StackPane(board);
        boardContainer.setAlignment(Pos.CENTER);
        boardContainer.setPadding(new Insets(20));
        root.setCenter(boardContainer);

        // Bottom controls
        HBox bottomBar = new HBox(20);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(15));
        
        Button mainBtn = new Button("Main Menu");
        mainBtn.setOnAction(e -> showMainMenu());
        Button quitAppBtn = new Button("Quit Application");
        quitAppBtn.setOnAction(e -> Platform.exit());
        
        bottomBar.getChildren().addAll(mainBtn, quitAppBtn);
        root.setBottom(bottomBar);

        primaryStage.setScene(new Scene(root, 700, 750));
    }

    private void loadGame() {
        File f = new File(SAVE_FILE);
        if (!f.exists()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "No save file found.");
            a.show();
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String whitePlayer = br.readLine();
            String blackPlayer = br.readLine();
            boolean whiteTurn = Boolean.parseBoolean(br.readLine());
            
            // Reconstruct board
            ChessBoard board = new ChessBoard();
            board.whiteTurn = whiteTurn;
            
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                // format: name,color,row,col,hasMoved
                String name = parts[0];
                String color = parts[1];
                int r = Integer.parseInt(parts[2]);
                int c = Integer.parseInt(parts[3]);
                boolean hasMoved = Boolean.parseBoolean(parts[4]);
                
                Cell dest = board.getCells()[r][c];
                Piece p = null;
                switch (name) {
                    case "pawn": p = new Pawn(color, dest); break;
                    case "rook": p = new Rook(color, dest); break;
                    case "knight": p = new Knight(color, dest); break;
                    case "bishop": p = new Bishop(color, dest); break;
                    case "queen": p = new Queen(color, dest); break;
                    case "king": p = new King(color, dest); break;
                }
                if (p != null) {
                    p.setHasMoved(hasMoved);
                    dest.addPiece(p);
                    dest.setGraphic(p.getImage());
                }
            }
            startGame(whitePlayer, blackPlayer, board, false);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR, "Failed to load save file.");
            a.show();
        }
    }

    public void handleGameOver(String title, String message) {
        // Delete save file so "Load Game" isn't active for a finished game
        File f = new File(SAVE_FILE);
        if (f.exists()) f.delete();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText("What would you like to do next?");

        ButtonType btnNewGame = new ButtonType("New Game");
        ButtonType btnMainMenu = new ButtonType("Main Menu");
        ButtonType btnQuit = new ButtonType("Quit");
        
        alert.getButtonTypes().setAll(btnNewGame, btnMainMenu, btnQuit);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == btnNewGame) {
                showSetupMenu();
            } else if (result.get() == btnMainMenu) {
                showMainMenu();
            } else {
                Platform.exit();
            }
        }
    }
}
