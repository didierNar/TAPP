package didiernarvaez.eam.tapp.Entidades;

/**
 * Created by luchobolivar on 11/13/17.
 */

public class RutaBus {

    private int id;

    private String destino;

    private String origen;

    private String numero;

    private double lat;

    private double lon;

    private String tipo;

    public RutaBus(int id, String destino, String origen, String numero, double lat, double lon, String tipo) {
        this.id = id;
        this.destino = destino;
        this.origen = origen;
        this.numero = numero;
        this.lat = lat;
        this.lon = lon;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Ruta: " + numero;
    }
}
