package sample;


import dbo.Users;

import java.sql.SQLException;

public class Controller {

    Modelo model = new Modelo();
    Users user;


    public void mensaje(){
        System.out.println("PRUEBA");
    }


    public boolean autenticar(Boolean aut){

        if(aut) {
            return true;
        } else {
            return false;
        }
    }

    public void createUser(String name, String pwd, int permisos) throws SQLException {
       //int pwdh = pwd.hashCode();
       //pwd = String.valueOf(pwdh);
        user = new Users(name,pwd,permisos);
        model.crearUsuario(user);

    }

    public boolean checkUser(String name, String pwd) throws SQLException {
        user = new Users(name, pwd);
        boolean exist = model.autenticar(user);
        if(exist) {
            return true;
        } else {
            return false;
        }
    }

    public int checkPermision(String name) throws SQLException {
        String log = model.permision(name);
        if(log.equals("404")) {
            return 0;
        } else if(log.equals("Admin")){
            return 3;
        } else if(log.equals("Boss")){
            return 2;
        } else if(log.equals("User")){
            return 1;
        }
        return 0;
    }

}
