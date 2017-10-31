package didiernarvaez.eam.tapp.Entidades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;

import java.util.List;

import didiernarvaez.eam.tapp.DireccionIP;
import didiernarvaez.eam.tapp.R;

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
