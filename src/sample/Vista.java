package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Vista extends Application {
    // variables
    String autentication;
    // estructura
    Controller ctrler = new Controller();

    // paneles base
    private Scene root;
    private StackPane base;
    private GridPane form; // texto y etiquetas
    private BorderPane login; // grid pane hbox
    private HBox buttonContainer; // botones aceptar y cancelar

    // botones
    private Button submit,cancel;

    // etiquetas
    private Label userLbl, pwdLbl;

    // campos de texto
    private TextField userTxtf;
    private PasswordField pwdTxtf;

    private void generarLogin() {
        login = new BorderPane();
        login.setPadding(new Insets(20));

        form = new GridPane();
        //form.setAlignment(Pos.CENTER);
        form.setHgap(10);
        form.setVgap(10);

        userLbl = new Label("Usuario");
        pwdLbl = new Label("Contraseña");
        userTxtf = new TextField();
        userTxtf.setPromptText("Nombre o correo electronico");
        pwdTxtf = new PasswordField();
        pwdTxtf.setPromptText("Contraseña");

        form.add(userLbl,0,0);
        form.add(userTxtf,1,0);
        form.add(pwdLbl,0,1);
        form.add(pwdTxtf,1,1);


        // botones
        buttonContainer = new HBox();
        submit = new Button("Aceptar");
        submit.setOnAction((e) -> {
            submit.setOnKeyPressed(keyEvent -> { // evento al pulsar una tecla
                try {
                    if(keyEvent.getCode().equals(KeyCode.ENTER)){ // si esa tecla es enter se envia la información
                        System.out.println("Has pulsado Enter");
                        autentication = ctrler.Aceptar(userTxtf.getText(),pwdTxtf.getText()); // comprobar el usuario y la contraseña introducidos
                        ctrler.autenticar(autentication);
                    }
                } catch (Exception err) {
                    System.err.println("ERROR ERROR ERROR, ha esplotado en algun momento del login");
                }
            });
            ctrler.mensaje();
        });
        cancel = new Button("Cancelar");
        cancel.setOnAction((e) -> {
            ctrler.mensaje();
        });
        buttonContainer.getChildren().addAll(submit,cancel);
        buttonContainer.setSpacing(25);
        buttonContainer.setPadding(new Insets(20,0,0,0));
        // botones

        login.setBottom(buttonContainer);
        login.setLeft(form);


    }
    // paneles
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setResizable(false);
        base = new StackPane();
        base.setPadding(new Insets(100));

        generarLogin();
        base.getChildren().add(login);
        root = new Scene(base,400,200);
        primaryStage.setTitle("TPV");
        primaryStage.setScene(root);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
