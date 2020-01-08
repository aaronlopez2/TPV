package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Modelo {
    Connection conexion;
    public boolean Autenticar(String user, String pwd){
        if(user.equals("Aaron") && pwd.equals("a")) {
            return true;
        } else {
            return false;
        }
    }

    public Connection conectBBDD() throws SQLException {
        conexion = DriverManager.getConnection("jdbc:mysql://localhost/3306","root", "turing");
        return conexion;
    }

}
