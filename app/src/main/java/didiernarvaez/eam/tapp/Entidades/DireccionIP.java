package didiernarvaez.eam.tapp.Entidades;

/**
 * Created by Didier_Narváez on 18/05/2017.
 */

public class DireccionIP {

    private static String ip;

    public static String getIp() {
        return "http://192.168.0.13/prueba/parcial.php";
    }

    public static void setIp(String ip) {
        DireccionIP.ip = ip;
    }
}
