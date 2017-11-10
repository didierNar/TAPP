package didiernarvaez.eam.tapp.Entidades;

/**
 * Created by LuchoBolivar on 7/13/17.
 */

public class Calificacion {

   private String descripcion;
   private double valor;
   private int idConductor;
   private String usuario;

    public Calificacion(String usuario, String descripcion, double valor, int idConductor) {
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.valor = valor;
        this.idConductor = idConductor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getValor() {
        return valor;
    }

    public int getIdConductor() {
        return idConductor;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setIdConductor(int idConductor) {
        this.idConductor = idConductor;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
