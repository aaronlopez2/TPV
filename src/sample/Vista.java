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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;


public class Vista extends Application {
    // variables
    private Boolean autentication;
    private Boolean enter;
    private Boolean hideLog = false;
    // Instancias
    Controller ctrler = new Controller();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // tool kit para la memoria tener el tamaño de la pantalla y hacer adaptables los elementos del programa
    private int screenHeight = screenSize.height;
    private int screenWidth = screenSize.width;

    // Stages
    private Stage baseTPV;  // Stage para el GUI
    private Stage loginTPV; // recoge la primera Stage
    // Scenes
    private Scene rootTPV; // escena para el GUI

    // paneles base
    private Scene root; // Scena para el login
    private StackPane base;
    private StackPane stackBase; // panel base para GUI
    private GridPane form; // texto y etiquetas
    private BorderPane login,bpBaseTpv; // grid pane hbox
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
        login.setOnKeyPressed(e -> {
            try {
                metodoLanzarPanelBase(userTxtf.getText(),e);
            } catch (Exception ex) {
                System.err.println("siguiente panel por teclado ha petado");
            }
        });
        form = new GridPane();
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
            try {
                metodoLanzarPanelBase(userTxtf.getText());

            } catch (Exception ex) {
                System.err.println("siguiente panel por teclado ha petado");
            }
            ctrler.mensaje();
        });
        submit.setOnKeyPressed(keyEvent -> { // evento al pulsar una tecla

        });
        cancel = new Button("Cancelar");
        cancel.setOnAction((e) -> {
            System.exit(0);
        });
        buttonContainer.getChildren().addAll(submit,cancel);
        buttonContainer.setSpacing(25);
        buttonContainer.setPadding(new Insets(20,0,0,0));
        // botones

        login.setBottom(buttonContainer);
        login.setLeft(form);


    }
    private void generarPanelBase(String user) {
        bpBaseTpv = new BorderPane();
        // se buscará el nombre del usuario en la base de datos, en la tabla el tipo de permiso
        // comprobacion de permiso
        int permiso = 1;
        switch(permiso){
            case 0:
                break;
            case 1:// permiso == 1 para el dependiente
                break;
            case 2:// permiso == 2 para el supervisor
                break;
            case 3:// permiso == 3 para el administrador del sistema
                break;
            case -1:// permiso == -1 si hay un fallo en la BBDD
                System.err.println("ERROR 500 No se encuentra usuario en la BBDD");
                break;
            default:
                System.out.println("No tiene permisos");
        }
    }
    private void panelBaseDependiente() { // el panel que se genera para el dependiente

    }
    private void panelBaseManager() {   // el panel que se genera para el supervisor

    }
    private void panelSysAdmin() { // panel para el administrador del sistemas

    }
    private void panelBase(String user){
        // cuando boton pasa aceptar hace esto
        try {
            baseTPV = new Stage();
            stackBase = new StackPane();
            stackBase.setStyle("-fx-background-color: #00ff22");
            stackBase.setMaxHeight(screenHeight);
            stackBase.setMaxWidth(screenWidth);
            generarPanelBase(user);


            rootTPV = new Scene(stackBase,900,800);
            baseTPV.setScene(rootTPV);
            baseTPV.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // control de paneles
    private void metodoLanzarPanelBase(String user, KeyEvent e) {
        if(e.getCode().equals(KeyCode.ENTER)) {
            System.out.println("Has pulsado Enter");
            autentication = ctrler.Aceptar(userTxtf.getText(),pwdTxtf.getText()); // comprobar el usuario y la contraseña introducidos
            enter = ctrler.autenticar(autentication);
            if(enter) {
                loginTPV.close();
                this.panelBase(user);
            } else {
                // no ha entrado, mensaje de fallo de autenticación
                System.out.println("FALLO");
            }
        }
        // comprobacion de usuario
        // recoge el tipo de usuario admin 1 supervisor 2 y dependiente 3

    } // polimorfismo
    private void metodoLanzarPanelBase(String user) {

            autentication = ctrler.Aceptar(userTxtf.getText(),pwdTxtf.getText()); // comprobar el usuario y la contraseña introducidos
            enter = ctrler.autenticar(autentication);
            if(enter) {
                loginTPV.close();
                this.panelBase(user);
            } else {
                // no ha entrado, mensaje de fallo de autenticación
                System.out.println("FALLO");
            }

        // comprobacion de usuario
        // recoge el tipo de usuario admin 1 supervisor 2 y dependiente 3

    } // polimorfismo



    @Override
    public void start(Stage primaryStage) throws Exception{
        loginTPV = new Stage();
        loginTPV = primaryStage;
        loginTPV.setResizable(false);
        base = new StackPane();
        base.setPadding(new Insets(100));

        generarLogin();
        base.getChildren().add(login);
        root = new Scene(base,400,200);
        loginTPV.setTitle("TPV");
        loginTPV.setScene(root);
        loginTPV.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
