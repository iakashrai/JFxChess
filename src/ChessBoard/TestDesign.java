package ChessBoard;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TestDesign extends Application {

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        StackPane pane = new StackPane();
        ChessBoard board = new ChessBoard();
        pane.getChildren().add(board);
//        pane.setPrefSize(600,600);
        pane.setAlignment(board, Pos.CENTER);
        stage.setScene(new Scene(pane));
        stage.show();
    }
}
