package br.com.bossini.sqlitegpslist;

import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class LocaisContract {

    private static List<Lugar> lugares;
    private static MainActivity mainActivity = new MainActivity();

    static{
        lugares = new ArrayList<>();
        lugares.add (new Lugar(
                10.000000, 10.000000
        ));
    }
    public static String createTableLugar() {
        return String.format(
                Locale.getDefault(),
                "CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s DOUBLE, %s DOUBLE);",
                LugarContract.TABLE_NAME,
                LugarContract.COLUMN_NAME_ID,
                LugarContract.COLUMN_NAME_LATITUDE,
                LugarContract.COLUMN_NAME_LONGITUDE
        );
    }

    private LocaisContract() {

    }

    public static String insertLugar() {
        String template = String.format(
                Locale.getDefault(),
                "INSERT INTO %s (%s, %s) VALUES",
                LugarContract.TABLE_NAME,
                LugarContract.COLUMN_NAME_LATITUDE,
                LugarContract.COLUMN_NAME_LONGITUDE
        );

        StringBuilder sb = new StringBuilder("");
        for (Lugar lugar : lugares) {
            Log.e("Teste", lugar.toString());
            sb.append(
                    String.format(
                            Locale.getDefault(),
                            "(%f, %f);",
                            lugar.getLatitude(),
                            lugar.getLongitude()
                    )
            );
        }

        String result = template + sb.toString();
        result = result.substring(0, result.length() - 1);
        result = result.concat(";");

        return result;
    }

    public static class LugarContract implements BaseColumns {
        public static final String TABLE_NAME = "tb_lugar";
        public static final String COLUMN_NAME_ID = "id_lugar";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";

        public static final String DROP_TABLE = String.format(Locale.getDefault(), "DROP TABLE %s", LugarContract.TABLE_NAME);
    }

}
