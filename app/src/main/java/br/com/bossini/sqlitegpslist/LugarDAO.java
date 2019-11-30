package br.com.bossini.sqlitegpslist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LugarDAO {

    private Context context;

    public LugarDAO(Context context) {
        this.context = context;
    }

    public List<Lugar> buscar() {
        LugarDBHelper dbHelper = new LugarDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Lugar> lugares = new ArrayList<>();
        String command = String.format(
                Locale.getDefault(),
                "SELECT * FROM %s ORDER BY id_lugar DESC LIMIT 50",
                LocaisContract.LugarContract.TABLE_NAME
        );
        Cursor cursor = db.rawQuery(command, null);
        while (cursor.moveToNext()) {
            int idLugar = cursor.getInt(
                    cursor.getColumnIndex(
                            LocaisContract.LugarContract.COLUMN_NAME_ID
                    )
            );

            double latitude = cursor.getDouble(
                    cursor.getColumnIndex(
                            LocaisContract.LugarContract.COLUMN_NAME_LATITUDE
                    )
            );

            double longitude = cursor.getDouble(
                    cursor.getColumnIndex(
                            LocaisContract.LugarContract.COLUMN_NAME_LONGITUDE
                    )
            );

            Lugar lugar = new Lugar(idLugar, latitude, longitude);
            lugares.add(lugar);
        }
        cursor.close();
        db.close();
        dbHelper.close();
        return lugares;
    }

    public Lugar insertLugar(Lugar lugar) {
        LugarDBHelper dbHelper = new LugarDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String template = "INSERT INTO %s (%s, %s) VALUES (%f, %f);";
        String command = String.format(
                Locale.getDefault(),
                template,
                LocaisContract.LugarContract.TABLE_NAME,
                LocaisContract.LugarContract.COLUMN_NAME_LATITUDE,
                LocaisContract.LugarContract.COLUMN_NAME_LONGITUDE,
                lugar.getLatitude(),
                lugar.getLongitude()
        );

        // Gets the data repository in write mode
        db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(LocaisContract.LugarContract.COLUMN_NAME_LATITUDE, lugar.getLatitude());
        values.put(LocaisContract.LugarContract.COLUMN_NAME_LONGITUDE, lugar.getLongitude());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(LocaisContract.LugarContract.TABLE_NAME, null, values);

        db.close();
        dbHelper.close();
        return lugar;
    }


}
