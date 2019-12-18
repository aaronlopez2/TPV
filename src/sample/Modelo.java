package sample;

public class Modelo {

    public boolean Autenticar(String user, String pwd){
        if(user.equals("Aaron") && pwd.equals("a")) {
            return true;
        } else {
            return false;
        }
    }
}
