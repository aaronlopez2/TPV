package dbo;

import java.sql.Date;

public class Empleados {
   private int emp_Id;
   private String dni,nombre,apellido1,apellido2,direccion,num_SS,num_Af_Sindicato,num_telf_emp,num_telf_priv,email,cp;
   private Date fechaNacimiento;

    public Empleados(int emp_Id,String dni, String nombre) {
        this.emp_Id = emp_Id;
        this.dni = dni;
        this.nombre = nombre;
    }
    public Empleados(String dni, String nombre, String apellido1, String apellido2) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.direccion = "dire";
        this.num_SS = "999";
        this.num_Af_Sindicato = "999";
        this.num_telf_emp = "654654654";
        this.num_telf_priv = "654654564";
        this.email = "email@falso.com";
        this.cp = "46950";
        Long date = Long.valueOf(999999999);
        this.fechaNacimiento = new Date(date);
    }

    public Empleados(int emp_Id, String dni, String nombre, String apellido1, String apellido2, String direccion, String num_SS, String num_Af_Sindicato, String num_telf_emp, String num_telf_priv, String email, String cp, Date fechaNacimiento) {
        this.emp_Id = emp_Id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.direccion = direccion;
        this.num_SS = num_SS;
        this.num_Af_Sindicato = num_Af_Sindicato;
        this.num_telf_emp = num_telf_emp;
        this.num_telf_priv = num_telf_priv;
        this.email = email;
        this.cp = cp;
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getEmp_Id() {
        return emp_Id;
    }

    public void setEmp_Id(int emp_Id) {
        this.emp_Id = emp_Id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNum_SS() {
        return num_SS;
    }

    public void setNum_SS(String num_SS) {
        this.num_SS = num_SS;
    }

    public String getNum_Af_Sindicato() {
        return num_Af_Sindicato;
    }

    public void setNum_Af_Sindicato(String num_Af_Sindicato) {
        this.num_Af_Sindicato = num_Af_Sindicato;
    }

    public String getNum_telf_emp() {
        return num_telf_emp;
    }

    public void setNum_telf_emp(String num_telf_emp) {
        this.num_telf_emp = num_telf_emp;
    }

    public String getNum_telf_priv() {
        return num_telf_priv;
    }

    public void setNum_telf_priv(String num_telf_priv) {
        this.num_telf_priv = num_telf_priv;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
