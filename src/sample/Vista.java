package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;


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

    public GridPane getButtonsUserBase() {
        return buttonsUserBase;
    }

    private GridPane buttonsUserBase; // botones para la base de usuarios
    private BorderPane login,bpBaseTpv; // grid pane hbox
    private HBox buttonContainer; // contiene botones aceptar y cancelar en el login
    private VBox listContainer;
    private StackPane gridButtonsContainer; // centrar los botones del gridPane

    // botones
    private Button submit,cancel;
    private Button[] buttonsBase;

    // etiquetas
    private Label userLbl, pwdLbl;

    // campos de texto
    private TextField userTxtf;
    private PasswordField pwdTxtf;

    // MenuBar, menu, menuItems
    private MenuBar topTools;
    private Menu fileMenu, editMenu, windowMenu, helpMenu;
    private MenuItem[] itemsFile,itemsEdit,itemsWindow,itemsHelp;

    // arrays
    String[] listadoPedidos;
    private void initializeArrays() {
        int length = 50; // este variará despues de conectarse con la bbdd
        listadoPedidos = new String[length];
        for (int i = 0; i < length; i++) {
            listadoPedidos[i] = "Este texto es de prueba "+0+0+i;
        }
    }

    // generate objects
    private void generateMenuItems() {
        itemsFile = new MenuItem[10];
        itemsEdit = new MenuItem[10];
        itemsWindow = new MenuItem[10];
        itemsHelp = new MenuItem[10];
        for (int i = 0; i < itemsFile.length; i++) {
            MenuItem itemFile = new MenuItem("TEXTO "+i);
            MenuItem itemEdit = new MenuItem("TEXTO "+i);
            MenuItem itemWindow = new MenuItem("TEXTO "+i);
            MenuItem itemHelp = new MenuItem("TEXTO "+i);
            itemsFile[i] = itemFile;
            itemsEdit[i] = itemEdit;
            itemsWindow[i] = itemWindow;
            itemsHelp[i] = itemHelp;
        }
    } // generar barra de herramientas
    private void generateButtonsBase() { // inicializar los botones del TPV
        buttonsBase = new Button[5];
        for (int i = 0; i < buttonsBase.length; i++) {
            Button bot = new Button("BOTON "+i);

            bot.setPrefSize(150,150);
            buttonsBase[i] = bot;
            buttonsBase[0].setText("LISTADO");
            buttonsBase[0].setOnAction(e -> {
                System.out.println("Boton listado funciona");
                generatePaneLateralIzq();
            });
        }

    }
    private void generatePaneLateralIzq(){
        ListView<String> list = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList(listadoPedidos);
        list.setItems(items);
        list.prefWidth(500);
        listContainer = new VBox();
        listContainer.setStyle("-fx-background-color: #003322");
        //listContainer.setPrefHeight(getButtonsUserBase().getHeight());
        //listContainer.setMaxHeight(getButtonsUserBase().getHeight());
        listContainer.getChildren().add(list);
        //VBox.setVgrow(listContainer, Priority.ALWAYS);

        bpBaseTpv.setLeft(listContainer);
        list.prefHeight(1100);
    }




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
        gridButtonsContainer = new StackPane();
        baseTPV = new Stage();
        stackBase = new StackPane();
        stackBase.setStyle("-fx-background-color: #8DAA91");
        stackBase.setMaxHeight(screenHeight);
        stackBase.setMaxWidth(screenWidth);
        // se buscará el nombre del usuario en la base de datos, en la tabla el tipo de permiso
        // comprobacion de permiso
        int permiso = 1;
        generarBotonesBase(permiso);
        switch(permiso){
            case 0:
                break;
            case 1:// permiso == 1 para el dependiente
                baseTPV.widthProperty().addListener(e -> {

                });
                stackBase.getChildren().add(bpBaseTpv);
                bpBaseTpv.setStyle("-fx-background-color: #486187");
                bpBaseTpv.setTop(barraHerramientasPanelBase());
                gridButtonsContainer.getChildren().add(buttonsUserBase);
                gridButtonsContainer.setAlignment(Pos.CENTER);
                gridButtonsContainer.setPadding(new Insets(0,40,0,40));
                //gridButtonsContainer.setMaxHeight(screenHeight);
                //gridButtonsContainer.setMaxWidth(screenWidth);
                gridButtonsContainer.setStyle("-fx-background-color: #ffffff");
                bpBaseTpv.setCenter(gridButtonsContainer);
                bpBaseTpv.setBottom(new Label("PRUEBA DE ETIQUETA Y ESPACIO AVISO LEGAL"));

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

    private void panelBase(String user){
        // cuando boton pasa aceptar hace esto
        try {
            generarPanelBase(user);


            rootTPV = new Scene(stackBase,1200,800);
            baseTPV.setScene(rootTPV);
            baseTPV.setMaximized(true);

            baseTPV.setResizable(false);
            baseTPV.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MenuBar barraHerramientasPanelBase() {
        try {
            topTools = new MenuBar(); //bpBaseTpv
            topTools.prefWidthProperty().bind(baseTPV.widthProperty());

            fileMenu = new Menu("ARCHIVO");
            fileMenu.getItems().addAll(itemsFile);

            editMenu = new Menu("EDITAR");
            editMenu.getItems().addAll(itemsEdit);

            windowMenu = new Menu("VENTANA");
            windowMenu.getItems().addAll(itemsWindow);

            helpMenu = new Menu("AYUDA");
            helpMenu.getItems().addAll(itemsHelp);

            topTools.getMenus().addAll(fileMenu,editMenu,windowMenu,helpMenu);
        } catch (Exception ex) {
            System.out.println("ALGO HA FALLADO");
        }

        return topTools;
    }
    private GridPane generarBotonesBase(int userlvl) {
        try {
            buttonsUserBase = new GridPane();
            buttonsUserBase.setGridLinesVisible(true);
            buttonsUserBase.setPrefSize(screenWidth,screenHeight);
            buttonsUserBase.setAlignment(Pos.CENTER);
            buttonsUserBase.setHgap(40);
            buttonsUserBase.setVgap(55);
            switch(userlvl) {
                case 0:
                    break;
                case 1: // permiso para el dependiente
                    for (int i = 0; i < buttonsBase.length; i++) {
                        buttonsUserBase.add(buttonsBase[i], i,0);
                    }
                    //if (baseTPV.isFullScreen()) {
                    //    for (int i = 0; i < buttonsBase.length; i++) {
                    //        buttonsUserBase.add(buttonsBase[i], i,3);
                    //    }
                    //} else {
                    //    for (int i = 0; i < buttonsBase.length; i++) {
                    //        if( i > 2) {
                    //            buttonsUserBase.add(buttonsBase[i], i,5);
                    //        } else {
                    //            buttonsUserBase.add(buttonsBase[i], i,3);
                    //        }
//
                    //    }
                    //}
                    break;
                case 2:// permiso == 2 para el supervisor
                    break;
                case 3:// permiso == 3 para el administrador del sistema
                    break;
                case -1:
                    break;
                default:
                    System.out.println("ASSDASD");

            }
        } catch (Exception e) {

        }
        return null;
    }
    private void resizeGridBotonesBase() { // ajustar la posicion de los botones del grid pane cuando se abra el panel lateral


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
                errorPWD();
                System.out.println("FALLO");
            }
        }
        // comprobacion de usuario
        // recoge el tipo de usuario admin 1 supervisor 2 y dependiente 3

    } // polimorfismo si se lanza pulsando enter
    private void metodoLanzarPanelBase(String user) {
            autentication = ctrler.Aceptar(userTxtf.getText(),pwdTxtf.getText()); // comprobar el usuario y la contraseña introducidos
            enter = ctrler.autenticar(autentication);
            if(enter) {
                loginTPV.close();
                this.panelBase(user);
            } else {
                // no ha entrado, mensaje de fallo de autenticación
                errorPWD();
                System.out.println("FALLO");
            }
        // comprobacion de usuario
        // recoge el tipo de usuario admin 1 supervisor 2 y dependiente 3

    } // polimorfismo si se lanza pulsando aceptar

    // POP UPS
    private void errorPWD() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Contraseña incorrecta");
        alert.setHeaderText(null);
        alert.setContentText("La contraseña que has introducido no es valida");
        alert.showAndWait();
    }
    private void popUpAlerta(){
        // WARNING MESSAGE
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Look, a Warning Dialog");
        alert.setContentText("Careful with the next step!");

        alert.showAndWait();

    }



    @Override
    public void start(Stage primaryStage) throws Exception{
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ctrler.model.conectBBDD();
            System.out.println("working!!");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        try {
            generateMenuItems();
            generateButtonsBase();
            initializeArrays();
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
        }catch (Exception e) {

        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
