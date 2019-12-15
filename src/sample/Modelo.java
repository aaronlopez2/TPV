package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



public class Modelo extends Application {
    // estructura
    Controller ctrler = new Controller();

    // paneles base
    private Scene root;
    private Pane base;
    private GridPane form; // texto y etiquetas
    private BorderPane login; // grid pane hbox
    private HBox buttonContainer; // botones aceptar y cancelar

    // botones
    private Button submit,cancel;

    // etiquetas
    private Label userLbl, pwdLbl;

    // campos de texto
    private TextField userTxtf, pwdTxtf;

    private void generarLogin() {
        login = new BorderPane();
        form = new GridPane();

        userLbl = new Label("Usuario");
        pwdLbl = new Label("Contraseña");
        userTxtf = new TextField();
        pwdTxtf = new TextField();
        form.add(userLbl,0,0);
        form.add(userTxtf,1,0);
        form.add(pwdLbl,0,1);
        form.add(pwdTxtf,1,1);

        buttonContainer = new HBox();
        submit = new Button("Aceptar");
        submit.setOnAction((e) -> {
            ctrler.mensaje();
        });
        cancel = new Button("Cancelar");
        cancel.setOnAction((e) -> {
            ctrler.mensaje();
        });
        buttonContainer.getChildren().addAll(submit,cancel);

        login.setBottom(buttonContainer);
        login.setCenter(form);

    }
    // paneles
    @Override
    public void start(Stage primaryStage) throws Exception{
        base = new Pane();
        generarLogin();
        base.getChildren().add(login);
        root = new Scene(base,400,400);
        primaryStage.setTitle("TPV");
        primaryStage.setScene(root);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
