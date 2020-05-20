package sample;


import dbo.Users;
//import org.hibernate.*;
//import org.hibernate.graph.RootGraph;
//import org.hibernate.internal.SessionFactoryImpl;
//import org.hibernate.jdbc.ReturningWork;
//import org.hibernate.jdbc.Work;
//import org.hibernate.procedure.ProcedureCall;
//import org.hibernate.query.NativeQuery;
//import org.hibernate.stat.SessionStatistics;

import java.io.Serializable;
import java.sql.*;

public class Modelo {
    Connection conexion;
    //String url = "jdbc:mysql://192.168.1.1:3306/tienda2";
    public Connection conectBBDD() throws SQLException {
        conexion = DriverManager.getConnection("jdbc:mysql://localhost/tienda2","root", "turing");
        return conexion;
    }
    protected void crearUsuario(Users user) throws SQLException {
        //SELECT * FROM tienda2.empleado order by Emp_Id DESC
        // Añadir contraseña por defecto

        // recibe empleado
        // instanciamos user
        // recoger id del ultimo empleado SELECT * FROM tienda2.empleado;
        // crear empleado INSERT INTO `tienda2`.`empleado` (`Emp_Id`, `dni`, `Nombre`, `Apellido1`, `Apellido2`) VALUES ('2', '12345678P', 'Vicent', 'Lopez', 'Bertomeu');
        String permisos = user.getUser_lvl();

        if( permisos.equalsIgnoreCase("Admin")){

        }


        //"INSERT INTO `tienda2`.`` (`IdUser`, `Pwd`, `permisos`) VALUES ('"+user.getUserName()+"', '"+user.getPwd()+"', '"+perm+"');"
        Statement s = conexion.createStatement();
        //int rs = s.executeUpdate ("INSERT INTO `tienda2`.`usuario` (`Id_User`, `User_pwd`, `User_lvl`, `User_name`, `empleado_Id`) VALUES ("+user.getUser_name()+user.get");");
        //int rs2 = s.executeUpdate( "");
    }
    protected boolean autenticar(Users user) throws SQLException {

        Statement s = conexion.createStatement();
        ResultSet rs = s.executeQuery ("select * from usuario");
        while (rs.next())
        {
            System.out.println (rs.getString (1) + " " + rs.getString (2)+ " " + rs.getString(3));
            //if(rs.getString(1).equals(user.getUserName()) && rs.getString(2).equals(user.getPwd())) {
            //    return true;
            //}

        }
        //return false;
        return true;
    }
    protected String permision(String name) {
        try{
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery ("SELECT User_lvl FROM tienda2.usuario where Id_User = '"+name+"'");
            if(rs.next()){
                return rs.getString(1);
            }

        }catch (SQLException sqlex) {
            sqlex.printStackTrace();
            System.out.println("FAIL ON PERMISION METHOD");
        }
        return "User";
    }

    protected String generate_User_Id(){
        return "";
    }
}
