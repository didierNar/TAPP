package didiernarvaez.eam.tapp.Entidades;

/**
 * Created by luchobolivar on 11/13/17.
 */

public class RegistroRutas {

    private int rutaId;

    private String nomUsuario;

    public RegistroRutas(int rutaId, String nomUsuario) {
        this.rutaId = rutaId;
        this.nomUsuario = nomUsuario;
    }

    public int getRutaId() {
        return rutaId;
    }

    public void setRutaId(int rutaId) {
        this.rutaId = rutaId;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }
}
