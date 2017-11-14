package didiernarvaez.eam.tapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import didiernarvaez.eam.tapp.Entidades.AsyncResponse;
import didiernarvaez.eam.tapp.Entidades.Usuario;
import didiernarvaez.eam.tapp.controlador.ctlGenerica;

public class RegistroUsuario extends AppCompatActivity implements AsyncResponse {

    EditText etNombre, etApellido, etCorreo, etUserName, etPassword, etCelular;
    ctlGenerica controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        etNombre = (EditText) findViewById(R.id.etNombreReg);
        etApellido = (EditText) findViewById(R.id.etApellidoReg);
        etCorreo = (EditText) findViewById(R.id.etCorreoReg);
        etCelular = (EditText) findViewById(R.id.etCelular);
        etUserName = (EditText) findViewById(R.id.etUserNameReg);
        etPassword = (EditText) findViewById(R.id.etPassReg);


    }

    public void registrarUsuario (View v){

        String nombre = etNombre.getText().toString();
        String apellido = etApellido.getText().toString();
        String correo = etCorreo.getText().toString();
        String celular = etCelular.getText().toString();
        String username = etUserName.getText().toString();
        String pass = etPassword.getText().toString();

        if (nombre.isEmpty() || apellido.isEmpty() || username.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "Debe ingresar los campos obligatorios", Toast.LENGTH_SHORT).show();
        } else {

            Usuario u = new Usuario(nombre, apellido, correo, celular, username, pass);
            controller = new ctlGenerica(u, "registrar");
            controller.delegate = this;
            controller.execute();

        }

    }

    @Override
    public void processFinish(JSONObject output) throws JSONException {
        String res = output.getString("registro");
        Log.e("query", res);

        if (res.equals("1")){
            Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();

            etNombre.setText("");
            etCelular.setText("");
            etApellido.setText("");
            etCorreo.setText("");
            etPassword.setText("");
            etUserName.setText("");

            Intent intent = new Intent(RegistroUsuario.this, VentanaPrincipalActivity.class);
            startActivity(intent);
        } else{
            Toast.makeText(this, "Registro Fallido", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void processFinishList(JSONArray output) throws JSONException {

    }

}
