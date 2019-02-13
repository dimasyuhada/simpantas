package com.example.admin.simpantas;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class VisualisasiActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latitude, longitude;
    private int val;

    private List<Hotspot> retrieveList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualisasi);
        latitude = 0.506;
        longitude = 101.437;

        retrieveList = (List<Hotspot>) getIntent().getSerializableExtra("resultObject");


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng riau = new LatLng(latitude, longitude);
        Hotspot h = new Hotspot();
        CustomInfoWindowGMap customInfoWindow = new CustomInfoWindowGMap(this);
        mMap.setInfoWindowAdapter(customInfoWindow);

        for (int i=0; i<retrieveList.size();i++){
            switch (retrieveList.get(i).getTemp())
            {
                case 0:
                    h.setConfidence(retrieveList.get(i).getConfidence());

                    Marker l1 = mMap.addMarker(new MarkerOptions().position(new LatLng(retrieveList.get(i).getLatitude(), retrieveList.get(i).getLongitude())).title(retrieveList.get(i).getKawasan()).snippet("Daerah : "+retrieveList.get(i).getKecamatan()+", "+retrieveList.get(i).getKabupaten()).icon(BitmapDescriptorFactory.fromResource(R.drawable.fireblue)));
                    l1.setTag(h);
                    l1.showInfoWindow();
                    break;
                case 1:
                    h.setConfidence(retrieveList.get(i).getConfidence());

                    Marker l2 = mMap.addMarker(new MarkerOptions().position(new LatLng(retrieveList.get(i).getLatitude(), retrieveList.get(i).getLongitude())).title(retrieveList.get(i).getKawasan()).snippet("Daerah : "+retrieveList.get(i).getKecamatan()+", "+retrieveList.get(i).getKabupaten()).icon(BitmapDescriptorFactory.fromResource(R.drawable.fireyellow)));
                    l2.setTag(h);
                    l2.showInfoWindow();
                    break;
                case 2:
                    h.setConfidence(retrieveList.get(i).getConfidence());

                    Marker l3 = mMap.addMarker(new MarkerOptions().position(new LatLng(retrieveList.get(i).getLatitude(), retrieveList.get(i).getLongitude())).title(retrieveList.get(i).getKawasan()).snippet("Daerah : "+retrieveList.get(i).getKecamatan()+", "+retrieveList.get(i).getKabupaten()).icon(BitmapDescriptorFactory.fromResource(R.drawable.fireorange)));
                    l3.setTag(h);
                    l3.showInfoWindow();
                    break;
                case 3:
                    h.setConfidence(retrieveList.get(i).getConfidence());

                    Marker l4 = mMap.addMarker(new MarkerOptions().position(new LatLng(retrieveList.get(i).getLatitude(), retrieveList.get(i).getLongitude())).title(retrieveList.get(i).getKawasan()).snippet("Daerah : "+retrieveList.get(i).getKecamatan()+", "+retrieveList.get(i).getKabupaten()).icon(BitmapDescriptorFactory.fromResource(R.drawable.firered)));
                    l4.setTag(h);
                    l4.showInfoWindow();
                    break;
            }
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(riau));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }
}
