package didiernarvaez.eam.tapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import didiernarvaez.eam.tapp.Fragmentos.FragmentoPrincipal;

public class VentanaPrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int GPS_ERRORDIALOG_REQUEST = 9001;
    GoogleMap mMap;
    MapView mMapView;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMapView = (MapView) findViewById(R.id.mapViewPrinci);

        fab = (FloatingActionButton) findViewById(R.id.mostrarRutas);

        //mMapView.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ventana_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.primerFragmento, new FragmentoPrincipal()).commit();

    }

    public void logInEmerjente() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VentanaPrincipalActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
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
        getMenuInflater().inflate(R.menu.ventana_principal, menu);
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

        } else if (id == R.id.rutaEspecifica) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.fotosParadero) {

        } else if (id == R.id.registrar) {
            Intent intent = new Intent(VentanaPrincipalActivity.this, RegistroUsuario.class);
            startActivity(intent);
        } else if (id == R.id.iniciarSesion) {
            Intent intent = new Intent(VentanaPrincipalActivity.this, LogInActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     @Override protected void onPause() {
     super.onPause();
     mMapView.onPause();
     }

     @Override public void onLowMemory() {
     super.onLowMemory();
     mMapView.onLowMemory();
     }

     @Override protected void onDestroy() {
     super.onDestroy();
     mMapView.onDestroy();
     }

     @Override protected void onResume() {
     super.onResume();
     mMapView.onResume();
     }

     @Override protected void onSaveInstanceState(Bundle outState) {
     super.onSaveInstanceState(outState);
     mMapView.onSaveInstanceState(outState);
     }
     */
}
