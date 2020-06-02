package sample;

import dbo.Almacen;
import dbo.Articulos;
import dbo.Empleados;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Vista extends Application {


    private Boolean autentication,enter,visibleLeft = false,visibleRight = false;
    private int userlvl;


    Controller ctrler = new Controller();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // tool kit para tener el tamaÃ±o de la pantalla y hacer adaptables los elementos del programa
    private int screenHeight = screenSize.height;
    private int screenWidth = screenSize.width;


    private Stage baseTPV;
    private Stage loginTPV;
    private Scene rootTPV;
    private Scene root;

    private StackPane base;
    private StackPane stackBase;

    private GridPane form;
    private GridPane buttonsUserBase; // botones para la base de usuarios
    private BorderPane login, bpBaseTpv; // grid pane hbox
    private HBox buttonContainer; // contiene botones aceptar y cancelar en el login
    private VBox listContainer;
    private StackPane gridButtonsContainer; // centrar los botones del gridPane

    // botones
    private Button submit, cancel, local, externo, volver, volver2, crearUser;
    private Button[] buttonsBase;


    // etiquetas
    private Label userLbl_name, pwdLbl, userLabel, passLabel, permisoLabel;
    private Label user_dni, user_ap1, user_ap2, user_address, user_nss, user_afSind, user_tlf_emp, user_mail, user_pob, user_birth, user_cp;
    private Label marca;
    private Label talla;

    // campos de texto
    private TextField userTxtf;
    // creacion usuarios
    private TextField dni_txt,name_txt,ap1_txt,ap2_txt,adss_txt,nss_txt,nafSind_txt,ntlf_txt,email_txt,pob_txt,dateB_txt,cp_txt;
    // creacion usuarios
    private TextField prodPrecioTF;
    private TextField marcaTF;
    private TextField tallaTF;

    private PasswordField pwdTxtf;
    //private TextField userField;
    //private TextField passField;

    // MenuBar, menu, menuItems
    private MenuBar topTools;
    private Menu fileMenu, editMenu, windowMenu, helpMenu;
    private MenuItem[] itemsFile, itemsEdit, itemsWindow, itemsHelp;

    // arrays
    String[] listadoPedidos;
    String[] buttonsNam;

    private void initializeArrays() {
        buttonsNam = new String[]{"Almacen", "Ventas", "Documentos", "Mantenimiento", "Adm. usuarios"};
    }

    // generate objects
    private void generatePanelDer() throws SQLException {
        ListView<String> list = new ListView<>();
        // go to controller
        Almacen[] almcs = null;
        if(ctrler.getAlmacen() == null) {
            errorPWD("No se han encontrado articulos");
        } else {
            almcs = ctrler.getAlmacen();
            listadoPedidos = new String[almcs.length];
            for (int i = 0; i < listadoPedidos.length; i++) {
                listadoPedidos[i] =" Id: "+ almcs[i].getIdArticulo()+" - "+ almcs[i].getMarca() +" - "+ almcs[i].getModelo() + " | Precio: " +almcs[i].getCoste() + " - Cantidad: "+almcs[i].getCantidad();

            }
        }
        ObservableList<String> items = FXCollections.observableArrayList(listadoPedidos);
        listContainer = new VBox();
        list.setItems(items);
        list.setPrefHeight(0);
        list.prefHeightProperty().bind(listContainer.heightProperty());
        list.setOnMouseClicked(e -> {
            if(e.getClickCount() == 2) {
                System.out.println("DOBLE CLICK EN UN ELEMENTO DE LA LISTA "+list.getSelectionModel().getSelectedItem());
                String element = list.getSelectionModel().getSelectedItem();
                System.out.println(element.substring(0,10));
                System.out.println(element.substring(4,10));
                System.out.println(element.substring(4,10).replace(" ",""));
                element = element.substring(4,10).replace(" ","");
                element = element.replace("-","");
                System.out.println("ID PRODUCTO: "+element);
                ctrler.pedidoAlmacen(element);
                bpBaseTpv.setLeft(null);
                bpBaseTpv.setRight(null);
                try {
                    generatePaneLateralIzq();
                    generatePanelDer();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println("error al cargar listado productos Vista.generatePaneDer setOnMouseClicked");
                }
            }

        });
        VBox.setVgrow(listContainer,Priority.ALWAYS);
        listContainer.setPrefWidth(400);
        listContainer.setStyle("-fx-background-color: #EAD7D7");
        listContainer.getChildren().add(list);
        bpBaseTpv.setRight(listContainer);
        list.prefHeight(1100);
    }



    private void generateAlmacenButtons() {

        local = new Button("Genero En Tienda");
        externo = new Button("Pedido Almacen");
        volver = new Button("Back");
        local.setPrefSize(150, 150);
        externo.setPrefSize(150, 150);
        local.setOnAction((e) -> {
            try {
                generatePaneLateralIzq();
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("ERROR AL GENERAR PANEL IZQUIERDO");
            }
            if (visibleLeft) {
                bpBaseTpv.setLeft(null);
                visibleLeft = false;
            } else {
                visibleLeft = true;
            }

        });
        externo.setOnAction((e) -> {

            try {
                generatePanelDer();
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("ERROR AL GENERAR PANEL DERECHO");
            }
            if (visibleRight) {
                bpBaseTpv.setRight(null);
                visibleRight = false;
            } else {
                visibleRight = true;
            }

        });
        volver.setOnAction((e) -> {
            if (visibleLeft) {
                bpBaseTpv.setLeft(null);
                visibleLeft = false;
            }
            if (bpBaseTpv.getRight() == null) {

            } else {
                bpBaseTpv.setRight(null);
            }
            buttonsUserBase.getChildren().removeAll(local, externo, volver);
            initArrayButtons(this.userlvl);
            gridButtonsContainer.getChildren().add(generarBotonesBase(this.userlvl));
        });


        buttonsUserBase.add(local, 0, 0);
        buttonsUserBase.add(externo, 1, 0);
        buttonsUserBase.add(volver, 2, 0);

    }

    private void generateMenuItems() {
        itemsFile = new MenuItem[1];
        itemsHelp = new MenuItem[1];
        for (int i = 0; i < itemsFile.length; i++) {
            MenuItem itemFile = new MenuItem("Cerrar sesion");
            MenuItem itemHelp = new MenuItem("Contactar");

            itemsFile[i] = itemFile;
            itemsFile[i].setOnAction(cerrarSesion);
            itemsHelp[i] = itemHelp;
            itemsHelp[i].setOnAction(enviarCorreo);
        }
    } // generar barra de herramientas

    private void initArrayButtons(int permisos) { // inicializar los botones del TPV
        switch (permisos) {
            case 1:
                buttonsBase = new Button[3];
                for (int i = 0; i < buttonsBase.length; i++) {
                    Button bot = new Button();
                    bot.setPrefSize(150, 150);
                    buttonsBase[i] = bot;
                    buttonsBase[i].setText(buttonsNam[i]);
                    System.out.println(buttonsBase[i]);
                }
                System.out.println("Permiso nivel 1");
                System.out.println(buttonsBase.length);
                break;
            case 2:
                buttonsBase = new Button[4];
                for (int i = 0; i < buttonsBase.length; i++) {
                    Button bot = new Button();
                    bot.setPrefSize(150, 150);
                    buttonsBase[i] = bot;
                    buttonsBase[i].setText(buttonsNam[i]);
                }
                System.out.println("Permiso nivel 2");
                System.out.println(buttonsBase.length);
                break;
            case 3:
                buttonsBase = new Button[5];
                for (int i = 0; i < buttonsBase.length; i++) {
                    Button bot = new Button();
                    bot.setPrefSize(150, 150);
                    buttonsBase[i] = bot;
                    buttonsBase[i].setText(buttonsNam[i]);
                }
                System.out.println("Permiso nivel 3");
                System.out.println(buttonsBase.length);
                break;
            default:
                System.out.println("DEfault switch generate buttons base error no case entered");
        }
        try {
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
                try {
                   //
                    ctrler.openFolderDoc();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }); // DOCUMENTOS --- LISTADOS
            buttonsBase[3].setOnAction(e -> { // MANTENIMIENTO --- AYUDA A SOPORTE --- GMAIL API
                buttonsUserBase.getChildren().removeAll(buttonsBase);
                generateEmailPanel();

            }); // MANTENIMIENTO --- AYUDA A SOPORTE --- GMAIL API
            buttonsBase[4].setOnAction(e -> { // ADM. USUARIOS --- AÃ‘ADIR USUARIOS

                buttonsUserBase.getChildren().removeAll(buttonsBase);
                administraUsersView();
            }); // ADM. USUARIOS --- AÃ‘ADIR USUARIOS
        } catch (Exception ex) {
            System.out.println("FALLO EN ALGUN BOTON, SEGURAMENTE POR NO EXISTIR");
            System.out.println("O fallo por otra cosa");
        }
    }

    private Button volver3, filterButonVentasPane, cleanFilters, pagoButton;
    private Label modelo, prodPrecio,idProducto,cantidadProd;
    private TextField modeloTF,idProductoTf,cantidadProdTF;

    private void generateVentasPane() {
        buttonsUserBase.setVgap(20);
        buttonsUserBase.setHgap(40);
        cantidadProd = new Label("Cantidad: ");
        cantidadProdTF = new TextField("0");
        idProducto = new Label("ID Product:");
        idProductoTf = new TextField();
        volver3 = new Button("Atras");
        pagoButton = new Button("Pago");
        filterButonVentasPane = new Button("Filtrar");
        cleanFilters = new Button("Limpiar filtros");
        prodPrecioTF = new TextField();
        marcaTF = new TextField();
        tallaTF = new TextField();
        prodPrecio = new Label("PRECIO");
        marca = new Label("MARCA");
        talla = new Label("TALLA");
        modelo = new Label("MODELO");
        modeloTF = new TextField();
        try {
            cleanFilters.setOnAction((e) -> {
                idProductoTf.setText("");
                cantidadProdTF.setText("0");
                prodPrecioTF.setText("");
                marcaTF.setText("");
                modeloTF.setText("");
                tallaTF.setText("");
            });
            volver3.setOnAction((e) -> {
                buttonsUserBase.getChildren().removeAll(prodPrecioTF, marca, tallaTF, prodPrecio,
                        filterButonVentasPane, pagoButton,
                        marcaTF, talla, volver3, cleanFilters,
                        modelo, modeloTF);
                initArrayButtons(this.userlvl);
                gridButtonsContainer.getChildren().add(generarBotonesBase(this.userlvl));
            });
            filterButonVentasPane.setOnAction(actionFilter);
            pagoButton.setOnAction(actionPago);
        } catch (Exception e) {
            popUpAlerta();
        }
        buttonsUserBase.add(idProducto, 0,0);
        buttonsUserBase.add(idProductoTf,1,0);
        buttonsUserBase.add(prodPrecio, 0, 2);
        buttonsUserBase.add(prodPrecioTF, 1, 2);
        buttonsUserBase.add(filterButonVentasPane, 3, 0);
        buttonsUserBase.add(cleanFilters, 4, 0);
        buttonsUserBase.add(marca, 0, 1);
        buttonsUserBase.add(talla, 0, 3);
        buttonsUserBase.add(modelo, 0, 4);
        buttonsUserBase.add(marcaTF, 1, 1);
        buttonsUserBase.add(tallaTF, 1, 3);
        buttonsUserBase.add(modeloTF, 1, 4);
        buttonsUserBase.add(cantidadProd, 0,5);
        buttonsUserBase.add(cantidadProdTF, 1, 5);
        buttonsUserBase.add(pagoButton, 3, 5);
        buttonsUserBase.add(volver3, 4, 5);
    }

    private Button volver4, sendEmail;
    private TextField titleTF;
    private ComboBox sendToCB;
    private TextArea contextTA;
    private Label emailTo, emailTitle, emailMessage;
    MailMnt mail = new MailMnt();
    EventHandler<ActionEvent> actionFilter = (e) -> {
        String idProducto = idProductoTf.getText();
        try {
            Articulos art = ctrler.getProducto(idProducto);
            marcaTF.setText(art.getMarca());
            tallaTF.setText(art.getTalla());
            modeloTF.setText(art.getModelo());
            prodPrecioTF.setText(String.valueOf(art.getCoste()) +  "\u20ac");
            if(marcaTF.getText().equals("")) {
                popUpAlerta("No se encuentra articulo");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            popUpAlerta("No se encuentra articulo");
            System.out.println("Error en filtro articulo vista.Eventhandler.actionFilter");
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            popUpAlerta("No se encuentra articulo");
            System.out.println("Error en filtro articulo vista.Eventhandler.actionFilter");
        }

    };
    EventHandler<ActionEvent> actionPago = (e) -> {
        boolean alerta = false;
        // gestionar el articulo con la base de datos cambiar estado a vendido
        String idProducto = idProductoTf.getText();
        String prodBrand = marcaTF.getText();
        String tallaProd = tallaTF.getText();
        String modeloProd = modeloTF.getText();
        int cantidadProd = Integer.parseInt(cantidadProdTF.getText());

        float precio = 0;
        try {
            precio = ctrler.getPrecio(idProducto);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("error en EventHandler call.getPrecio vista.actionPago");
        }
        if(idProducto.equals("")  || prodBrand.equals("") || tallaProd.equals("") || modeloProd.equals("")) {
            errorPWD("Rellene los filtros");
            alerta = true;
        } else {
            if(cantidadProd <= 0 && alerta == false) {
                errorPWD("la cantidad no puede ser menor o igual a 0");
            } else if(alerta == false){
                try {
                    if(ctrler.getCantidadProd(idProducto,cantidadProd)) {
                        Object[] data = {idProducto, prodBrand, tallaProd, modeloProd, cantidadProd, precio};
                        popUpConfirm("Realizando operacion");
                        ctrler.generarExcel(data);
                    } else {
                        System.out.println("NO SE HA PODIDO REALIZAR LA ACCION");
                        errorPWD("NO HAY STOCK SUFICIENTE");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.err.println("Error event handler Pago");
                } catch (SQLException sqlx){
                    sqlx.printStackTrace();
                    System.out.println("ERROR AL RECOGER CANTIDAD event handler Pago");
                }
            }

        }



    };

    EventHandler<ActionEvent> cerrarSesion = (e) -> {
        this.baseTPV.close();
        this.loginTPV.show();
        userTxtf.clear();
        pwdTxtf.clear();
    };
    EventHandler<ActionEvent> enviarCorreo = (e) -> {
        mail.enviar_correo();
    };
    EventHandler<ActionEvent> mntEnviarCorreo = (e) -> {
        String as = titleTF.getText();
        String emailTO = (String) sendToCB.getSelectionModel().getSelectedItem();
        String msj = contextTA.getText();
        mail.enviar_correo(emailTO,as,msj);
    };

    private void generatePaneLateralIzq() throws SQLException {
        ListView<String> list = new ListView<>();
        // go to controller
        Articulos[] arts = null;
        if(ctrler.getArticulos() == null) {
            errorPWD("No se han encontrado articulos");
        } else {
            arts = ctrler.getArticulos();
            listadoPedidos = new String[arts.length];
            for (int i = 0; i < listadoPedidos.length; i++) {
                listadoPedidos[i] ="Id: "+ arts[i].getIdArticulo() +" - "+ arts[i].getMarca() +" - "+ arts[i].getModelo()+" Precio: " +arts[i].getCoste() + " - Cantidad: "+arts[i].getCantidad();


            }
        }
        ObservableList<String> items = FXCollections.observableArrayList(listadoPedidos);

        listContainer = new VBox();
        list.setItems(items);
        list.setPrefHeight(0);
        list.prefHeightProperty().bind(listContainer.heightProperty());
        //list.maxHeight(900);
        //list.prefWidth(380);
        //list.prefHeight(900);
        VBox.setVgrow(listContainer,Priority.ALWAYS);
        listContainer.setPrefWidth(400);


        listContainer.setStyle("-fx-background-color: #EAD7D7");
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
                metodoLanzarPanelBase(userTxtf.getText(), e);
            } catch (Exception ex) {
                System.err.println("siguiente panel por teclado ha petado");
                errorPWD("Error al iniciar sesión");
            }
        });
        form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        userLbl_name = new Label("Usuario");
        pwdLbl = new Label("Password");
        userTxtf = new TextField();
        userTxtf.setPromptText("Nombre o correo electronico");
        pwdTxtf = new PasswordField();
        pwdTxtf.setPromptText("Password");

        form.add(userLbl_name, 0, 0);
        form.add(userTxtf, 1, 0);
        form.add(pwdLbl, 0, 1);
        form.add(pwdTxtf, 1, 1);

        // botones
        buttonContainer = new HBox();
        submit = new Button("Aceptar");
        submit.setOnAction((e) -> {
            try {
                if(userTxtf.getText().equals("") && pwdTxtf.getText().equals("")) {
                    popUpAlerta("Introduzca usuario y password");
                } else {
                    if(userTxtf.getText().equals("")) {
                        popUpAlerta("Introduzca usuario");
                    }
                    if(pwdTxtf.getText().equals("")) {
                        popUpAlerta("Introduzca password");
                    } else {
                        metodoLanzarPanelBase(userTxtf.getText());
                    }
                }


            } catch (Exception ex) {
                System.err.println("siguiente panel por boton ha fallado");
                ex.printStackTrace();
            }
            ctrler.mensaje();
        });
        cancel = new Button("Cancelar");
        cancel.setOnAction((e) -> {
            System.exit(0);
        });
        buttonContainer.getChildren().addAll(submit, cancel);
        buttonContainer.setSpacing(25);
        buttonContainer.setPadding(new Insets(20, 0, 0, 0));
        // botones

        login.setBottom(buttonContainer);
        login.setLeft(form);
    }

    private boolean generarPanelBase(String user) throws Exception {

        bpBaseTpv = new BorderPane();
        gridButtonsContainer = new StackPane();
        baseTPV = new Stage();
        baseTPV.setTitle("TPV");
        stackBase = new StackPane();
        stackBase.setStyle("-fx-background-color: #c5c2ff");
        stackBase.setMaxHeight(screenHeight);
        stackBase.setMaxWidth(screenWidth);
        // se busca el nombre del usuario en la base de datos, en la tabla el tipo de permiso
        // comprobacion de permiso
        int permiso = ctrler.checkPermision(user);
        //int permiso = 3;
        stackBase.getChildren().add(bpBaseTpv);
        bpBaseTpv.setStyle("-fx-background-color: #c5c2ff");
        gridButtonsContainer.setAlignment(Pos.CENTER);
        gridButtonsContainer.setPadding(new Insets(0, 40, 0, 40));
        //gridButtonsContainer.setMaxHeight(screenHeight);
        //gridButtonsContainer.setMaxWidth(screenWidth);
        gridButtonsContainer.setStyle("-fx-background-color: #c5c2ff");
        bpBaseTpv.setCenter(gridButtonsContainer);
        bpBaseTpv.setBottom(new Label("AVISO LEGAL, ESTE SOFTWARE ESTA BAJO LA LICENCIA GNU"));
        switch (permiso) {
            case 0:
                errorPWD("El usuario no tiene permisos");
                System.out.println(user+ " no tiene permisos");
                break;
            case 1:// permiso == 1 para el dependiente
                bpBaseTpv.setTop(barraHerramientasPanelBase());
                try {
                    gridButtonsContainer.getChildren().add(generarBotonesBase(permiso));
                } catch (Exception ex) {
                    System.out.println("Error al anadir el grid pane al stack pane gridbuttonscontainer");
                }
                buttonsUserBase.setHgap(200);
                buttonsUserBase.setVgap(55);
                return true;
            case 2:// permiso == 2 para el supervisor

                System.out.println("permisos de nivel 2");
                bpBaseTpv.setTop(barraHerramientasPanelBase());
                try {
                    gridButtonsContainer.getChildren().add(generarBotonesBase(permiso));
                } catch (Exception ex) {
                    System.out.println("Error al anadir el grid pane al stack pane gridbuttonscontainer");
                }
                buttonsUserBase.setHgap(100);
                buttonsUserBase.setVgap(55);

                return true;

            case 3:// permiso == 3 para el administrador del sistema
                System.out.println("permisos de nivel 3");
                bpBaseTpv.setTop(barraHerramientasPanelBase());
                try {

                    gridButtonsContainer.getChildren().add(generarBotonesBase(permiso));
                } catch (Exception ex) {
                    System.out.println("Error al anadir el grid pane al stack pane gridbuttonscontainer");
                }
                return true;
            case -1:// permiso == -1 si no tiene permisos en la BBDD
                errorPWD("ERROR 404 No se encuentra en la BBDD");
                System.err.println("ERROR 404 No se encuentra en la BBDD");
                return false;
            default:

                return false;
        }
        return false;
    }

    private void panelBase(String user) {
        // cuando boton pasa aceptar hace esto
        boolean genPan = false;
        try {

            genPan = generarPanelBase(user);
            if(genPan == true) {
                System.out.println("Fuera del bucle");
                rootTPV = new Scene(stackBase, 1200, 800);
                baseTPV.setScene(rootTPV);
                baseTPV.setMaximized(true);
                baseTPV.setResizable(false);
                baseTPV.show();
            } else {
                loginTPV.show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MenuBar barraHerramientasPanelBase() {
        try {
            topTools = new MenuBar(); //bpBaseTpv
            topTools.prefWidthProperty().bind(baseTPV.widthProperty());

            fileMenu = new Menu("SESION");
            fileMenu.getItems().addAll(itemsFile);

            helpMenu = new Menu("AYUDA");
            helpMenu.getItems().addAll(itemsHelp);

            topTools.getMenus().addAll(fileMenu, helpMenu);
        } catch (Exception ex) {
            System.out.println("ALGO HA FALLADO");
        }
        return topTools;
    }

    private GridPane generarBotonesBase(int userlvl) {
        this.userlvl = userlvl;
        try {
            buttonsUserBase = new GridPane();
            initArrayButtons(userlvl);

            //buttonsUserBase.setGridLinesVisible(true);
            buttonsUserBase.setPrefSize(screenWidth, screenHeight);
            buttonsUserBase.setAlignment(Pos.CENTER);

            for (int i = 0; i < buttonsBase.length; i++) {
                buttonsUserBase.add(buttonsBase[i], i, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (userlvl) {
            case 1:
                buttonsUserBase.setHgap(200);
                buttonsUserBase.setVgap(55);
                break;
            case 2:
                buttonsUserBase.setHgap(110);
                buttonsUserBase.setVgap(55);
                break;
            case 3:
                buttonsUserBase.setHgap(80);
                buttonsUserBase.setVgap(55);
                break;

        }
        buttonsUserBase.setStyle("-fx-background-color: #9BBEBF");
        return buttonsUserBase;
    }

    // control de paneles pulsando enter
    private void metodoLanzarPanelBase(String user, KeyEvent e) throws SQLException {
        if (e.getCode().equals(KeyCode.ENTER)) {
            System.out.println("Has pulsado Enter");
            autentication = ctrler.checkUser(userTxtf.getText(), pwdTxtf.getText()); // comprobar el usuario y la pwd introducidos
            enter = ctrler.autenticar(autentication); // autentication
            if (enter) { //enter
                loginTPV.close();
                this.panelBase(user);
            } else {
                // no ha entrado, mensaje de fallo de autenticacion
                int error = ctrler.checkPermision(user);
                System.out.println(error + "USUARIO NO EXISTE");
                if (error == -1) { errorPWD("ERROR 404 No se encuentra en la BBDD"); }
                if (error == 0)  { errorPWD(); }
                System.out.println("FALLO");
            }
        }
        // comprobacion de usuario
        // recoge el tipo de usuario admin 1 supervisor 2 y dependiente 3
    }
    // sin pulsar enter
    private void metodoLanzarPanelBase(String user) throws SQLException {
        autentication = ctrler.checkUser(userTxtf.getText(), pwdTxtf.getText()); // comprobar el usuario y la pwd introducidos
        enter = ctrler.autenticar(autentication); //autentication
        if (enter) {
            loginTPV.close();
            this.panelBase(user);
        } else {
            int error = ctrler.checkPermision(user);
            System.out.println(error + " ------          USUARIO NO EXISTE");
            if (error == -1) { errorPWD("ERROR 404 No se encuentra en la BBDD"); }
            if (error == 0)  { errorPWD(); }
            System.out.println("FALLO");
        }
        // comprobacion de usuario
        // recoge el tipo de usuario admin 1 supervisor 2 y dependiente 3
    }

    // POP UPS
    private void errorPWD() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Clave incorrecta");
        alert.setHeaderText(null);
        alert.setContentText("La clave que has introducido no es valida");
        alert.showAndWait();
    }
    public static void errorPWDs() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Clave incorrecta");
        alert.setHeaderText(null);
        alert.setContentText("La clave que has introducido no es valida");
        alert.showAndWait();
    }
    private void errorPWD(String txt) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("error");
        alert.setHeaderText(null);
        alert.setContentText(txt);
        alert.showAndWait();
    }
    private Alert alert;

    private void popUpAlerta() {
        // WARNING MESSAGE
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Look, a Warning Dialog");
        alert.setContentText("Careful with the next step!");
        alert.showAndWait();

    }


    private boolean popUpConfirm() {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            return true;
        } else {
            // ... user chose CANCEL or closed the dialog
            return false;
        }
    }



    private void generateEmailPanel() {
        volver4 = new Button("Atras");
        sendEmail = new Button("Enviar");
        sendEmail.setOnAction(mntEnviarCorreo);

        volver4.setOnAction((e) -> {
            buttonsUserBase.getChildren().removeAll(volver4, titleTF, sendToCB, emailMessage, sendEmail
                    , emailTitle, emailTo, contextTA);
            initArrayButtons(this.userlvl);
            gridButtonsContainer.getChildren().add(generarBotonesBase(this.userlvl));
        });
        emailTo = new Label("Para :");
        emailTitle = new Label("Asunto :");
        sendToCB = new ComboBox<String>();
        sendToCB.getItems().addAll("alopez@inlogiq.com", "jjerez@inlogiq.com", "tiendamntefep@gmail.com");
        titleTF = new TextField();
        emailMessage = new Label("Mensaje");
        BorderPane bdPaneMen = new BorderPane();
        bdPaneMen.setTop(emailMessage);
        contextTA = new TextArea();
        buttonsUserBase.add(emailTo, 0, 0);
        buttonsUserBase.add(emailTitle, 0, 1);
        buttonsUserBase.add(bdPaneMen, 0, 2);
        buttonsUserBase.add(sendEmail, 0, 3);
        buttonsUserBase.add(sendToCB, 1, 0);
        buttonsUserBase.add(titleTF, 1, 1);
        buttonsUserBase.add(contextTA, 1, 2);
        buttonsUserBase.add(volver4, 4, 4);
    }

    private void popUpAlerta(String info) {
        // WARNING MESSAGE
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cuidado");
        //alert.setHeaderText("Look, a Warning Dialog");
        alert.setContentText(info);
        alert.setHeight(200);
        alert.showAndWait();
    }

    private void popUpConfirm(String info) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Dialog");
        alert.setHeaderText("Look, a Confirm Dialog");
        alert.setContentText(info);
        alert.setHeight(300);
        alert.showAndWait();

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ctrler.model.conectBBDD();
            System.out.println("working!!");
        } catch (Exception e) {
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
            root = new Scene(base, 400, 200);
            loginTPV.setTitle("TPV");
            loginTPV.setScene(root);
            loginTPV.show();
        } catch (Exception e) {
            System.out.println("Ha fallado la inicialización del login, first start metod failed");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }


    private void administraUsersView() {
        //    private Label user_dni, user_ap1, user_ap2, user_address, user_nss, user_afSind, user_tlf_emp, user_mail, user_pob, user_birth, user_cp;
        //     private TextField dni_txt,name_txt,ap1_txt,ap2_txt,adss_txt,nss_txt,nafSind_txt,ntlf_txt,email_txt,pob_txt,dateB_txt,cp_txt;
        userLabel = new Label("Nombre");//
        user_dni = new Label("Dni");
        user_ap1 = new Label("Primer Apellido");
        user_ap2 = new Label("Segundo Apellido");
        user_address = new Label("Direccion");
        user_nss = new Label("NUSS");
        user_afSind = new Label("Numero Afiliado Sindicato");
        user_tlf_emp = new Label("Numero telefono Empresa");
        user_mail = new Label("Correo Elecronico");
        user_pob = new Label("Poblacion");
        user_birth = new Label("Fecha Nacimiento");
        user_cp = new Label("Codigo Postal");
        permisoLabel = new Label("Permisos");

        dni_txt = new TextField();
        dni_txt.setPromptText("Dni");
        name_txt = new TextField();
        name_txt.setPromptText("Nombre");
        ap1_txt = new TextField();
        ap1_txt.setPromptText("Primer Apellido");
        ap2_txt = new TextField();
        ap2_txt.setPromptText("Segundo Apellido");
        adss_txt = new TextField();
        adss_txt.setPromptText("Direccion");
        nss_txt = new TextField();
        nss_txt.setPromptText("Numero SS");
        nafSind_txt = new TextField();
        nafSind_txt.setPromptText("Num. Afiliado Sindicato");
        ntlf_txt = new TextField();
        ntlf_txt.setPromptText("Num. Tlf. Empresa");
        email_txt = new TextField();
        email_txt.setPromptText("Email");
        pob_txt = new TextField();
        pob_txt.setPromptText("Poblacion");
        dateB_txt = new TextField();
        dateB_txt.setPromptText("Fecha Nacimiento");
        cp_txt = new TextField();
        cp_txt.setPromptText("Codigo Postal");

        Node[] objLbl = {userLabel,user_dni,user_ap1,user_ap2,user_address,user_nss,
                user_afSind,user_tlf_emp,user_mail,user_pob,user_birth,user_cp,permisoLabel};
        Node[] objTxt = {name_txt,dni_txt, ap1_txt,ap2_txt,adss_txt,nss_txt
                ,nafSind_txt,ntlf_txt,email_txt,pob_txt,dateB_txt,cp_txt};

        ObservableList<String> permis = FXCollections.observableArrayList();
        permis.addAll("1", "2", "3");
        ComboBox<String> cbx = new ComboBox<>(permis);
        cbx.getSelectionModel().selectFirst(); // valor predeterminado
        crearUser = new Button("Crear");
        volver2 = new Button("Atras");
        // (0,x) (1,y)
        for (int x = 0; x < objLbl.length; x++){
            buttonsUserBase.add(objLbl[x],0,x);
        }
        for(int y = 0; y < objTxt.length; y++) {
            buttonsUserBase.add(objTxt[y],1,y);
        }
        buttonsUserBase.add(cbx, 1, objTxt.length);
        buttonsUserBase.add(crearUser, 0, objTxt.length+2);
        buttonsUserBase.add(volver2, 1, objTxt.length+2);
        buttonsUserBase.setVgap(10);
        volver2.setOnAction((e) -> {
            ArrayList<Node> obj_list = new ArrayList();
            ArrayList<Node> obj_list2 = new ArrayList();
            for (int i = 0; i < objLbl.length; i++) {
                obj_list.add(objLbl[i]);
            }
            for (int i = 0; i < objTxt.length; i++) {
                obj_list2.add(objTxt[i]);
            }

            buttonsUserBase.getChildren().removeAll(obj_list);
            buttonsUserBase.getChildren().removeAll(obj_list2);
            buttonsUserBase.getChildren().removeAll(cbx,crearUser,volver2);
            initArrayButtons(this.userlvl);
            gridButtonsContainer.getChildren().add(generarBotonesBase(this.userlvl));
        });
        crearUser.setOnAction((e) -> {
            try {
                Empleados emp1 = new Empleados(dni_txt.getText(),name_txt.getText(),ap1_txt.getText(),ap2_txt.getText());
                int p = cbx.getSelectionModel().getSelectedIndex();
                System.out.println("permisos seleccionado "+p);
                ctrler.createUser(emp1,p);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Usuario creado");
            alert.setHeaderText(null);
            alert.setContentText("El usuario se ha creado correctamente");
            alert.showAndWait();
        });

    }

}
