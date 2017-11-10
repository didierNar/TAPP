package didiernarvaez.eam.tapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import didiernarvaez.eam.tapp.Entidades.AsyncResponse;
import didiernarvaez.eam.tapp.Entidades.LogIn;
import didiernarvaez.eam.tapp.Entidades.UsuarioLogIn;
import didiernarvaez.eam.tapp.controlador.ctlGenerica;

public class LogInActivity extends AppCompatActivity implements AsyncResponse{

    EditText etUserName, etPassword;

    ctlGenerica controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        etUserName = (EditText) findViewById(R.id.etUserNameLogIn);
        etPassword = (EditText) findViewById(R.id.etPassLogIn);

    }

    public void logIn (View view){

        String userName = etUserName.getText().toString();
        String pass = etPassword.getText().toString();

        if(userName.isEmpty() || pass.isEmpty()) {

            Toast.makeText(this, "Debe Ingresar los datos para el inicion de sesion", Toast.LENGTH_SHORT).show();

        }else{

            LogIn log = new LogIn(userName, pass);
            controller = new ctlGenerica(log, "LogIn");
            controller.delegate = this;
            controller.execute();

        }

    }

    @Override
    public void processFinish(JSONObject output) throws JSONException {

        String res = output.getString("registro");

        if (res.equals("1")){
            UsuarioLogIn.setUserNameLog(etUserName.getText().toString());
            Intent intent = new Intent(this, RegistroUsuario.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "El nombre de usuario o contraseña estan erroneas", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void processFinishList(JSONArray output) throws JSONException {

        if(!output.isNull(0)){

            UsuarioLogIn.setUserNameLog(etUserName.getText().toString());
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);

        }else{
            Toast.makeText(this, "El nombre de usuario o contraseña estan erroneas", Toast.LENGTH_SHORT).show();
        }

    }


}
