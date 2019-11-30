package br.com.bossini.sqlitegpslist;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final int REQUEST_CODE_GPS_PERMISSION = 1001;

    private Lugar lugar;
    private ArrayList<Lugar> lugares = new ArrayList<>();

    private double latitude;
    private double longitude;

    private LugarDAO lugarDAO = new LugarDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                latitude = lat;
                longitude = lon;
                System.out.println("pegando coordenadas");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        setContentView(R.layout.activity_main);
    }

    public void gerarLista(List<Lugar> l) {
        lugar = new Lugar();
        if(l.size() >= 50) {
            l.remove(0);
        }
        lugar.setLatitude(latitude);
        lugar.setLongitude(longitude);
        l.add(lugar);

        lugarDAO.insertLugar(lugar);

    }


    public void listar(View view) {
        gerarLista(lugares);
        Intent intent = new Intent(this, ListaActivity.class);
        intent.putExtra("lugares", lugares);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();
        //a permissÃ£o jÃ¡ foi dada?
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED){
            //somente ativa
            // a localizaÃ§Ã£o Ã© obtida via hardware, intervalo de 0 segundos e 0 metros entre atualizaÃ§Ãµes
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0, 0, locationListener);
        }
        else{
            //permissÃ£o ainda nÃ£o foi nada, solicita ao usuÃ¡rio
            //quando o usuÃ¡rio responder, o mÃ©todo onRequestPermissionsResult vai ser chamado
            ActivityCompat.requestPermissions(this,
                    new String []{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_GPS_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull
            String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GPS_PERMISSION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //permissÃ£o concedida, ativamos o GPS
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            0, 0, locationListener);
                }
            }
            else{
                //usuÃ¡rio negou, nÃ£o ativamos
                Toast.makeText(this,
                        getString(R.string.solicita_gps), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

}
