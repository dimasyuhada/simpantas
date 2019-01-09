package com.example.admin.simpantas;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
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
////
//        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps").snippet("test").icon(BitmapDescriptorFactory.fromResource(R.drawable.fireblue));
//        MarkerOptions marker1 = new MarkerOptions().position(new LatLng(0.510, 101.511)).title("Hello Maps").snippet("test").icon(BitmapDescriptorFactory.fromResource(R.drawable.firered));
//
//        mMap.addMarker(marker);
//        mMap.addMarker(marker1);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(riau));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        for (int i=0; i<retrieveList.size();i++){
            switch (retrieveList.get(i).getTemp())
            {
                case 0:
                    mMap.addMarker(new MarkerOptions().position(new LatLng(retrieveList.get(i).getLatitude(), retrieveList.get(i).getLongitude())).title(retrieveList.get(i).getKawasan()).snippet(retrieveList.get(i).getKecamatan()).icon(BitmapDescriptorFactory.fromResource(R.drawable.fireblue)));
                    break;
                case 1:
                    mMap.addMarker(new MarkerOptions().position(new LatLng(retrieveList.get(i).getLatitude(), retrieveList.get(i).getLongitude())).title(retrieveList.get(i).getKawasan()).snippet(retrieveList.get(i).getKecamatan()).icon(BitmapDescriptorFactory.fromResource(R.drawable.fireyellow)));
                    break;
                case 2:
                    mMap.addMarker(new MarkerOptions().position(new LatLng(retrieveList.get(i).getLatitude(), retrieveList.get(i).getLongitude())).title(retrieveList.get(i).getKawasan()).snippet(retrieveList.get(i).getKecamatan()).icon(BitmapDescriptorFactory.fromResource(R.drawable.fireorange)));
                    break;
                case 3:
                    mMap.addMarker(new MarkerOptions().position(new LatLng(retrieveList.get(i).getLatitude(), retrieveList.get(i).getLongitude())).title(retrieveList.get(i).getKawasan()).snippet(retrieveList.get(i).getKecamatan()).icon(BitmapDescriptorFactory.fromResource(R.drawable.firered)));
                    break;
            }
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(riau));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

    }
}
