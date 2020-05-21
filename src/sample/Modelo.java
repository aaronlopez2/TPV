package sample;


import dbo.Empleados;
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
    protected void crearUsuario(Empleados emp, String user_id, String user_lvl) throws SQLException {
        Statement s = conexion.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM `tienda2`.`empleado` order by Emp_Id DESC");
        int id_empleado = 0;
        while (rs.next())
        {
            //System.out.println (rs.getString (1) + " " + rs.getString (2)+ " " + rs.getString(3));
            System.out.println("RESULT SET"+rs.getInt(1));
            if(rs.getInt(1) > id_empleado) {
                id_empleado = rs.getInt(1);
            }
            System.out.println("ID_GREATER: "+id_empleado);
            //if(rs.getString(1).equals(user.getUserName()) && rs.getString(2).equals(user.getPwd())) {
            //    return true;
            //
        }
        s.close();
        System.out.println("ID_ NUEVO: "+(id_empleado+1));
        id_empleado++;
        String id_emp = Integer.toString(id_empleado);
        System.out.println("ID_ NUEVO STRING: "+id_emp);
        Users new_user = new Users(user_id,"a",user_lvl,emp.getNombre(),id_emp);
        Statement stm_new_emp = conexion.createStatement();
        Statement stm_new_user = conexion.createStatement();
        try {
            //INSERT INTO `tienda2`.`empleado` (`Emp_Id`, `dni`, `Nombre`, `Apellido1`, `Apellido2`) VALUES ('3', '123432A', 'ASD', 'ADS', 'ASD');
            int rsIn = stm_new_emp.executeUpdate("INSERT INTO `tienda2`.`empleado` (`Emp_Id`, `dni`, `Nombre`, `Apellido1`, `Apellido2`) VALUES ('"+id_emp+"', '"+emp.getDni()+"','"+emp.getNombre()+"','"+emp.getApellido1()+"','"+emp.getApellido2()+"');");
            System.out.println(rsIn);
        } catch (SQLException sqex) {
            sqex.printStackTrace();
            System.out.println("FALLO AL CREAR EMPLEADO");
        } finally {
            stm_new_emp.close();
        }

        try {
            //INSERT INTO `tienda2`.`usuario` (`Id_User`, `User_pwd`, `User_lvl`, `User_name`, `empleado_Id`) VALUES ('asd', 'a', 'adaa', 'aaaass', '2');

            int rsIn = stm_new_user.executeUpdate("INSERT INTO `tienda2`.`usuario` (`Id_User`, `User_pwd`, `User_lvl`, `User_name`, `empleado_Id`) VALUES ('"+new_user.getId_User()+"', '"+new_user.getUser_pwd()+"', '"+new_user.getUser_lvl()+"', '"+new_user.getUser_name()+"', '"+new_user.getEmp_id()+"');");
            System.out.println(rsIn);
        } catch (SQLException sqex) {
            sqex.printStackTrace();
            System.out.println("FALLO AL CREAR USUARIO");
        } finally {
            stm_new_user.close();
        }
        //"INSERT INTO `tienda2`.`` (`IdUser`, `Pwd`, `permisos`) VALUES ('"+user.getUserName()+"', '"+user.getPwd()+"', '"+perm+"');"
        //int rs = s.executeUpdate ("INSERT INTO `tienda2`.`usuario` (`Id_User`, `User_pwd`, `User_lvl`, `User_name`, `empleado_Id`) VALUES ("+user.getUser_name()+user.get");");
        //int rs2 = s.executeUpdate( "");

        //SELECT * FROM tienda2.empleado order by Emp_Id DESC
        // Añadir contraseña por defecto

        // recibe empleado
        // instanciamos user
        // recoger id del ultimo empleado SELECT * FROM tienda2.empleado;
        // crear empleado INSERT INTO `tienda2`.`empleado` (`Emp_Id`, `dni`, `Nombre`, `Apellido1`, `Apellido2`) VALUES ('2', '12345678P', 'Vicent', 'Lopez', 'Bertomeu');
        //String permisos = user.getUser_lvl();





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
