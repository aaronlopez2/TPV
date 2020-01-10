package sample;


import org.hibernate.*;
import org.hibernate.graph.RootGraph;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.query.NativeQuery;
import org.hibernate.stat.SessionStatistics;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Modelo {
    Connection conexion;
    //SessionFactory sF = new SessionFactoryImpl();
    public boolean Autenticar(String user, String pwd){
        if(user.equals("Aaron") && pwd.equals("a")) {
            return true;
        } else {
            return false;
        }
    }

    public Connection conectBBDD() throws SQLException {
        conexion = DriverManager.getConnection("jdbc:mysql://localhost/tienda","root", "turing");
        return conexion;
    }

}
