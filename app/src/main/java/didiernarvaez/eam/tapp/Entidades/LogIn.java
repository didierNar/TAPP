package didiernarvaez.eam.tapp.Entidades;

/**
 * Created by luchobolivar on 11/4/17.
 */

public class LogIn {

    private String userName;

    private String passWord;

    public LogIn(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
