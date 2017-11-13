package didiernarvaez.eam.tapp.Entidades;

/**
 * Created by luchobolivar on 11/12/17.
 */

public class Paradero {

    private String nombre;

    private double latitud;

    private double longitud;

    private String tipo;

    public Paradero(String nombre, double latitud, double longitud, String tipo) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
