package com.example.admin.simpantas;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowGMap implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowGMap(Context c){
        context = c;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater().inflate(R.layout.list_infowindow,null);

        TextView tvProvinsi = view.findViewById(R.id.txProvinsi);
        TextView tvLatitude = view.findViewById(R.id.txLatitude);
        TextView tvLongitude = view.findViewById(R.id.txLongitude);
        TextView tvConfidence = view.findViewById(R.id.txConfidence);
        TextView tvKecamatanKabupaten = view.findViewById(R.id.txKecamatanKabupaten);

        tvProvinsi.setText(marker.getTitle());
        tvKecamatanKabupaten.setText(marker.getSnippet());

        Hotspot hs = (Hotspot) marker.getTag();
        tvLatitude.setText("Latitude : "+String.valueOf(hs.getLatitude()));
        tvLongitude.setText("Longitude : "+String.valueOf(hs.getLongitude()));
        tvConfidence.setText("Confidence : "+String.valueOf(hs.getConfidence()));

        return view;
    }
}
