package sample;


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
    public boolean autenticar(String aut){
        if(aut.equals("Bien")) {
            return true;
        } else if(aut.equals("Fallo")) {
            return false;
        }
        return false;
    }
}
