package didiernarvaez.eam.tapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import didiernarvaez.eam.tapp.Entidades.AsyncResponse;
import didiernarvaez.eam.tapp.Entidades.Paradero;
import didiernarvaez.eam.tapp.Entidades.RutaBus;
import didiernarvaez.eam.tapp.Entidades.RutasFavoritas;
import didiernarvaez.eam.tapp.Entidades.UserFavoritas;
import didiernarvaez.eam.tapp.Entidades.UsuarioLogIn;
import didiernarvaez.eam.tapp.controlador.ctlGenerica;

public class VentanaPrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, AsyncResponse,
        GoogleMap.OnMarkerClickListener, SensorEventListener {

    SupportMapFragment supportMapFragment;

    private final LatLng inicio = new LatLng(4.541763, -75.663464);

    private GoogleMap mMap;

    private List<Paradero> paraderos;

    private List<RutaBus> rutas;

    private List<RutasFavoritas> rutasFavoritas;

    ctlGenerica controller;

    boolean pa = false;

    boolean ru = false;

    boolean fav = false;

    private static final float LIMITE_SENSIBILIDAD_SACUDIDA = 1.4f;

    private static final int LIMITE_TIEMPO_MS_SACUDIDA = 250;

    private long tiempoSacudida = 0;

    Sensor s;

    SensorManager sensorM;

    private Sensor sensorAcelerometro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //se define el administrador de sensores
        sensorM = (SensorManager) getSystemService(SENSOR_SERVICE);
        //Acelerometro
        sensorAcelerometro = sensorM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //se agrega el administrador de sensores
        sensorM.registerListener(VentanaPrincipalActivity.this,s, SensorManager.SENSOR_DELAY_NORMAL);

        supportMapFragment = SupportMapFragment.newInstance();

        setContentView(R.layout.activity_ventana_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();

        validarOpciones(menu);

        supportMapFragment.getMapAsync(this);

        android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();

        sFm.beginTransaction().replace(R.id.fragmentoMapa, supportMapFragment).commit();


    }

    /*Tan pronto se activan los sensores, se asocian al gestionador de sensores*/
    @Override
    protected void onResume() {
        super.onResume();
        /*Se asocia el acelerometro al administrador*/
        sensorM.registerListener(this, sensorAcelerometro, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /*Cuando se pausa un sensor*/
    @Override
    protected void onPause() {
        /*Si se pausa, se des-asocia del administrador de sensores*/
        super.onPause();
        /*El this es el sensor que se pauso*/
        sensorM.unregisterListener(this);
    }

    public void validarOpciones(Menu menu) {

        if (UsuarioLogIn.getUserNameLog() == null) {

            menu.findItem(R.id.fotosParadero).setVisible(false);
            menu.findItem(R.id.calificarConductor).setVisible(false);
            menu.findItem(R.id.cerrarSesion).setVisible(false);
            menu.findItem(R.id.mostrarRutasFavoritas).setVisible(false);
            menu.findItem(R.id.rutasFavoritas).setVisible(false);
            menu.findItem(R.id.iniciarSesion).setVisible(true);
            menu.findItem(R.id.registrar).setVisible(true);

        } else {

            menu.findItem(R.id.fotosParadero).setVisible(true);
            menu.findItem(R.id.calificarConductor).setVisible(true);
            menu.findItem(R.id.cerrarSesion).setVisible(true);
            menu.findItem(R.id.mostrarRutasFavoritas).setVisible(true);
            menu.findItem(R.id.rutasFavoritas).setVisible(true);
            menu.findItem(R.id.iniciarSesion).setVisible(false);
            menu.findItem(R.id.registrar).setVisible(false);

        }
    }

    public void validarMarkersAPoner (){

        if(pa) {
            marcarMapaParaderos(paraderos);
            pa = false;
        }
        if(ru){
            marcarMapaRutas(rutas);
            ru = false;
        }
        if(fav){
            marcaMapaRutasFavoritas(rutasFavoritas);
        }


    }

    public void listaRutasFavoritas(){

        UserFavoritas u = new UserFavoritas(UsuarioLogIn.getUserNameLog());

        controller = new ctlGenerica(u, "ListarRutasFavoritas");
        controller.delegate= this;
        controller.execute();

    }

    public void marcaMapaRutasFavoritas(List<RutasFavoritas> rf){

        if(!rf.isEmpty()){

            mMap.clear();
            for (int i = 0; i < rf.size(); i++) {

                RutasFavoritas rutF = rf.get(i);
                double latitud = rutF.getLat();
                double lon = rutF.getLon();
                String tipo = rutF.getTipo();

                mMap.addMarker(new MarkerOptions().position(new LatLng(latitud, lon))
                        .title(tipo).icon(BitmapDescriptorFactory.fromResource(R.drawable.rutasencontrar)));

            }
        }

    }

    public void listarParaderos() {

        Object obj = null;

        controller = new ctlGenerica(obj, "listarParaderos");
        controller.delegate = this;
        controller.execute();


    }

    public void marcarMapaParaderos(List<Paradero> p) {

        if (!p.isEmpty()) {

            mMap.clear();
            for (int i = 0; i < p.size(); i++) {

                Paradero par = p.get(i);
                double latitud = par.getLatitud();
                double lon = par.getLongitud();
                String tipo = par.getTipo();

                mMap.addMarker(new MarkerOptions().position(new LatLng(latitud, lon))
                        .title(tipo).icon(BitmapDescriptorFactory.fromResource(R.drawable.paradero)));

            }
        }

    }

    public void listarPosicionBuses() {

        Object obj = null;

        controller = new ctlGenerica(obj, "listarPoscionRutas");
        controller.delegate = this;
        controller.execute();


    }

    public void marcarMapaRutas (List<RutaBus> r){

        if(!r.isEmpty()){
            mMap.clear();
            for(int i = 0; i<r.size(); i++){

                RutaBus rut = r.get(i);
                double lat = rut.getLat();
                double lon = rut.getLon();
                String tip = rut.getTipo();
                //String num = rut.getNumero();

                Marker mar = mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title(tip));
                mar.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.rutasencontrar));

                /**
                if(num.equals("1")){
                    mar.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.uno));
                }
                if(num.equals("4")){
                    mar.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.cuatro));
                } else {
                    mar.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.rutasencontrar));
                }
                */
            }
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_ventana_principal_drawer, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();

        if (id == R.id.mostrarRutas) {

            listarPosicionBuses();
        } else if (id == R.id.paraderos) {

            listarParaderos();

        } else if (id == R.id.calificarConductor) {
            Intent intent = new Intent(VentanaPrincipalActivity.this, CalificarConductor.class);
            startActivity(intent);

        } else if (id == R.id.fotosParadero) {
            Intent intent = new Intent(VentanaPrincipalActivity.this, SubirFotosActivity.class);
            startActivity(intent);
        } else if (id == R.id.registrar) {
            Intent intent = new Intent(VentanaPrincipalActivity.this, RegistroUsuario.class);
            startActivity(intent);
        } else if (id == R.id.iniciarSesion) {
            Intent intent = new Intent(VentanaPrincipalActivity.this, LogInActivity.class);
            startActivity(intent);
        } else if (id == R.id.cerrarSesion) {
            UsuarioLogIn.setUserNameLog(null);
            Intent intent = new Intent(VentanaPrincipalActivity.this, VentanaPrincipalActivity.class);
            startActivity(intent);
        } else if(id == R.id.rutasFavoritas){
            Intent inntent = new Intent(VentanaPrincipalActivity.this, RegistroRutasFavoritasActivity.class);
            startActivity(inntent);
        } else if (id == R.id.mostrarRutasFavoritas){
            listaRutasFavoritas();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(inicio, 14));

        miPosicionActual();

    }


    @SuppressLint("MissingPermission")
    public void miPosicionActual() {


        mMap.setMyLocationEnabled(true);

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
            rutas = new ArrayList<>();
            rutasFavoritas = new ArrayList<>();

            for (int i = 0; i < output.length(); i++) {

                JSONObject row = output.getJSONObject(i);

                String tipo = row.getString("TIPO");

                if (tipo.equals("PARADERO")) {

                    pa = true;
                    String nombre = row.getString("NOMBRE");
                    double lat = row.getDouble("LATITUD");
                    double lon = row.getDouble("LONGITUD");

                    Paradero para = new Paradero(nombre, lat, lon, tipo);
                    paraderos.add(para);
                }
                if(tipo.equals("RUTA")){

                    ru = true;
                    String destino = row.getString("DESTINO");
                    String origen = row.getString("ORIGEN");
                    String numero = row.getString("NUMERO");
                    double lat = row.getDouble("LATITUD");
                    double lon = row.getDouble("LONGITUD");
                    int id = row.getInt("ID");

                    RutaBus rut = new RutaBus(id, destino, origen, numero, lat, lon, tipo);
                    rutas.add(rut);

                }
                if(tipo.equals("FAVORITA")){

                    fav = true;
                    String destino = row.getString("DESTINO");
                    String origen = row.getString("ORIGEN");
                    String numero = row.getString("NUMERO");
                    double lat = row.getDouble("LATITUD");
                    double lon = row.getDouble("LONGITUD");

                    RutasFavoritas rutFav = new RutasFavoritas(destino, origen, numero, lat, lon, tipo);
                    rutasFavoritas.add(rutFav);

                }

            }

            validarMarkersAPoner();

        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getTitle().equals("PARADERO")) {

            double lat = marker.getPosition().latitude;
            double lon = marker.getPosition().longitude;

            for (int i = 0; i < paraderos.size(); i++) {
                if (lat == paraderos.get(i).getLatitud() && lon == paraderos.get(i).getLongitud()) {

                    Toast.makeText(VentanaPrincipalActivity.this, paraderos.get(i).getNombre(),
                            Toast.LENGTH_SHORT).show();
                    break;
                }


            }
        }

        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            detectShake(sensorEvent);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * Detecta una sacudida a partir del acelerometro
     */
    private void detectShake(SensorEvent event) {

        /*Se captura el tiempo actual*/
        long now = System.currentTimeMillis();

        /*Si esta sacudida supera el tiempo estipulado para la proxima sacudida*/
        if ((now - tiempoSacudida) > LIMITE_TIEMPO_MS_SACUDIDA) {

            /*Se almacena para ser utilizado en una proxima sacudida*/
            tiempoSacudida = now;

            /*Se obtiene el movimiento en X, Y, Z y se divide por la gravedad para detercar una
            *  sacudida*/
            float gX = event.values[0] / SensorManager.GRAVITY_EARTH;
            float gY = event.values[1] / SensorManager.GRAVITY_EARTH;
            float gZ = event.values[2] / SensorManager.GRAVITY_EARTH;

            float posicionx = event.values[0];
            float posiciony = event.values[SensorManager.AXIS_Y];

            /*Se calcula con la operacion matematica el cambio gravitacional*/
            double gForce = Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            //Si supera el limite establecido, suena el audio
            if (gForce > LIMITE_SENSIBILIDAD_SACUDIDA) {
                if(UsuarioLogIn.getUserNameLog() == null) {
                    listarParaderos();
                } else {
                    listaRutasFavoritas();
                }
            }
        }
    }
}
