package com.example.admin.simpantas;

import android.annotation.SuppressLint;
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

    @SuppressLint("SetTextI18n")
    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater().inflate(R.layout.list_infowindow,null);

        TextView tvProvinsi = view.findViewById(R.id.txProvinsi);
//        TextView tvLatitude = view.findViewById(R.id.txLatitude);
//        TextView tvLongitude = view.findViewById(R.id.txLongitude);
        TextView tvConfidence = view.findViewById(R.id.txConfidence);
        TextView tvKecamatanKabupaten = view.findViewById(R.id.txKecamatanKabupaten);
        TextView tvTanggal = view.findViewById(R.id.txTanggal);

        tvProvinsi.setText(marker.getTitle());
        tvKecamatanKabupaten.setText(marker.getSnippet());

        Hotspot hs = (Hotspot) marker.getTag();
        tvConfidence.setText("Confidence : "+String.valueOf(hs.getConfidence())+"%");
        tvTanggal.setText("Tanggal Kejadian : "+hs.getTanggal());

        return view;
    }
}
