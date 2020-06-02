package sample;


import dbo.Almacen;
import dbo.Articulos;
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
import java.util.ArrayList;

public class Modelo {
    Connection conexion;
    //String urlBBDD = "jdbc:mysql://192.168.1.1:3306/tienda2";
    String urlBBDD = "jdbc:mysql://localhost/tienda2";

    public Connection conectBBDD() throws SQLException {
        conexion = DriverManager.getConnection(urlBBDD, "root", "turing");
        return conexion;
    }

    protected void crearUsuario(Empleados emp, String user_id, String user_lvl) throws SQLException {
        Statement s = conexion.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM `tienda2`.`empleado` order by Emp_Id DESC");
        int id_empleado = 0;
        while (rs.next()) {
            System.out.println("RESULT SET" + rs.getInt(1));
            if (rs.getInt(1) > id_empleado) {
                id_empleado = rs.getInt(1);
            }
            System.out.println("ID_GREATER: " + id_empleado);
        }
        s.close();
        System.out.println("ID_ NUEVO: " + (id_empleado + 1));
        id_empleado++;
        String id_emp = Integer.toString(id_empleado);
        System.out.println("ID_ NUEVO STRING: " + id_emp);
        Users new_user = new Users(user_id, "a", user_lvl, emp.getNombre(), id_emp);
        Statement stm_new_emp = conexion.createStatement();
        Statement stm_new_user = conexion.createStatement();
        try {
            int rsIn = stm_new_emp.executeUpdate("INSERT INTO `tienda2`.`empleado` (`Emp_Id`, `dni`, `Nombre`, `Apellido1`, `Apellido2`) VALUES ('" + id_emp + "', '" + emp.getDni() + "','" + emp.getNombre() + "','" + emp.getApellido1() + "','" + emp.getApellido2() + "');");
            System.out.println(rsIn);
        } catch (SQLException sqex) {
            sqex.printStackTrace();
            System.out.println("FALLO AL CREAR EMPLEADO");
        } finally {
            stm_new_emp.close();
        }
        try {
            int rsIn = stm_new_user.executeUpdate("INSERT INTO `tienda2`.`usuario` (`Id_User`, `User_pwd`, `User_lvl`, `User_name`, `empleado_Id`) VALUES ('" + new_user.getId_User() + "', '" + new_user.getUser_pwd() + "', '" + new_user.getUser_lvl() + "', '" + new_user.getUser_name() + "', '" + new_user.getEmp_id() + "');");
            System.out.println(rsIn);
        } catch (SQLException sqex) {
            sqex.printStackTrace();
            System.out.println("FALLO AL CREAR USUARIO");
        } finally {
            stm_new_user.close();
        }
    }

    protected boolean autenticar(Users user) throws SQLException {
        Statement s2 = conexion.createStatement();
        try {
            ResultSet rs2 = s2.executeQuery("SELECT User_pwd from tienda2.usuario where Id_User LIKE '" + user.getId_User() + "'");
            //System.out.println(rs2.getString(1));
            //SELECT Id_User from tienda2.usuario;
            //SELECT User_pwd from tienda2.usuario where Id_User LIKE 'aalolo';
            while (rs2.next()) {
                System.out.println(rs2.getString(1)+ " PASSWORDDDD" );
                System.out.println(user.getUser_pwd()+ " INTRODUCIDA");
                //rs.getString(1).equals(user.getUserName()) && rs.getString(2).equals(user.getPwd())
                if (rs2.getString(1).equalsIgnoreCase(user.getUser_pwd())) {
                    System.out.println("ENTRA EN LA CONDICION");
                    s2.close();
                    return true;
                } else {
                    System.out.println("CLAVE INCORRECTA");
                    Vista.errorPWDs();
                    return false;
                }
            }
        } catch (Exception exslq) {
            exslq.printStackTrace();
            System.out.println("Fallo en Modelo.autenticar");
        } finally {
            s2.close();
        }
        return false;
    }

