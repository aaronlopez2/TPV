package sample;


public class Controller {

    Modelo model = new Modelo();


    public void mensaje(){
        System.out.println("PRUEBA");
    }

    public boolean Aceptar(String user, String pwd) {
        boolean log = model.Autenticar(user,pwd);
        if(log) {
            return true;
        } else {
            return false;
        }
    }
    public boolean autenticar(Boolean aut){

        if(aut) {
            return true;
        } else {
            return false;
        }
    }
}
