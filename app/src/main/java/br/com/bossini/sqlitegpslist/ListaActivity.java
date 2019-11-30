package br.com.bossini.sqlitegpslist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {

    private ListView lugaresLV;
    private List<Lugar> lista = new ArrayList<>();

    private List<Lugar> places;

    private LugarDAO lugarDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        lugarDAO = new LugarDAO(this);
        lugaresLV = findViewById(R.id.lugaresListView);
        Intent origemIntent = getIntent();
        places = (ArrayList) origemIntent.getSerializableExtra("places");

        List<Lugar> lugares = lugarDAO.buscar();
        LugarArrayAdapter adapter =
                new LugarArrayAdapter(this, lugares);
        lugaresLV.setAdapter(adapter);
        lugaresLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String locaisList = lugaresLV.getItemAtPosition(position).toString();
                String lat = locaisList.split(",")[0].split(":")[1];
                String lon = locaisList.split(",")[1].split(":")[1];

                Uri gmmIntentUri = Uri.parse(String.format("geo:%s,%s", lat, lon));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        Toast.makeText(this, "Salvo", Toast.LENGTH_LONG).show();

    }
}
