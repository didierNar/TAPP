package didiernarvaez.eam.tapp.Entidades;

/**
 * Created by luchobolivar on 11/9/17.
 */

public class UsuarioLogIn {

    private static String userNameLog;

    public static String getUserNameLog() {
        return userNameLog;
    }

    public static void setUserNameLog(String userNameLog) {
        UsuarioLogIn.userNameLog = userNameLog;
    }
}
