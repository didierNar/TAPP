package didiernarvaez.eam.tapp.Entidades;

/**
 * Created by Didier_Narváez on 2/11/2017.
 */

public class Usuario {

    private String nombre;
    private String apellido;
    private String correo;
    private String celular;
    private String username;
    private String contrasenia;

    public Usuario(String nombre, String apellido, String correo, String celular, String username, String contrasenia) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.celular = celular;
        this.username = username;
        this.contrasenia = contrasenia;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String password) {
        this.contrasenia = password;
    }
}
