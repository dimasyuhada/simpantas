package com.example.admin.simpantas;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

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
    private int mId;

    private List<Hotspot> retrieveList = new ArrayList<>();
    private List<TvFrequent> retrieveFreq = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualisasi);
        latitude = 3.1;
        longitude = 115.8;

        Bundle bundle = getIntent().getExtras();
        mId = bundle.getInt("menuIdentifier");
        if(mId==1){
            retrieveFreq = (List<TvFrequent>) getIntent().getSerializableExtra("resultObjectFreq");
        }else{
            retrieveList = (List<Hotspot>) getIntent().getSerializableExtra("resultObject");
        }

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
        LatLng indonesia = new LatLng(latitude, longitude);

        if (mId==1){
            for (int i=0; i<retrieveFreq.size();i++){
                Marker l1 = mMap.addMarker(new MarkerOptions().position(new LatLng(retrieveFreq.get(i).getLatitude(), retrieveFreq.get(i).getLongitude())).title(retrieveFreq.get(i).getKecamatan()+","+retrieveFreq.get(i).getKabupaten()).snippet("Kode Tanggal Kejadian : "+retrieveFreq.get(i).getTanggal1()+", "+retrieveFreq.get(i).getTanggal2()).icon(BitmapDescriptorFactory.fromResource(R.drawable.fire)));
                l1.showInfoWindow();
            }
        }else{
            Hotspot h = new Hotspot();
            CustomInfoWindowGMap customInfoWindow = new CustomInfoWindowGMap(this);
            mMap.setInfoWindowAdapter(customInfoWindow);

            for (int i=0; i<retrieveList.size();i++){
                switch (retrieveList.get(i).getTemp())
                {
                    case 0:
                        h.setConfidence(retrieveList.get(i).getConfidence());
                        h.setTanggal(retrieveList.get(i).getTanggal());

                        Marker l1 = mMap.addMarker(new MarkerOptions().position(new LatLng(retrieveList.get(i).getLatitude(), retrieveList.get(i).getLongitude())).title(retrieveList.get(i).getProvinsi()).snippet("Daerah : "+retrieveList.get(i).getKecamatan()+", "+retrieveList.get(i).getKabupaten()).icon(BitmapDescriptorFactory.fromResource(R.drawable.fireyellow)));
                        l1.setTag(h);
                        l1.showInfoWindow();
                        break;
                    case 1:
                        h.setConfidence(retrieveList.get(i).getConfidence());
                        h.setTanggal(retrieveList.get(i).getTanggal());

                        Marker l2 = mMap.addMarker(new MarkerOptions().position(new LatLng(retrieveList.get(i).getLatitude(), retrieveList.get(i).getLongitude())).title(retrieveList.get(i).getProvinsi()).snippet("Daerah : "+retrieveList.get(i).getKecamatan()+", "+retrieveList.get(i).getKabupaten()).icon(BitmapDescriptorFactory.fromResource(R.drawable.fireorange)));
                        l2.setTag(h);
                        l2.showInfoWindow();
                        break;
                    case 2:
                        h.setConfidence(retrieveList.get(i).getConfidence());
                        h.setTanggal(retrieveList.get(i).getTanggal());

                        Marker l3 = mMap.addMarker(new MarkerOptions().position(new LatLng(retrieveList.get(i).getLatitude(), retrieveList.get(i).getLongitude())).title(retrieveList.get(i).getProvinsi()).snippet("Daerah : "+retrieveList.get(i).getKecamatan()+", "+retrieveList.get(i).getKabupaten()).icon(BitmapDescriptorFactory.fromResource(R.drawable.firered)));
                        l3.setTag(h);
                        l3.showInfoWindow();
                        break;
                    case 3:
                        h.setConfidence(retrieveList.get(i).getConfidence());
                        h.setTanggal(retrieveList.get(i).getTanggal());

                        Marker l4 = mMap.addMarker(new MarkerOptions().position(new LatLng(retrieveList.get(i).getLatitude(), retrieveList.get(i).getLongitude())).title(retrieveList.get(i).getProvinsi()).snippet("Daerah : "+retrieveList.get(i).getKecamatan()+", "+retrieveList.get(i).getKabupaten()).icon(BitmapDescriptorFactory.fromResource(R.drawable.firered)));
                        l4.setTag(h);
                        l4.showInfoWindow();
                        break;
                }
            }
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(indonesia));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(4), 2000, null);
    }
}
