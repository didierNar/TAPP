package didiernarvaez.eam.tapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import didiernarvaez.eam.tapp.Entidades.ImageAdapter;

public class ImagenCompletaActivity extends AppCompatActivity {

    ImageView imagen;
    ImageAdapter im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen_completa);

        imagen = (ImageView) findViewById(R.id.imagen);

        Intent intent = getIntent();
        int position = intent.getExtras().getInt("id");

        if(SubirFotosActivity.uno) {
            im = new ImageAdapter(this, SubirFotosActivity.imagesGranUno);
        }
        if(SubirFotosActivity.dos) {
            im = new ImageAdapter(this, SubirFotosActivity.imagesGranDos);
        }
        if(SubirFotosActivity.tres) {
            im = new ImageAdapter(this, SubirFotosActivity.imagesUnicentroBajando);
        }
        if(SubirFotosActivity.cuatro) {
            im = new ImageAdapter(this, SubirFotosActivity.imagesFundadoresBajando);
        }
        if(SubirFotosActivity.cinco) {
            im = new ImageAdapter(this, SubirFotosActivity.imagesFundadoresSubiendo);
        }

        imagen.setImageResource(im.imagesGranUno[position]);

    }
}
