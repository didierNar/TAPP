package didiernarvaez.eam.tapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import didiernarvaez.eam.tapp.Entidades.AsyncResponse;

public class  MainActivity extends AppCompatActivity implements AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void infoRutas(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void abrirRegistro (View v){
        Intent i = new Intent(this, RegistroUsuario.class);
        startActivity(i);
    }

    public void abrirLogIn (View v){
        Intent i = new Intent(this, LogInActivity.class);
        startActivity(i);
    }

    public void Calificar(View v){
        Intent i = new Intent(this, CalificarConductor.class);
        startActivity(i);
    }
    @Override
    public void processFinish(JSONObject output) throws JSONException {

    }

    @Override
    public void processFinishList(JSONArray output) throws JSONException {

    }

}
