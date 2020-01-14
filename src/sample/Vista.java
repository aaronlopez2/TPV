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
    private int userlvl;
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
    private Button submit, cancel, local, externo, volver, volver2, crearUser;
    private Button[] buttonsBase;

    // etiquetas
    private Label userLbl, pwdLbl, userLabel, passLabel, permisoLabel;

    // campos de texto
    private TextField userTxtf;
    private PasswordField pwdTxtf;
    /*Vicent*/ private TextField userField;
    /*Vicent*/ private TextField passField;

    // MenuBar, menu, menuItems
    private MenuBar topTools;
    private Menu fileMenu, editMenu, windowMenu, helpMenu;
    private MenuItem[] itemsFile,itemsEdit,itemsWindow,itemsHelp;

    // arrays
    String[] listadoPedidos;
    String[] buttonsNam;
    private void initializeArrays() {
        int length = 50; // este variará despues de conectarse con la bbdd
        buttonsNam = new String[]{"Almacen","Ventas","Documentos","Mantenimiento","Informes","Adm. usuarios"};
        listadoPedidos = new String[length];
        for (int i = 0; i < length; i++) {
            listadoPedidos[i] = "Este texto es de prueba "+0+0+i;
        }
    }

    // generate objects
    private void generatePanelDer() {
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

        bpBaseTpv.setRight(listContainer);
        list.prefHeight(1100);    }

        boolean visibleLeft = false;
        boolean visibleRight = false;

        private void generateAlmacenButtons() {
        local = new Button("Genero En Tienda");
        externo = new Button("Pedido Almacen");
        volver = new Button("Atrás");
        local.setPrefSize(150,150);
        externo.setPrefSize(150,150);
        local.setOnAction((e) -> {
            generatePaneLateralIzq();
            if(visibleLeft) {
                bpBaseTpv.setLeft(null);
                visibleLeft = false;
            } else {
                visibleLeft = true;
            }

        });
        externo.setOnAction((e) -> {

            generatePanelDer();
            if(visibleRight) {
                bpBaseTpv.setRight(null);
                visibleRight = false;
            } else {
                visibleRight = true;
            }

        });
        volver.setOnAction((e) -> {
            if(bpBaseTpv.getRight() == null) {

            } else {
                bpBaseTpv.setRight(null);
            }
            buttonsUserBase.getChildren().removeAll(local,externo,volver);
            initArrayButtons(this.userlvl);
            gridButtonsContainer.getChildren().add(generarBotonesBase(this.userlvl));
        });


        buttonsUserBase.add(local, 0,0);
        buttonsUserBase.add(externo,1,0);
        buttonsUserBase.add(volver,2,0);

    }
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
    private void initArrayButtons(int permisos) { // inicializar los botones del TPV
        switch (permisos) {
            case 1:
                buttonsBase = new Button[3];
                for (int i = 0; i < buttonsBase.length; i++) {
                    Button bot = new Button();
                    bot.setPrefSize(150,150);
                    buttonsBase[i] = bot;
                    buttonsBase[i].setText(buttonsNam[i]);
                    System.out.println(buttonsBase[i]);
                }
                System.out.println("Permiso nivel 1");
                System.out.println(buttonsBase.length);
                break;
            case 2:
                buttonsBase = new Button[5];
                for (int i = 0; i < buttonsBase.length; i++) {
                    Button bot = new Button();
                    bot.setPrefSize(150,150);
                    buttonsBase[i] = bot;
                    buttonsBase[i].setText(buttonsNam[i]);
                }
                System.out.println("Permiso nivel 2");
                System.out.println(buttonsBase.length);
                break;
            case 3:
                buttonsBase = new Button[6];
                for (int i = 0; i < buttonsBase.length; i++) {
                    Button bot = new Button();
                    bot.setPrefSize(150,150);
                    buttonsBase[i] = bot;
                    buttonsBase[i].setText(buttonsNam[i]);
                }
                System.out.println("Permiso nivel 3");
                System.out.println(buttonsBase.length);
                break;
            default:
                System.out.println("DEfault switch generate buttons base error no case entered");
        }
        try
        {

            buttonsBase[0].setOnAction((e) -> { // ALMACEN
                System.out.println("Entra");
                // buscar panel de los botones y limpiar
                buttonsUserBase.getChildren().removeAll(buttonsBase);
                generateAlmacenButtons();
            });// ALMACEN
            buttonsBase[1].setOnAction(e -> { // VENTAS
                buttonsUserBase.getChildren().removeAll(buttonsBase);
                generateVentasPane();
            });  // VENTAS
            buttonsBase[2].setOnAction(e -> { // DOCUMENTOS --- LISTADOS
                generatePaneLateralIzq();
                if(visibleLeft) {
                    bpBaseTpv.setLeft(null);
                    visibleLeft = false;
                } else {
                    visibleLeft = true;
                }
            }); // DOCUMENTOS --- LISTADOS
            buttonsBase[3].setOnAction(e -> { // MANTENIMIENTO --- AYUDA A SOPORTE --- GMAIL API

            }); // MANTENIMIENTO --- AYUDA A SOPORTE --- GMAIL API
            buttonsBase[4].setOnAction(e ->{ // INFORMES --- FILTROS ESTADISTICAS

            }); // INFORMES --- FILTROS ESTADISTICAS
            buttonsBase[5].setOnAction(e -> { // ADM. USUARIOS --- AÑADIR USUARIOS
            	//Vicent
            	buttonsUserBase.getChildren().removeAll(buttonsBase);
            	administraUsersView();
            }); // ADM. USUARIOS --- AÑADIR USUARIOS

        } catch (Exception ex)
        {
            ex.printStackTrace();
            System.out.println("FALLO EN ALGUN BOTON, SEGURAMENTE POR NO EXISTIR");
            System.out.println("O fallo por otra cosa");
        }

    }
    private TextField filtro;
    private TextField marcaTF;
    private TextField tallaTF;
    private Label marca;
    private Label talla;
    private void generateVentasPane() {
        filtro = new TextField();
        marcaTF = new TextField();
        tallaTF = new TextField();
        marca = new Label("MARCA");
        talla = new Label("TALLA");
        buttonsUserBase.add(filtro,1,0);
        buttonsUserBase.add(marca, 0,1);
        buttonsUserBase.add(talla,0,2);
        buttonsUserBase.add(marcaTF,1,1);
        buttonsUserBase.add(tallaTF, 1,2);

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
        int permiso = 3;
        stackBase.getChildren().add(bpBaseTpv);
        bpBaseTpv.setStyle("-fx-background-color: #486187");
        gridButtonsContainer.setAlignment(Pos.CENTER);
        gridButtonsContainer.setPadding(new Insets(0,40,0,40));
        //gridButtonsContainer.setMaxHeight(screenHeight);
        //gridButtonsContainer.setMaxWidth(screenWidth);
        gridButtonsContainer.setStyle("-fx-background-color: #ffffff");
        bpBaseTpv.setCenter(gridButtonsContainer);
        bpBaseTpv.setBottom(new Label("PRUEBA DE ETIQUETA Y ESPACIO AVISO LEGAL"));
        switch(permiso){
            case 0:
                break;
            case 1:// permiso == 1 para el dependiente
                bpBaseTpv.setTop(barraHerramientasPanelBase());
                try {
                    gridButtonsContainer.getChildren().add(generarBotonesBase(permiso));
                } catch (Exception ex) {
                    System.out.println("Error al añadir el grid pane al stack pane gridbuttonscontainer");
                }
                break;
            case 2:// permiso == 2 para el supervisor
                System.out.println("permisos de nivel 2");
                bpBaseTpv.setTop(barraHerramientasPanelBase());
                try {
                    gridButtonsContainer.getChildren().add(generarBotonesBase(permiso));
                } catch (Exception ex) {
                    System.out.println("Error al añadir el grid pane al stack pane gridbuttonscontainer");
                }
                break;
            case 3:// permiso == 3 para el administrador del sistema
            	 System.out.println("permisos de nivel 3");
                 bpBaseTpv.setTop(barraHerramientasPanelBase());
                 try {
                     gridButtonsContainer.getChildren().add(generarBotonesBase(permiso));
                 } catch (Exception ex) {
                     System.out.println("Error al añadir el grid pane al stack pane gridbuttonscontainer");
                 }
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
        this.userlvl = userlvl;
        try {
            initArrayButtons(userlvl);
            buttonsUserBase = new GridPane();

            //buttonsUserBase.setGridLinesVisible(true);
            buttonsUserBase.setPrefSize(screenWidth,screenHeight);
            buttonsUserBase.setAlignment(Pos.CENTER);
            buttonsUserBase.setHgap(200);
            buttonsUserBase.setVgap(55);

            for (int i = 0; i < buttonsBase.length; i++) {
                buttonsUserBase.add(buttonsBase[i], i,0);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return buttonsUserBase;
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

    }
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

    }

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
            System.out.println("Not working");
        }
        try {
            // inicialziar objetos
            initializeArrays();
            generateMenuItems();
            // inicializar objetos

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
    
    //Codigo Vicent
    
    private void administraUsersView() {
    	
    	
    	userLabel = new Label("Usuario");
        passLabel = new Label("Contraseña");
        permisoLabel = new Label("Permisos");
        
        userField = new TextField();
        userField.setPromptText("Nombre o correo electronico");
        passField = new TextField();
        passField.setPromptText("Contraseña");
        
        //ListView<String> lvl = new ListView<>();
        ObservableList<String> permis = FXCollections.observableArrayList();
        permis.addAll("1", "2", "3");
   	 	ComboBox<String> cbx = new ComboBox<>(permis);
   	 	
   	 	
        crearUser = new Button("Crear");
        crearUser.setOnAction((e) -> {
        	Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Usuario creado");
            alert.setHeaderText(null);
            alert.setContentText("El usuario se ha a�adido correctamente");
            alert.showAndWait();
        });
        
        volver2 = new Button("Atrás");
        volver2.setOnAction((e) -> {
            buttonsUserBase.getChildren().removeAll(userLabel, passLabel, permisoLabel, userField, passField, volver2, cbx, crearUser);
            initArrayButtons(this.userlvl);
            gridButtonsContainer.getChildren().add(generarBotonesBase(this.userlvl));
        });
        
        buttonsUserBase.add(userLabel,0,0);
        buttonsUserBase.add(userField,1,0);
        buttonsUserBase.add(passLabel,0,1);
        buttonsUserBase.add(passField,1,1);
        buttonsUserBase.add(permisoLabel, 0, 2);
        buttonsUserBase.add(cbx, 1, 2);
        buttonsUserBase.add(crearUser, 0, 3);
        buttonsUserBase.add(volver2, 1, 3);
    	
    }
    /*
     * 
     ObservableList<String> items = FXCollections.observableArrayList();
	 items.addAll("item-1", "item-2", "item-3", "item-4", "item-5");
	 ComboBox<String> cbx = new ComboBox<>(items);
     * 
     * */
    
    //Fin Codigo Vicent
}
