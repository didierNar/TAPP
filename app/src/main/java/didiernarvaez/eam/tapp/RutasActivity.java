package didiernarvaez.eam.tapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;

import java.util.List;

import didiernarvaez.eam.tapp.Entidades.DireccionIP;
import didiernarvaez.eam.tapp.Entidades.Rutas;

public class RutasActivity extends AppCompatActivity {

    private String enlaceRutas;
    private Spinner rutas;
    private String ip;

    List<Rutas> listaRutas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutas);

        rutas = (Spinner) findViewById(R.id.cbRutas);
        ip = DireccionIP.getIp();


    }






}
