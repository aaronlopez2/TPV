package sample;

import javafx.scene.input.KeyCode;

import javax.crypto.spec.PBEParameterSpec;
import java.awt.event.KeyEvent;


public class Controller {

    Modelo model = new Modelo();

    public void mensaje(){
        System.out.println("PRUEBA");
    }

    public String Aceptar(String user, String pwd) {
        boolean log = model.Autenticar(user,pwd);
        if(log) {
            return "Bien";
        } else {
            return "Fallo";
        }
    }
    public void autenticacion(String aut){
        if(aut.equals("Bien")) {

        } else if(aut.equals("Fallo")) {

        }

    }
}
