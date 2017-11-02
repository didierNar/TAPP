package didiernarvaez.eam.tapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class LogIn extends AppCompatActivity {

    EditText etUserName, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        etUserName = (EditText) findViewById(R.id.etUserNameLogIn);
        etPassword = (EditText) findViewById(R.id.etPassLogIn);

    }
}
