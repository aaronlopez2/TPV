package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    private Scene root;
    private Pane base;
    @Override
    public void start(Stage primaryStage) throws Exception{
        base = new Pane();
        root = new Scene(base,200,200);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(this.root);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
