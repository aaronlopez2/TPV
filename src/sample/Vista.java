package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Vista extends Application {
    String[] buttonText = {"Aceptar", "Cancelar"};

    // paneles
    private Scene scene;
    private StackPane base;
    private BorderPane login;
    private GridPane cred;
    private HBox botons;
    // paneles
    // etiquetas
    private Label user,pwd;
    // etiquetas
    // textfields
    private TextField tfUser, tfPwd;
    // textfields
    // botones
    private JButton submit,cancel;
    // botones

    @Override
    public void start(Stage primaryStage) throws Exception{
        // instanciamos paneles
        base = new StackPane();
        login = new BorderPane();
        cred = new GridPane();
        botons = new HBox();
        // instanamos componentes de panel botons
        submit = new JButton();
        submit.setText(buttonText[0]);
        cancel.setText(buttonText[1]);



        login.setCenter(cred);
        login.setBottom(botons);
        // base.getChildren();
        scene = new Scene(base , 950, 650);
        primaryStage.setTitle("TPV");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