    protected String permision(String name) throws SQLException {
        Statement s = conexion.createStatement();
        Statement s1 = conexion.createStatement();
        try {
            ResultSet rs1 = s1.executeQuery("SELECT Id_User from tienda2.usuario where Id_User = '"+name+"';");
            if(rs1.next()) {
                System.out.println(rs1.getString(1)+ "SELECT ID_USER");
            } else {
                return "404";
            }

            ResultSet rs = s.executeQuery("SELECT User_lvl FROM tienda2.usuario where Id_User = '" + name + "'");
            if (rs.next()) {
                System.out.println(rs.getString(1));
                return rs.getString(1);
            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
            System.out.println("FAIL ON PERMISION METHOD");
        } finally {
            s1.close();
            s.close();
        }
        System.out.println("404 Modelo.permision");
        return "500";
    }

    protected Articulos[] getArticulos() throws SQLException {
        Articulos  art;
        Articulos[] arts;
        ArrayList<Articulos> names = new ArrayList<Articulos>();
        Statement s = conexion.createStatement();
        try{
            ResultSet rs = s.executeQuery("SELECT * FROM `tienda2`.`articulos`;");

            while(rs.next())
            {
                //System.out.println("Adding : "+ rs.getString(1)+"-"+rs.getString(2)+"-"+rs.getString(4)+"-"+rs.getString(5)+"-precio : "+rs.getFloat(3)+"- cantidad: "+rs.getInt(6));
                art = new Articulos(rs.getString(1),rs.getString(2),rs.getString(4),rs.getString(5),rs.getFloat(3),rs.getInt(6)); //String idArticulo, String marca, String talla, String modelo, float coste, int cantidad
                names.add(art);
                //System.out.println("-------------------------- FIN ITERACION --------------------------");
            }
            arts = new Articulos[names.size()];
            for (int i = 0; i < arts.length; i++) {
                arts[i] = names.get(i);
                System.out.println("Adding2 : "+names.get(i));
            }
            return arts;
        } catch(Exception excpArt) {
            excpArt.printStackTrace();
            System.out.println("Error en modelo.getArticulos");
        } finally {
            s.close();

        }
        return null;
    }
    protected Almacen[] getAlmacen() throws SQLException {
        Almacen  almcn;
        Almacen[] almc;
        ArrayList<Almacen> names = new ArrayList<Almacen>();
        Statement s = conexion.createStatement();
        try{
            ResultSet rs = s.executeQuery("SELECT * FROM `tienda2`.`Almacen`;");

            while(rs.next())
            {
                System.out.println("Adding : "+ rs.getString(1)+"-"+rs.getString(2)+"-"+rs.getString(4)+"-"+rs.getString(5)+"-precio : "+rs.getFloat(3)+"- cantidad: "+rs.getInt(6));
                almcn = new Almacen(rs.getString(1),rs.getString(2),rs.getString(4),rs.getString(5),rs.getFloat(3),rs.getInt(6)); //String idArticulo, String marca, String talla, String modelo, float coste, int cantidad
                names.add(almcn);
                System.out.println("-------------------------- FIN ITERACION --------------------------");
            }
            almc = new Almacen[names.size()];
            for (int i = 0; i < almc.length; i++) {
                almc[i] = names.get(i);
                System.out.println("Adding2 : "+names.get(i));
            }
            return almc;
        } catch(Exception excpArt) {
            excpArt.printStackTrace();
            System.out.println("Error en modelo.getAlmacen");
        } finally {
            s.close();

        }
        return null;
    }

    protected Articulos getArticuloVenta(Articulos art) throws SQLException {
        Statement s = conexion.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM `tienda2`.`articulos` where `IDarticulo` = "+art.getIdArticulo()+";");
        Articulos art2 = new Articulos(art.getIdArticulo());
        while(rs.next())
        {
            //public Articulos(             String idArticulo, String marca, String talla, String modelo, float coste, int cantidad)
            art2 = new Articulos(art.getIdArticulo(),rs.getString(2),rs.getString(4),rs.getString(5),rs.getFloat(3),rs.getInt(6));
            System.out.println(art2.getIdArticulo()+" cantidad :" +art2.getCantidad() + " precio: "+ art2.getCoste());
        }
        s.close();

        return  art2;

    }
    protected float getPrecioArticulo(String idArticulo) throws  SQLException {
        Statement s = conexion.createStatement();
        System.out.println(idArticulo);
        float coste = 0;
        try {
            ResultSet rs = s.executeQuery("SELECT coste FROM `tienda2`.`articulos` WHERE IDarticulo = '"+idArticulo+"';");
            while (rs.next())
            {
                coste = rs.getFloat(1);
                System.out.println("EL COSTE DEL PRODUCTO ES "+coste);
            }
        }catch (SQLException sqex){
            sqex.printStackTrace();
            System.out.println("ERROR AL OBTENER EL COSTE DEL ARTICULO modelo.getPrecioArticulo");
        } finally {
            s.close();
        }

        return coste;
    }
    protected boolean getCantidad(String idArticulo, int cantidadPedida) throws  SQLException {
        Statement s = conexion.createStatement();
        Statement s2 = conexion.createStatement();
        System.out.println(idArticulo);
        int cantidad = 0;
        try {
            ResultSet rs = s.executeQuery("SELECT Cantidad FROM `tienda2`.`articulos` WHERE IDarticulo = '"+idArticulo+"';");
            while (rs.next())
            {
                cantidad = rs.getInt(1);
                System.out.println("HAY "+cantidad+ " PRODUCTOS");
            }

            if(cantidadPedida > cantidad) {
                return false;
            } else {
                int cantidadRestar = cantidad - cantidadPedida;
                //UPDATE `tienda2`.`articulos` SET `Cantidad` = '4' WHERE (`IDarticulo` = '1243');
                int rs2 = s2.executeUpdate("UPDATE `tienda2`.`articulos` SET `Cantidad` = '"+cantidadRestar+"' WHERE (`IDarticulo` = '"+idArticulo+"');");
                System.out.println("Filas afectadas: "+rs2);
                return true;
            }


        }catch (SQLException sqex){
            sqex.printStackTrace();
            System.out.println("ERROR AL REALIZAR UPDATE DE LA CANTIDAD DEL ARTICULO modelo.getCantidad");
        } finally {
            s2.close();
            s.close();
        }

        return false;
    }
    protected int getCantidadArt(String idArticulo) throws  SQLException {
        Statement s = conexion.createStatement();
        Statement s2 = conexion.createStatement();
        System.out.println(idArticulo);
        int cantidad = 0;
        try {
            ResultSet rs = s.executeQuery("SELECT Cantidad FROM `tienda2`.`articulos` WHERE IDarticulo = '"+idArticulo+"';");
            while (rs.next())
            {
                cantidad = rs.getInt(1);
                System.out.println("HAY "+cantidad+ " PRODUCTOS");
            }
            return cantidad;

        }catch (SQLException sqex){
            sqex.printStackTrace();
            System.out.println("ERROR AL REALIZAR UPDATE DE LA CANTIDAD DEL ARTICULO modelo.getCantidad");
        } finally {
            s2.close();
            s.close();
        }
        return 0;
    }
    protected void pedidoAlmacen(String idArticulo) throws SQLException {
        Statement s = conexion.createStatement();
        Statement s2 = conexion.createStatement();
        Statement s3 = conexion.createStatement();
        Statement s4 = conexion.createStatement();
        try{
            ResultSet rs = s.executeQuery("SELECT IDarticulo from `tienda2`.`articulos` WHERE IDarticulo = '"+idArticulo+"'");
            if(rs.next()) {
                System.out.println("ARTICULO EXISTE EN LA TABLA");
                int cantidad = getCantidadArt(idArticulo) + 1;
                int rs2 = s2.executeUpdate("UPDATE `tienda2`.`articulos` SET `Cantidad` = '"+cantidad+"' WHERE (`IDarticulo` = '"+idArticulo+"')");
                // UPDATE `tienda2`.`articulos` SET `Cantidad` = '4' WHERE (`IDarticulo` = '1243');
                System.out.println("Actualizando articulos "+rs2);
                ResultSet rs3 = s3.executeQuery("SELECT * from `tienda2`.`almacen` WHERE IDarticulo_almacen = '"+idArticulo+"'");
                int cantidadAlmacen = 0;
                if(rs3.next()) {
                    //int rs2 = s2.executeUpdate("INSERT INTO `tienda2`.`articulos` (`IDarticulo`, `Marca`, `Coste`, `Talla`, `Modelo`, `Cantidad`) VALUES ('"+rs3.getString(1)+"', '"+rs3.getString(2)+"', '"+rs3.getFloat(3)+"', '"+rs3.getString(4)+"', '"+rs3.getString(5)+"', '"+1+"')");
                    cantidadAlmacen = rs3.getInt(6) - 1;
                    if(cantidadAlmacen < 0) {

                    } else {
                        int rs4 = s4.executeUpdate("UPDATE `tienda2`.`almacen` SET `Cantidad` = '"+cantidadAlmacen+"' WHERE (`IDarticulo_almacen` = '"+idArticulo+"');");
                    }

                }

            } else {
                ResultSet rs3 = s3.executeQuery("SELECT * from `tienda2`.`almacen` WHERE IDarticulo_almacen = '"+idArticulo+"'");
                int cantidadAlmacen = 0;
                if(rs3.next()) {
                    int rs2 = s2.executeUpdate("INSERT INTO `tienda2`.`articulos` (`IDarticulo`, `Marca`, `Coste`, `Talla`, `Modelo`, `Cantidad`) VALUES ('"+rs3.getString(1)+"', '"+rs3.getString(2)+"', '"+rs3.getFloat(3)+"', '"+rs3.getString(4)+"', '"+rs3.getString(5)+"', '"+1+"')");
                    cantidadAlmacen = rs3.getInt(6) - 1;
                    if(cantidadAlmacen < 0) {

                    } else {
                        int rs4 = s4.executeUpdate("UPDATE `tienda2`.`almacen` SET `Cantidad` = '"+cantidadAlmacen+"' WHERE (`IDarticulo_almacen` = '"+idArticulo+"');");
                    }
                }


            }
        } catch (SQLException sqlx) {
            sqlx.printStackTrace();
            System.out.println("ERROR EN Modelo.pedidoAlmacen");
        } finally {
            s4.close();
            s3.close();
            s2.close();
            s.close();
        }
    }

}
