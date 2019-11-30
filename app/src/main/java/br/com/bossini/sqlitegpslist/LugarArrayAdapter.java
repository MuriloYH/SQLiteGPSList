package br.com.bossini.sqlitegpslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LugarArrayAdapter extends ArrayAdapter<Lugar> {

    private Context context;
    private List<Lugar> lugares;

    public LugarArrayAdapter(Context context, List<Lugar> lugares) {
        super (context, -1, lugares);
        this.context = context;
        this.lugares = lugares;
    }

    @NonNull
    @Override
    public View getView (int position,
                         @Nullable View convertView,
                         @NonNull ViewGroup parent) {
        Lugar localAtual = getItem(position);
        LugarViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new LugarViewHolder();
            viewHolder.latitudeTV = convertView.findViewById(R.id.latitudeTextView);
            viewHolder.longitudeTV = convertView .findViewById(R.id.longitudeTextView);
            viewHolder.val_latitudeTV = convertView.findViewById(R.id.val_latitudeTextView);
            viewHolder.val_longitudeTV = convertView .findViewById(R.id.val_longitudeTextView);
            convertView.setTag(viewHolder);
        }
        viewHolder = (LugarViewHolder) convertView.getTag();

        double latitude = localAtual.getLatitude();
        double longitude = localAtual.getLongitude();

        viewHolder.latitudeTV.setText("Latitude: ");
        viewHolder.longitudeTV.setText("Longitude: ");
        viewHolder.val_latitudeTV.setText(String.valueOf(latitude));
        viewHolder.val_longitudeTV.setText(String.valueOf(longitude));
        return convertView;

    }

    @Override
    public int getCount() {
        return lugares.size();
    }


}
