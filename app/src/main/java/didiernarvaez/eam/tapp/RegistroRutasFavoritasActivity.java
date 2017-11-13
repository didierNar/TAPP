package didiernarvaez.eam.tapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import didiernarvaez.eam.tapp.Entidades.AsyncResponse;
import didiernarvaez.eam.tapp.Entidades.Conductor;
import didiernarvaez.eam.tapp.Entidades.RegistroRutas;
import didiernarvaez.eam.tapp.Entidades.RutaBus;
import didiernarvaez.eam.tapp.Entidades.UsuarioLogIn;
import didiernarvaez.eam.tapp.controlador.ctlGenerica;

public class RegistroRutasFavoritasActivity extends AppCompatActivity implements AsyncResponse {

    Spinner rutas;

    List<RutaBus> rutasFav;

    ctlGenerica controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_rutas_favoritas);

        rutas = (Spinner) findViewById(R.id.cbRutas);

        listarRutasBuses();

    }

    public void registrarRutaFavorita (View view){

        RutaBus ruta = (RutaBus) rutas.getSelectedItem();

        RegistroRutas reg = new RegistroRutas(ruta.getId(), UsuarioLogIn.getUserNameLog());
        controller = new ctlGenerica(reg, "RegistroRutaFavorita");
        controller.delegate = this;
        controller.execute();

    }


    public void listarRutasBuses() {

        Object obj = null;

        controller = new ctlGenerica(obj, "listarPoscionRutas");
        controller.delegate = this;
        controller.execute();


    }

    @Override
    public void processFinish(JSONObject output) throws JSONException {

        String res = output.getString("registro");

        if(res.equals("1")){
            Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, VentanaPrincipalActivity.class);
            startActivity(intent);
        } else{
            Toast.makeText(this, "ERROR al intentar registrar", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void processFinishList(JSONArray output) throws JSONException {

        if (output.isNull(0)) {

            Toast.makeText(this, "Sucedio un error", Toast.LENGTH_SHORT).show();

        } else {

            rutasFav = new ArrayList<>();

            for (int i = 0; i < output.length(); i++) {

                JSONObject row = output.getJSONObject(i);

                String tipo = row.getString("TIPO");

                String destino = row.getString("DESTINO");
                String origen = row.getString("ORIGEN");
                String numero = row.getString("NUMERO");
                double lat = row.getDouble("LATITUD");
                double lon = row.getDouble("LONGITUD");
                int id = row.getInt("ID");

                RutaBus rut = new RutaBus(id, destino, origen, numero, lat, lon, tipo);
                rutasFav.add(rut);
            }

            ArrayAdapter<RutaBus> adapter1 = new ArrayAdapter<RutaBus>(this,
                    android.R.layout.simple_spinner_item, rutasFav);
            rutas.setAdapter(adapter1);
        }
    }
}
