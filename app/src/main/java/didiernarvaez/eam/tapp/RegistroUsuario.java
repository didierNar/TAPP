package didiernarvaez.eam.tapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class RegistroUsuario extends AppCompatActivity {

    EditText etNombre, etApellido, etCorreo, etUserName, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        etNombre = (EditText) findViewById(R.id.etNombreReg);
        etApellido = (EditText) findViewById(R.id.etApellidoReg);
        etCorreo = (EditText) findViewById(R.id.etCorreoReg);
        etUserName = (EditText) findViewById(R.id.etUserNameReg);
        etPassword = (EditText) findViewById(R.id.etPassReg);

    }
}
