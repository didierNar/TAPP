package didiernarvaez.eam.tapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import didiernarvaez.eam.tapp.Entidades.AsyncResponse;
import didiernarvaez.eam.tapp.Entidades.ImageAdapter;
import didiernarvaez.eam.tapp.Entidades.Paradero;
import didiernarvaez.eam.tapp.controlador.ctlGenerica;

public class SubirFotosActivity extends AppCompatActivity implements AsyncResponse {

    GridView galleria;

    ctlGenerica controller;

    List<Paradero> paraderos;

    Spinner cbParaderos;

    public static Integer[] imagesGranUno = {R.drawable.granuno, R.drawable.unicentro, R.drawable.calima};

    public static Integer[] imagesGranDos = {R.drawable.granunodos, R.drawable.hotelarmenia, R.drawable.cafe};

    public static Integer[] imagesUnicentroBajando = {R.drawable.granunotres, R.drawable.hoteldos, R.drawable.diecinueve};

    public static Integer[] imagesFundadoresBajando = {R.drawable.grancolo, R.drawable.ultimauno};

    public static Integer[] imagesFundadoresSubiendo = {R.drawable.bava, R.drawable.ultimodos};

    static boolean uno = false;

    static boolean dos = false;

    static boolean tres = false;

    static boolean cuatro = false;

    static boolean cinco = false;

    private static final int SELECT_PICTURE = 1;

    private String selectedImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_fotos);

        galleria = (GridView) findViewById(R.id.galeria);

        cbParaderos = (Spinner) findViewById(R.id.cbParaderos);

        galleria.setAdapter(new ImageAdapter(this, imagesGranUno));
        listarParaderos();

        galleria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(SubirFotosActivity.this, ImagenCompletaActivity.class);
                i.putExtra("id", position);
                startActivity(i);

            }
        });

        cbParaderos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                uno = false;
                dos = false;
                tres = false;
                cuatro = false;
                cinco = false;

                if (position == 0) {
                    uno = true;
                    galleria.setAdapter(new ImageAdapter(SubirFotosActivity.this, imagesGranUno));
                }
                if (position == 1) {
                    dos = true;
                    galleria.setAdapter(new ImageAdapter(SubirFotosActivity.this, imagesGranDos));
                }
                if (position == 2) {
                    tres = true;
                    galleria.setAdapter(new ImageAdapter(SubirFotosActivity.this, imagesUnicentroBajando));

                }
                if (position == 3) {
                    cuatro = true;
                    galleria.setAdapter(new ImageAdapter(SubirFotosActivity.this, imagesFundadoresBajando));

                }
                if (position == 4) {
                    cinco = true;
                    galleria.setAdapter(new ImageAdapter(SubirFotosActivity.this, imagesFundadoresSubiendo));

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void tomarFoto(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//definimos un intent para abrir la activity de fotografia
        File foto = new File(getExternalFilesDir(null), "foto");

        intent.putExtra(MediaStore.EXTRA_OUTPUT, getExternalFilesDir(null) + "/" + "foto");

        startActivity(intent);


    }

    public void listarParaderos() {

        Object obj = null;

        controller = new ctlGenerica(obj, "listarParaderos");
        controller.delegate = this;
        controller.execute();


    }

    public void abrirGaleria (View view){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccione imagen"), SELECT_PICTURE);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
            }
        }
    }

    /**
     * helper to retrieve the path of an image URI
     */
    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        // this is our fallback here
        return uri.getPath();
    }


    public void recuperarFoto(View view) {

        /*
        recuperamos la img, mandamos la ruta(recordar el null es porque no es un directorio en especifico,
        solo la raiz del proyecto en la carpeta android - y el nombre del archivo )
         */
        Bitmap bitmap = BitmapFactory.decodeFile(getExternalFilesDir(null) + "/" + "foto");


    }

    @Override
    public void processFinish(JSONObject output) throws JSONException {

    }

    @Override
    public void processFinishList(JSONArray output) throws JSONException {

        if (output.isNull(0)) {

            Toast.makeText(this, "Sucedio un error", Toast.LENGTH_SHORT).show();

        } else {

            paraderos = new ArrayList<>();
            for (int i = 0; i < output.length(); i++) {

                JSONObject row = output.getJSONObject(i);

                String tipo = row.getString("TIPO");
                String nombre = row.getString("NOMBRE");
                double lat = row.getDouble("LATITUD");
                double lon = row.getDouble("LONGITUD");

                Paradero para = new Paradero(nombre, lat, lon, tipo);
                paraderos.add(para);
            }

            ArrayAdapter<Paradero> adapter1 = new ArrayAdapter<Paradero>(this,
                    android.R.layout.simple_spinner_item, paraderos);
            cbParaderos.setAdapter(adapter1);
        }

    }
}
