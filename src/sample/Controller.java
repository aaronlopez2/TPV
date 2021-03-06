package sample;


import dbo.Almacen;
import dbo.Articulos;
import dbo.Empleados;
import dbo.Users;

import javax.xml.namespace.QName;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Controller {

    Modelo model = new Modelo();
    Users user;
    Empleados emp;
    Excel gen = new Excel();

    public void mensaje(){
        System.out.println("mensaje desde controlador ctrler.mensaje");
    }


    public boolean autenticar(Boolean aut){

        if(aut) {
            return true;
        } else {
            return false;
        }
    }

    public void createUser(Empleados emp, int permisos) throws SQLException {
        String user_level = "";
        switch (permisos) {
            case 0:
                user_level = "admin";
                break;
            case 1:
                user_level = "Boss";
                break;
            case 2:
                user_level = "User";
                break;
        }
        String user_id = generateUser_ID(emp.getNombre(),emp.getApellido1(),emp.getApellido2());
        System.out.println("Permisos usuarios: "+user_level);
        model.crearUsuario(emp,user_id,user_level);

    }
    public String generateUser_ID(String name, String ap1, String ap2) {
        char[] letras = new char[6];
        char[] names = name.toCharArray();
        char[] ap1s = ap1.toCharArray();
        char[] ap2s = ap2.toCharArray();
        String user_id = "";
        for (int i = 0; i < letras.length; i++) {
            if(i < 2) letras[i] = names[i];
            if(i >= 2 && i < 4) letras[i] = ap1s[i-2];
            if(i >= 4) letras[i] = ap2s[i-4];
            user_id = user_id + letras[i];
        }

        System.out.println(user_id);
        return user_id;
    }

    public boolean checkUser(String name, String pwd) throws SQLException {
        user = new Users(name, pwd);
        boolean exist = model.autenticar(user);
        System.out.println(exist+ " valor autenticacion --- controller.CheckUser");
        if(exist) {
            return true;
        } else {
            return false;
        }
    }

    public int checkPermision(String name) throws SQLException {
        String log = model.permision(name);
        if(log.equals("404")) {
            return -1;
        } else if(log.equalsIgnoreCase("Admin")){
            return 3;
        } else if(log.equalsIgnoreCase("Boss")){
            return 2;
        } else if(log.equalsIgnoreCase("User")){
            return 1;
        }
        return 0;
    }
    public Articulos[] getArticulos() throws SQLException{
        // go to model devuelve un array de objetos articulos
        if(model.getArticulos() == null) {
            return null;
        } else {
            Articulos[] arts = model.getArticulos();
            return arts;
        }
    }
    public Almacen[] getAlmacen() throws SQLException{
        // go to model devuelve un array de objetos articulos
        if(model.getAlmacen() == null) {
            return null;
        } else {
            Almacen[] almc = model.getAlmacen();
            return almc;
        }
    }


    public void generarExcel(Object[] excel) throws IOException {

        gen.writeExcel(excel);
    }
    public void openFolderDoc() throws IOException {
        File excelFolder = null;
        String name = "";
        try {

            name = new com.sun.security.auth.module.NTSystem().getName();
            System.out.println("USUARIO: " +name);
            excelFolder = new File("C:\\Users\\"+name+"\\Documents\\excelDoc");
            boolean f = excelFolder.mkdir();
            // print
            System.out.print("Directory created? "+f);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error al crear carpeta");
        }
        System.out.println(" USUARIO: " +name);
        Desktop.getDesktop().open(new File("C:\\Users\\"+name+"\\Documents\\excelDoc"));

    }

    public float getPrecio(String idProd) throws SQLException {
        float precio = model.getPrecioArticulo(idProd);
        // llamar a modelo
        return precio;
    }
    public Articulos getProducto(String idProd) throws SQLException {
        Articulos art = new Articulos(idProd);
        art = model.getArticuloVenta(art);
        return art;
    }

    public boolean getCantidadProd(String idProd, int cant) throws SQLException {
        boolean stock = model.getCantidad(idProd,cant);
        return stock;
    }

    public void pedidoAlmacen(String idProd) {
        try {
            model.pedidoAlmacen(idProd);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error en controler.pedidoAlmacen");
        }
    }
}
