package didiernarvaez.eam.tapp.Entidades;

/**
 * Created by LuchoBolivar on 7/13/17.
 */

public class Conductor {

   private int identificacion;
   private String nombre;


   public Conductor(int identificacion, String nombre) {
      this.identificacion = identificacion;
      this.nombre = nombre;
   }

   public int getIdentificacion() {
      return identificacion;
   }

   public String getNombre() {
      return nombre;
   }

   public void setIdentificacion(int identificacion) {
      this.identificacion = identificacion;
   }

   public void setNombre(String nombre) {
      this.nombre = nombre;
   }

   @Override
   public String toString() {
      return  nombre;
   }
}
