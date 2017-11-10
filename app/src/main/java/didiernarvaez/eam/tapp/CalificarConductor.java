package didiernarvaez.eam.tapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import didiernarvaez.eam.tapp.Entidades.AsyncResponse;
import didiernarvaez.eam.tapp.Entidades.Calificacion;
import didiernarvaez.eam.tapp.Entidades.Conductor;
import didiernarvaez.eam.tapp.Entidades.UsuarioLogIn;
import didiernarvaez.eam.tapp.R;
import didiernarvaez.eam.tapp.controlador.ctlGenerica;

public class CalificarConductor extends AppCompatActivity implements AsyncResponse {

    ArrayList<Conductor> conductores;
    String[] calif = {"Seleccione una opcion", "Buena", "Regular", "Mala"};
    Spinner calificacion, conductor;
    EditText descripcion;
    ctlGenerica controller;

    double calificaci = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificar_conductor);

        calificacion = (Spinner) findViewById(R.id.cbCalificacion);
        conductor = (Spinner) findViewById(R.id.cbConductor);
        descripcion = (EditText) findViewById(R.id.etDescripcion);
        listarConductores();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, calif);
        calificacion.setAdapter(adapter);


    }


    public void registarCalificacion(View view) {

        if (calificacion.getSelectedItemPosition() == 1) {
            calificaci = 5.0;
        }
        if (calificacion.getSelectedItemPosition() == 2) {
            calificaci = 2.5;
        }
        if (calificacion.getSelectedItemPosition() == 3) {
            calificaci = 1.0;
        }

        if (descripcion.getText().toString().isEmpty() || calificacion.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "ingrese todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            String descrip = descripcion.getText().toString();
            Conductor c = (Conductor) conductor.getSelectedItem();
            String user = UsuarioLogIn.getUserNameLog();
            Calificacion calificacion = new Calificacion(user,descrip, calificaci, c.getIdentificacion());
            controller = new ctlGenerica(calificacion, "registrarCalificacion");
            controller.delegate = this;
            controller.execute();
        }
    }

    @Override
    public void processFinish(JSONObject output) throws JSONException {
        String res = output.getString("registro");
        Log.e("query", res);

        if (res.equals("1")) {
            Toast.makeText(this, "Conductor calificado correctamente", Toast.LENGTH_SHORT).show();
            descripcion.setText("");
            calificacion.setSelection(0);
            conductor.setSelection(0);
        } else {
            Toast.makeText(this, "error realizando la calificacion", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void processFinishList(JSONArray output) throws JSONException {

        try {
            conductores = new ArrayList<Conductor>();
            for (int i = 0; i < output.length(); i++) {
                JSONObject row = output.getJSONObject(i);
                int identificacion = row.getInt("identificacion");
                String nombre = row.getString("nombre");
                Conductor c = new Conductor(identificacion, nombre);
                conductores.add(c);

            }
            ArrayAdapter<Conductor> adapter1 = new ArrayAdapter<Conductor>(this,
                    android.R.layout.simple_spinner_item, conductores);
            conductor.setAdapter(adapter1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void listarConductores() {
        Object obj = null;
        controller = new ctlGenerica(obj, "listarConductores");
        controller.delegate = this;
        controller.execute();
    }
}
