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
import didiernarvaez.eam.tapp.Entidades.LogIn;
import didiernarvaez.eam.tapp.controlador.ctlGenerica;

public class LogInActivity extends AppCompatActivity implements AsyncResponse{

    EditText etUserName, etPassword;

    ctlGenerica ctlGenerica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        etUserName = (EditText) findViewById(R.id.etUserNameLogIn);
        etPassword = (EditText) findViewById(R.id.etPassLogIn);

    }

    public void logIn (View view){
        if(etUserName.getText().equals("") || etPassword.getText().equals("")) {

            String userName = etUserName.getText().toString();
            String pass = etPassword.getText().toString();

            LogIn log = new LogIn(userName, pass);
            ctlGenerica = new ctlGenerica(log, "LogIn");
            ctlGenerica.execute();

        }

    }

    @Override
    public void processFinish(JSONObject output) throws JSONException {

        String res = output.getString("registro");
        Log.e(res, " respuestaaaaaaaaaaa");

        if (res.equals("1")){
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void arrayProcessFinish(JSONArray jsonArray) throws JSONException {

    }


}
