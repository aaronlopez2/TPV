package dbo;

public class Users {
    private String id_User, user_pwd, user_lvl, user_name, emp_id;

    public Users(String id_User, String user_pwd, String user_lvl, String user_name, String emp_id) {
        this.id_User = id_User;
        this.user_pwd = user_pwd;
        this.user_lvl = user_lvl;
        this.user_name = user_name;
        this.emp_id = emp_id;
    }

    public String getId_User() {
        return id_User;
    }

    public void setId_User(String id_User) {
        this.id_User = id_User;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_lvl() {
        return user_lvl;
    }

    public void setUser_lvl(String user_lvl) {
        this.user_lvl = user_lvl;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }
}
