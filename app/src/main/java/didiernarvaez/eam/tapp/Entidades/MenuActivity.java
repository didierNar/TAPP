package didiernarvaez.eam.tapp.Entidades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }



    public void rutas(View v){

        Intent intent = new Intent(this, RutasActivity.class);
        startActivity(intent);

    }

    public void localizacionRutas(View v){

        Intent intent = new Intent(this, GPSActivity.class);
        startActivity(intent);

    }
}
