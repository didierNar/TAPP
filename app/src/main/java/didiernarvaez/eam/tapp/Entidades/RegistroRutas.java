package didiernarvaez.eam.tapp.Entidades;

/**
 * Created by luchobolivar on 11/13/17.
 */

public class RegistroRutas {

    private int rutaId;

    private String nomUsuario;

    private String tipo;

    public RegistroRutas(int rutaId, String nomUsuario, String tipo) {
        this.rutaId = rutaId;
        this.nomUsuario = nomUsuario;
        this.tipo = tipo;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
